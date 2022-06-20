package com.my.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.common.vo.JsonResult;
import com.my.pojo.SysMenu;
import com.my.service.SysMenuService;

@RestController
@RequestMapping("/menu")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;
	@RequestMapping("/doFindObjects")
	public JsonResult doFindObjects() {
		return new JsonResult(sysMenuService.findObjects());
	}
	@RequestMapping("/doDeleteObject")
	public JsonResult doDeleteObject(Integer id){
		sysMenuService.deleteObject(id);
		return new JsonResult("删除成功");
	}
	@RequestMapping("/doFindZtreeMenuNodes")
	public JsonResult doFindZtreeMenuNodes(){
		return new JsonResult(
				sysMenuService.findZtreeMenuNodes());
	}
	@RequestMapping("/doSaveObject")
	public JsonResult doSaveObject(SysMenu entity){
		sysMenuService.saveObject(entity);
		return new JsonResult("保存成功");
	}

	@RequestMapping("/doUpdateObject")
	public JsonResult doUpdateObject(SysMenu entity){
		sysMenuService.updateObject(entity);
		return new JsonResult("更新成功");
	}

}
