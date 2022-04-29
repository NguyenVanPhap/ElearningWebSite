package com.elearning.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.elearning.service.ListeningExerciseService;
import com.elearning.service.ListeningQuestionService;

@RestController
@RequestMapping("api/client/listening-exercise")
public class ListeningExerciseApiClient {

	@Autowired
	private ListeningExerciseService listeningExerciseService;

	@Autowired
	private ListeningQuestionService listeningQuestionService;

	@GetMapping("/id={listeningExerciseId}")
	public ResponseEntity<Object> getListListeningQuestionByListeningExerciseId(
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "4") int pagesize,
			@PathVariable long listeningExerciseId) {
		return ResponseEntity
				.ok(listeningQuestionService.findByListeningExerciseId(page, pagesize, listeningExerciseId));
	}

	@GetMapping("/getall")
	public ResponseEntity<Object> findAllByPartAndLevel(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "") String part, @RequestParam(defaultValue = "") String level) {
		return ResponseEntity
				.ok(listeningExerciseService.findAllListListeningExerciseByPartAndlevelAdmin(page, 4, part, level));
	}

}
