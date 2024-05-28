package com.example.masterthesisbe.dto.discussion;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionDto {
    private int id;
    private String title;
    private String content;
}
