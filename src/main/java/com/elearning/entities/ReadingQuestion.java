package com.elearning.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reading_question")
public class ReadingQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String number;
	@Column(columnDefinition = "TEXT")
	private String question;

	private String answer_1;
	private String answer_2;
	private String answer_3;
	private String answer_4;
	private String correct_answer;
	private String ansExplain;

	@Column(columnDefinition = "TEXT")
	private String paragraph;

	@ManyToOne
	@JoinColumn(name = "readingExerciseId")
	private ReadingExercise readingExercise;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer_1() {
		return answer_1;
	}

	public void setAnswer_1(String answer_1) {
		this.answer_1 = answer_1;
	}

	public String getAnswer_2() {
		return answer_2;
	}

	public void setAnswer_2(String answer_2) {
		this.answer_2 = answer_2;
	}

	public String getAnswer_3() {
		return answer_3;
	}

	public void setAnswer_3(String answer_3) {
		this.answer_3 = answer_3;
	}

	public String getAnswer_4() {
		return answer_4;
	}

	public void setAnswer_4(String answer_4) {
		this.answer_4 = answer_4;
	}

	public String getCorrect_answer() {
		return correct_answer;
	}

	public void setCorrect_answer(String correct_answer) {
		this.correct_answer = correct_answer;
	}

	public String getAnsExplain() {
		return ansExplain;
	}

	public void setAnsExplain(String ansExplain) {
		this.ansExplain = ansExplain;
	}

	public String getParagraph() {
		return paragraph;
	}

	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}

	public ReadingExercise getReadingExercise() {
		return readingExercise;
	}

	public void setReadingExercise(ReadingExercise readingExercise) {
		this.readingExercise = readingExercise;
	}

	@Override
	public String toString() {
		return "ReadingQuestion [id=" + id + ", number=" + number + ", question=" + question + ",  paragraph="
				+ paragraph + ", answer_1=" + answer_1 + ", answer_2=" + answer_2 + ", answer_3=" + answer_3
				+ ", answer_4=" + answer_4 + ", correct_answer=" + correct_answer + ", explain=" + ansExplain + "]";
	}

}
