package com.jt.sys.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.Node;
import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;
/**负责菜单业务处理
 * @Transactional 注解可以应用在类上，也可以应用在方法上
 * 1)当应用在类上时，表示类中所有方法都采用默认事务处理策略
 * 2)当类上和方法上都有这个注解时，默认采用方法上定义的事务
 * 处理策略。
 * */
@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Override
	public int updateObject(SysMenu entity) {
		//1.合法验证
		if(entity==null)
		throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
		throw new ServiceException("菜单名不能为空");
		int rows;
		//2.更新数据
		try{
		rows=sysMenuDao.updateObject(entity);
		}catch(Exception e){
		e.printStackTrace();
		throw new ServiceException("更新失败");
		}
		//3.返回数据
		return rows;
	}
	@Override
	public int saveObject(SysMenu entity) {
		//1.合法验证
		if(entity==null)
		throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
		throw new ServiceException("菜单名不能为空");
		int rows;
		//2.保存数据
		try{
		rows=sysMenuDao.insertObject(entity);
		//假设还有其他操作，现在模拟其他SQL操作失败
		if(rows==1)throw new ServiceException("写入失败");
		}catch(Exception e){
		e.printStackTrace();
		throw new ServiceException("保存失败");
		}
		//3.返回数据
		return rows;
	}
	@Override
	public List<Node> findZTreeNodes() {
		// TODO Auto-generated method stub
		return sysMenuDao.findZTreeNodes();
	}
	@Override
	public List<Map<String, Object>> 
	    findObjects() {
		return sysMenuDao.findObjects();
	}
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		if(id==null||id<=0)
		throw new ServiceException("数据不合法,id="+id);
		Map<String,Object> map=sysMenuDao.findObjectById(id);
		if(map==null||map.size()==0)
		throw new ServiceException("此记录已经不存在");
		return map;
	}
	
	@Override
	public int deleteObject(Integer id) {
		//1.合法性验证
		if(id==null||id<=0)
		throw new ServiceException("数据不合法,id="+id);
		//2.执行删除操作
		//2.1判定此id对应的菜单是否有子元素
		int childCount=sysMenuDao.getChildCount(id);
		if(childCount>0)
		throw new ServiceException("此元素有子元素，不允许删除");
		//2.2执行删除操作
		int rows=sysMenuDao.deleteObject(id);
		//2.3判定此菜单角色关系数据
		sysRoleMenuDao.deleteObjectByMenuId(id);
		if(rows==0)
		throw new ServiceException("此菜单在数据库中可能已经不存在");
		return rows;
	}
}






