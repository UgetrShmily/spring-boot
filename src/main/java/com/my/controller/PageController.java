package com.my.controller;

import com.my.pojo.SysUser;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
	@RequestMapping("doIndexUI")
	public String doIndexUI(Model model) {
//		SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
		SysUser user = new SysUser();
		model.addAttribute("user", DefaultFilter.user);
		return "starter";
	}
	@RequestMapping("/{module}/{modulePage}")
	public String doLogUI(@PathVariable String modulePage) {
		return "sys/"+modulePage;
	}
	@RequestMapping("/doPageUI")
	public String doPageUI() {
		return "common/page";
	}
	@RequestMapping("/doLoginUI")
	public String doLoginUI(){
			return "login";
	}

}
