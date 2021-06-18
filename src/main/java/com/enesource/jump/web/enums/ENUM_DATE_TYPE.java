package com.enesource.jump.web.enums;

public enum ENUM_DATE_TYPE {

	MONTH("1", "年"),
	DAY("2", "月"),
	;

	private final String code;

	private final String msg;

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	ENUM_DATE_TYPE(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
