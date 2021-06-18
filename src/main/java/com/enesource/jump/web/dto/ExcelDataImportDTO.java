package com.enesource.jump.web.dto;

import lombok.Data;

/* *
 * @author lio
 * @Description: 嘉善数据维护导入传参
 * @date :created in 11:15 上午 2021/1/7
 */
@Data
public class ExcelDataImportDTO {

    /**
     * 导入数据类型
     */
    private String dataType;

    /**
     * 导入日期
     */
    private String date;

    /**
     * 导入日期类型
     */
    private String selectType;

    private String userId;

    private String tce;


}
