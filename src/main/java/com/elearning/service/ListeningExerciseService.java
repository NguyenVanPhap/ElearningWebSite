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
import com.elearning.entities.ListeningExercise;
import com.elearning.util.ExcelUtil;
import com.elearning.entities.ListeningQuestion;
import com.elearning.entities.QListeningExercise;
import com.elearning.helper.ApiRes;
import com.elearning.repository.ListeningExerciseRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class ListeningExerciseService {

	@Autowired
	private ListeningExerciseRepository ListeningExerciseRepo;

	@Autowired
	private ListeningQuestionService listeningQuestionService;

	@Autowired
	private ExcelUtil excelUtil;

	public List<ListeningExercise> findAll() {
		return ListeningExerciseRepo.findAll();
	}

	public Page<ListeningExercise> findAllListListeningExerciseByPartAndlevel(int page, int size, int part, int level) {
		return ListeningExerciseRepo.findByPartAndLevel(part, level, PageRequest.of(page - 1, size));
	}

	public ApiRes<Object> findAllListListeningExerciseByPartAndlevelAdmin(int page, int size, String part,
			String level) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			BooleanBuilder builder = new BooleanBuilder();

			if (!part.equals("")) {
				builder.and(QListeningExercise.listeningExercise.part.eq(Integer.parseInt(part)));
			}

			if (!level.equals("")) {
				builder.and(QListeningExercise.listeningExercise.level.eq(Integer.parseInt(level)));
			}
			Page<ListeningExercise> lstListeningExercisePage = ListeningExerciseRepo.findAll(builder,
					PageRequest.of(page - 1, size));
			apiRes.setObject(lstListeningExercisePage);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;

	}

	public Optional<ListeningExercise> findListeningExerciseById(long id) {
		return ListeningExerciseRepo.findById(id);
	}

	@Transactional
	public ListeningExercise saveListeningExercise(ListeningExercise ListeningExercise, HttpServletRequest request) {
		List<ListeningQuestion> listCauHoi = new ArrayList<>();
		ListeningExercise currentListeningExercise = ListeningExerciseRepo.save(ListeningExercise);
		try {
			for (ListeningQuestion cauHoi : excelUtil
					.getListCauHoiFromFileExcel(ListeningExercise.getFileExcelQuestion().getInputStream())) {
				cauHoi.setListeningExercise(currentListeningExercise);
				listCauHoi.add(listeningQuestionService.saveListeningQuestion(cauHoi, request));
			}
			return currentListeningExercise;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteListeningExercise(long id) {
		ListeningExerciseRepo.deleteById(id);
	}
}
