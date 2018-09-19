package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleDao {
	List<Integer> findRoleIdsByUserId(Integer userId);
	
	int insertObject(
			@Param("userId")Integer userId,
			@Param("roleIds")String[]roleIds);
	
	int deleteObjectByRoleId(@Param("roleId")Integer roleId);
	int deleteObjectByUserId(@Param("userId")Integer userId);
}
