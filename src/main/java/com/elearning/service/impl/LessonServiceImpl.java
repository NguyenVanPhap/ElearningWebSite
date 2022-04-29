package com.elearning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.elearning.entities.Lesson;
import com.elearning.repository.LessonRepository;
import com.elearning.service.LessonService;

@Service
public class LessonServiceImpl implements LessonService {
	@Autowired
	LessonRepository lessonRepo;

	@Override
	public void save(Lesson lesson) {
		lessonRepo.save(lesson);
	}

	@Override
	public List<Lesson> getLesson(int id) {

		return lessonRepo.findByLessonid(id);
	}

	@Override
	public Page<Lesson> getLesson(int page, int size) {

		return lessonRepo.findAll(PageRequest.of(page, size));

	}

	@Override
	public List<Lesson> getCourseLesson(int courseId) {
		return lessonRepo.findByCourseid(courseId);
	}

	@Override
	public void delete(int id) {
		lessonRepo.deleteById(id);
	}

}
