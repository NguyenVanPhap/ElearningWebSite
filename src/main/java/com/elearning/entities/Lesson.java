package com.elearning.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lesson")
public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lessonid", nullable = false)
	private Integer lessonid;

	@Column(name = "courseid")
	private Integer courseid;

	@Column(name = "lessonname")
	private String lessonname;

	@Column(name = "videopath")
	private String videopath;

	public Integer getLessonId() {
		return lessonid;
	}

	public void setLessonId(Integer lessonid) {
		this.lessonid = lessonid;
	}

	public Integer getCourseId() {
		return courseid;
	}

	public void setCourseId(Integer courseid) {
		this.courseid = courseid;
	}

	public String getLessonName() {
		return lessonname;
	}

	public void setLessonName(String lessonname) {
		this.lessonname = lessonname;
	}

	public Lesson() {

	}

	public String getVideoPath() {
		return videopath;
	}

	public void setVideoPath(String videoPath) {
		this.videopath = videoPath;
	}

	public Lesson(Integer lessonid, String lessonname, String videopath) {
		super();
		this.lessonid = lessonid;
		this.lessonname = lessonname;
		this.videopath = videopath;
	}

}
