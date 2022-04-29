package com.elearning.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.elearning.entities.ReadingQuestion;
import com.elearning.service.ReadingExerciseService;
import com.elearning.service.ReadingQuestionService;

@RestController
@RequestMapping("api/client/reading-exercise")
public class ReadingExerciseApiClient {

	@Autowired
	private ReadingExerciseService readingExerciseService;

	@Autowired
	private ReadingQuestionService readingQuestionService;

	@GetMapping("/id={readingExerciseId}")
	public ResponseEntity<Object> getListreadingQuestionByReadingExerciseId(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "4") int pagesize, @PathVariable long readingExerciseId) {
		return ResponseEntity.ok(readingQuestionService.findByReadingExerciseId(page, pagesize, readingExerciseId));
	}

	@GetMapping("/getall")
	public ResponseEntity<Object> findAllByPartAndLevel(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "") String part, @RequestParam(defaultValue = "") String level) {
		return ResponseEntity
				.ok(readingExerciseService.findAllListReadingExerciseByPartAndLevelAdmin(page, 2, part, level));
	}

}
