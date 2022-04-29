package com.elearning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.elearning.entities.ReadingExercise;

public interface ReadingExerciseRepository
		extends JpaRepository<ReadingExercise, Long>, QuerydslPredicateExecutor<ReadingExercise> {
	Page<ReadingExercise> findByPartAndLevel(int part, int level, Pageable pageable);

}
