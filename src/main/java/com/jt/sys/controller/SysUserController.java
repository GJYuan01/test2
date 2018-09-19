package com.jt.sys.controller;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;

@RequestMapping("/user/")
@Controller
public class SysUserController {
	@Autowired
    private SysUserService sysUserService;
	@RequestMapping("listUI")
	public String listUI(){
		return "sys/user_list";
	}
	@RequestMapping("editUI")
	public String editUI(){
		return "sys/user_edit";
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysUser entity,String roleIds){
		sysUserService.updateObject(entity,
				roleIds);
		return new JsonResult();
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(
			Integer id){
		Map<String,Object> map=
		sysUserService.findObjectById(id);
		return new JsonResult(map);
	}
	
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(
			SysUser entity,String roleIds){
		sysUserService.saveObject(entity,
				roleIds);
		JsonResult r=new JsonResult();
		r.setMessage("save ok");
		return r;
	}
	
	@RequestMapping("doFindRoles")
	@ResponseBody
	public JsonResult doFindRoles(){
		return new JsonResult(
				sysUserService.findRoles());
	}
	
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(
		Integer id,Integer valid){
		sysUserService.validById(id, valid);
		return new JsonResult();
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			Integer pageCurrent,String username){
		PageObject<Map<String,Object>> pageObject=
		sysUserService.findPageObjects(pageCurrent, username);
		return new JsonResult(pageObject);
	}
	
	
	
}
