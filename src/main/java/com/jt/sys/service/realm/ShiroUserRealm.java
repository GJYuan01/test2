package com.jt.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jt.sys.dao.SysUserDao;
import com.jt.sys.entity.SysUser;

/**
 * 借助此对象访问用户身份信息,权限信息并对
 * 其进行封装,返回给调用者.
 * 思考?
 * 1)此对象由谁来调用?(认证管理器,授权管理器)
 * @author adminitartor
 */

public class ShiroUserRealm extends AuthorizingRealm {
	
	@Autowired
	private SysUserDao sysUserDao;
	/***
	 * 完成授权信息的获取以及封装.
	 * 此方法何时调用?(执行授权检测时调用)
	 * 
	 */
	@Override
	protected AuthorizationInfo 
	doGetAuthorizationInfo(
			PrincipalCollection principals) {
		System.out.println("realm.doGetAuthorizationInfo");
		//1.获取登陆用户身份信息
		String username=
		(String)principals.getPrimaryPrincipal();
		//2.查找用户的权限信息
		List<String> list=//sys:user:update,sys:user:view,....,
		sysUserDao.findUserPermissions(username);
		System.out.println("list="+list);
		Set<String> permissions=new HashSet<>();
		for(String permission:list){
			if(!StringUtils.isEmpty(permission)){
				permissions.add(permission);
			}
		}
		System.out.println();
		System.out.println("set="+permissions);
		//3.对权限信息进行封装
		SimpleAuthorizationInfo info=
		new SimpleAuthorizationInfo();
		info.setStringPermissions(permissions);
		return info;
	}
	/**
	 * 完成认证信息的获取以及封装
	 * 此方法何时调用?(执行登陆认证时调用)
	 * @param  
	 * 用于接收用户身份以及凭证信息的对象(用户输入的)
	 * 
	 * @return AuthenticationInfo  
	 * 封装了认证信息的对象(从数据库查询到的)
	 * 
	 * client-->controller-->service-->realm
	 */
	@Override
	protected AuthenticationInfo 
	        doGetAuthenticationInfo(
			AuthenticationToken token) 
			throws AuthenticationException {
		System.out.println("realm.doGetAuthenticationInfo");
		//1.获取用户身份信息
		UsernamePasswordToken uToken=
		(UsernamePasswordToken)token;
		String username=uToken.getUsername();
		//2.基于用户身份查询数据库信息
		SysUser sysUser=
		sysUserDao.findUserByUserName(username);
		//3.对查询结果进行封装.
		//3.1获取用户salt值,并将其转换为一个字节源对象
		ByteSource byteSource=
		ByteSource.Util.bytes(sysUser.getSalt());
		//3.2对用户信息进行封装返回.
		AuthenticationInfo info=
		new SimpleAuthenticationInfo(
		    sysUser.getUsername(), //主身份
		    sysUser.getPassword(), //已加密的密码
		    byteSource,//salt对应的字节源对象
		    getName());//realm 的名字
		return info;
	}
	
}
