package com.enesource.jump.web.common;

import lombok.Data;

@Data
public class Page {
	private Integer page;
	private Integer pageSize = 10;
	private String  timestamp;
	
}
