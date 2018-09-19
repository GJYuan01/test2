package com.jt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.common.annotation.RequestLog;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.dao.SysRoleDao;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@RequestLog("登陆操作")
	@Override
	public void login(String username,String password) {
		System.out.println(sysRoleDao.getClass().getName());
		System.out.println("service.login");
		//0.参数合法性验证
		if(StringUtils.isEmpty(username))
		throw new ServiceException("用户名不能为空");
		if(StringUtils.isEmpty(password))
		throw new ServiceException("密码不能为空");
		//1.获取Subject(主体)对象
		Subject subject=SecurityUtils.getSubject();
		//2.封装用户名和密码
		UsernamePasswordToken token=
		new UsernamePasswordToken(
		username, password);
	    //3.执行身份认证
		try {
		subject.login(token);
		//此请求会提交给SecurityManager
		//SecurityManager会调用认证处理器Authenticator
		//认证处理器会去访问相关Realm对象获取认证信息
		} catch (AuthenticationException e) {
		e.printStackTrace();
		throw new ServiceException("用户名或密码不正确");
		}
		//4.记录用户信息
		Session session=
		SecurityUtils.getSubject().getSession();
	    session.setAttribute("user", username);
	}
	
	
	@Override
	public void updateObject(SysUser entity,String roleIds) {
        
		//1.对数据进行合法验证
		if (entity == null)
		throw new ServiceException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getUsername()))
		throw new ServiceException("用户名不能为空");
		if (StringUtils.isEmpty(roleIds))
		throw new ServiceException("必须要选择一个角色");
		//2.更新数据
		//2.1设置新密码
		System.out.println("entity.getPassword()="+entity.getPassword());
		if(!StringUtils.isEmpty(entity.getPassword())){
		String pwd=entity.getPassword();
		String salt=UUID.randomUUID().toString();
		SimpleHash sHash=//Shiro中的一个类
		new SimpleHash("MD5",pwd,salt);
		String newPwd=sHash.toString();
		entity.setSalt(salt);
		entity.setPassword(newPwd);
		}
		//2.2更新数据
		try{
		sysUserDao.updateObject(entity);
		sysUserRoleDao.deleteObjectByUserId(entity.getId());
		sysUserRoleDao.insertObject(
			entity.getId(), roleIds.split(","));
		}catch(Exception e){
		e.printStackTrace();
		throw new ServiceException("系统维护中");
		}
	}
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.合法性验证
		if(id==null||id<1)
		throw new ServiceException("数据不合法,id="+id);
		//2.查询用户信息
		Map<String,Object> user=sysUserDao.findObjectById(id);
		System.out.println("user="+user);
		if(user==null)
		throw new ServiceException("用户已经不存在");
		//3.查询用户角色
		List<Integer> roleIds=
		sysUserRoleDao.findRoleIdsByUserId(id);
		//4.封装数据
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	
	@Override
	public int saveObject(SysUser entity, 
			String roleIds) {
		//1.对数据进行合法验证
		if(entity==null)
		throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
		throw new ServiceException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
		throw new ServiceException("用户密码不能为空");
		if(StringUtils.isEmpty(roleIds))
		throw new ServiceException("必须要选择一个角色");
		//2.保存用户数据
		//2.1对密码进行加密(后续会采用md5加密算法)
		String salt=UUID.randomUUID().toString();
		String pwd=entity.getPassword();
		SimpleHash sHash=//Shiro中的一个类
		new SimpleHash("MD5",pwd, salt);
		String newPwd=sHash.toString();
		entity.setSalt(salt);
		entity.setPassword(newPwd);
		//2.2存储用户信息
		int rows;
		try{
		
		rows=sysUserDao.insertObject(entity);
		}catch(Exception e){
		e.printStackTrace();
		//报警
		 if(e instanceof DuplicateKeyException){
		 throw new ServiceException("此用户已存在");
		 }
		 throw new ServiceException("系统维护中");
		}
		//3.保存用户和角色的关系数据
		try{
		sysUserRoleDao.insertObject(
				entity.getId(),
				roleIds.split(","));
		}catch(Exception e){
		e.printStackTrace();
		 //报警
		throw new ServiceException("系统维护中");
		}
		//4.返回结果
		return rows;
	}
	
	@Override
	public List<CheckBox> findRoles() {
		return sysRoleDao.findObjects();
	}
	//Subject.isPermitted("sys:user:valid")
	//当访问此方法时,假如有@RequiresPermissions
	//这个注解,表示此用户必须是授权用户,才能访问
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer id, 
			Integer valid) {
		//1.合法性验证
		if(id==null||id<1)
		throw new ServiceException("数据不合法,id="+id);
		if(valid==null)
		throw new ServiceException("状态值不能为空");
		if(valid!=0&&valid!=1)
		throw new ServiceException("状态值不正确,valid="+valid);
		//2.执行更新操作
		int rows;
		try{
	    rows=sysUserDao.validById(id, valid);
		}catch(Throwable e){
		e.printStackTrace();
	    //报警
		throw new ServiceException("系统维护中");
		}
		//3.验证结果并处理
		if(rows==0)
		throw new ServiceException("数据可能已经不存在");
		return rows;
	}
	
	@Override
	public PageObject<Map<String,Object>> findPageObjects(
			Integer pageCurrent, 
			String username) {
		//1.查询总记录数
		int rowCount=
		sysUserDao.getRowCount(username);
		//2.查询当前页记录
		int pageSize=3;
		if(pageCurrent==null||pageCurrent<1)
		throw new ServiceException("当前页码不正确");
		int startIndex=(pageCurrent-1)*pageSize;
		List<Map<String,Object>> list=sysUserDao.findPageObjects(
				startIndex,
				pageSize, username);
		//3.封装数据
		PageObject<Map<String,Object>> pageObject=new PageObject<>();
		pageObject.setRecords(list);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCurrent(pageCurrent);
		//4.返回数据
		return pageObject;
	}
}
