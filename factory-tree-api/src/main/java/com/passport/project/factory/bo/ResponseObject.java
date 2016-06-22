package com.passport.project.factory.bo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.passport.project.factory.domain.Factory;

@JsonInclude(Include.NON_NULL)
public class ResponseObject {

	@JsonProperty("text")
	private String rootText = "<b>Root</b>";

	private String backColor = "#019c70";

	private String color = "#fff";
	
	private String icon = "icon-cd";

	private boolean expanded = true;
	
	@JsonProperty("nodes")
	private List<Factory> factories;
	
	public ResponseObject(List<Factory> factories) {
		this.factories = factories;
	}

	public String getRootText() {
		return rootText;
	}

	public void setRootText(String rootText) {
		this.rootText = rootText;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public List<Factory> getFactories() {
		return factories;
	}

	public void setFactories(List<Factory> factories) {
		this.factories = factories;
	}
}
