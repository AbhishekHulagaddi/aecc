package com.tierra.question.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tierra.question.domain.WeeklyTestQuestions;

public interface WeeklyTestQuestionRepo extends JpaRepository<WeeklyTestQuestions, UUID> {

	List<WeeklyTestQuestions> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDateTime start, LocalDateTime end);

}
