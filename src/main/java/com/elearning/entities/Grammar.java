package com.elearning.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grammar")
public class Grammar {
	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grammarid", nullable = false)
	private Integer grammarid;

	@Column(name = "grammarname")
	private String grammarname;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "file_name")
	private String fileName;

	@Column(columnDefinition = "TEXT", name = "content_HTML")
	private String contentHTML;

	@Column(columnDefinition = "TEXT", name = "content_MarkDown")
	private String contentMarkDown;

	public Integer getGrammarId() {
		return grammarid;
	}

	public void setGrammarId(Integer grammarid) {
		this.grammarid = grammarid;
	}

	public String getGrammarName() {
		return grammarname;
	}

	public void setGrammarName(String grammarName) {
		this.grammarname = grammarName;
	}

	public String getContentHTML() {
		return contentHTML;
	}

	public void setContentHTML(String contentHTML) {
		this.contentHTML = contentHTML;
	}

	public String getContentMarkDown() {
		return contentMarkDown;
	}

	public void setContentMarkDown(String contentMarkDown) {
		this.contentMarkDown = contentMarkDown;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Grammar() {

	}

	public Grammar(Integer grammarid, String grammarname, String contentHTML, String contentMarkDown) {
		super();
		this.grammarid = grammarid;
		this.grammarname = grammarname;

		this.contentHTML = contentHTML;
		this.contentMarkDown = contentMarkDown;
	}

	public Grammar(Integer grammarid, String grammarname, String filePath, String fileName, String contentHTML,
			String contentMarkDown) {
		super();
		this.grammarid = grammarid;
		this.grammarname = grammarname;
		this.filePath = filePath;
		this.fileName = fileName;
		this.contentHTML = contentHTML;
		this.contentMarkDown = contentMarkDown;
	}

}
