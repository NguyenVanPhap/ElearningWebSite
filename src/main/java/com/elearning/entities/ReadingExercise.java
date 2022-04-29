package com.elearning.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reading_exercise")
public class ReadingExercise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private int level;

	@Transient
	@JsonIgnore
	private MultipartFile image;

	private int part;

	@JsonIgnore
	@Transient
	private MultipartFile fileExcel;

	@Column(columnDefinition = "TEXT")
	private String script;

	@OneToMany(mappedBy = "readingExercise", cascade = CascadeType.ALL)
	@JsonBackReference
	List<ReadingQuestion> readingQuestions;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public MultipartFile getFileExcel() {
		return fileExcel;
	}

	public void setFileExcel(MultipartFile fileExcel) {
		this.fileExcel = fileExcel;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public List<ReadingQuestion> getReadingQuestions() {
		return readingQuestions;
	}

	public void setReadingQuestions(List<ReadingQuestion> readingQuestions) {
		this.readingQuestions = readingQuestions;
	}

	@Override
	public String toString() {
		return "ReadingExercise name=" + name;
	}

}
