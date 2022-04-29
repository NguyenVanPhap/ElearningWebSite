package com.elearning.service;

import java.util.*;

import org.springframework.data.domain.Page;

import com.elearning.entities.Grammar;
import com.elearning.entities.Vocabulary;

public interface VocabularyService {
	List<Vocabulary> findAll();

    void save(Vocabulary vocabulary);

    void delete(Integer id);

    Page<Vocabulary> getVocabulary(int page, int limit);

    Optional<Vocabulary> getVocabularyById(Integer id);

    List<Vocabulary> getVocabulary(int id);

    List<Vocabulary> searchListVocabulary(String search);
    
    //List<BaiTapTuVung> getAllBaiTapTuVung();

    Vocabulary getVocabId(int id);

}
