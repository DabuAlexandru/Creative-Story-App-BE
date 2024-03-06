package com.example.masterthesisbe.helpers.mappers;

import com.example.masterthesisbe.dto.story.CreateStoryRequestDto;
import com.example.masterthesisbe.dto.story.StoryContentResponseDto;
import com.example.masterthesisbe.dto.story.StoryResponseDto;
import com.example.masterthesisbe.dto.story.UpdateStoryRequestDto;
import com.example.masterthesisbe.model.Story;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

import static java.util.Objects.isNull;

@Component
public class StoryMapper {
    public StoryResponseDto convertToResponseDto(Story story) {
        if (isNull(story)) {
            return null;
        }

        return new StoryResponseDto(
                story.getId(),
                story.getTitle(),
                story.getDescription(),
                story.getCreationDate(),
                story.getLastModifiedDate()
        );
    }

    public StoryContentResponseDto convertToContentResponseDto(Story story) {
        if (isNull(story)) {
            return null;
        }

        return new StoryContentResponseDto(
                story.getId(),
                story.getTitle(),
                story.getContent(),
                story.getCreationDate(),
                story.getLastModifiedDate()
        );
    }

    public Story convertFromCreateRequestDto(CreateStoryRequestDto newStory) {
        if (isNull(newStory)) {
            return null;
        }

        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());

        return new Story(
                newStory.getTitle(),
                newStory.getDescription(),
                now,
                now
        );
    }

    public void updateStoryWithDto(Story story, UpdateStoryRequestDto storyDto) {
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());

        story.setTitle(storyDto.getTitle());
        story.setDescription(storyDto.getDescription());
        story.setLastModifiedDate(now);
    }
}
