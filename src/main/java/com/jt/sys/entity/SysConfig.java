
package com.jt.sys.entity;
import java.io.Serializable;
import com.jt.common.entity.BaseEntity;
public class SysConfig extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	/**参数名*/
	private String name;
	/**参数值*/
	private String value;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
