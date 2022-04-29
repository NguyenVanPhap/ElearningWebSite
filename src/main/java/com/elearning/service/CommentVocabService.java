package com.elearning.service;

import java.util.List;

import com.elearning.entities.CommentVocabulary;
import com.elearning.entities.Vocabulary;
import com.elearning.helper.ApiRes;
import com.elearning.repository.CommentVocabularyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentVocabService {
    @Autowired
    CommentVocabularyRepository commentvocabRepo;

    public ApiRes<Object> findByVocabulary(Vocabulary vocabulary){
        ApiRes<Object> apiRes = new ApiRes<Object>();
        try{
            List<CommentVocabulary> lstCommentVocabs = commentvocabRepo.findByVocabulary(vocabulary);
            apiRes.setObject(lstCommentVocabs);
        }catch(Exception ex){
            apiRes.setError(true);
            apiRes.setErrorReason(ex.getMessage());
        }
        return apiRes;
    }

    public ApiRes save(CommentVocabulary commentVocabulary){
        ApiRes<Object> apiRes = new ApiRes<Object>();
        try{
            commentvocabRepo.save(commentVocabulary);
            apiRes.setObject(true);
        }catch(Exception ex){
            apiRes.setError(true);
            apiRes.setErrorReason(ex.getMessage());
        }
        return apiRes;
    }
}
