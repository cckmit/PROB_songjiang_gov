package com.enesource.jump.web.cache;

import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dto.InsightDTO;
import com.enesource.jump.web.service.IEntInfoService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/* *
 * @author lio
 * @Description: 缓存到本地能源基础数据
 * @date :created in 4:02 下午 2021/1/19
 */
@Service
public class GovOverviewLocalCacheManager extends AbstractLocalCacheManager<Map<String, Object>> {

    @Autowired
    IGovMapper govMapper;


    @Override
    Map<String, Object> update() {
        Map<String, Object> res = Maps.newHashMap();
        InsightDTO dto = new InsightDTO();
        dto.setPage(1);
        dto.setAreaCode("all");
        dto.setAreaLabel("jiashan");
        dto.setIndustryType("all");
        dto.setType("follow");
        dto.setType("all");
        PageInfo pageInfo = new PageInfo(govMapper.findGovOverviewEntEnergy(dto));
        res.put("entEnergyList",pageInfo);
        return res;
    }


}
