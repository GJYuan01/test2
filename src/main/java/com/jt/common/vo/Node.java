package com.jt.common.vo;

import java.io.Serializable;
/**借助此对象封装树节点信息*/
public class Node implements Serializable{
	private static final long serialVersionUID = -6577397050669133046L;
    private Integer id;
    private String name;
    private Integer parentId;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
}
