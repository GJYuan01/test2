package com.jt.sys.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysRoleDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;
@Service
public class SysRoleServiceImpl implements SysRoleService{
    @Autowired
	private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Override
    public int updateObject(SysRole entity,String menuIds) {
    	//1.合法性验证
    	if(entity==null)
        throw new ServiceException("更新的对象不能为空");
    	if(entity.getId()==null)
    	throw new ServiceException("id的值不能为空");
    	if(StringUtils.isEmpty(entity.getName()))
    	throw new ServiceException("角色名不能为空");
    	if(StringUtils.isEmpty(menuIds))
    	throw new ServiceException("必须为角色指定一个权限");
    	//2.判定对象是否还存在
    	SysRole role=
    	sysRoleDao.findObjectById(entity.getId());
    	if(role==null)
        throw new ServiceException("对象可能已经不存在");
    	//3.更新数据
    	int rows;
    	try{
    	
    	rows=sysRoleDao.updateObject(entity);
    	sysRoleMenuDao.deleteObjectByRoleId(entity.getId());
    	sysRoleMenuDao.insertObject(entity.getId(),
    			menuIds.split(","));
    	
    	}catch(Exception e){
    	e.printStackTrace();
    	//系统报警，给运维人员发短信
    	throw new ServiceException("更新失败，服务端忙");
    	}
    	//4.返回结果
    	return rows;
    }
    
    @Override
    public Map<String,Object> findObjectById(Integer id) {
    	//1.合法性验证
    	if(id==null||id<=0)
    	throw new ServiceException("id的值不合法");
    	//2.执行查询
    	SysRole role=sysRoleDao.findObjectById(id);
    	List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleId(id);
    	//3.验证结果并返回
    	if(role==null)
    	throw new ServiceException("此记录已经不存在");
    	if(menuIds==null)
    	throw new ServiceException("角色与菜单记录不存在");
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("role", role);
    	map.put("menuIds", menuIds);
    	return map;
    }
    
    @Override
    public int saveObject(SysRole entity,String menuIds) {
    	//1.合法性验证
    	if(entity==null)
        throw new ServiceException("保存数据不能为空");
    	if(StringUtils.isEmpty(entity.getName()))
    	throw new ServiceException("角色名不能为空");
    	//2.保存数据
    	int rows;
    	try{
    	rows=sysRoleDao.insertObject(entity);
    	sysRoleMenuDao.insertObject(
    			entity.getId(),
    			menuIds.split(","));
    	}catch(Exception e){
    	//给系统维护人员发短信(出现问题了)
    	e.printStackTrace();
    	throw new ServiceException("保存失败,系统正维护");
    	}
    	//3.返回结果
    	return rows;
    }
	@Override
	public PageObject<SysRole> findPageObjects(
			Integer pageCurrent,String name) {
		//1.查询总记录数
		int rowCount=sysRoleDao.getRowCount(name);
		if(rowCount==0)
		throw new ServiceException("没有记录");
	    //2.查询当前页记录
		//2.1定义页面大小(每页最多现实多少条记录)
		int pageSize=3;
		//2.2计算当前页位置
		if(pageCurrent==null||pageCurrent<=0)
		throw new ServiceException("当前页码不合法");
		int startIndex=(pageCurrent-1)*pageSize;
		//2.3查询当前数据
		List<SysRole> list=
		sysRoleDao.findPageObjects(startIndex,pageSize,name);
		//3.封装数据
		PageObject<SysRole> pageObject
		=new PageObject<>();
		pageObject.setRecords(list);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		/*		
		如下这段代码提取到了PageObject类的getPageCount方法中了
		目的是实现代码复用
		int pageCount=rowCount/pageSize;
		if(rowCount%pageSize!=0)pageCount++;
		pageObject.setPageCount(pageCount);*/
		return pageObject;
	}
	@Override
	public int deleteObject(Integer id) {
		//1.参数合法性验证
		if(StringUtils.isEmpty(id))
		throw new ServiceException("必须选中才能删除");
		//2.调用数据层方法执行删除操作
		int rows=sysRoleDao.deleteObject(id);
		if(rows==0)
		throw new ServiceException("数据已经不存在");
		sysUserRoleDao.deleteObjectByRoleId(Integer.valueOf(id));
		//4.返回处理结果
		return rows;
	}
}
