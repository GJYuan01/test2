package com.jt.sys.entity;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jt.common.entity.BaseEntity;
import com.jt.common.util.DateFormatConverter;

public class SysRole extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 3098457856539501697L;
	public SysRole() {}
	private Integer id;
	private String name;
	private String note;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "SysRole [id=" + id + ", name=" + name + ", note=" + note + "]";
	}	
    
	
	
}
