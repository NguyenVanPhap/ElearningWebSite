package com.elearning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elearning.entities.Vocabulary;
import com.elearning.entities.VocabularyContent;
import com.elearning.repository.VocabularyDetailRepository;
import com.elearning.service.VocabularyDetailService;

@Service
public class VocabularyDetailServiceImpl implements VocabularyDetailService {
	@Autowired
	VocabularyDetailRepository vocabularydetailrepository;

	@Override
	public void save(VocabularyContent vocabularycontent) {
		vocabularydetailrepository.save(vocabularycontent);
	}
	@Override
	public void delete(Integer id) {
        vocabularydetailrepository.deleteById(id);
    }
    @Override
    public List<VocabularyContent> getListVocabulary(Vocabulary vocabulary) {
        return vocabularydetailrepository.findByVocabulary(vocabulary);
    }
}
