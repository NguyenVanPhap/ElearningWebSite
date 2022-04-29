package com.elearning.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "courseid", nullable = false)
	private Integer courseid;

	@Column(name = "coursename")
	private String coursename;

	@Column(name = "targetuser")
	private String targetuser;

	@Column(name = "content")
	private String content;
	
	
	
	public Integer getCourseId() {
		return courseid;
	}

	public void setCourseId(Integer courseid) {
		this.courseid = courseid;
	}

	public String getCourseName() {
		return coursename;
	}

	public void setCourseName(String coursename) {
		this.coursename = coursename;
	}

	public Course() {

	}

	public String getTargetUser() {
		return targetuser;
	}

	public void setTargetUser(String targetuser) {
		this.targetuser = targetuser;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Course(Integer courseid, String coursename, String targetuser) {
		super();
		this.courseid = courseid;
		this.coursename = coursename;
		this.targetuser = targetuser;
	}

}
