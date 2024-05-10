package com.example.masterthesisbe.service;

import com.example.masterthesisbe.constants.SectionConstants;
import com.example.masterthesisbe.constants.StoryConstants;
import com.example.masterthesisbe.dto.section.*;
import com.example.masterthesisbe.exception.ApiException;
import com.example.masterthesisbe.helpers.mappers.SectionMapper;
import com.example.masterthesisbe.model.Section;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.User;
import com.example.masterthesisbe.repository.SectionRepository;
import com.example.masterthesisbe.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepository;
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

    public SectionContentResponseDto getSectionContentById(Integer sectionId) {
        Section foundSection = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE));
        return sectionMapper.convertToContentResponseDto(foundSection);
    }

    public SectionResponseDto createNewSection(CreateSectionRequestDto section, int storyId) {
        Section convertedSection = sectionMapper.convertFromCreateRequestDto(section);

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ApiException(StoryConstants.STORY_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if (loggedInUser.getId() != story.getAuthor().getId()) {
            throw new ApiException(SectionConstants.NO_PERMISSIONS_TO_MODIFY);
        }
        convertedSection.setStory(story);

        Section newSection = sectionRepository.save(convertedSection);
        return sectionMapper.convertToResponseDto(newSection);
    }

    public SectionResponseDto updateSection(Integer sectionId, UpdateSectionRequestDto updatedSection) {
        Section section = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if (loggedInUser.getId() != section.getStory().getAuthor().getId()) {
            throw new ApiException(SectionConstants.NO_PERMISSIONS_TO_MODIFY);
        }

        sectionMapper.updateSectionWithDto(section, updatedSection);
        return this.sectionMapper.convertToResponseDto(sectionRepository.save(section));
    }

    public void updateSectionContent(Integer sectionId, UpdateSectionContentRequestDto updatedSection) {
        Section section = this.sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE));

        User loggedInUser = authService.getLoggedInUser();
        if (loggedInUser.getId() != section.getStory().getAuthor().getId()) {
            throw new ApiException(SectionConstants.NO_PERMISSIONS_TO_MODIFY);
        }

        section.setContent(updatedSection.getContent());
    }

    public void deleteSection(Integer sectionId) {
        boolean exists = this.sectionRepository.existsById(sectionId);

        if(!exists) {
            throw new ApiException(SectionConstants.SECTION_NOT_FOUND_MESSAGE);
        }

        this.sectionRepository.deleteById(sectionId);
    }
}
