package com.example.diary_server.service;

import com.example.diary_server.dto.DiaryRequest;
import com.example.diary_server.dto.DiaryResponse;
import com.example.diary_server.entity.Diary;
import com.example.diary_server.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public Diary saveDiary(DiaryRequest request) {
        Diary diary = new Diary();
        diary.setEmotionId(request.getEmotionId());
        diary.setContent(request.getContent());
        diary.setCreatedDate(convertToTime(request.getCreatedDate()));
        diaryRepository.save(diary);
        return diary;
    }

    public Diary findById(long id) {
        return diaryRepository.findById(id).orElse(new Diary());
    }

    public Diary update(long id, DiaryRequest request) {
        Optional<Diary> target = diaryRepository.findById(id);
        if(target.isPresent()) {
            Diary diary =  target.get();
            diary.setContent(request.getContent());
            diary.setEmotionId(request.getEmotionId());
            diary.setCreatedDate(convertToTime(request.getCreatedDate()));
            diaryRepository.save(diary);
            return diary;
        } else throw new RuntimeException(id + "가 존재하지 않습니다.");
    }

    public Map<String, String> deleteDiary(Long id) {
        Map<String, String> result = new HashMap<>();

        if (diaryRepository.existsById(id)) {
            diaryRepository.deleteById(id);
            result.put("message", "삭제되었습니다");
        } else {
            result.put("message", "해당 ID의 일기를 찾을 수 없습니다");
        }

        return result;
    }

    public List<DiaryResponse> findByMonth(YearMonth yearMonth){
        List<DiaryResponse> result = new ArrayList<>();

        LocalDateTime start = yearMonth.atDay(1).atStartOfDay(); // 2025-07-01T00:00:00
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59); // 2025-07-31T23:59:59

        for(Diary item : diaryRepository.findByCreatedDateBetween(start, end)){
            DiaryResponse response = DiaryResponse.fromEntity(item);
            result.add(response);
        }
        return result;
    }

    private LocalDateTime convertToTime(long date){
        Instant instant = Instant.ofEpochMilli(date);
        LocalDateTime createdDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
        return createdDate;
    }
}
