package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.jt.sys.entity.SysConfig;

public interface SysConfigDao {
	
	int insertObject(SysConfig entity);
	int updateObject(SysConfig entity);
	int deleteObjects(@Param("ids") String[] ids);
	List<SysConfig> findPageObjects(
			@Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize,
			@Param("name") String name);//limit #{startIndex},#{pageSize};
	/**获取总记录数*/
	int getRowCount(@Param("name")String name);
}
