package com.jt.sys.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping("listUI")
	public String listUI(){
		return "sys/menu_list";
	}
	@RequestMapping("editUI")
	public String editUI(){
		return "sys/menu_edit";
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu entity){
	    sysMenuService.updateObject(entity);
	    return new JsonResult();
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id){
		Map<String,Object> map=
		sysMenuService.findObjectById(id);
		return new JsonResult(map);
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu entity){
		sysMenuService.saveObject(entity);
		return new JsonResult();
	}
	
	@RequestMapping("doFindZTreeNodes")
	@ResponseBody
	public JsonResult doFindZTreeNodes(){
		return new JsonResult(
		sysMenuService.findZTreeNodes());
	}
	
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id){
		sysMenuService.deleteObject(id);
		JsonResult r=new JsonResult();
		r.setMessage("delete ok");
		return r;
	}
	
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects(){
		return new JsonResult(
			sysMenuService.findObjects());
	}	
	
	
}
