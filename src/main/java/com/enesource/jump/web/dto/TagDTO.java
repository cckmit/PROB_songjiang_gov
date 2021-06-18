package com.enesource.jump.web.dto;

import com.enesource.jump.web.common.Page;

import lombok.Data;

@Data
public class TagDTO extends Page {
	
	private String attribute;
	
	private String createUser;
	
	private String createTime;
	
	private String updateUser;
	
	private String updateTime;
	
	private Integer valid;
	
	private String type;
	
	private String tagKey;
	
	private String tagName;
	
	private String areaLabel;
	
	private String identifier;
}
