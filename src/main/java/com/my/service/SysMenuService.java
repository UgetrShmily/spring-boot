package com.my.service;

import java.util.List;
import java.util.Map;

import com.my.common.vo.Node;
import com.my.pojo.SysMenu;

public interface SysMenuService {
	public List<Map<String, Object>> findObjects();
	public int deleteObject(Integer id);
	public List<Node> findZtreeMenuNodes();
	public int saveObject(SysMenu entity);
	public int updateObject(SysMenu entity);
}
