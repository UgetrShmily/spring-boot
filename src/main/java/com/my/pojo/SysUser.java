package com.my.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class SysUser implements Serializable {
	private static final long serialVersionUID = -2084506667460906909L;
	private Integer id;
	private String username;
	private String password;
	private String salt;//盐值
	private String email;
	private String mobile;
	private Integer valid=1;
	private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;

}
