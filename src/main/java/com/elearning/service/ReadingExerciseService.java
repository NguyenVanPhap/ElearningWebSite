
package com.elearning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elearning.entities.QReadingExercise;
import com.elearning.entities.ReadingExercise;
import com.elearning.entities.ReadingQuestion;
import com.elearning.helper.ApiRes;
import com.elearning.repository.ReadingExerciseRepository;
import com.elearning.util.ExcelUtilDoc;
import com.querydsl.core.BooleanBuilder;

@Service
public class ReadingExerciseService {

	@Autowired
	private ReadingExerciseRepository ReadingExerciseRepo;

	@Autowired
	private ReadingQuestionService readingQuestionService;

	@Autowired
	private ExcelUtilDoc excelUtilDoc;

	public ApiRes<Object> findAllListReadingExerciseByPartAndLevel(int page, int size, int part, int Level) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			Page<ReadingExercise> lstReadingExercisePage = ReadingExerciseRepo.findByPartAndLevel(part, Level,
					PageRequest.of(page - 1, size));
			apiRes.setObject(lstReadingExercisePage);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	};

	public ApiRes<Object> findAllListReadingExerciseByPartAndLevelAdmin(int page, int size, String part, String Level) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			BooleanBuilder builder = new BooleanBuilder();
			if (!part.equals("")) {
				builder.and(QReadingExercise.readingExercise.part.eq(Integer.parseInt(part)));
			}
			if (!Level.equals("")) {
				builder.and(QReadingExercise.readingExercise.level.eq(Integer.parseInt(Level)));
			}
			Page<ReadingExercise> lstReadingExercisePage = ReadingExerciseRepo.findAll(builder,
					PageRequest.of(page - 1, size));

			apiRes.setObject(lstReadingExercisePage);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public Optional<ReadingExercise> findReadingExerciseById(long id) {
		return ReadingExerciseRepo.findById(id);
	}

	@Transactional
	public ReadingExercise save(ReadingExercise ReadingExercise, HttpServletRequest request) {
		List<ReadingQuestion> listQuestion = new ArrayList<>();
		ReadingExercise currentBaiDoc = ReadingExerciseRepo.save(ReadingExercise);
		try {
			for (ReadingQuestion objQuestion : excelUtilDoc
					.getListQuestionFromFileExcel(ReadingExercise.getFileExcel().getInputStream())) {
				objQuestion.setReadingExercise(currentBaiDoc);
				listQuestion.add(readingQuestionService.saveReadingQuestion(objQuestion, request));
			} //
			currentBaiDoc.setReadingQuestions(listQuestion);
			return currentBaiDoc;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteReadingExercise(long id) {
		ReadingExerciseRepo.deleteById(id);
	}

}
