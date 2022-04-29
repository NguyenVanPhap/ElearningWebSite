package com.elearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elearning.entities.Vocabulary;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Integer> {

    List<Vocabulary> findByVocabularyid(int id);

    @Query("select vocab FROM Vocabulary vocab WHERE vocab.vocabularyname LIKE CONCAT('%',:search,'%')")
    List<Vocabulary> searchVocab(@Param("search") String search);
}
