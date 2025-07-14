package com.example.diary_server.dto;

import com.example.diary_server.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@AllArgsConstructor
public class DiaryResponse {
    private int id;
    private long createdDate;
    private int emotionId;
    private String content;

    public static DiaryResponse fromEntity(Diary c) {
        return new DiaryResponse(
                c.getId(),
                convertToMs(c.getCreatedDate()),
                c.getEmotionId(),
                c.getContent()
        );
    }

    private static long convertToMs(LocalDateTime time) {
        Instant instant = time.atZone(ZoneId.of("Asia/Seoul")).toInstant();
        return instant.toEpochMilli();
    }
}
