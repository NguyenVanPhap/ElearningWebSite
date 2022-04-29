package com.elearning.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comment_grammar")
public class CommentGrammar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "content")
	private String content;

	@Column(name = "time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
	private Date commentDate;

	private String userName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grammarid", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Grammar grammar;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Grammar getGrammar() {
		return grammar;
	}

	public void setGrammar(Grammar grammar) {
		this.grammar = grammar;
	}

	@Override
	public String toString() {
		return "{"+ "\"idcomment\":" + "\"" + id + "\"" + "\"cmtgrammarcontent\":" + "\"" + content + "\"" + "," + "\"date\":" + "\"" + commentDate + "\""
				+ "," + "\"nguoidung\":" + "\"" + userName + "\"" + "\"grammar\":" + "\"" + grammar + "\"" + "}";
	}

	public CommentGrammar() {

	}

	public CommentGrammar(Integer id, String content, Date commentDate, String userName, Grammar grammar) {
		super();
		this.id = id;
		this.content = content;
		this.commentDate = commentDate;
		this.userName = userName;
		this.grammar = grammar;
	}

	/*
	 * public CommentGrammar(Integer cmtgrammarid, String cmtgrammarcontent, Date
	 * ngaycomment, NguoiDung nguoidung, Grammar baigrammar) {
	 * 
	 * super(); this.id = cmtgrammarid; this.content = cmtgrammarcontent;
	 * this.ngaycomment = ngaycomment; this.nguoidung = nguoidung; this.baigrammar =
	 * baigrammar; }
	 */

}
