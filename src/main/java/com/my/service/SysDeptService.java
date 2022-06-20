package com.my.service;

import java.util.List;
import java.util.Map;

import com.my.common.vo.Node;
import com.my.pojo.SysDept;


public interface SysDeptService {
	 List<Map<String,Object>> findObjects();
	 List<Node> findZTreeNodes();
	 int saveObject(SysDept entity);
	 int updateObject(SysDept entity);
	 int deleteObject(Integer id);
}
