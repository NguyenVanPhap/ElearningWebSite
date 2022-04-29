package com.elearning.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.elearning.entities.ReadingQuestion;

public interface ReadingQuestionRepository extends JpaRepository<ReadingQuestion, Long> {

	Page<ReadingQuestion> findById(long id, Pageable pageable);

	Page<ReadingQuestion> findByReadingExerciseId(long readingExerciseId, Pageable pageable);

	List<ReadingQuestion> findById(long id);
}
