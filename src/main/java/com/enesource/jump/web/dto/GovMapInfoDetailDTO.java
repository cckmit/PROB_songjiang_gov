package com.enesource.jump.web.dto;

import org.hibernate.validator.constraints.NotBlank;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.Data;


/**
 * 多能全景明细曲线数据
 * @author EDZ
 *
 */

@Data
public class GovMapInfoDetailDTO extends ExcelBaseDTO {
	
	@Excel(name = "站点编号*")
	private String siteId; 
	
	/**
	 * 站点类型，，1 发电厂、2 分布式光伏、3 电网、4 供热网、5 供冷网、6 储能、7 充电站,、8 水电站、9 垃圾发电站、10 变电站
	 */
	@Excel(name = "场站类型", replace = {"分布式光伏_2", "充电站_7", "水电站_8", "垃圾发电站_9", "变电站_10"})
	private Integer siteType;
	
	@Excel(name = "站点名称*")
	@NotBlank(message = "[站点名称]不能为空")
	private String siteName; 	
	
	/**
	 * 值
	 */
	private String value; 
	/**
	 * 时间
	 */
	private String date; 
	
	@ExcelEntity(name = "时间维度数据")
	private DateColumnDTO dateColumn;
}
