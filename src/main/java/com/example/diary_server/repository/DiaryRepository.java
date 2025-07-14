package com.example.diary_server.repository;

import com.example.diary_server.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
}