package com.elearning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elearning.entities.CommentGrammar;
import com.elearning.entities.Grammar;
import com.elearning.helper.ApiRes;
import com.elearning.repository.CommentGrammarRepository;

@Service
public class CommentGrammarService {
	@Autowired
	CommentGrammarRepository commentgrammarRepo;

	public ApiRes<Object> findByGrammar(Grammar baigrammar) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			List<CommentGrammar> lstCommentGrammars = commentgrammarRepo.findByGrammar(baigrammar);
			apiRes.setObject(lstCommentGrammars);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes save(CommentGrammar commentGrammar) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			commentgrammarRepo.save(commentGrammar);
			apiRes.setObject(true);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}
}
