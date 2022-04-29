package com.elearning.request;

import java.util.Date;

public class VocabCommentReq {
    
    private Integer vocabularyId;

    private String contentVocabulary;

    private Date time;

    public Integer getVocabularyId() {
		return vocabularyId;
	}

	public void setVocabularyId(Integer vocabularyId) {
		this.vocabularyId = vocabularyId;
	}

	public String getContentVocabulary() {
		return contentVocabulary;
	}

	public void setContentVocabulary(String contentVocabulary) {
		this.contentVocabulary = contentVocabulary;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
