package com.elearning.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.elearning.entities.Lesson;

public interface LessonService {
	void save(Lesson baiLesson);

	List<Lesson> getLesson(int id);

	Page<Lesson> getLesson(int page, int limit);

	List<Lesson> getCourseLesson(int courseId);

	void delete(int id);

}
