package com.jt.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.vo.CheckBox;
import com.jt.sys.entity.SysRole;
public interface SysRoleDao {
	List<CheckBox> findObjects();
	/**获取当前页数据*/
	List<SysRole> findPageObjects(
			@Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize,
			@Param("name") String name);//limit #{startIndex},#{pageSize};
	/**获取总记录数*/
	int getRowCount(@Param("name")String name);
	int deleteObject(Integer id);
	int insertObject(SysRole entity);
	SysRole findObjectById(Integer id);
	int updateObject(SysRole entity);
}
