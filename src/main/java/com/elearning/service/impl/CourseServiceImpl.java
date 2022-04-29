
package com.elearning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.elearning.entities.Course;
import com.elearning.repository.CourseRepository;
import com.elearning.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepo;

	@Override
	public void save(Course course) {
		courseRepo.save(course);
	}

	@Override
	public List<Course> getCourse(int id) {
		return courseRepo.findByCourseid(id);
	}

	@Override
	public Page<Course> getCourse(int page, int size) {
		return courseRepo.findAll(PageRequest.of(page, size));

	}

	@Override
	public List<Course> getAllCourse() {
		return courseRepo.findAll();
	}

	@Override
	public void delete(int id) {
		courseRepo.deleteById(id);
	}

	/*
	 * @Override public List<KhoaHoc> searchListKhoaHoc(String search) { return
	 * khoaHocRepo.searchGrammar(search);
	 * 
	 * }
	 */

}
