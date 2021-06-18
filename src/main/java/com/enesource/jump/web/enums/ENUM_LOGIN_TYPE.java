package com.enesource.jump.web.enums;

public enum ENUM_LOGIN_TYPE {

	FREE_LOGIN("1", "免密登录"),
	PASSWORD_VALID_LOGIN("2", "密码验证码登陆"),
	SSO_LOGIN("3", "单点登陆"),
	PASSWORD_LOGIN("4", "密码登陆"),
	;

	private final String code;

	private final String msg;

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	ENUM_LOGIN_TYPE(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
