package com.enesource.jump.web.enums;

public enum ENUM_USER_LEVEL {

	ADMIN("0", "国网用户"),
	COMPANY("1", "企业用户"),
	;

	private final String code;

	private final String msg;

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	ENUM_USER_LEVEL(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
