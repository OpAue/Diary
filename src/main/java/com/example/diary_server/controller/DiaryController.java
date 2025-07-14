package com.example.diary_server.controller;

import com.example.diary_server.dto.DiaryRequest;
import com.example.diary_server.dto.DiaryResponse;
import com.example.diary_server.entity.Diary;
import com.example.diary_server.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
@Tag(name = "DiaryApI", description = "감정일기장 관리 API")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    @Operation(summary = "감정 일기장 추가" ,description = "Date, EmotionId, Content 추가하기" )
    public DiaryResponse createDiary(@RequestBody DiaryRequest diary){
        Diary saved = diaryService.saveDiary(diary);
        return DiaryResponse.fromEntity(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "감정 일기장 수정(Update)" ,description = "Date, EmotionId, Content 수정하기" )
    public DiaryResponse updateDiary(@PathVariable Long id, @RequestBody DiaryRequest request){
        Diary update = diaryService.update(id, request);
        return DiaryResponse.fromEntity(update);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "감정 일기장 삭제" ,description = "ID로 감정 일기장 삭제" )
    public Map<String, String> deleteDiary(@PathVariable Long id) {
        return diaryService.deleteDiary(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "감정 일기장 조회(ID)" ,description = "ID로 감정 일기장 조회" )
    public DiaryResponse getDiary(@PathVariable Long id){
        Diary diary = diaryService.findById(id);
        return DiaryResponse.fromEntity(diary);
    }

    @GetMapping
    @Operation(summary = "감정 일기장 조회(Month)" ,description = "월별로 감정 일기장 조회하기(2025-07)형식  으로" )
    public List<DiaryResponse> getDiariesByMonth (@RequestParam("month") YearMonth yearMonth){
        return diaryService.findByMonth(yearMonth);
    }
}
