
package com.elearning.api.admin;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elearning.entities.Course;
import com.elearning.entities.Grammar;
import com.elearning.service.CourseService;

@RestController

@RequestMapping("/api/admin/course")
public class CourceApi {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private CourseService courseService;

	@GetMapping("/loadCources")
	public List<String> showAllGrammar() {
		List<Course> list = courseService.getAllCourse();
		List<String> response = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			String json = "courseId===" + list.get(i).getCourseId() + "|@|" + "courseName===" + list.get(i).getCourseName()
					+ "|@|" + "targetuser===" + list.get(i).getTargetUser() + "|@|" + "content===" + list.get(i).getContent();
			response.add(json);
		}
		return response;
	}

	@RequestMapping(value = "/delete/{courseId}")
	public String deleteById(@PathVariable("courseId") int id) {
		courseService.delete(id);
		return "success";
	}

	@PostMapping(value = "/save")
	@ResponseBody
	public List<String> add(@RequestParam("courseName") String name, @RequestParam("targetUser") String targetUser,
			@RequestParam("content") String content) {
		List<String> response = new ArrayList<String>();
		Course objCourse = new Course();

		try {
			objCourse.setCourseName(name);
			objCourse.setTargetUser(targetUser);
			objCourse.setContent(content);
			courseService.save(objCourse);

		} catch (Exception e) {
			response.add(e.toString());
			System.out.println("ErrorAddGrammar:" + e);

		}
		return response;
	}

	@RequestMapping(value = "/infoCourse/{idCourse}")
	public String infoGrammarById(@PathVariable("idCourse") int id) {
		Course course = courseService.getCourse(id).get(0);

		String json = "courseId==" + course.getCourseId() + "|" + "courseName==" + course.getCourseName() + "|"
				+ "targetuser==" + course.getTargetUser() + "|" + "content==" + course.getContent();

		return json;
	}

	@PostMapping(value = "/update")
	@ResponseBody
	public List<String> updateBaiGrammar(@RequestParam("courseId") int id, @RequestParam("courseName") String name,
			@RequestParam("targetUser") String targetUser, @RequestParam("content") String content) {

		List<String> response = new ArrayList<String>();

		Course objcourse = courseService.getCourse(id).get(0);
		courseService.save(objcourse);
		try {
			objcourse.setCourseName(name);
			objcourse.setTargetUser(targetUser);
			objcourse.setContent(content);
			courseService.save(objcourse);

		} catch (Exception e) {
			response.add(e.toString());
			System.out.println("ErrorAddGrammar:" + e);

		}

		return response;
	}
}
