package com.elearning.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.elearning.entities.Grammar;
import com.elearning.entities.Vocabulary;
import com.elearning.repository.VocabularyRepository;
import com.elearning.repository.GrammarRepository;
import com.elearning.service.VocabularyService;

@Service
public class VocabularyServicelmpl implements VocabularyService{
	@Autowired
	VocabularyRepository vocabularyRepository;

    @Override
    public List<Vocabulary> findAll() {
        return (List<Vocabulary>) vocabularyRepository.findAll();
    }

    @Override
    public void save(Vocabulary vocabulary) {
        vocabularyRepository.save(vocabulary);
    }

    @Override
    public void delete(Integer id) {
        vocabularyRepository.deleteById(id);
    }

    @Override
    public Page<Vocabulary> getVocabulary(int page, int size) {
        return vocabularyRepository.findAll(PageRequest.of(page, size));
        //return vocabularyRepository.f

    }

    @Override
    public Optional<Vocabulary> getVocabularyById(Integer id) {
        return vocabularyRepository.findById(id);
    }

    @Override
    public List<Vocabulary> getVocabulary(int id) {
        return vocabularyRepository.findByVocabularyid(id);
    }

    @Override
    public List<Vocabulary> searchListVocabulary(String search) {
        return vocabularyRepository.searchVocab(search);
    }

    @Override
    public Vocabulary getVocabId(int id){
        List<Vocabulary> lstVocabs = vocabularyRepository.findByVocabularyid(id);
        return lstVocabs.get(0);
    }
}
