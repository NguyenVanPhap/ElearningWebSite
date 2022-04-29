package com.elearning.repository;

import java.util.List;

import com.elearning.entities.CommentVocabulary;
import com.elearning.entities.Vocabulary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVocabularyRepository extends JpaRepository<CommentVocabulary, Integer> {
    List<CommentVocabulary> findByVocabulary(Vocabulary vocabulary);
}
