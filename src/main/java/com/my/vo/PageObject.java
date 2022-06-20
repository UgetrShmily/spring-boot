package com.my.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * 页面数据包装类
 * @author A2
 *
 */
@Data
public class PageObject<T> implements Serializable{
	private static final long serialVersionUID = 415955087257240996L;
	/**当前页*/
	private Integer pageCurrent;
	/**页面大小*/
	private Integer pageSize;
	/**查询总记录数*/
	private Integer rowCount;
	/**总页数*/
	private Integer pageCount;
	/**页面数据*/
	private List<T> records;
	{
		pageSize=10;
	}
	public PageObject(Integer pageCurrent, Integer pageSize, Integer rowCount,List<T> records) {
		super();
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.pageCount = (rowCount-1)/pageSize+1;
		this.records = records;
	}
	
}
