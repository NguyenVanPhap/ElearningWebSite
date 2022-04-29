package com.elearning.request;

import java.util.Date;

public class GrammarCommentReq {
    private Integer grammarId;

    private String content;

    private Date time;

	public Integer getGrammarId() {
		return grammarId;
	}

	public void setGrammarId(Integer grammarId) {
		this.grammarId = grammarId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
    
    
}
