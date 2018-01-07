package com.logicq.mlm.model.profile;

import java.io.Serializable;
import java.util.List;

public class NetWorkDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8474476648409874805L;
	
	private String name;
	private String title;
	private String category;
	private String id;
	private String parentid;
	private List<NetWorkDetails> children;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public List<NetWorkDetails> getChildren() {
		return children;
	}
	public void setChildren(List<NetWorkDetails> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "NetWorkDetails [name=" + name + ", title=" + title + ", category=" + category + ", id=" + id
				+ ", parentid=" + parentid + ", children=" + children + "]";
	}
	
	

}
