package com.jt.sys.service;
import java.util.Map;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;
public interface SysRoleService {
	 /**
	  * 查询当前页数据
	  * @param pageCurrent (当前页码)
	  * @return
	  */
	 PageObject<SysRole> findPageObjects(
			 Integer pageCurrent,String name);

	 int deleteObject(Integer id);
	 int saveObject(SysRole entity,String menuIds);
	 
	 Map<String,Object> findObjectById(Integer id);
	 
	 int updateObject(SysRole entity,String menuIds);
	 
}
