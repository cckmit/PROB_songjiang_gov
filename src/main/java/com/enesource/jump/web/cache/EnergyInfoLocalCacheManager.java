package com.enesource.jump.web.cache;

import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.service.IEntInfoService;
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
public class EnergyInfoLocalCacheManager extends AbstractLocalCacheManager<Map<String, Object>> {

    @Autowired
    IGovMapper govMapper;

    @Autowired
    IEntInfoService entInfoService;

    @Override
    Map<String, Object> update() {
        Map<String, Object> res = Maps.newHashMap();
        Map<String,Object> reqMap = Maps.newHashMap();
        reqMap.put("areaLabel","jiashan");
        List<Map<String, Object>> multipleStatisticsMap = entInfoService.multipleStatistics(Maps.newHashMap());
        res.put("multipleStatisticsMap",multipleStatisticsMap);
        return res;
    }


}
