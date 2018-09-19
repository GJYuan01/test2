package com.jt.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysUser;

public interface SysUserDao {
	/**
	 * 根据用户名查找用户信息
	 * @param username
	 * @return
	 */
	SysUser findUserByUserName(String username);
	/**
	 * 根据用户id查找用户权限标识信息
	 * 例如：sys:role:view,sys:role:add
	 * @param userId
	 * @return
	 */
	List<String> findUserPermissions(String username);
	int updateObject(SysUser entity);
	/**
	 * 根据用户id查找用户对象
	 * @param id 为用户id
	 * @return
	 */
	Map<String,Object> findObjectById(Integer id);
	int insertObject(SysUser entity);
	int getRowCount(@Param("username")String username);
	List<Map<String,Object>> findPageObjects(
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize,
			@Param("username")String username);
	int validById(@Param("id")Integer id,
			      @Param("valid")Integer valid);
}
