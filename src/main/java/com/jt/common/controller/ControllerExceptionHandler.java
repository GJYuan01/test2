package com.jt.common.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.JsonResult;
/**Spring MVC 基于注解方式实现统一异常处理方式
 * 规则
 * 1)controller内部-->父类-->@ControllerAdvice
 * */
@ControllerAdvice
public class ControllerExceptionHandler {
	  @ExceptionHandler(ServiceException.class)
	  @ResponseBody
	  public JsonResult handleServiceException(
			  RuntimeException e){
		  System.out.println("handleServiceException");
		  e.printStackTrace();
		  //封装错误信息
		  return new JsonResult(e);
	  }
	  @ExceptionHandler(AuthorizationException.class)
	  @ResponseBody
	  public JsonResult handleAuthorizationeException(
			  AuthorizationException e){
		  System.out.println("handleAuthorizationeException");
		  e.printStackTrace();
		  //封装错误信息
		  return new JsonResult(new ServiceException("没有此权限"));
	  }
}
