package com.passport.project.factory.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Factory {

	private String id;
	private String text;
	private Integer lowerBound;
	private Integer upperBound;
	private Integer parentId = 0;
	private List<Node> nodes;
	private boolean rangeChanged = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Integer lowerBound) {
		this.lowerBound = lowerBound;
	}

	public Integer getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Integer upperBound) {
		this.upperBound = upperBound;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Integer> nodes) {
		this.nodes = new ArrayList<Node>();
		if (nodes != null) {
			for (Integer value : nodes) {
				this.nodes.add(new Node(value));
			}
		}
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public boolean isRangeChanged() {
		return rangeChanged;
	}

	public void setRangeChanged(boolean rangeChanged) {
		this.rangeChanged = rangeChanged;
	}
}
