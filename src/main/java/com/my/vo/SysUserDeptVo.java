package com.my.vo;

import java.io.Serializable;
import java.util.Date;

import com.my.pojo.SysDept;

import lombok.Data;
@Data
public class SysUserDeptVo implements Serializable{
	private static final long serialVersionUID = 5477389876913514595L;
	private Integer id;
	private String username;
	private String password;//md5
	private String salt;
	private String email;
	private String mobile;
	private Integer valid=1;
	private SysDept sysDept; //private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
