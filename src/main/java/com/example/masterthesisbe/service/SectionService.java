package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.SectionConstants;
import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.dto.section.*;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.SectionMapper;
import com.example.masterthesisbe.model.Section;
import com.example.masterthesisbe.model.SectionContent;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.User;
import com.example.masterthesisbe.repository.SectionContentRepository;
import com.example.masterthesisbe.repository.SectionRepository;
import com.example.masterthesisbe.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepository;
    private final SectionContentRepository sectionContentRepository;
    private final SectionMapper sectionMapper;
    private final StoryRepository storyRepository;
    private final AuthenticationService authService;

    public Page<SectionResponseDto> getSectionsPaginate(int storyId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Section> sectionsPagination = this.sectionRepository.findAllByStoryId(storyId, pageable);

        List<SectionResponseDto> content = sectionsPagination.getContent().stream()
                .map(sectionMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, sectionsPagination.getPageable(), sectionsPagination.getTotalElements());
    }

    public List<SectionResponseDto> getAllSectionsOfStory(int storyId) {
        return this.sectionRepository.findAllByStoryIdOrderByDisplayOrder(storyId).stream()
                .map(sectionMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public SectionContentResponseDto getSectionContentById(Integer sectionId) {
        SectionContent sectionContent = this.sectionContentRepository.findFirstBySectionId(sectionId)
                .orElseThrow(() -> new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE));
        return new SectionContentResponseDto(sectionContent.getId(), sectionContent.getContent());
    }

    public SectionResponseDto getLastModifiedSection(int storyId) {
        Section foundSection = sectionRepository.findFirstByStoryIdOrderByLastModifiedDateDesc(storyId);
        if (foundSection == null) {
            return null;
        }

        return sectionMapper.convertToResponseDto(foundSection);
    }

    public SectionResponseDto createNewSection(CreateSectionRequestDto section, int storyId) {
        Section convertedSection = sectionMapper.convertFromCreateRequestDto(section);
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException(StoryConstants.STORY_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if (loggedInUser.getId() != story.getAuthor().getUser().getId()) {
            throw new ApiException(SectionConstants.NO_PERMISSIONS_TO_MODIFY);
        }
        convertedSection.setStory(story);

        int totalCountOfSection = sectionRepository.countAllByStoryId(storyId);
        convertedSection.setDisplayOrder(totalCountOfSection + 1);

        Section newSection = sectionRepository.save(convertedSection);

        SectionContent newSectionContent = new SectionContent("");
        newSectionContent.setSection(newSection);
        sectionContentRepository.save(newSectionContent);

        return sectionMapper.convertToResponseDto(newSection);
    }

    public SectionResponseDto updateSection(Integer sectionId, UpdateSectionRequestDto updatedSection) {
        Section section = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if (loggedInUser.getId() != section.getStory().getAuthor().getUser().getId()) {
            throw new ApiException(SectionConstants.NO_PERMISSIONS_TO_MODIFY);
        }

        sectionMapper.updateSectionWithDto(section, updatedSection);
        return this.sectionMapper.convertToResponseDto(sectionRepository.save(section));
    }

    public void updateSectionContent(Integer sectionId, UpdateSectionContentRequestDto updatedSection) {
        Section section = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if (loggedInUser.getId() != section.getStory().getAuthor().getUser().getId()) {
            throw new ApiException(SectionConstants.NO_PERMISSIONS_TO_MODIFY);
        }

        Optional<SectionContent> sectionContent = this.sectionContentRepository.findFirstBySectionId(sectionId);
        if (sectionContent.isEmpty()) {
            SectionContent newSectionContent = new SectionContent(updatedSection.getContent());
            newSectionContent.setSection(section);
            sectionContentRepository.save(newSectionContent);
        } else {
            SectionContent editedSection = sectionContent.get();
            editedSection.setContent(updatedSection.getContent());
            sectionContentRepository.save(editedSection);
        }
    }

    public void deleteSection(Integer sectionId) {
        boolean exists = this.sectionRepository.existsById(sectionId);

        if(!exists) {
            throw new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE);
        }
        Optional<SectionContent> sectionContent = this.sectionContentRepository.findFirstBySectionId(sectionId);
        sectionContent.ifPresent(content -> this.sectionContentRepository.deleteById(content.getId()));

        this.sectionRepository.deleteById(sectionId);
    }
}
