package com.jt.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysConfig;
import com.jt.sys.service.SysConfigService;

@Controller
@RequestMapping("/config/")
public class SysConfigController {
	@Autowired
    private SysConfigService sysConfigService;
    
    @RequestMapping("listUI")
    public String listUI() {
    	return "sys/config_list";
    }
    @RequestMapping("editUI")
    public String editUI() {
    	return "sys/config_edit";
    }
    
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysConfig entity) {
    	sysConfigService.saveObject(entity);
    	return new JsonResult("save ok");
    }
    
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysConfig entity) {
    	sysConfigService.updateObject(entity);
    	return new JsonResult("update ok");
    }
    
    @RequestMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(String ids) {
    	sysConfigService.deleteObjedcts(ids);
    	return new JsonResult("delete ok");
    }
    
    @RequestMapping("doFindPageObjects")
    @ResponseBody
	public JsonResult doFindPageObjects(
			Integer pageCurrent,String name){
         PageObject<SysConfig> pageObject=
         sysConfigService.findPageObjects(pageCurrent,name);
		 return new JsonResult(pageObject);
	}
    
    
    
}
