package com.elearning.service;

import java.util.List;

import com.elearning.entities.Vocabulary;
import com.elearning.entities.VocabularyContent;

public interface VocabularyDetailService {
	void save(VocabularyContent vocabularycontent);

    void delete(Integer id);

    List<VocabularyContent> getListVocabulary(Vocabulary vocabulary);
}
