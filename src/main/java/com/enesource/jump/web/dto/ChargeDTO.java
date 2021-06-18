package com.enesource.jump.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 充电桩特殊属性
 * @author EDZ
 *
 */

@Data
public class ChargeDTO extends GovMapInfoDTO {
	
	@Excel(name = "充电类型, 快充、慢充")
	private String chargeType; 

	@Excel(name = "充电桩数量*")
	private String chargeCountSum; 	

	@Excel(name = "充电站额定总功率")
	private String changePowerSum; 
}
