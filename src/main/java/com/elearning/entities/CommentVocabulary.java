package com.elearning.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "comment_tu_vung")
public class CommentVocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "contentvocab")
    private String contentVocabulary;

    @Column(name = "time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+7")
    private Date commentDateVocab;

    private String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vocabularyid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Vocabulary vocabulary;
    
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getContentVocabulary(){
        return contentVocabulary;
    }

    public void setContentVocabulary(String contentVocabulary){
        this.contentVocabulary = contentVocabulary;
    }

    public Date getCommentDateVocab() {
		return commentDateVocab;
	}

	public void setCommentDateVocab(Date commentDateVocab) {
		this.commentDateVocab = commentDateVocab;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(Vocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	@Override
	public String toString() {
		return "{"+ "\"idcomment\":" + "\"" + id + "\"" + "\"cmtgrammarcontent\":" + "\"" + contentVocabulary + "\"" + "," + "\"date\":" + "\"" + commentDateVocab + "\""
				+ "," + "\"nguoidung\":" + "\"" + userName + "\"" + "\"grammar\":" + "\"" + vocabulary + "\"" + "}";
	}

	public CommentVocabulary() {

	}

	public CommentVocabulary(Integer id, String contentVocabulary, Date commentDateVocab, String userName, Vocabulary vocabulary) {
		super();
		this.id = id;
		this.contentVocabulary = contentVocabulary;
		this.commentDateVocab = commentDateVocab;
		this.userName = userName;
		this.vocabulary = vocabulary;
	}
}
