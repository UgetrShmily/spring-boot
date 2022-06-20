package com.my.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.common.vo.JsonResult;
import com.my.pojo.SysRole;
import com.my.service.SysRoleService;

@RestController
@RequestMapping("/role")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String name,Integer pageCurrent) {
		return new JsonResult(sysRoleService.findPageObjects(name,pageCurrent));
	}
	@RequestMapping("doDeleteObject")
	public JsonResult doDeleteObject(Integer id){
		sysRoleService.deleteObject(id);
		return new JsonResult("删除成功");
	}
	@RequestMapping("/doSaveObject")
	public JsonResult doSaveObject(SysRole entity,Integer[] menuIds){
		sysRoleService.saveObject(entity,menuIds);
		return new JsonResult("保存成功");    
	}
	@RequestMapping("/doFindObjectById")
	public JsonResult doFindObjectById(Integer id){
		return new JsonResult(sysRoleService.findObjectById(id));
	}
	@RequestMapping("/doUpdateObject")
	public JsonResult doUpdateObject(SysRole entity,Integer[] menuIds){
		sysRoleService.updateObject(entity,menuIds);
		return new JsonResult("update ok");
	}
	 @RequestMapping("/doFindRoles")
	 public JsonResult doFindRoles() {
		 return new JsonResult(sysRoleService.findRoles());
	 }

}
