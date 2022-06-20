package com.my.common.vo;

import java.io.Serializable;

import lombok.Data;
@Data
public class Node implements Serializable{
	private static final long serialVersionUID = -8344041767423103947L;
	private Integer id;
	private String name;
	private Integer parentId;
}
