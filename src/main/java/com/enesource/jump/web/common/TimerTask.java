package com.enesource.jump.web.common;


import com.enesource.jump.web.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TimerTask {

	@Autowired
	TimeService timeService;

	/**
	 * 定时任务统计每月电量
	 * 每三分钟执行一次，有些数据查询很慢放到redis
	 * @author liuyy
	 * @date 2019年9月7日 下午4:10:56
	 */
	@Scheduled(cron ="0 0/3 * * * ? ")
	public void updateLocalCache() {
		timeService.updateLocalCache();
	}





}
