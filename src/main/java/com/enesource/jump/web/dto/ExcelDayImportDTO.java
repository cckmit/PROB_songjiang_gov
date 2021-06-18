package com.enesource.jump.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/* *
 * @author lio
 * @Description:
 * @date :created in 4:28 下午 2021/1/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDayImportDTO extends ExcelBaseDTO {

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
        @Excel(name = "13")
        private String _Data13="";
        @Excel(name = "14")
        private String _Data14="";
        @Excel(name = "15")
        private String _Data15="";
        @Excel(name = "16")
        private String _Data16="";
        @Excel(name = "17")
        private String _Data17="";
        @Excel(name = "18")
        private String _Data18="";
        @Excel(name = "19")
        private String _Data19="";
        @Excel(name = "20")
        private String _Data20="";
        @Excel(name = "21")
        private String _Data21="";
        @Excel(name = "22")
        private String _Data22="";
        @Excel(name = "23")
        private String _Data23="";
        @Excel(name = "24")
        private String _Data24="";
        @Excel(name = "25")
        private String _Data25="";
        @Excel(name = "26")
        private String _Data26="";
        @Excel(name = "27")
        private String _Data27="";
        @Excel(name = "28")
        private String _Data28="";
        @Excel(name = "29")
        private String _Data29="";
        @Excel(name = "30")
        private String _Data30="";
        @Excel(name = "31")
        private String _Data31="";

    }
