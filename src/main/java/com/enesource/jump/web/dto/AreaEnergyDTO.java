package com.enesource.jump.web.dto;

import lombok.Data;

/* *
 * @author lio
 * @Description:
 * @date :created in 2:34 下午 2021/1/18
 */
@Data
public class AreaEnergyDTO {

    /**
     * 单位GDP能耗降幅
     */
    private String gdpDown;

    /**
     * 单位GDP能耗降幅目标
     */
    private String gdpTarget;

    /**
     * 单位规上工业增加值能耗降幅
     */
    private String regulationDown;

    /**
     * 单位规上工业增加值能耗降幅目标
     */
    private String regulationTarget;

}
