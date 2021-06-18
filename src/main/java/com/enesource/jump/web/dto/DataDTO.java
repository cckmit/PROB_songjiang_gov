package com.enesource.jump.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataDTO {
	
	@Excel(name = "项目名称", orderNum = "0")
    private String name;

    @Excel(name = "电量值", orderNum = "1")
    private String value;

    @Excel(name = "是否小微园企业", orderNum = "2")
    private String isPark;
    
    @Excel(name = "地址", orderNum = "3")
    private String address;
    
    @Excel(name = "区域", orderNum = "4")
    private String area;
    
    @Excel(name = "所属小微园", orderNum = "5")
    private String park;
    
    @Excel(name = "备注", orderNum = "6")
    private String remark;
    
    
//    public PersonDTO(String name, String sex, Date birthday) {
//        this.name = name;
//        this.sex = sex;
//        this.birthday = birthday;
//    }
}
