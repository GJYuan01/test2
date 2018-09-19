package com.jt.sys.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;

@Controller
@RequestMapping("/role/")
public class SysRoleController {
    @Autowired
	private SysRoleService sysRoleService;
    @RequestMapping("listUI")
    public String listUI(){
    	return "sys/role_list";
    }
    @RequestMapping("editUI")
    public String editUI(){
    	return "sys/role_edit";
    }
    @ResponseBody
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(
    		SysRole entity,String menuIds){
    	sysRoleService.updateObject(entity,menuIds);
    	JsonResult r=new JsonResult();
    	r.setMessage("update ok");
    	return r;
    }
    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id){
    	Map<String,Object> map=
    	sysRoleService.findObjectById(id);
    	
    	return new JsonResult(map);
    }
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(
    		SysRole entity,String menuIds){
    	sysRoleService.saveObject(entity,menuIds);
    	JsonResult r=new JsonResult();
    	r.setMessage("save ok");
    	return r;
    }
    @RequestMapping("doFindPageObjects")
    @ResponseBody
	public JsonResult doFindPageObjects(
			Integer pageCurrent,String name){
         PageObject<SysRole> pageObject=
		 sysRoleService.findPageObjects(pageCurrent,name);
		 return new JsonResult(pageObject);
	}
    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id){
    	sysRoleService.deleteObject(id);
    	return new JsonResult();
    }
}




