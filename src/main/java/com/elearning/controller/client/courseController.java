package com.elearning.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elearning.entities.Grammar;
import com.elearning.entities.Lesson;
import com.elearning.entities.Course;
import com.elearning.service.CourseService;
import com.elearning.service.LessonService;

@Controller

@RequestMapping("/course")
public class courseController {

	@Autowired
	private LessonService lessonService;
	@Autowired
	private CourseService courseService;

	@GetMapping("")
	public String course(Model model) {

		List<Course> listkKhoaHocs = courseService.getAllCourse();
		model.addAttribute("listData", listkKhoaHocs);
		return "client/list-course";

	}

	@GetMapping("/detail")
	public String DetalVocab(@RequestParam int courseId, Model model) {

		List<Lesson> listLesson = lessonService.getCourseLesson(courseId);
		Course course = courseService.getCourse(courseId).get(0);
		model.addAttribute("course", course);
		model.addAttribute("listLesson", listLesson);
		model.addAttribute("CourseId", course.getCourseId());
		return "client/coursedetail";

	}

	@GetMapping("/learning")
	public String lesson(@RequestParam int courseId,@RequestParam int start, Model model) {
		List<Lesson> listLesson = lessonService.getCourseLesson(courseId);
		Course course = courseService.getCourse(courseId).get(0);
		model.addAttribute("course", course);
		model.addAttribute("listLesson", listLesson);
		model.addAttribute("CourseId", course.getCourseId());
		model.addAttribute("currentLessonId", start);
		return "client/learning";

	}
}
