package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuDao {
    /**
     * 判定关系表中菜单是否被角色使用
     * @param menuId
     * @return
     */
	int getMenuCount(Integer menuId);
	
	int insertObject(
			@Param("roleId")Integer roleId,
			@Param("menuIds")String[] menuIds);
	
	List<Integer> findMenuIdsByRoleId(
			Integer roleId);
	
	int deleteObjectByRoleId(Integer roleId);
	int deleteObjectByMenuId(Integer menuId);
	
	
}
