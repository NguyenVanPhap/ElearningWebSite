
package com.elearning.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import com.elearning.entities.QReadingExercise;
import com.elearning.entities.ReadingExercise;
import com.elearning.entities.ReadingQuestion;
import com.elearning.helper.ApiRes;
import com.elearning.repository.ReadingQuestionRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class ReadingQuestionService {

	@Autowired
	private ReadingQuestionRepository readingQuestionRepo;

	public ApiRes<Object> findByReadingExerciseId(int page, int size, long readingExerciseId) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {

			Page<ReadingQuestion> pageReadingQuestion = readingQuestionRepo.findByReadingExerciseId(readingExerciseId,
					PageRequest.of(page - 1, size));
			apiRes.setObject(pageReadingQuestion);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public List<ReadingQuestion> findCauHoiById(long id) {
		return readingQuestionRepo.findById(id);
	}

	@Transactional
	public ReadingQuestion saveReadingQuestion(ReadingQuestion ReadingQuestion, HttpServletRequest request)
			throws IOException {
		return readingQuestionRepo.save(ReadingQuestion);
	}

	public void deleteReadingQuestion(long id) {
		readingQuestionRepo.deleteById(id);
	}

}
