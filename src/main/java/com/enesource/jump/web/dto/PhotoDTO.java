package com.enesource.jump.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 光伏场站特殊属性
 * @author EDZ
 *
 */

@Data
public class PhotoDTO extends GovMapInfoDTO {
	@Excel(name = "企业户号", orderNum="0")
	private String consNo; 
	
	@Excel(name = "申报企业", orderNum="0")
	private String declaredEnt; 

	@Excel(name = "光伏户号", orderNum="0")
	private String photoNumber; 	

	@Excel(name = "投资单位", orderNum="0")
	private String investor; 	

	@Excel(name = "并网时间", orderNum="0")
	private String ongridDate; 	
}
