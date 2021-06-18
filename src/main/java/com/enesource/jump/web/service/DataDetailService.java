package com.enesource.jump.web.service;

import com.enesource.jump.web.dto.CompanyDataDTO;
import com.enesource.jump.web.dto.ParamDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/* *
 * @author lio
 * @Description:
 * @date :created in 11:51 上午 2021/1/12
 */
public interface DataDetailService {


    List<Map<String, Object>> getDataDetail(ParamDTO detailParam,List<Map<String,Object>> entList);

    String insertOrUpdateCompanyData(Map<String,Object> companyDataMap);

    Map<String, Object> getCompanyData(Map<String,Object> companyDataMap);
}
