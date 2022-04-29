package com.elearning.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.elearning.entities.ListeningQuestion;

public interface ListeningQuestionRepository extends JpaRepository<ListeningQuestion, Long> {

	Page<ListeningQuestion> findByListeningExerciseId(long listeningExerciseId, Pageable pageable);

	List<ListeningQuestion> findByListeningExerciseId(long listeningExerciseId);
}
