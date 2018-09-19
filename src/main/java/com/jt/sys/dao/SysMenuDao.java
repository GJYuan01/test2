package com.jt.sys.dao;
import java.util.List;
import java.util.Map;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;
public interface SysMenuDao {
	int updateObject(SysMenu entity);
	int insertObject(SysMenu entity);
	List<Node> findZTreeNodes();
	/**获取菜单信息及对应的上级菜单的名称
	 * Map<String,Object>：一行记录对应一个map
	 * key:为字段名
	 * value:为字段对应值
	 * 多行记录对应多个map，多个map存储到list集合
	 * */
	List<Map<String,Object>> findObjects();
	/**
	 * 根据id查找当前菜单以及上级菜单相关信息
	 * @param id
	 * @return
	 */
	Map<String,Object> findObjectById(Integer id);
	/**
	 * 查询当前菜单对应的子菜单个数
	 * @return int
	 */
	int getChildCount(Integer id);
	/**删除菜单元素定义*/
	int deleteObject(Integer id);

}
