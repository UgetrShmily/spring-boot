package com.my.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.common.util.IpUtils;
import com.my.common.vo.JsonResult;
import com.my.pojo.SysLog;
import com.my.service.SysLogService;
import com.my.vo.PageObject;

@Controller
@ResponseBody
@RequestMapping("/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	@RequestMapping("/doFindPageObject")
	public JsonResult doFindPageObject(String username,Integer pageCurrent) {
		PageObject<SysLog> pageObject=sysLogService.findPageObject(username, pageCurrent);
		return new JsonResult(pageObject);
	}
	@RequestMapping("/doDeleteObjects")
	public JsonResult doDeleteObjects(@RequestParam("ids") Integer...ids) {
		int rows=sysLogService.deleteByIds(ids);
		return new JsonResult("删除完成");
	}
}

