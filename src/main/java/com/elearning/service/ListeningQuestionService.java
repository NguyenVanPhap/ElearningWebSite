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

import com.elearning.entities.ListeningQuestion;
import com.elearning.entities.ReadingQuestion;
import com.elearning.helper.ApiRes;
import com.elearning.repository.ListeningQuestionRepository;

@Service
public class ListeningQuestionService {

	@Autowired
	private ListeningQuestionRepository listeningQuestionRepository;

	/*
	 * public Page<ListeningQuestion> findListCauHoiByBaiTapId(int page, int size,
	 * long ListeningExerciseId) { return
	 * listeningQuestionRepository.findById(ListeningExerciseId, PageRequest.of(page
	 * - 1, size)); }
	 */
	public ApiRes<Object> findByListeningExerciseId(int page, int size, long listeningExerciseId) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {

			Page<ListeningQuestion> pageListeningQuestion = listeningQuestionRepository
					.findByListeningExerciseId(listeningExerciseId, PageRequest.of(page - 1, size));
			apiRes.setObject(pageListeningQuestion);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}
	/*
	 * public List<ListeningQuestion> findListCauHoiByBaiTapId(long
	 * ListeningExerciseId) { return
	 * listeningQuestionRepository.findById(ListeningExerciseId); }
	 */

	public Optional<ListeningQuestion> findCauHoiById(long id) {
		return listeningQuestionRepository.findById(id);
	}

	@Transactional
	public ListeningQuestion saveListeningQuestion(ListeningQuestion listeningQuestion, HttpServletRequest request)
			throws IOException {
		ListeningQuestion ch = listeningQuestionRepository.save(listeningQuestion);
		if (ch.getPhotoData() != null) {
			String rootDirectory = request.getSession().getServletContext().getRealPath("/");
			Path path = Paths
					.get(rootDirectory + "/static/file/images/listening/listeningQuestionId=" + ch.getId() + ".png");
			Files.write(path, listeningQuestion.getPhotoData());
			listeningQuestion.setPhotoName("listeningQuestionId=" + ch.getId() + ".png");

		}
		return listeningQuestionRepository.save(listeningQuestion);
	}

	public void deleteListeningQuestion(long id) {
		listeningQuestionRepository.deleteById(id);
	}
}
