package com.elearning.api.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elearning.entities.Grammar;
import com.elearning.entities.Lesson;
import com.elearning.service.LessonService;

@RestController
@RequestMapping("/api/admin/lesson")
public class LessonApi {

	@Autowired
	private LessonService lessonService;

	@RequestMapping(value = "/delete/{lessonId}")
	public String deleteById(@PathVariable("lessonId") int id) {
		lessonService.delete(id);
		return "success";
	}

	@GetMapping("/loadCourseLesson/{currentCourseId}")
	public List<String> courseLesson(@PathVariable("currentCourseId") int id) {

		List<Lesson> list = lessonService.getCourseLesson(id);

		List<String> response = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			String json = "lessonid==" + list.get(i).getLessonId() + "|||" + "courseid==" + list.get(i).getCourseId()
					+ "|||" + "lessonname==" + list.get(i).getLessonName() + "|||" + "videopath=="
					+ list.get(i).getVideoPath();

			response.add(json);
		}

		return response;

	}

	// get info Lesson ->edit Lesson
	@RequestMapping(value = "/infoLesson/{lessonId}")
	public String infoLessonById(@PathVariable("lessonId") int id) {
		Lesson lesson = lessonService.getLesson(id).get(0);

		String json = "lessonid==" + lesson.getLessonId() + "|||" + "courseid==" + lesson.getCourseId() + "|||"
				+ "lessonname==" + lesson.getLessonName() + "|||" + "videopath==" + lesson.getVideoPath();

		return json;
	}

	@PostMapping(value = "/save")
	@ResponseBody
	public List<String> add(@RequestParam("lessonName") String lessonName, @RequestParam("courseId") Integer courseId,
			@RequestParam("videoPath") String videoPath) {
		List<String> response = new ArrayList<String>();

		Lesson lesson = new Lesson();
		lessonService.save(lesson);
		try {
			lesson.setCourseId(courseId);
			lesson.setLessonName(lessonName);
			lesson.setVideoPath(videoPath);
			lessonService.save(lesson);
		} catch (Exception e) {
			response.add(e.toString());
			System.out.println("ErrorAddLesson==" + e);
		}
		return response;
	}

	@PostMapping(value = "/update")
	@ResponseBody
	public List<String> updatelesson(@RequestParam("lessonId") Integer lessonId,
			@RequestParam("lessonName") String lessonName, @RequestParam("videoPath") String videoPath) {

		List<String> response = new ArrayList<String>();
		Lesson lesson = lessonService.getLesson(lessonId).get(0);
		lessonService.save(lesson);
		try {
			lesson.setLessonName(lessonName);
			lesson.setVideoPath(videoPath);
			lessonService.save(lesson);
		} catch (Exception e) {
			response.add(e.toString());
			System.out.println("ErrorAddLesson==" + e);

		}
		return response;
	}

}
