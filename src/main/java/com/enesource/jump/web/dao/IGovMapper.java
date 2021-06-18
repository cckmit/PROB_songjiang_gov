/**
 * @author xs
 * @date 2019年7月12日  
 */
package com.enesource.jump.web.dao;

import java.util.List;
import java.util.Map;

import com.enesource.jump.web.dto.*;


/**
 * @author Administrator
 *
 */
public interface IGovMapper {
	
	/**
	 * 政府园区总览企业数量
	 * @return
	 */
	List<Map<String, Object>> findEntList(Map<String, Object> paraMap);
	
	/**
	 * 企业档案列表数据
	 * @return
	 */
	List<Map<String, Object>> getValueList(Map<String, Object> paraMap);
	List<Map<String, Object>> getValueListByGroup(Map<String, Object> paraMap);
	
	/**
	 * 产能状态列表
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> getProductionxValueList(Map<String, Object> paraMap);
	
	/**
	 * 企业档案列表数据
	 * @return
	 */
	List<Map<String, Object>> getSubValueList(Map<String, Object> paraMap);
	
	/**
	 * 政府园区总览企业数量
	 * @return
	 */
	List<Map<String, Object>> findGovOverviewEntNum(Map<String, Object> paraMap);
	
	/**
	 * 政府园区总览企业统计饼图
	 * @return
	 */
	List<Map<String, Object>> findGovOverviewEntStatistics(Map<String, Object> paraMap);
	
	/**
	 * 政府园区总览企业统计饼图
	 * @return
	 */
	List<Map<String, Object>> findGovOverviewEntType(Map<String, Object> paraMap);
	
	
	/**
	 * 政府园区总览企业能源统计
	 * @return
	 */
	List<Map<String, Object>> findGovOverviewEntEnergy(InsightDTO dto);
	
	/**
	 * 获取户号对应的场站objectId
	 * @return
	 */
	List<Map<String, Object>> getObjectList(Map<String, Object> paraMap);
	
	/**
	 * 企业工商信息
	 * @param orgCode
	 * @return
	 */
	Map<String, Object> getEntInfo(Map<String, Object> paraMap);
	
    /**
     * 能源年度统计
     * @param orgCode
     * @return
     */
	List<Map<String, Object>> getEntEnergyInfoByYear(Map<String, Object> paraMap);
	
	/**
     * 能源月度统计
     * @param orgCode
     * @return
     */
	List<Map<String, Object>> getEntEnergyInfoByMonth(Map<String, Object> paraMap);
	
	/**
     * 企业光伏数据统计
     * @param orgCode
     * @return
     */
	List<Map<String, Object>> getEntPhotovoInfo(Map<String, Object> paraMap);
	
	/**
     * 企业经济数据统计
     * @param orgCode
     * @return
     */
	List<Map<String, Object>> getEntEconomicsInfo(Map<String, Object> paraMap);
	
	/**
     * 企业经济数据明细
     * @param orgCode
     * @return
     */
	List<Map<String, Object>> getEntEconomicsDetail(Map<String, Object> paraMap);
	
	/**
     * 企业经济数据列表
     * @param orgCode
     * @return
     */
	List<Map<String, Object>> getEntEconomicsList(Map<String, Object> paraMap);
	
	
	/**
	 * 电力数据查询
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findPowerListbyYear(Map<String, Object> paraMap);
	
	/**
	 * 天然气数据查询
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findGasListbyYear(Map<String, Object> paraMap);
	
	
	/**
	 * 热力数据查询
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findHotListbyYear(Map<String, Object> paraMap);
	
	/**
	 * 水数据查询
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findWaterListbyYear(Map<String, Object> paraMap);
	
	/**
	 * 其他数据查询
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findOrtherListbyYear(Map<String, Object> paraMap);
	
	/**
	 * 电力户号查询
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findConsNoList(ParamDTO dto);
	
	/**
	 * 查询户号
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findAccnumberList(ParamDTO dto);
	
	/**
	 * 电力户号查询用电量（年）
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findConsNoPowerListbyYear(Map<String, Object> paraMap);
	
	/**
	 * 电力户号查询用电量（月）
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findConsNoPowerListbyMonth(Map<String, Object> paraMap);
	
	/**
	 * 电力户号查询用电量（去年）
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findConsNoPowerListbyLastYear(Map<String, Object> paraMap);
	
	/**
	 * 电力户号查询用电量（上月）
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findConsNoPowerListbyLastMonth(Map<String, Object> paraMap);
	
	/**
	 * 用能列表按年
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findEntEnergyList(Map<String, Object> paraMap);
	
	/**
	 * 用能列表按月
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findEntEnergyListByMonth(Map<String, Object> paraMap);
	
	/**
	 * 园区用能总和
	 * @param paraMap
	 * @return
	 */
	Double getEntEnergySum(Map<String, Object> paraMap);
	
	/**
	 * 更新企业信息
	 * @param paraMap
	 */
	void updateEntInfo(Map<String, Object> paraMap);
	
	/**
	 * 新增关联户号
	 * @param paraMap
	 */
	void insertConsNo(Map<String, Object> paraMap);
	
	/**
	 * 更新关联户号
	 * @param paraMap
	 */
	void updateConsNo(Map<String, Object> paraMap);
	
	/**
	 * 删除关联户号
	 * @param paraMap
	 */
	void delConsNo(Map<String, Object> paraMap);
	
	
	/****
	 * --------------------------------------------------批量新增记录----------------------------------------------------------------
	 */
	
	/**
	 * 获得企业Id号
	 * @return
	 */
	String getCompanyId();
	
	/**
	 * 获得项目Id号
	 * @return
	 */
	String getSiteId();
	
	
	/**
	 * 添加企业信息
	 * @param dto
	 */
	void mergeGovMapInfoDTO(GovMapInfoDTO dto);
	
	/**
	 * 添加企业信息
	 * @param dto
	 */
	void mergeEntInfo(EntInfoDTO dto);
	
	/**
	 * 添加企业用能数据
	 * @param dto
	 */
	void mergeEnergyinfo(EnergyinfoDTO dto);
	
	/**
	 * 按类型删除区域统计表数据，能源消耗
	 * @param paraMap
	 */
	void deleteAreaStatistics(Map<String, Object> paraMap);
	
	/**
	 * 重建能源数据
	 * @param paraMap
	 */
	void insertAreaStatistics(Map<String, Object> paraMap);
	
	/**
	 * 重建经济数据
	 * @param paraMap
	 */
	void insertAreaStatisticsEC(Map<String, Object> paraMap);
	
	
	/**
	 * 添加企业用能数据
	 * @param dto
	 */
	void mergeEconomicsinfo(EconomicsinfoDTO dto);
	
	/**
	 * 更新多能监测项目曲线数据
	 * @param dto
	 */
	void mergeGovMapCurveDTO(GovMapInfoDetailDTO dto);
	
	/**
	 * 根据企业id和和类型查询企业用能状况
	 * @return
	 */
	public List<Map<String, Object>> findConsumeinfoByCompanyIdANDEnergyType(Map<String, Object> paraMap);
	
	/**
	 * 根据企业id查询企业经济状况
	 * @return
	 */
	public List<Map<String, Object>> findEconomicsinfoByCompanyId(String companyId);
	
	
	/**
	 * 根据项目Id查询用电情况
	 * @return
	 */
	public List<Map<String, Object>> findPowerCurveBySiteId(String companyId);
	
	
	/**
	 * ***************************************************************小微园区弹窗*********************************************************************************************
	 */
	
	/**
	 * 小微园区列表
	 * @return
	 */
	public List<Map<String, Object>> findMicroParkList(Map<String, Object> paramMap);
	
	/**
	 * 小微园区列表
	 * @return
	 */
	public List<Map<String, Object>> findCompanyListMicroPark(Map<String, Object> paraMap);
	
	
	/**
	 * 小微园区能源消费趋势月度
	 * @return
	 */
	public List<Map<String, Object>> findEngryConsumeByMonth(Map<String, Object> paraMap);
	
	/**
	 * 小微园区能源消费趋势月度
	 * @return
	 */
	public List<Map<String, Object>> findEngryConsumeByMonth_tce(Map<String, Object> paraMap);
	
	
	/**
	 * 小微园区能源强度，能源消耗量/生产总值(年度)
	 * @return
	 */
	public List<Map<String, Object>> findEngryConsumeByYear(Map<String, Object> paraMap);
	
	/**
	 * 小微园区能源强度，能源消耗量/生产总值(季度)
	 * @return
	 */
	public List<Map<String, Object>> findEngryConsumeByQuarter(Map<String, Object> paraMap);
	
	/**
	 * 小微园区能源结构
	 * @return
	 */
	public List<Map<String, Object>> findEngryConsumeByStruct(Map<String, Object> paraMap);
	
	
	/**
	 * **********************************************************************街区统计*************************************************************************************************
	 */
	
	/**
	 * 能源消费趋势月度
	 * @return
	 */
	public List<Map<String, Object>> findStreetEngryConsumeByMonth(Map<String, Object> paraMap);
	
	/**
	 * 能源消费趋势月度
	 * @return
	 */
	public List<Map<String, Object>> findStreetEngryConsumeByMonth_tce(Map<String, Object> paraMap);
	
	
	/**
	 * 能源消费趋势年度
	 * @return
	 */
	public List<Map<String, Object>> findStreetEngryConsumeByYear(Map<String, Object> paraMap);
	
	/**
	 * 小微园区能源结构
	 * @return
	 */
	public List<Map<String, Object>> findStreetEngryConsumeByStruct(Map<String, Object> paraMap);
	
	
	
	/**
	 *  综合统计查询
	 * @return
	 */
	public List<Map<String, Object>> integrativeStatistics(Map<String, Object> paraMap);
	
	
	/**
	 * **********************************************************************增幅计算  Increase *************************************************************************************************
	 */
	
	/**
	 * 月度能效增幅
	 * @param paraMap
	 * @return
	 */
	public List<Map<String, Object>> engryConsumeIncreaseByMonth(Map<String, Object> paraMap);
	
	/**
	 * 季度能效增幅
	 * @param paraMap
	 * @return
	 */
	public List<Map<String, Object>> engryConsumeIncreaseByQuarter(Map<String, Object> paraMap);
	
	
	/**
	 * **********************************************************************能流图数据维护*************************************************************************************************
	 */
	
//	/**
//	 * 能源统计
//	 * @param paraMap
//	 * @return
//	 */
//	public List<Map<String, Object>> findEnergyFlowStatistics();
//	
//	/**
//	 * 能源结构
//	 * @param paraMap
//	 * @return
//	 */
//	public List<Map<String, Object>> findEnergyFlowStruct();
	
	
	/**
	 * **********************************************************************缺失数据导入导出*************************************************************************************************
	 */
	
	/**
	 * 缺失数据导出
	 * @param paraMap
	 * @return
	 */
	public List<Map<String, Object>> exportLoseCompanyInfo(Map<String, Object> param);
	
	
	
	/**
	 * **********************************************************************缺失数据导入导出*************************************************************************************************
	 */
	
	/**
	 * 查询时间轴
	 * @param param
	 * @return
	 */
	public List<String> findDateList(Map<String, Object> param);
	
	
	/**
	 * 查询数值
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findDateValueListByDay(Map<String, Object> param);
	
	/**
	 * 查询数值
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findDateValueListByMonth(Map<String, Object> param);
	
	/**
	 * 导入更新日电量表
	 * @param dto
	 */
	public void mergeEntEnergyinfoDay(ImportDayValueDTO dto);
	
	/**
	 * 删除每日缺失户号
	 * @param date
	 */
	public void delLoseAccnumberDay(String date);
	
	
	/**
	 * 重新统计每日缺失户号
	 * @param date
	 */
	public void saveLoseAccnumberDay(String date);
	
	/**
	 * 删除每日缺失户号
	 * @param date
	 */
	public void delLoseStatisticsDay(String date);
	
	
	/**
	 * 重新统计每日缺失户号比例
	 * @param date
	 */
	public void saveLoseStatisticsDay(String date);
	
	
	
	/**
     * 按企业与户号关联关系合并企业每月用电
     * @param param
     */
    public void saveEntEnergyinfoCompSum(Map<String, Object> param);
    
    /**
     * 更新产能状态
     * @param param
     */
    public void updateProductionStatus_0(Map<String, Object> param);
    public void updateProductionStatus_week_0(Map<String, Object> param);
    
    /**
     * 更新产能状态
     * @param param
     */
    public void updateProductionStatus_1(Map<String, Object> param);
    public void updateProductionStatus_week_1(Map<String, Object> param);
    
    /**
     * 更新产能状态
     * @param param
     */
    public void updateProductionStatus_2(Map<String, Object> param);
    public void updateProductionStatus_week_2(Map<String, Object> param);
    
    
    /**
     * 更新产能状态
     * @param param
     */
    public void updateProductionStatus_3(Map<String, Object> param);
    public void updateProductionStatus_week_3(Map<String, Object> param);
    
    /**
     * 更新产能状态
     * @param param
     */
    public void updateProductionStatus_4(Map<String, Object> param);
    public void updateProductionStatus_week_4(Map<String, Object> param);
    
    public void updateProductionStatus_5(Map<String, Object> param);
    public void updateProductionStatus_week_5(Map<String, Object> param);
    
    
    /**
     * 更新环比值
     * @param param
     */
    public void updateMonthRate(Map<String, Object> param);
    
    
    
    
    /**
     * 更新同比值
     * @param param
     */
    public void updateYearRate(Map<String, Object> param);
    
    
    
    /**
     * 更新环比状态
     * @param param
     */
    public void updateGrowthRates_0(Map<String, Object> param);
    
    /**
     * 更新环比状态
     * @param param
     */
    public void updateGrowthRates_1(Map<String, Object> param);
    
    /**
     * 更新环比状态
     * @param param
     */
    public void updateGrowthRates_2(Map<String, Object> param);
    
    /**
     * 更新环比状态
     * @param param
     */
    public void updateGrowthRates_3(Map<String, Object> param);
    
    
    /**
     * 更新环比状态
     * @param param
     */
    public void updateGrowthRates_week_0(Map<String, Object> param);
    
    /**
     * 更新环比状态
     * @param param
     */
    public void updateGrowthRates_week_1(Map<String, Object> param);
    
    /**
     * 更新环比状态
     * @param param
     */
    public void updateGrowthRates_week_2(Map<String, Object> param);
    
    /**
     * 更新环比状态
     * @param param
     */
    public void updateGrowthRates_week_3(Map<String, Object> param);
    
    
    /**
     * 更新产能比例
     * @param param
     */
    public void updateProductionValue(Map<String, Object> param);
    
    
    
    /**
	 * **********************************************************************企业信息数据更新*************************************************************************************************
	 */
	
    /**
	 * 更新企业基础信息
	 * @param paramMap
	 * @param dto
	 */
	void updateEntBaseInfo(Map<String, Object> param);
	
	/**
	 * 更新企业户号关联表
	 * @param param
	 */
	void mergeEntAccnumberRel(Map<String, Object> param);
	
	
	 /**
	  * **********************************************************************一户多表查询*************************************************************************************************
	  */
    
	List<Map<String, Object>> findAccnumberMdmidList(Map<String, Object> paraMap);
	
	List<Map<String, Object>> findAccnumberLineList(Map<String, Object> paraMap);
	
	
	
	
	/**
	 * 动态查询户号
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> dynamicAccnumberList(ParamDTO dto);
	
	
	/**
	 * 根据户号查询回路
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findLoopListByAccnumber(Map<String, Object> paraMap);
	
	
	/**
	 * 查询当年电量
	 * @param paraMap
	 * @return
	 */
	List<Map<String, Object>> findEleValueByCompanyId(Map<String, Object> paraMap);
	
	
	
	List<Map<String, Object>> getAccnumberMdmidDetail(Map<String, Object> paramMap);
	
	
	List<String> getAccnumberMdmidEleByYear(Map<String, Object> paramMap);
	
	
	List<Map<String, Object>> findMdmidList(Map<String, Object> paramMap);
	
	
	void delAccnumber(Map<String, Object> paraMap);
	
	void delAccnumberMdmid(Map<String, Object> paraMap);
	
	/***
	 * 更新企业每月基准电量
	 * @param paraMap
	 */
	void updateCompanyIdUnitEle(Map<String, Object> paraMap);
	
	
	/***
	 * 验证企业下户号是否已经选择
	 * @param paraMap
	 */
	Integer checkCompanyAccnumber(Map<String, Object> paraMap);
	
	
	/***
	 * *******************************************************************能效全景********************************************************************************
	 */
	List<Map<String, Object>> findEntNumByAreaCode(Map<String, Object> paramMap);
	
	
	
	List<Map<String, Object>> findEleListByAreaCode(Map<String, Object> paramMap);
	
	Map<String, Object> findEleRateValueByAreaCode(Map<String, Object> paramMap);
	
	
	List<Map<String, Object>> energyStatistics(Map<String, Object> paramMap);
	
	
	List<Map<String, Object>> findfingEleConsumeByIndustry(ParamDTO dto);
	
	
	List<Map<String, Object>> findfingEleConsumeByTag(ParamDTO dto);
	
	
	List<Map<String, Object>> findfingEleConsumeByOutputvalue(ParamDTO dto);
	
	
	List<Map<String, Object>> findfingEleConsumeByFollow(ParamDTO dto);
	
	
	
	List<Map<String, Object>> findCapacityWarningGrowth(Map<String, Object> paramMap);
	
	
	
	
	List<Map<String, Object>> findCapacityWarningProduction(Map<String, Object> paramMap);
	
	
	/***
	 * *******************************************************************view3D********************************************************************************
	 */
	
	/**
	 * view3D 
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> findValueList(Map<String, Object> paramMap);
	
	
	/**
	 * 保存3D指标
	 * @param paramMap
	 */
	void saveView3DIndex(Map<String, Object> paramMap);
	
	/**
	 * 查询3D指标
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> findView3DIndex(Map<String, Object> paramMap);
	
	/**
	 * 删除3D指标
	 * @param paramMap
	 * @return
	 */
	void delView3DIndex(Map<String, Object> paramMap);
	
	
	String getViewIndexUUID();
	
	
	
	
	/***
	 * *******************************************************************经济运行动态分析********************************************************************************
	 */
	
	/**
	 * 报税销售额看板统计
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> findEcStatistics(Map<String, Object> paramMap);
	
	
	
	/**
	 * 用地情况按行业统计统计
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findCoveredAreaStatisticsByIndustry(ParamDTO dto);
	
	/**
	 * 用地情况按标签统计统计
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findCoveredAreaStatisticsByTag(ParamDTO dto);
	
	/**
	 * 用地情况按产值统计统计
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findCoveredAreaStatisticsByOutputvalue(ParamDTO dto);
	
	/**
	 * 用地情况按关注企业统计统计
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findCoveredAreaStatisticsByFollow(ParamDTO dto);
	
	/**
	 * 用工情况情况按行业统计统计
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findPeopleStatisticsByIndustry(ParamDTO dto);
	
	/**
	 * 用工情况情况按标签统计统计
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findPeopleStatisticsByTag(ParamDTO dto);
	
	/**
	 * 用工情况情况按产值统计统计
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findPeopleStatisticsByOutputvalue(ParamDTO dto);
	
	/**
	 * 用工情况情况按关注企业统计统计
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findPeopleStatisticsByFollow(ParamDTO dto);
	
	
	/**
	 * 经济运行动态弹窗综合统计
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findECStatistics(Map<String, Object> paramMap);
	
	/**
	 * 企业生产状态列表
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findProductionStatusEntList(Map<String, Object> paramMap);
	
	
	/**
	 * 企业生产状态列表
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findProductionStatusEntListForExcel(Map<String, Object> paramMap);
	
	/**
	 * 报税异常企业列表
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findTaxUnusualEntList(Map<String, Object> paramMap);
	
	/**
	 * 报税异常企业列表
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findTaxUnusualEntListForExcel(Map<String, Object> paramMap);
	
	/***
	 * *******************************************************************温州原 tableau大屏********************************************************************************
	 */
	
	
	/**
	 * 各产能状态企业数量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> findStateTrend(Map<String, Object> paramMap);
	
	
	/**
	 * 各产能状态企业详情
	 * @param dto
	 * @return
	 */
	List<Map<String, Object>> findStateTrendEnt(ParamDTO dto);
	
	/**
	 * 各产能状态企业地区占比
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> findStateTrendEntRate(Map<String, Object> paramMap);
	
	/**
	 * 增产企业数量变化趋势
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> findIncreaseProductionlEntNum(Map<String, Object> paramMap);
	
	/**
	 * 五大行业周电量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> findIndustryWeekEle(Map<String, Object> paramMap);
	
	/**
	 * 行业基准电量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> findIndustryUnitEle(Map<String, Object> paramMap);

	/**
	 * 获取所有的企业信息
	 * @return
	 */
	List<Map<String, Object>> getAllEntInfo();


	String getExcelModelUrl(String type);


	AreaEnergyDTO findAreaEnergy(Map<String, Object> paramMap);

	List<EnergySpeedDTO> findEnergySpeedByCompany(Map<String, Object> paramMap);

	List<EnergySpeedDTO> findEnergySpeedByArea(Map<String, Object> paramMap);

	List<Map<String, Object>> multipleStatistics(Map<String, Object> paramMap);

	int findAllEntNumByAreaCode(Map<String, Object> paramMap);

	List<Map<String, Object>> getAreaTarget();

	List<Map<String, Object>> getEntAllEnergyInfoByYear(Map<String, Object> paramMap);


	Map<String, Object> findPowerStructure(Map<String, Object> paramMap);

	List<Map<String, Object>> findEleCostRank(Map<String, Object> paramMap);

	String getFixedContract(Map<String, Object> paramMap);

	List<Map<String, Object>> getFuHeList(Map<String, Object> paramMap);

	List<Map<String, Object>> getAvgFuHeList(Map<String, Object> paramMap);

	List<Map<String, Object>> findTceListByAreaCode(Map<String, Object> paramMap);

	Map<String, Object> findTceRateValueByAreaCode(Map<String, Object> paramMap);

	String getFirstDate();

	List<Map<String, Object>> findEnergySpeedByEnergyTarget(Map<String, Object> paramMap);

	List<String> findCompanyAccNumber(String companyId);

	List<Map<String,Object>> getCompanyGuEleProportion(Map<String, Object> paramMap);

	List<Map<String, Object>> getEntAllEnergyInfoByCompany(Map<String, Object> paramMap);

	String getIndustryTypeByCompanyId(String companyId);

	void insertImportEleEenergyInfo(ExcelDataImportDTO dataImportDto);

	void delEenergyInfo(ExcelDataImportDTO dataImportDto);

	void delWeekSum(Map<String, Object> weekMap);

	void insertEntEnergyInfoWeekSum(Map<String, Object> weekMap);

	void uploadWeekSumRate(Map<String, Object> weekMap);

	void updateWeekProductionStatus_0(Map<String, Object> weekMap);
	void updateWeekProductionStatus_1(Map<String, Object> weekMap);
	void updateWeekProductionStatus_2(Map<String, Object> weekMap);
	void updateWeekProductionStatus_3(Map<String, Object> weekMap);
	void updateWeekProductionStatus_4(Map<String, Object> weekMap);
	void updateWeekProductionStatus_5(Map<String, Object> weekMap);

	void updateWeekGrowthRates_0(Map<String, Object> weekMap);
	void updateWeekGrowthRates_1(Map<String, Object> weekMap);
	void updateWeekGrowthRates_2(Map<String, Object> weekMap);
	void updateWeekGrowthRates_3(Map<String, Object> weekMap);

	void delEnergyinfoDayArea(ExcelDataImportDTO dataImportDto);

	void insertEnergyinfoDayArea(ExcelDataImportDTO dataImportDto);

	void delAreaStatistics(ExcelDataImportDTO dataImportDto);

	void insertAreaStatisticsByImport(ExcelDataImportDTO dataImportDto);

	void insertImportReLiEenergyInfo(ExcelDataImportDTO dataImportDto);

	void delAreaStatisticsByDay(ExcelDataImportDTO dataImportDto);

	void insertAreaStatisticsByImportDay(ExcelDataImportDTO dataImportDto);

	List<ExcelEnergyCompanyExport> findexportEnergyData(Map<String, Object> paramMap);

	void deleteEntEnergyInfoComSum(ExcelDataImportDTO dataImportDto);

	void insertEntEnergyInfoComSum(ExcelDataImportDTO dataImportDto);

	void updateProductionStatusMonth_0(ExcelDataImportDTO dataImportDto);
	void updateProductionStatusMonth_1(ExcelDataImportDTO dataImportDto);
	void updateProductionStatusMonth_2(ExcelDataImportDTO dataImportDto);
	void updateProductionStatusMonth_3(ExcelDataImportDTO dataImportDto);
	void updateProductionStatusMonth_4(ExcelDataImportDTO dataImportDto);
	void updateProductionStatusMonth_5(ExcelDataImportDTO dataImportDto);

	void updateMonthRateByImport(ExcelDataImportDTO dataImportDto);

	void updateYearRateByImport(ExcelDataImportDTO dataImportDto);

	void updateGrowthRatesMonth_0(ExcelDataImportDTO dataImportDto);
	void updateGrowthRatesMonth_1(ExcelDataImportDTO dataImportDto);
	void updateGrowthRatesMonth_2(ExcelDataImportDTO dataImportDto);
	void updateGrowthRatesMonth_3(ExcelDataImportDTO dataImportDto);

	void updateProductionValueByImport(ExcelDataImportDTO dataImportDto);


	void delAreaStatisticsByMonth(ExcelDataImportDTO dataImportDto);

	void insertAreaStatisticsByImportByMonth(ExcelDataImportDTO dataImportDto);

	void deleteEntEnergyInfoComSumByMonth(ExcelDataImportDTO dataImportDto);

	void insertEntEnergyInfoComSumByMonth(ExcelDataImportDTO dataImportDto);

	void delAreaStatisticsByCompany(Map<String, Object> resMap);


	void insertAreaStatisticsByImportByCompany(Map<String, Object> resMap);

	void deleteEntEnergyInfoComSumByCompany(Map<String, Object> resMap);

	void insertEntEnergyInfoComSumByCompany(Map<String, Object> resMap);

	List<Map<String, Object>> findReLiEnergyListByMonth(Map<String, Object> paramMap);

	Map<String, Object> findEntBaseInfoByCompanyId(String companyId);

	List<Map<String,Object>> findAllEntAccnumberRelsByType(Integer type);
}
