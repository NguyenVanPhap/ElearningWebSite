package com.elearning.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ListeningExercise {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;

	private int level; // 1: dễ, 2:trung bình, 3: khó

	@Transient
	@JsonIgnore
	private MultipartFile photo; // ảnh bài nghe nếu có

	private int part; // phần 1,2,3,4 ?

	@JsonIgnore
	@Transient
	private MultipartFile audio; // audio bài nghe

	@JsonIgnore
	@Transient
	private MultipartFile fileExcelQuestion;

	@Column(columnDefinition = "TEXT")
	private String script; // chi tiết bài nghe

	@OneToMany(mappedBy = "listeningExercise", cascade = CascadeType.ALL)
	@JsonBackReference
	List<ListeningQuestion> listListeningQuestion;

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

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public MultipartFile getAudio() {
		return audio;
	}

	public void setAudio(MultipartFile audio) {
		this.audio = audio;
	}

	public MultipartFile getFileExcelQuestion() {
		return fileExcelQuestion;
	}

	public void setFileExcelQuestion(MultipartFile fileExcelQuestion) {
		this.fileExcelQuestion = fileExcelQuestion;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public List<ListeningQuestion> getListListeningQuestion() {
		return listListeningQuestion;
	}

	public void setListListeningQuestion(List<ListeningQuestion> listListeningQuestion) {
		this.listListeningQuestion = listListeningQuestion;
	}

}
