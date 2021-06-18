package com.enesource.jump.web.dto;

import lombok.Data;

/* *
 * @author lio
 * @Description:
 * @date :created in 9:57 上午 2021/1/22
 */
@Data
public class CompanyDataDTO {

    /**
     * 用户ID
     */
    private String userId;


    /**
     *企业ID
     */
    private String companyId;


    private String entName;

    /**
     * 填报时间
     */
    private String date;

    /**
     * 开票额
     */
    private String kaiPiao;

    /**
     * 累积用水
     */
    private String yongShui;

    /**
     * 天然气
     */
    private String tianRanQi;

    /**
     * 煤炭
     */
    private String meiTan;

    /**
     * 汽油
     */
    private String qiYou;

    /**
     * 柴油
     */
    private String chaiYou;

    /**
     * 生物质
     */
    private String shengWuZhi;



}
