package com.enesource.jump.web.dao;

import com.enesource.jump.web.dto.ExceptionLog;

public interface IExceptionMapper {
	/**
	 * 添加异常信息
	 * @param exceptionLog
	 */
	public void saveExceptionInformation(ExceptionLog exceptionLog);
}
