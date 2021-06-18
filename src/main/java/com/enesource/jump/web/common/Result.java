package com.enesource.jump.web.common;

import java.io.Serializable;

import com.enesource.jump.web.utils.Conf;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code = Conf.SUCCESS;
	
	private String msg = Conf.SUCCESSMSG;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object data;

	private String timestamp;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void failure(){this.setCode(Conf.ERROR);this.setMsg("FAIL");}

	public void success(){this.setCode(Conf.SUCCESS);this.setMsg(Conf.SUCCESSMSG);}
}
