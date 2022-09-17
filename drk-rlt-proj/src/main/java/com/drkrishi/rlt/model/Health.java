package com.drkrishi.rlt.model;

import java.util.List;

public class Health {
	private String left;
	private String front;
	private String right;
	private List<Question> questions;
	
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getFront() {
		return front;
	}
	public void setFront(String front) {
		this.front = front;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
}
