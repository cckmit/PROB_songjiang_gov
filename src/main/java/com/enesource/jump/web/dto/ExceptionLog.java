package com.enesource.jump.web.dto;

import java.util.Date;

import lombok.Data;
/**
 * 异常
 * @author king
 *
 */
@Data
public class ExceptionLog {
	int id;
	private Date createTime;
	int type;
	String name;
	int level;
	String information;
	String operator;
	String url;
	
}
