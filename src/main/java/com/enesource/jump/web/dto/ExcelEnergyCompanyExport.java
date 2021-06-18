package com.enesource.jump.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/* *
 * @author lio
 * @Description:
 * @date :created in 5:05 下午 2021/1/7
 */
@Data
public class ExcelEnergyCompanyExport extends ExcelBaseDTO {

    @Excel( name = "企业")
    private String entName;
    @Excel( name = "所属区域")
    private String areaName;
    @Excel( name = "园区")
    private String microPark;
    @Excel( name = "行业名称")
    private String industryTypeName;
    @Excel( name = "当年用能指标（吨标煤）")
    private String energySumTarget;
    @Excel( name = "当年累积能耗总量（吨标煤）")
    private String energySum;
    @Excel( name = "当年累积用电总量（万kWh）")
    private String eleSum;
    @Excel( name = "当年用能进度（%）")
    private String speedValue;
    @Excel( name = "时间")
    private String date;

}
