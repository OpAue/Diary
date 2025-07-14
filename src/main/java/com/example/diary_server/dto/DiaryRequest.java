package com.example.diary_server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryRequest {
    @NotBlank
    private long createdDate;

    @NotBlank
    private int emotionId;

    @NotBlank
    private String content;
}
