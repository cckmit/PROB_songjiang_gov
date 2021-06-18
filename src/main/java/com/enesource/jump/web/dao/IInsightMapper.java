package com.enesource.jump.web.dao;

import java.util.List;
import java.util.Map;

import com.enesource.jump.web.dto.DataDTO;
import com.enesource.jump.web.dto.InsightDTO;


public interface IInsightMapper {
	
	/**
	 * 查询产能洞察配置数据
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findInsightIndex(Map<String, Object> paramMap);
	
	
	/**
	 * 保存产能洞察配置数据
	 * @param name
	 * @return
	 */
	public void saveInsightIndex(Map<String, Object> paramMap);
	
	/**
	 * 查询产能洞察统计数据
	 * @param name
	 * @return
	 */
	public Map<String, Object> findInsightStatistics(Map<String, Object> paramMap);
	public Map<String, Object> findInsightWeekStatistics(Map<String, Object> paramMap);
	
	/**
	 * 查询去年同期电量
	 * @param name
	 * @return
	 */
	public Double getLastsametimeElevalue(Map<String, Object> paramMap);
	
	/**
	 * 查询产能洞察日电量数据
	 * @param name
	 * @return
	 */
	public List<Map<String, Object>> findInsightEleList(Map<String, Object> paramMap);
	public List<Map<String, Object>> findInsightEleListWeek(Map<String, Object> paramMap);
	public List<Map<String, Object>> findInsightEleListLastWeek(Map<String, Object> paramMap);
	
	/**
	 * 查询产能洞察企业列表
	 * @param name
	 * @return
	 */
	public List<Map<String, Object>> findInsightCompanyList(InsightDTO dto);
	public List<Map<String, Object>> findInsightWeekCompanyList(InsightDTO dto);
	
	/**
	 * 查询产能洞察企业列表
	 * @param name
	 * @return
	 */
	public void updateCompanyUnitEle(Map<String, Object> paramMap);

    List<Map<String, Object>> findInsightYongReList(Map<String, Object> paramMap);
}
