package com.enesource.jump.web.dao;

import java.util.List;
import java.util.Map;

import com.enesource.jump.web.dto.EntInfoDTO;
import com.enesource.jump.web.dto.GovMapInfoDTO;
import com.enesource.jump.web.dto.ParamDTO;

public interface IGovBigScreen {

	/**
	 * 查询政府统计数据
	 * @param name
	 * @return
	 */
	public Map<String, Object> findGovStatisticsData(Map<String, Object> pm);
	
	
	
	public Map<String, Object> findGovCountStatisticsData(Map<String, Object> pm);
	
	
	public void updateGovCountStatisticsData(Map<String, Object> pm);
	
	/**
	 * 查询政府页面数据
	 * @param name
	 * @return
	 */
	public List<Map<String, Object>> findGovMapData(Map<String, Object> pm);
	
	/**
	 * 查询明细
	 * @param pm
	 * @return
	 */
	public Map<String, Object> getGovDetail(Map<String, Object> pm);
	
	/**
	 * 查询列表
	 * @param pm
	 * @return
	 */
	public List<Map<String, Object>> getGovList(Map<String, Object> pm);
	
	
	/**
	 * 获得站点曲线
	 * @param pm
	 * @return
	 */
	public List<Map<String, Object>> siteIdCurve(Map<String, Object> pm);
	
	
	/**
	 * 企业用能全景--企业地图
	 * @param pm
	 * @return
	 */
	public List<Map<String, Object>> getParkAddress(Map<String, Object> pm);
	
	/**
	 * 区域电量总和及同步、环比
	 * @param pm
	 * @return
	 */
	public Map<String, Object> getParkAddressRate(Map<String, Object> pm);
	
	/**
	 * 企业用能全景--企业全量地图
	 * @param pm
	 * @return
	 */
	public List<Map<String, Object>> getParkAddressSum(Map<String, Object> pm);
	
	/**
	 * 企业用能全景--企业明细
	 * @param pm
	 * @return
	 */
	public List<Map<String, Object>> pointDetail(Map<String, Object> pm);
	
	/**
	 * 企业用能全景--企业明细-今年
	 * @param pm
	 * @return
	 */
	public List<Map<String, Object>> pointThisDetail(Map<String, Object> pm);
	
	/**
	 * 企业用能全景--企业能耗排名
	 * @param pm
	 * @return
	 */
	public List<Map<String, Object>> entOrderList(ParamDTO dto);
	
	/**
	 * 企业用能全景--企业能耗排名
	 * @param pm
	 * @return
	 */
	public List<EntInfoDTO> findEnergyinfoList(Map<String, Object> pm);
	
	/**
	 * 按用能类型查询企业户号
	 * @param pm
	 * @return
	 */
	public List<Map<String, Object>> findAccnumberListByEnergrType(Map<String, Object> pm);
	
	
	 
	public List<Map<String, Object>> getParkCompanyCounts(Map<String, Object> paramMap);
	public List<Map<String, Object>> getParkCompanyCountsBywhenzhou(Map<String, Object> paramMap);
	
	/**
	 * 多能全景数据统计
	 * @return
	 */
	public Map<String, Object> multiEnergyStatistics(Map<String, Object> paramMap);
	
	
	public List<Map<String, Object>> projectListBysiteType(GovMapInfoDTO dto);
	
	
	/**
	 * 电量数据完整程度统计
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> findEleLoseCounts(Map<String, Object> paramMap);
	
	
	/**************************************************************************数据同步 redis************************************************************************************************/
	
	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> findEntInfo(Map<String, Object> paramMap);

	Map<String, Object> getParkTceAddressRate(Map<String, Object> paramMap);

	List<Map<String, Object>> pointEleDetail(Map<String, Object> paramMap);

	List<Map<String, Object>> pointEleThisDetail(Map<String, Object> paramMap);
}
