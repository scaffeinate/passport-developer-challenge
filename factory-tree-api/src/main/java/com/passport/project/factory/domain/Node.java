package com.passport.project.factory.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Node {

	@JsonProperty("text")
	private Integer value;

	public Node(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
