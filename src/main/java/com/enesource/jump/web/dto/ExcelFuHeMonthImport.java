package com.enesource.jump.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/* *
 * @author lio
 * @Description:
 * @date :created in 9:54 上午 2021/2/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelFuHeMonthImport extends ExcelBaseDTO {


    /**
     * 公司名称
     */
    @Excel(name = "*企业名称")
    @NotBlank(message = "[企业名称]不能为空")
    private String entName;

    /**
     * 户号
     */
    @Excel(name = "*户号")
    @NotBlank(message = "[户号]不能为空")
    private String accnumber;

    @Excel(name = "1")
    private String _Data01="";
    @Excel(name = "2")
    private String _Data02="";
    @Excel(name = "3")
    private String _Data03="";
    @Excel(name = "4")
    private String _Data04="";
    @Excel(name = "5")
    private String _Data05="";
    @Excel(name = "6")
    private String _Data06="";
    @Excel(name = "7")
    private String _Data07="";
    @Excel(name = "8")
    private String _Data08="";
    @Excel(name = "9")
    private String _Data09="";
    @Excel(name = "10")
    private String _Data10="";
    @Excel(name = "11")
    private String _Data11="";
    @Excel(name = "12")
    private String _Data12="";

}
