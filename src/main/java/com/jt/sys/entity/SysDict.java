package com.jt.sys.entity;
import java.io.Serializable;
import com.jt.common.entity.BaseEntity;
public class SysDict extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -3395244574945754089L;
	private Integer id;
	private String name;
	private String type;
	private String code;
	private String value;
	private Integer sort;
	private Integer valid;
	private String note;
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "SysDict [id=" + id + ", name=" + name + ", type=" + type + ", code=" + code + ", value=" + value
				+ ", sort=" + sort + ", note=" + note + "]";
	}
	
    
}
