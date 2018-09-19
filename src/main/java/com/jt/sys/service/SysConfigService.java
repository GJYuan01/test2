package com.jt.sys.service;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysConfig;

public interface SysConfigService {

	 int saveObject(SysConfig entity);
	 int updateObject(SysConfig entity);
	 int deleteObjedcts(String ids);
	 PageObject<SysConfig> findPageObjects(
			 Integer pageCurrent,String name);
	 
	 
	   
}
