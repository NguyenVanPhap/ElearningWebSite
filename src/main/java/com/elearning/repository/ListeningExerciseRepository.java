package com.elearning.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.elearning.entities.ListeningExercise;

public interface ListeningExerciseRepository
		extends JpaRepository<ListeningExercise, Long>, QuerydslPredicateExecutor<ListeningExercise> {

	Page<ListeningExercise> findByPartAndLevel(int part, int level, Pageable pageable);
}
