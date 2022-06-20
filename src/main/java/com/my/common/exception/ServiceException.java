package com.my.common.exception;
/**
 * 自定义系统异常
 * @author A2
 *
 */
public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = -6376556937251282918L;
	public ServiceException() {
		super();
	}
	public ServiceException(String message) {
		super(message);
	}
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
