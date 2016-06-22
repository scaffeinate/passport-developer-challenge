package com.passport.project.factory.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "factories")
@JsonInclude(Include.NON_NULL)
public class FactoryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2156819623993641350L;

	@Id
	private String id;

	@Field("name")
	private String factoryName;

	@Field("lowerBound")
	private Integer lowerBound;

	@Field("upperBound")
	private Integer upperBound;

	@Field("children")
	private List<Integer> children;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date createdAt;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
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

	public List<Integer> getChildren() {
		if (children != null) {
			return children;
		} else {
			return new ArrayList<Integer>();
		}

	}

	public void setChildren(List<Integer> children) {
		this.children = children;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
