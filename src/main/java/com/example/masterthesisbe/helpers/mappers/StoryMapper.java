package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.auth.UserDto;
import com.example.masterthesisbe.dto.story.CreateStoryRequestDto;
import com.example.masterthesisbe.dto.story.StoryContentResponseDto;
import com.example.masterthesisbe.dto.story.StoryResponseDto;
import com.example.masterthesisbe.dto.story.UpdateStoryRequestDto;
import com.example.masterthesisbe.model.Story;
import com.example.masterthesisbe.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class StoryMapper {
    private final UserMapper userMapper;

    public StoryResponseDto convertToResponseDto(Story story) {
        if (isNull(story)) {
            return null;
        }

        User author = story.getAuthor();
        UserDto convertedAuthor = userMapper.convertToResponseDto(author);

        return new StoryResponseDto(
                story.getId(),
                story.getTitle(),
                story.getDescription(),
                story.getCreatedOn(),
                story.getLastUpdatedOn(),
                convertedAuthor
        );
    }

    public StoryContentResponseDto convertToContentResponseDto(Story story) {
        if (isNull(story)) {
            return null;
        }

        User author = story.getAuthor();
        UserDto convertedAuthor = userMapper.convertToResponseDto(author);

        return new StoryContentResponseDto(
                story.getId(),
                story.getTitle(),
                story.getContent(),
                story.getCreatedOn(),
                story.getLastUpdatedOn(),
                convertedAuthor
        );
    }

    public Story convertFromCreateRequestDto(CreateStoryRequestDto newStory) {
        if (isNull(newStory)) {
            return null;
        }

        return new Story(
                newStory.getTitle(),
                newStory.getDescription()
//                newStory.getGenres(),
        );
    }

    public void updateStoryWithDto(Story story, UpdateStoryRequestDto storyDto) {
        story.setTitle(storyDto.getTitle());
        story.setDescription(storyDto.getDescription());
    }
}
