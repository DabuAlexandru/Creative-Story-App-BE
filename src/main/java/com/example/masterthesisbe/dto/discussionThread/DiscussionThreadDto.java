package com.example.masterthesisbe.dto.discussionThread;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionThreadDto {
    private int id;
    private String content;
    private Integer mainThreadId;
}
