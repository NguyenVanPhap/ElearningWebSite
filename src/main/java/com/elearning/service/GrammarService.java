package com.elearning.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.elearning.entities.Grammar;
import com.elearning.helper.ApiRes;

public interface GrammarService {
	void save(Grammar baigrammar);

	Grammar getInfor(int id);

	Page<Grammar> getGrammar(int page, int limit);

	ApiRes<Object> getGrammar4api(int page, int limit);

	ApiRes<Object> getGrammar4api(int page, int limit, String keywork);

	List<Grammar> getAllGrammar();

	void delete(int id);

	List<Grammar> searchListBaiGrammar(String search);

}
