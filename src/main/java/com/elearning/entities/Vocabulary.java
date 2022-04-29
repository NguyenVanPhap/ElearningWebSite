package com.elearning.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "vocabulary")
public class Vocabulary {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vocabularyid", nullable = false)
	private Integer vocabularyid;
	
	
	@Column(name = "vocabularyname")
	private String vocabularyname;
	
	@Column(name = "image")
	private String image;
	
	public Vocabulary() {}

	public Vocabulary(Integer vocabularyid, String vocabylaryname, String image) {
		super();
		this.vocabularyid = vocabularyid;
		this.vocabularyname = vocabularyname;
		this.image = image;
		
	}


	public Integer getVocabularyId() {
		return vocabularyid;
	}

	public void setVocabularyId(Integer vocabularyid) {
		this.vocabularyid = vocabularyid;
	}

	public String getVocabularyName() {
		return vocabularyname;
	}

	public void setVocabularyName(String vocabularyname) {
		this.vocabularyname = vocabularyname;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
