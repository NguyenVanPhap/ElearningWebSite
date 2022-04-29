package com.elearning.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elearning.entities.ListeningExercise;
import com.elearning.entities.ListeningQuestion;
import com.elearning.entities.ReadingExercise;
import com.elearning.entities.ReadingQuestion;
import com.elearning.repository.ListeningQuestionRepository;
import com.elearning.repository.ReadingQuestionRepository;
import com.elearning.service.ListeningExerciseService;
import com.elearning.service.ReadingExerciseService;
import com.elearning.service.ReadingQuestionService;

@Controller
@RequestMapping("/test/skill-test")
public class SkillTestController {

	@Autowired
	private ReadingQuestionRepository readingQuestionRepo;
	@Autowired
	private ReadingExerciseService ReadingExerciseService;

	@Autowired
	private ListeningQuestionRepository listeningQuestionRepo;
	@Autowired
	private ListeningExerciseService listeningExerciseService;

	@GetMapping("/reading/part-{partNumber}/{id}")
	public String baiDocPart(@PathVariable long id, @PathVariable int partNumber, Model model) {
		ReadingExercise objReadingExercise = ReadingExerciseService.findReadingExerciseById(id).get();
		Page<ReadingQuestion> pageReadingQuestion = readingQuestionRepo.findByReadingExerciseId(id,
				PageRequest.of(2 - 1, 2));
		pageReadingQuestion.getTotalElements();

		model.addAttribute("readingExercise", objReadingExercise);
		model.addAttribute("totalQuestion", pageReadingQuestion.getTotalElements());
		return "client/SkillTest/readingPart" + partNumber;
	}

	@GetMapping("/listening/part-{partNumber}/{id}")
	public String listeningExercisePart(@PathVariable long id, @PathVariable int partNumber, Model model) {
		ListeningExercise objListeningExercise = listeningExerciseService.findListeningExerciseById(id).get();
		Page<ListeningQuestion> pageListeningQuestion = listeningQuestionRepo.findByListeningExerciseId(id,
				PageRequest.of(2 - 1, 2));
		pageListeningQuestion.getTotalElements();

		model.addAttribute("listeningExercise", objListeningExercise);
		model.addAttribute("totalQuestion", pageListeningQuestion.getTotalElements());
		return "client/SkillTest/listeningPart" + partNumber;
	}

	@GetMapping("/reading")
	public String Reading(Model model) {
		return "client/SkillTest/reading";
	}

	@GetMapping("/listening")
	public String Listening(Model model) {
		return "client/SkillTest/listening";
	}

	@GetMapping("")
	public String SkillTest(Model model) {
		return "client/SkillTest/skill-test";
	}

}
