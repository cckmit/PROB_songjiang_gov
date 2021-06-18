package com.enesource.jump.web.service.impl;/* *
 * @author lio
 * @Description:
 * @date :created in 2:45 下午 2021/2/18
 */

import com.alibaba.fastjson.JSON;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dto.InsightDTO;
import com.enesource.jump.web.redis.IRedisService;
import com.enesource.jump.web.service.IEntInfoService;
import com.enesource.jump.web.service.TimeService;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

@Service
@Transactional
public class TimeServiceImpl implements TimeService {

    @Autowired
    IGovMapper govMapper;

    @Autowired
    private IRedisService redisService;

    @Autowired
    IEntInfoService entInfoService;


    @Override
    public void updateLocalCache() {

        Map<String,Object> reqMap = Maps.newHashMap();
        reqMap.put("areaLabel","jiashan");
        List<Map<String, Object>> multipleStatisticsMap = entInfoService.multipleStatistics(Maps.newHashMap());
            redisService.set("multipleStatistics", JSON.toJSONString(multipleStatisticsMap));
    }

}
