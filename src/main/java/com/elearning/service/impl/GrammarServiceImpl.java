package com.elearning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.elearning.entities.Grammar;
import com.elearning.helper.ApiRes;
import com.elearning.repository.GrammarRepository;
import com.elearning.service.GrammarService;

@Service
public class GrammarServiceImpl implements GrammarService {
	@Autowired
	GrammarRepository baigrammarRepo;

	@Override
	public void save(Grammar baigrammar) {
		baigrammarRepo.save(baigrammar);
	}

	@Override
	public Grammar getInfor(int id) {
		List<Grammar> lstGrammars = baigrammarRepo.findByGrammarid(id);
		return lstGrammars.get(0);
	}

	@Override
	public Page<Grammar> getGrammar(int page, int size) {

		return baigrammarRepo.findAll(PageRequest.of(page, size));

	}

	public ApiRes<Object> getGrammar4api(int page, int limit) {

		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			Page<Grammar> lstGrammar = baigrammarRepo.findAll(PageRequest.of(page, limit));
			apiRes.setObject(lstGrammar);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	@Override
	public ApiRes<Object> getGrammar4api(int page, int limit, String keyword) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			Page<Grammar> lstGrammar = baigrammarRepo.search4page(keyword, PageRequest.of(page, limit));
			apiRes.setObject(lstGrammar);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	@Override
	public List<Grammar> getAllGrammar() {
		return baigrammarRepo.findAll();
	}

	@Override
	public void delete(int id) {
		baigrammarRepo.deleteById(id);
	}

	@Override
	public List<Grammar> searchListBaiGrammar(String search) {
		return baigrammarRepo.searchGrammar(search);

	}

}
