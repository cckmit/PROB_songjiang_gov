package com.enesource.jump.web.dto;

import lombok.Data;

/* *
 * @author lio
 * @Description:
 * @date :created in 7:31 下午 2021/1/25
 */
@Data
public class EnergySpeedDTO {

    private double speedValue;

    private String name ;

    private double energySum;

    private double targetEnergy;

    private String companyId;

}
