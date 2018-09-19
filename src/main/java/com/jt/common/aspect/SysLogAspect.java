
package com.jt.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.annotation.RequestLog;
import com.jt.common.exception.ServiceException;
import com.jt.common.util.IPUtils;
import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;

@Aspect 
@Component
public class SysLogAspect {
	@Autowired
	private SysLogDao sysLogDao;
	@Pointcut("@annotation(com.jt.common.annotation.RequestLog)")
	public void logPointCut(){}
	
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		System.out.println("===SyslogAspect===");
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time);
		return result;
	}
	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Class<?> classTarget=joinPoint.getTarget().getClass();  
	    Class<?>[] par=((MethodSignature) joinPoint.getSignature()).getParameterTypes();  
	    Method targetMethod=null;
	    try {
			targetMethod=classTarget.getMethod(method.getName(), par);
		}catch(Exception e1) {
			e1.printStackTrace();
			throw new ServiceException(e1.getMessage());
		}
		SysLog sysLog = new SysLog();
		RequestLog requestLog = targetMethod.getAnnotation(RequestLog.class);
		System.out.println("requestLog="+requestLog);
		if(requestLog != null){
			//注解上的描述
			System.out.println("oper=="+requestLog.value());
			sysLog.setOperation(requestLog.value());
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			//String params = new Gson().toJson(args[0]);//Gson
			String params=new ObjectMapper().writeValueAsString(args[0]);//Jackson
			sysLog.setParams(params);
		}catch (Exception e){
            e.printStackTrace();
		}
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr());
		//用户名
		String username =(String)SecurityUtils.getSubject().getPrincipal();
		sysLog.setUsername(username);
		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		//保存系统日志
		sysLogDao.insertObject(sysLog);
	}
}
