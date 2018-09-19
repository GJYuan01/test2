package com.jt.sys.service;
import java.util.List;
import java.util.Map;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

public interface SysMenuService {
	int saveObject(SysMenu entity);
	int updateObject(SysMenu entity);
	
	List<Node> findZTreeNodes();
	List<Map<String,Object>> findObjects();
	
	Map<String,Object> findObjectById(Integer id);
	int deleteObject(Integer id);
}
