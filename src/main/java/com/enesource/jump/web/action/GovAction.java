package com.enesource.jump.web.action;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.enesource.jump.web.cache.GovOverviewLocalCacheManager;
import com.enesource.jump.web.dao.IGovBigScreen;
import com.enesource.jump.web.dto.*;
import com.enesource.jump.web.redis.IRedisService;
import com.enesource.jump.web.service.DataDetailService;
import com.enesource.jump.web.utils.*;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.enesource.jump.web.annotation.JwtToken;
import com.enesource.jump.web.common.BaseAction;
import com.enesource.jump.web.common.Result;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dao.ITagMapper;
import com.enesource.jump.web.service.IAsyncService;
import com.enesource.jump.web.service.IEntInfoService;
import com.enesource.jump.web.vo.EnergyinfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
 * 企业能源档案
 *
 * @author EDZ
 */
@Controller
@CrossOrigin
public class GovAction extends BaseAction {

    @Autowired
    IGovMapper govMapper;

    @Autowired
    ITagMapper tagMapper;

    @Autowired
    IAsyncService asyncService;

    @Autowired
    IEntInfoService entInfoService;

    @Autowired
    DataDetailService dataDetailService;
    @Autowired
    IGovBigScreen govBigScreen;
    @Autowired
    private IRedisService redisService;


    @JwtToken
    @RequestMapping(value = "/gov/findEntList", method = RequestMethod.POST)
    @ResponseBody
    public Result findEntList(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"userId"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            List<Map<String, Object>> entList = govMapper.findEntList(paramMap);

            result.setData(entList);
        } catch (Exception e) {
            logger.error("企业档案-企业查询错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findEntList");
            exceptionLog.setName("企业档案企业查询错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/getValueList", method = RequestMethod.POST)
    @ResponseBody
    public Result getValueList(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            paramMap.put("type", "area");
            List<Map<String, Object>> areaList = govMapper.getValueList(paramMap);

            paramMap.put("type", "investment");
            List<Map<String, Object>> investmentList = govMapper.getValueList(paramMap);

            paramMap.put("type", "entType");
            List<Map<String, Object>> entTypeList = govMapper.getValueList(paramMap);

            paramMap.put("type", "areaCode");
            List<Map<String, Object>> areaCodeList = govMapper.getValueList(paramMap);

            Map<String, Object> resultMap = new HashMap<String, Object>();


            //  企业洞察页面参数
            // TODO 标签选框
            List<Map<String, Object>> typeList = tagMapper.findTagList(paramMap);
            ;

            resultMap.put("typeList", typeList);


            List<Map<String, Object>> proStatusList = new ArrayList<Map<String, Object>>();

            Map<String, Object> proStatusMap1 = new HashMap<String, Object>();
            proStatusMap1.put("name", "0");
            proStatusMap1.put("value", "停产");
            Map<String, Object> proStatusMap2 = new HashMap<String, Object>();
            proStatusMap2.put("name", "1");
            proStatusMap2.put("value", "半停产");
            Map<String, Object> proStatusMap3 = new HashMap<String, Object>();
            proStatusMap3.put("name", "2");
            proStatusMap3.put("value", "减产");
            Map<String, Object> proStatusMap4 = new HashMap<String, Object>();
            proStatusMap4.put("name", "3");
            proStatusMap4.put("value", "正常");
            Map<String, Object> proStatusMap5 = new HashMap<String, Object>();
            proStatusMap5.put("name", "4");
            proStatusMap5.put("value", "增产");
            Map<String, Object> proStatusMap6 = new HashMap<String, Object>();
            proStatusMap6.put("name", "5");
            proStatusMap6.put("value", "异常增产");

            proStatusList.add(proStatusMap1);
            proStatusList.add(proStatusMap2);
            proStatusList.add(proStatusMap3);
            proStatusList.add(proStatusMap4);
            proStatusList.add(proStatusMap5);
            proStatusList.add(proStatusMap6);

            resultMap.put("proStatusList", proStatusList);

            List<Map<String, Object>> growthRateList = new ArrayList<Map<String, Object>>();

            Map<String, Object> growthRatesMap1 = new HashMap<String, Object>();
            growthRatesMap1.put("name", "0");
            growthRatesMap1.put("value", "锐减");
            Map<String, Object> growthRatesMap2 = new HashMap<String, Object>();
            growthRatesMap2.put("name", "1");
            growthRatesMap2.put("value", "平稳");
            Map<String, Object> growthRatesMap3 = new HashMap<String, Object>();
            growthRatesMap3.put("name", "2");
            growthRatesMap3.put("value", "激增");
            Map<String, Object> growthRatesMap4 = new HashMap<String, Object>();
            growthRatesMap4.put("name", "3");
            growthRatesMap4.put("value", "异常激增");

            growthRateList.add(growthRatesMap1);
            growthRateList.add(growthRatesMap2);
            growthRateList.add(growthRatesMap3);
            growthRateList.add(growthRatesMap4);

            resultMap.put("growthRateList", growthRateList);

            paramMap.put("type", "view3DIndex");
            List<Map<String, Object>> view3DIndexList = govMapper.getValueListByGroup(paramMap);


            List<Map<String, Object>> getProductionxValueList = govMapper.getProductionxValueList(paramMap);


            resultMap.put("areaList", areaList);
            resultMap.put("investmentList", investmentList);
            resultMap.put("entTypeList", entTypeList);
            resultMap.put("areaCodeList", areaCodeList);
            resultMap.put("view3DIndexList", view3DIndexList);
            resultMap.put("getProductionxValueList", getProductionxValueList);

            paramMap.put("areaLabel", null);
            paramMap.put("type", "industry");
            List<Map<String, Object>> industryList = govMapper.getValueList(paramMap);

            if (industryList != null && industryList.size() > 0) {
                for (Map<String, Object> map : industryList) {

                    map.put("type", "industryMain");
                    map.put("subType", map.get("value"));
                    List<Map<String, Object>> industryMainList = govMapper.getValueList(map);

                    map.put("childList", industryMainList);
                }
            }

            resultMap.put("industryList", industryList);


            paramMap.put("areaLabel", null);
            paramMap.put("type", "industryMain");
            List<Map<String, Object>> industryMainList = govMapper.getValueList(paramMap);

            resultMap.put("industryMainList", industryMainList);

            paramMap.put("areaLabel", null);
            paramMap.put("type", "outputValue");
            List<Map<String, Object>> outputValueList = govMapper.getValueList(paramMap);
            resultMap.put("outputValueList", outputValueList);
            
            paramMap.put("areaLabel", null);
            paramMap.put("type", "park");
            List<Map<String, Object>> parkList = govMapper.getValueList(paramMap);

            resultMap.put("parkList", parkList);

            //获取导入数据类型
            paramMap.put("areaLabel", null);
            paramMap.put("type", "importDateType");
            List<Map<String, Object>> importTypeList = govMapper.getValueList(paramMap);
            resultMap.put("importTypeList", importTypeList);

            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("企业档案列表数据查询错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/getValueList");
            exceptionLog.setName("企业档案列表数据查询错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/overview", method = RequestMethod.POST)
    @ResponseBody
    public Result overview(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> m = new HashMap<String, Object>();

            List<Map<String, Object>> entNum = govMapper.findGovOverviewEntNum(paramMap);

            List<Map<String, Object>> entStatistics = govMapper.findGovOverviewEntStatistics(paramMap);

            List<Map<String, Object>> entTypeStatistics = govMapper.findGovOverviewEntType(paramMap);

            m.put("entNum", entNum);
            m.put("entStatistics", entStatistics);
            m.put("entTypeStatistics", entTypeStatistics);

            result.setData(m);
        } catch (Exception e) {
            logger.error("政府园区总览错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/overview");
            exceptionLog.setName("政府园区总览错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/findGovOverviewEntEnergy", method = RequestMethod.POST)
    @ResponseBody
    public Result findGovOverviewEntEnergy(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        try {
            String[] checkParamsMap = {};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);
                return result;
            }
            InsightDTO dto = (InsightDTO) MapUtils.map2JavaBean(InsightDTO.class, paramMap);
            if (dto.getPage() != null) {
                PageHelper.startPage(dto.getPage(), dto.getPageSize());
            }
            PageInfo pageInfo = new PageInfo(govMapper.findGovOverviewEntEnergy(dto));
            result.setData(pageInfo);
        } catch (Exception e) {
            logger.error("政府园区总览企业能源统计错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findGovOverviewEntEnergy");
            exceptionLog.setName("政府园区总览企业能源统计错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/getObjectList", method = RequestMethod.POST)
    @ResponseBody
    public Result getObjectList(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"companyId"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> m = new HashMap<String, Object>();

            List<Map<String, Object>> objectList = govMapper.getObjectList(paramMap);

            m.put("objectList", objectList);

            result.setData(m);
        } catch (Exception e) {
            logger.error("查询企业对应站点错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/getObjectList");
            exceptionLog.setName("查询企业对应站点错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/getEntEnergyOverview", method = RequestMethod.POST)
    @ResponseBody
    public Result getEntEnergyOverview(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"companyId"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> m = new HashMap<String, Object>();

            Map<String, Object> entInfo = govMapper.getEntInfo(paramMap);

            // TODO
            ParamDTO dto;

            dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);
            dto.setType("0");

//			if (dto.getPage() != null) {
//				PageHelper.startPage(dto.getPage(), dto.getPageSize());
//			}

            List<Map<String, Object>> consNoList = govMapper.findAccnumberList(dto);

            m.put("consNoList", consNoList);

            dto.setType("3");

            List<Map<String, Object>> waterNoList = govMapper.findAccnumberList(dto);

            m.put("waterNoList", waterNoList);

            dto.setType("1");

            List<Map<String, Object>> gasNoList = govMapper.findAccnumberList(dto);

            m.put("gasNoList", gasNoList);

            dto.setType("8");
            List<Map<String, Object>> coalList = govMapper.findAccnumberList(dto);

            m.put("coalList", coalList);

            dto.setType("4");
            List<Map<String, Object>> oilList = govMapper.findAccnumberList(dto);

            m.put("oilList", oilList);

            dto.setType("6");
            List<Map<String, Object>> petrolList = govMapper.findAccnumberList(dto);

            m.put("petrolList", petrolList);

            dto.setType("2");
            List<Map<String, Object>> hotList = govMapper.findAccnumberList(dto);

            m.put("hotList", hotList);

            List<Map<String, Object>> entEnergyInfoYear = govMapper.getEntEnergyInfoByYear(paramMap);

            List<Map<String, Object>> entEconomicsInfo = govMapper.getEntEconomicsInfo(paramMap);

            m.put("entInfo", entInfo);
            m.put("entEnergyInfoYear", entEnergyInfoYear);
            m.put("entEconomicsInfo", entEconomicsInfo);

            result.setData(m);
        } catch (Exception e) {
            logger.error("政府企业能源概况错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/getEntEnergyOverview");
            exceptionLog.setName("政府企业能源概况错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/getEntEnergyOverviewNew", method = RequestMethod.POST)
    @ResponseBody
    public Result getEntEnergyOverviewNew(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"companyId"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> m = new HashMap<String, Object>();

            Map<String, Object> entInfo = govMapper.getEntInfo(paramMap);

            m.put("entInfo", entInfo);

            // TODO
            ParamDTO dto;

            dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);
            dto.setType("0");

            List<Map<String, Object>> consNoList = govMapper.findAccnumberLineList(paramMap);

//			if(consNoList != null && consNoList.size() > 0) {
//				
//				for (Map<String, Object> map : consNoList) {
//					List<Map<String, Object>> mdmidList = govMapper.findMdmidList(map);
//					
//					map.put("mdmidList", mdmidList);
//				}
//				
//			}


            m.put("consNoList", consNoList);

            dto.setType("3");

            List<Map<String, Object>> waterNoList = govMapper.findAccnumberList(dto);

            m.put("waterNoList", waterNoList);

            dto.setType("1");

            List<Map<String, Object>> gasNoList = govMapper.findAccnumberList(dto);

            m.put("gasNoList", gasNoList);

            dto.setType("8");
            List<Map<String, Object>> coalList = govMapper.findAccnumberList(dto);

            m.put("coalNoList", coalList);

            dto.setType("4");
            List<Map<String, Object>> oilList = govMapper.findAccnumberList(dto);

            m.put("oilNoList", oilList);

            dto.setType("6");
            List<Map<String, Object>> petrolList = govMapper.findAccnumberList(dto);

            m.put("petrolNoList", petrolList);

            dto.setType("2");
            List<Map<String, Object>> hotList = govMapper.findAccnumberList(dto);

            m.put("hotNoList", hotList);

            paramMap.put("identifier", paramMap.get("companyId"));

            List<TagDTO> tagList = tagMapper.findTagListByCompanyId(paramMap);

            m.put("tagList", tagList);

            result.setData(m);
        } catch (Exception e) {
            logger.error("政府企业能源概况错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/getEntEnergyOverviewNew");
            exceptionLog.setName("政府企业能源概况错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/getEntEconomicsInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getEntEconomicsInfo(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"companyId", "date"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> m = new HashMap<String, Object>();

            List<Map<String, Object>> entEconomicsDetail = govMapper.getEntEconomicsDetail(paramMap);
            List<Map<String, Object>> entEconomicsList = govMapper.getEntEconomicsList(paramMap);

            paramMap.put("date", Integer.valueOf(paramMap.get("date").toString()) - 1);

            List<Map<String, Object>> entEconomicsLastList = govMapper.getEntEconomicsList(paramMap);

            m.put("entEconomicsDetail", entEconomicsDetail);
            m.put("entEconomicsList", entEconomicsList);
            m.put("entEconomicsLastList", entEconomicsLastList);

            result.setData(m);
        } catch (Exception e) {
            logger.error("政府企业经济数据错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/getEntEconomicsInfo");
            exceptionLog.setName("政府企业经济数据错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/getEntEnergyInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getEntEnergyInfo(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"companyId", "date"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> m = new HashMap<String, Object>();

            // 电力数据
            List<Map<String, Object>> powerList = govMapper.findPowerListbyYear(paramMap);
            List<Map<String, Object>> gasList = govMapper.findGasListbyYear(paramMap);
            List<Map<String, Object>> hotList = govMapper.findHotListbyYear(paramMap);
            List<Map<String, Object>> waterList = govMapper.findWaterListbyYear(paramMap);
            List<Map<String, Object>> otherList = govMapper.findOrtherListbyYear(paramMap);

            m.put("powerList", powerList);
            m.put("gasList", gasList);
            m.put("hotList", hotList);
            m.put("waterList", waterList);
            m.put("otherList", otherList);

            result.setData(m);
        } catch (Exception e) {
            logger.error("政府企业能源数据错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/getEntEnergyInfo");
            exceptionLog.setName("政府企业能源数据错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/getEntEnergyDetail", method = RequestMethod.POST)
    @ResponseBody
    public Result getEntEnergyDetail(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        try {
            String[] checkParamsMap = {"companyId", "date", "type"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);
                return result;
            }
            Map<String, Object> m = new HashMap<String, Object>();
            int type = Integer.valueOf(paramMap.get("type").toString());
            // 户号统一传递
            List<Map<String, Object>> entEnergyList = new ArrayList<Map<String, Object>>();
            // 电力
            if (type == 0) {
                String[] dateTypeMap = {"dateType"};
                String dateTypeString = checkParams(paramMap, dateTypeMap, ERROR_STRING_MAP);
                if (null != dateTypeString) {
                    result.setCode(Conf.ERROR);
                    result.setMsg(dateTypeString);
                    return result;
                }
                ParamDTO dto;
                dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);
                dto.setType("0");
                if (dto.getPage() != null) {
                    PageHelper.startPage(dto.getPage(), dto.getPageSize());
                }
                entEnergyList = govMapper.findConsNoList(dto);
                if (entEnergyList != null) {
                    m.put("entEnergyList", new PageInfo(entEnergyList));
                    for (Map<String, Object> map : entEnergyList) {
                        map.put("date", paramMap.get("date"));
                        map.put("type", type);
                        // 年
                        if ("1".equals(paramMap.get("dateType").toString())) {
                            List<Map<String, Object>> infoList = govMapper.findConsNoPowerListbyYear(map);
                            List<Map<String, Object>> infoLastList = govMapper.findConsNoPowerListbyLastYear(map);
                            Map<String, Object> tempMap = new HashMap<String, Object>();
                            tempMap.put("collObjId", map.get("consNo"));
                            tempMap.put("list", infoList);
                            tempMap.put("lastList", infoLastList);
                            m.put("infoList_" + map.get("consNo").toString(), tempMap);
                        }

                        // 月
                        if ("2".equals(paramMap.get("dateType").toString())) {
                            List<Map<String, Object>> infoList = govMapper.findConsNoPowerListbyMonth(map);
                            List<Map<String, Object>> infoLastList = govMapper.findConsNoPowerListbyLastMonth(map);
                            Map<String, Object> tempMap = new HashMap<String, Object>();
                            tempMap.put("collObjId", map.get("consNo"));
                            tempMap.put("list", infoList);
                            tempMap.put("lastList", infoLastList);
                            m.put("infoList_" + map.get("consNo").toString(), tempMap);
                        }
                    }
                }
            }
            // 供热\用水
            else if (type == 2 || type == 3) {
                ParamDTO dto;
                dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);
                dto.setType(type + "");
                if (dto.getPage() != null) {
                    PageHelper.startPage(dto.getPage(), dto.getPageSize());
                }
                entEnergyList = govMapper.findAccnumberList(dto);
                m.put("entEnergyList", new PageInfo(entEnergyList));
                if (entEnergyList != null && entEnergyList.size() > 0) {
                    for (Map<String, Object> map2 : entEnergyList) {
                        paramMap.put("consNo", map2.get("consNo"));
                        // 年
                        if ("1".equals(paramMap.get("dateType").toString())) {
                            List<Map<String, Object>> infoList = govMapper.findEntEnergyList(paramMap);
                            paramMap.put("date", Integer.valueOf(paramMap.get("date").toString()) - 1);
                            List<Map<String, Object>> lastInfoList = govMapper.findEntEnergyList(paramMap);
                            Map<String, Object> tempMap = new HashMap<String, Object>();
                            tempMap.put("collObjId", map2.get("consNo"));
                            tempMap.put("list", infoList);
                            tempMap.put("lastList", lastInfoList);
                            m.put("infoList_" + map2.get("consNo").toString(), tempMap);
                        }
                        // 月
                        if ("2".equals(paramMap.get("dateType").toString()) && type == 2) {
                            List<Map<String, Object>> infoList = govMapper.findReLiEnergyListByMonth(paramMap);
                            paramMap.put("last", 1);
                            List<Map<String, Object>> lastInfoList = govMapper.findReLiEnergyListByMonth(paramMap);
                            Map<String, Object> tempMap = new HashMap<String, Object>();
                            tempMap.put("collObjId", map2.get("consNo"));
                            tempMap.put("list", infoList);
                            tempMap.put("lastList", lastInfoList);
                            m.put("infoList_" + map2.get("consNo").toString(), tempMap);
                        }
                    }
                }
            }
            // 天然气\其他
            else if (type == 1 || type == 9) {
                String[] subTypeMap = {"subType"};
                String subTypeString = checkParams(paramMap, subTypeMap, ERROR_STRING_MAP);
                if (null != subTypeString) {
                    result.setCode(Conf.ERROR);
                    result.setMsg(subTypeString);
                    return result;
                }
                ParamDTO dto;
                dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);
                if (dto.getPage() != null) {
                    PageHelper.startPage(dto.getPage(), dto.getPageSize());
                }
                if ("4".equals(paramMap.get("subType").toString())) {
                    paramMap.put("type", "4");
                    dto.setType("4");
                }
                if ("6".equals(paramMap.get("subType").toString())) {
                    paramMap.put("type", "6");
                    dto.setType("6");
                }
                if ("8".equals(paramMap.get("subType").toString())) {
                    paramMap.put("type", "8");
                    dto.setType("8");
                }
                if ("10".equals(paramMap.get("subType").toString())) {
                    paramMap.put("type", "10");
                    dto.setType("10");
                }
                if ("11".equals(paramMap.get("subType").toString())) {
                    paramMap.put("type", "11");
                    dto.setType("11");
                }
                entEnergyList = govMapper.findAccnumberList(dto);
                m.put("entEnergyList", new PageInfo(entEnergyList));
                if (entEnergyList != null && entEnergyList.size() > 0) {
                    for (Map<String, Object> map2 : entEnergyList) {
                        paramMap.put("consNo", map2.get("consNo"));
                        // 年
                        if ("1".equals(StringUtil.getString(paramMap.get("dateType")))) {
                            List<Map<String, Object>> infoList = govMapper.findEntEnergyList(paramMap);
                            paramMap.put("date", Integer.valueOf(paramMap.get("date").toString()) - 1);
                            List<Map<String, Object>> lastInfoList = govMapper.findEntEnergyList(paramMap);
                            Map<String, Object> tempMap = new HashMap<String, Object>();
                            tempMap.put("collObjId", map2.get("consNo"));
                            tempMap.put("list", infoList);
                            tempMap.put("lastList", lastInfoList);
                            m.put("infoList_" + map2.get("consNo").toString(), tempMap);
                        }
                        // 月
                        if ("2".equals(StringUtil.getString(paramMap.get("dateType")))) {
                            List<Map<String, Object>> infoList = govMapper.findEntEnergyListByMonth(paramMap);
                            paramMap.put("last", 1);
                            List<Map<String, Object>> lastInfoList = govMapper.findEntEnergyListByMonth(paramMap);
                            Map<String, Object> tempMap = new HashMap<String, Object>();
                            tempMap.put("collObjId", map2.get("consNo"));
                            tempMap.put("list", infoList);
                            tempMap.put("lastList", lastInfoList);
                            m.put("infoList_" + map2.get("consNo").toString(), tempMap);
                        }
                    }
                }
            }
            result.setData(m);
        } catch (Exception e) {
            logger.error("政府企业能源明细错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/getEntEnergyDetail");
            exceptionLog.setName("政府企业能源明细错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/updateEntInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateEntInfo(@RequestBody EntInfoDTO dto) {
        Result result = new Result();

        try {
            Map<String, Object> paramMap = MapUtils.java2Map(dto);

            entInfoService.updateEntInfo(paramMap, dto);

        } catch (Exception e) {
            logger.error("企业档案更新企业信息错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/updateEntInfo");
            exceptionLog.setName("企业档案更新企业信息错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/inOrUpdateConsNo", method = RequestMethod.POST)
    @ResponseBody
    public Result inOrUpdateConsNo(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"orgCode", "type", "operation"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

//			if("add".equals(paramMap.get("operation").toString())) {
//				govMapper.insertConsNo(paramMap);
//			}
//			
//			if("update".equals(paramMap.get("operation").toString())) {
//				govMapper.updateConsNo(paramMap);
//			}
//
//			if("del".equals(paramMap.get("operation").toString())) {
//				govMapper.delConsNo(paramMap);
//			}

        } catch (Exception e) {
            logger.error("户号信息维护错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/inOrUpdateConsNo");
            exceptionLog.setName("户号信息维护错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/mergeGovMapinfo", method = RequestMethod.POST)
    @ResponseBody
    public Result mergeGovMapinfo(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"orgCode", "type"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            GovMapInfoDTO dto = new GovMapInfoDTO();

            dto = MapUtils.map2Java(dto, paramMap);

//			govMapper.mergeEntInfo(dto);

        } catch (Exception e) {
            logger.error("户号信息维护错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/inOrUpdateConsNo");
            exceptionLog.setName("户号信息维护错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/mergeEnergyinfo", method = RequestMethod.POST)
    @ResponseBody
    public Result mergeEnergyinfo(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"orgCode"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            EnergyinfoDTO dto = new EnergyinfoDTO();

            dto = MapUtils.map2Java(dto, paramMap);

            govMapper.mergeEnergyinfo(dto);

        } catch (Exception e) {
            logger.error("户号信息维护错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/inOrUpdateConsNo");
            exceptionLog.setName("户号信息维护错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/mergeEconomicsinfo", method = RequestMethod.POST)
    @ResponseBody
    public Result mergeEconomicsinfo(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"orgCode"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            EconomicsinfoDTO dto = new EconomicsinfoDTO();

            dto = MapUtils.map2Java(dto, paramMap);

            govMapper.mergeEconomicsinfo(dto);

        } catch (Exception e) {
            logger.error("户号信息维护错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/inOrUpdateConsNo");
            exceptionLog.setName("户号信息维护错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    /***
     *	********************************************************企业信息更新**********************************************************************
     */


    @JwtToken
    @RequestMapping(value = "/gov/updateEntBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateEntBaseInfo(@RequestBody EntInfoDTO dto) {
        Result result = new Result();

        try {
            Map<String, Object> paramMap = MapUtils.java2Map(dto);

            entInfoService.updateEntBaseInfo(paramMap, dto);

        } catch (Exception e) {
            logger.error("企业档案更新企业基础信息错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/updateEntBaseInfo");
            exceptionLog.setName("企业档案更新企业基础信息错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

//	@JwtToken
//	@RequestMapping(value = "/gov/updateEntTagInfo", method = RequestMethod.POST)  
//	@ResponseBody
//	public Result updateEntTagInfo(@RequestBody EntInfoDTO dto) {
//		Result result = new Result();
//		
//		try {
//			Map<String, Object> paramMap = MapUtils.java2Map(dto);
//			
//			entInfoService.updateEntTagInfo(paramMap, dto);
//			
//		} catch (Exception e) {
//		    logger.error("企业档案更新企业标签信息错误", e);
//		    ExceptionLog exceptionLog = new ExceptionLog();
//		    exceptionLog.setType(1);
//		    exceptionLog.setUrl("/gov/updateEntTagInfo");
//		    exceptionLog.setName("企业档案更新企业标签信息错误");
//		    exceptionLog.setLevel(1);
//		    exceptionLog.setInformation(e.getMessage());
//		    exceptionLog.setOperator("");
//
//		    this.saveExceptionLog(exceptionLog);
//		    
//		    result.setCode(Conf.ERROR);
//		    result.setMsg("执行异常");
//		    
//		    return result;
//
//		}
//        
//        return result;
//    }

//	@JwtToken
//	@RequestMapping(value = "/gov/updateEntFileInfo", method = RequestMethod.POST)  
//	@ResponseBody
//	public Result updateEntFileInfo(@RequestBody EntInfoDTO dto) {
//		Result result = new Result();
//		
//		try {
//			Map<String, Object> paramMap = MapUtils.java2Map(dto);
//			
//			entInfoService.updateEntFileInfo(paramMap, dto);
//			
//		} catch (Exception e) {
//		    logger.error("企业档案更新企业文件信息错误", e);
//		    ExceptionLog exceptionLog = new ExceptionLog();
//		    exceptionLog.setType(1);
//		    exceptionLog.setUrl("/gov/updateEntFileInfo");
//		    exceptionLog.setName("企业档案更新企业文件信息错误");
//		    exceptionLog.setLevel(1);
//		    exceptionLog.setInformation(e.getMessage());
//		    exceptionLog.setOperator("");
//
//		    this.saveExceptionLog(exceptionLog);
//		    
//		    result.setCode(Conf.ERROR);
//		    result.setMsg("执行异常");
//		    
//		    return result;
//
//		}
//        
//        return result;
//    }

    @JwtToken
    @RequestMapping(value = "/gov/updateEntAccnumberInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateEntAccnumberInfo(@RequestBody AccnumberMdmidRelDTO dto) {
        Result result = new Result();

        try {
            Map<String, Object> paramMap = MapUtils.java2Map(dto);

            entInfoService.updateEntAccnumberInfo(paramMap, dto);

        } catch (Exception e) {
            logger.error("企业档案更新企业电力户号信息错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/updateEntAccnumberInfo");
            exceptionLog.setName("企业档案更新企业电力户号信息错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/updateEntOtherAccnumberInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateEntOtherAccnumberInfo(@RequestBody EntInfoDTO dto) {
        Result result = new Result();

        try {
            Map<String, Object> paramMap = MapUtils.java2Map(dto);

            entInfoService.updateEntOtherAccnumberInfo(paramMap, dto);

        } catch (Exception e) {
            logger.error("企业档案更新企业其他类型户号信息错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/updateEntOtherAccnumberInfo");
            exceptionLog.setName("企业档案更新企业其他类型户号信息错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/saveTag", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTag(@RequestBody TagDTO dto) {
        Result result = new Result();

        try {
            dto.setAttribute("private");
            dto.setType("ent");

            Integer check = tagMapper.checkTagName(dto);

            if (check == 0) {
                tagMapper.saveTag(dto);

                result.setData(dto.getTagKey());
            } else {
                result.setCode(Conf.ERROR);
                result.setMsg("标签已存在");
            }

        } catch (Exception e) {
            logger.error("添加标签错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/saveTag");
            exceptionLog.setName("添加标签错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/findAccnumberMdmidList", method = RequestMethod.POST)
    @ResponseBody
    public Result findAccnumberMdmidList(@RequestBody EntInfoDTO dto) {
        Result result = new Result();

        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> paramMap = MapUtils.java2Map(dto);

        try {
            String[] checkParamsMap = {"areaLabel"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            if (dto.getPage() != null) {
                PageHelper.startPage(dto.getPage(), dto.getPageSize());
            }

            List<Map<String, Object>> accnumberList = govMapper.findAccnumberMdmidList(paramMap);

            resultMap.put("accnumberList", new PageInfo(accnumberList));

            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("查询一表多户列表错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findAccnumberMdmidList");
            exceptionLog.setName("查询一表多户列表错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/getAccnumberMdmidDetail", method = RequestMethod.POST)
    @ResponseBody
    public Result getAccnumberDetail(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            String[] checkParamsMap = {"accnumber"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            List<Map<String, Object>> mdmidList = govMapper.getAccnumberMdmidDetail(paramMap);


            if (mdmidList != null && mdmidList.size() > 0) {
                for (Map<String, Object> map : mdmidList) {

                    List<String> monthList = govMapper.getAccnumberMdmidEleByYear(map);

                    String[] arr = new String[12];


                    for (int i = 0; i < monthList.size(); i++) {
                        arr[i] = monthList.get(i);
                    }


                    map.put("monthList", arr);

                }
            }


            resultMap.put("mdmidList", mdmidList);

            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("查询一表多户明细错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/getAccnumberDetail");
            exceptionLog.setName("查询一表多户明细错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/dynamicAccnumberList", method = RequestMethod.POST)
    @ResponseBody
    public Result findAccnumberList(@RequestBody ParamDTO dto) {
        Result result = new Result();

        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> paramMap = MapUtils.java2Map(dto);

        try {
            String[] checkParamsMap = {"key"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            List<Map<String, Object>> findAccnumberList = govMapper.dynamicAccnumberList(dto);

            resultMap.put("findAccnumberList", findAccnumberList);

            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("动态查询户号错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findAccnumberList");
            exceptionLog.setName("动态查询户号错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/findLoopListByAccnumber", method = RequestMethod.POST)
    @ResponseBody
    public Result findLoopListByAccnumber(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"accnumber", "type"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            List<Map<String, Object>> findAccnumberList = govMapper.findLoopListByAccnumber(paramMap);

            result.setData(findAccnumberList);

        } catch (Exception e) {
            logger.error("查询户号下的回路错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/getAccnumberDetail");
            exceptionLog.setName("查询户号下的回路错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/updateFollowTag", method = RequestMethod.POST)
    @ResponseBody
    public Result updateFollowTag(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"companyId", "flag"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            if ("Y".equals(paramMap.get("flag").toString())) {
                tagMapper.updateFollowTag(paramMap);
            } else if ("N".equals(paramMap.get("flag").toString())) {

                TagDTO dto = new TagDTO();

                dto.setIdentifier(paramMap.get("companyId").toString());
                dto.setTagKey("follow");

                tagMapper.delTagRelByIdentifier(dto);
            }


        } catch (Exception e) {
            logger.error("更新关注标签错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/updateFollowTag");
            exceptionLog.setName("更新关注标签错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/delTag", method = RequestMethod.POST)
    @ResponseBody
    public Result delTag(@RequestBody TagDTO dto) {
        Result result = new Result();

        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> paramMap = MapUtils.java2Map(dto);

        try {
            String[] checkParamsMap = {"tagKey"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            tagMapper.delTag(dto);

        } catch (Exception e) {
            logger.error("删除标签错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/delTag");
            exceptionLog.setName("删除标签错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/delAccnumber", method = RequestMethod.POST)
    @ResponseBody
    public Result delAccnumber(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"companyId", "accnumber"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            govMapper.delAccnumber(paramMap);

            govMapper.delAccnumberMdmid(paramMap);

            // TODO 计算企业的基准电量，并异步触发产能状态的调整
            govMapper.updateCompanyIdUnitEle(paramMap);


            asyncService.updateProductionStatus(paramMap);

        } catch (Exception e) {
            logger.error("删除户号错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/delAccnumber");
            exceptionLog.setName("删除户号错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/checkCompanyAccnumber", method = RequestMethod.POST)
    @ResponseBody
    public Result checkCompanyAccnumber(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"companyId", "accnumber"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            int i = govMapper.checkCompanyAccnumber(paramMap);


            result.setData(i);


        } catch (Exception e) {
            logger.error("验证企业下户号是否已经选择错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/checkCompanyAccnumber");
            exceptionLog.setName("验证企业下户号是否已经选择错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    /***
     * *******************************************************************能效全景********************************************************************************
     */
    @JwtToken
    @RequestMapping(value = "/gov/findEntNumByAreaCode", method = RequestMethod.POST)
    @ResponseBody
    public Result findEntNumByAreaCode(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"areaLabel", "areaCode"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            List<Map<String, Object>> m = govMapper.findEntNumByAreaCode(paramMap);

            result.setData(m);

        } catch (Exception e) {
            logger.error("按街道统计企业数量错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findEntNumByAreaCode");
            exceptionLog.setName("按街道统计企业数量错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");
            return result;
        }
        return result;
    }


    /**
     * @Author:lio
     * @Description:用能趋势
     * @Date :7:44 下午 2021/1/20
     */
    @RequestMapping(value = "/gov/findTceByAreaCode", method = RequestMethod.POST)
    @ResponseBody
    public Result findTceByAreaCode(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        Map<String, Object> resMap = Maps.newHashMap();
        //用能趋势曲线
        List<Map<String, Object>> tceValueList = govMapper.findTceListByAreaCode(paramMap);
        Map<String, Object> rateValue = govMapper.findTceRateValueByAreaCode(paramMap);
        resMap.put("tceValueList", CollectionUtils.isEmpty(tceValueList) ? Lists.newArrayList() : tceValueList);
        resMap.put("rateValue", null == rateValue ? Maps.newHashMap() : rateValue);
        result.setData(resMap);
        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/findEleByAreaCode", method = RequestMethod.POST)
    @ResponseBody
    public Result findEleByAreaCode(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        try {
            String[] checkParamsMap = {"areaCode", "areaLabel", "date"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);
                return result;
            }
            List<Map<String, Object>> valueList = govMapper.findEleListByAreaCode(paramMap);
            Map<String, Object> rateValue = govMapper.findEleRateValueByAreaCode(paramMap);
            if (rateValue != null) {
                rateValue.put("valueList", valueList);
            } else {
                rateValue = new HashMap<String, Object>();
                rateValue.put("valueList", valueList);
            }
            result.setData(rateValue);
        } catch (Exception e) {
            logger.error("日电量列表错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findEleByAreaCode");
            exceptionLog.setName("日电量列表错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");
            this.saveExceptionLog(exceptionLog);
            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");
            return result;
        }
        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/energyStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Result energyStatistics(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        try {
            String[] checkParamsMap = {"areaLabel", "selectType"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);
                return result;
            }
            List<Map<String, Object>> l = govMapper.energyStatistics(paramMap);
            result.setData(l);
        } catch (Exception e) {
            logger.error("能源控制统计错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/energyStatistics");
            exceptionLog.setName("能源控制统计错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");
            this.saveExceptionLog(exceptionLog);
            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");
            return result;
        }
        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/fingEleConsume", method = RequestMethod.POST)
    @ResponseBody
    public Result fingEleConsume(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        try {
            String[] checkParamsMap = {"areaLabel", "selectType"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);
                return result;
            }
            // TODO
            ParamDTO dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);
            if (dto.getPage() != null) {
                PageHelper.startPage(dto.getPage(), 5);
            }

            List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();

            if ("1".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findfingEleConsumeByIndustry(dto);
            } else if ("2".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findfingEleConsumeByTag(dto);
            } else if ("3".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findfingEleConsumeByOutputvalue(dto);
            } else if ("4".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findfingEleConsumeByFollow(dto);
            }

            result.setData(new PageInfo(l));

        } catch (Exception e) {
            logger.error("电力消耗统计错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/fingEleConsume");
            exceptionLog.setName("电力消耗统计错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/findCapacityWarning", method = RequestMethod.POST)
    @ResponseBody
    public Result findCapacityWarning(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"areaLabel", "selectType"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            if ("1".equals(paramMap.get("selectType").toString())) {
                paramMap.put("growth", 0);

                List<Map<String, Object>> list_0 = govMapper.findCapacityWarningGrowth(paramMap);

                paramMap.put("growth", 1);

                List<Map<String, Object>> list_1 = govMapper.findCapacityWarningGrowth(paramMap);

                paramMap.put("growth", 2);

                List<Map<String, Object>> list_2 = govMapper.findCapacityWarningGrowth(paramMap);

                paramMap.put("growth", 3);

                List<Map<String, Object>> list_3 = govMapper.findCapacityWarningGrowth(paramMap);

                resultMap.put("list_0", list_0);
                resultMap.put("list_1", list_1);
                resultMap.put("list_2", list_2);
                resultMap.put("list_3", list_3);

            } else if ("2".equals(paramMap.get("selectType").toString())) {
                paramMap.put("production", 0);

                List<Map<String, Object>> list_0 = govMapper.findCapacityWarningProduction(paramMap);

                paramMap.put("production", 1);

                List<Map<String, Object>> list_1 = govMapper.findCapacityWarningProduction(paramMap);

                paramMap.put("production", 2);

                List<Map<String, Object>> list_2 = govMapper.findCapacityWarningProduction(paramMap);

                paramMap.put("production", 3);

                List<Map<String, Object>> list_3 = govMapper.findCapacityWarningProduction(paramMap);

                paramMap.put("production", 4);

                List<Map<String, Object>> list_4 = govMapper.findCapacityWarningProduction(paramMap);

                paramMap.put("production", 5);

                List<Map<String, Object>> list_5 = govMapper.findCapacityWarningProduction(paramMap);

                resultMap.put("list_0", list_0);
                resultMap.put("list_1", list_1);
                resultMap.put("list_2", list_2);
                resultMap.put("list_3", list_3);
                resultMap.put("list_4", list_4);
                resultMap.put("list_5", list_5);
            }

            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("产能预警查询错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findCapacityWarning");
            exceptionLog.setName("产能预警查询错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    /***
     * *******************************************************************经济运行动态分析********************************************************************************
     */
    @JwtToken
    @RequestMapping(value = "/gov/findEcStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Result findEcStatistics(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"areaLabel", "selectType"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();

            if ("1".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findEcStatistics(paramMap);
            }

            result.setData(l);

        } catch (Exception e) {
            logger.error("报税销售额看板统计错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findEcStatistics");
            exceptionLog.setName("报税销售额看板统计错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/findPeopleNumStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Result findPeopleNumStatistics(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"areaLabel", "selectType"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            // TODO
            ParamDTO dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);

            if (dto.getPage() != null) {
                PageHelper.startPage(dto.getPage(), 5);
            }

            List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();

            if ("1".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findPeopleStatisticsByIndustry(dto);
            } else if ("2".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findPeopleStatisticsByTag(dto);
            } else if ("3".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findPeopleStatisticsByOutputvalue(dto);
            } else if ("4".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findPeopleStatisticsByFollow(dto);
            }

            result.setData(new PageInfo(l));

        } catch (Exception e) {
            logger.error("用工情况统计错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findPeopleNumStatistics");
            exceptionLog.setName("用工情况统计错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/findCoveredAreaStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Result findCoveredAreaStatistics(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"areaLabel", "selectType"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            // TODO
            ParamDTO dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);

            if (dto.getPage() != null) {
                PageHelper.startPage(dto.getPage(), 5);
            }

            List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();

            if ("1".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findCoveredAreaStatisticsByIndustry(dto);
            } else if ("2".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findCoveredAreaStatisticsByTag(dto);
            } else if ("3".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findCoveredAreaStatisticsByOutputvalue(dto);
            } else if ("4".equals(paramMap.get("selectType").toString())) {
                l = govMapper.findCoveredAreaStatisticsByFollow(dto);
            }

            result.setData(new PageInfo(l));

        } catch (Exception e) {
            logger.error("用地情况统计错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findCoveredAreaStatistics");
            exceptionLog.setName("用地情况统计错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }

    @JwtToken
    @RequestMapping(value = "/gov/findECStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Result findECStatistics(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"areaLabel"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            List<Map<String, Object>> l = govMapper.findECStatistics(paramMap);

            result.setData(l);

        } catch (Exception e) {
            logger.error("经济运行动态弹窗错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findECStatistics");
            exceptionLog.setName("经济运行动态弹窗错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    @JwtToken
    @RequestMapping(value = "/gov/findProductionStatusEntList", method = RequestMethod.POST)
    @ResponseBody
    public Result findProductionStatusEntList(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"areaLabel"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            paramMap.put("productionStatus", 0);
            List<Map<String, Object>> statusl0 = govMapper.findProductionStatusEntList(paramMap);

            paramMap.put("productionStatus", 1);
            List<Map<String, Object>> statusl1 = govMapper.findProductionStatusEntList(paramMap);

            paramMap.put("productionStatus", 2);
            List<Map<String, Object>> statusl2 = govMapper.findProductionStatusEntList(paramMap);


            resultMap.put("statusl0", statusl0);
            resultMap.put("statusl1", statusl1);
            resultMap.put("statusl2", statusl2);


            List<Map<String, Object>> TaxUnusualEntList = govMapper.findTaxUnusualEntList(paramMap);

            resultMap.put("TaxUnusualEntList", TaxUnusualEntList);

            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("企业生产状态列表错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/findProductionStatusEntList");
            exceptionLog.setName("企业生产状态列表错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

            result.setCode(Conf.ERROR);
            result.setMsg("执行异常");

            return result;

        }

        return result;
    }


    /**
     * @Author:lio
     * @Description: 获取区域能耗数据
     * @Date :1:09 下午 2021/1/18
     */
    @RequestMapping(value = "/gov/findAreaEnergy", method = RequestMethod.POST)
    @ResponseBody
    public Result findAreaEnergy(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        String areaCode = StringUtil.getString(paramMap.get("areaCode"));
        String date = StringUtil.getString(paramMap.get("date"));
        AssertUtil.NotBlank(areaCode, "区域不能为空");
        AssertUtil.NotBlank(date, "日期不能为空");
        Map<String, Object> resMap = Maps.newHashMap();
        int entNumber = govMapper.findAllEntNumByAreaCode(paramMap);
        AreaEnergyDTO areaData = govMapper.findAreaEnergy(paramMap);
        resMap.put("areaData", areaData == null ? new AreaEnergyDTO() : areaData);
        resMap.put("entNumber", entNumber);
        result.setData(resMap);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 获取能耗进度
     * @Date :1:09 下午 2021/1/18
     */
    @RequestMapping(value = "/gov/findEnergySpeed", method = RequestMethod.POST)
    @ResponseBody
    public Result findEnergySpeed(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        String energyType = StringUtil.getString(paramMap.get("energyType"));
        String date = StringUtil.getString(paramMap.get("date"));
        AssertUtil.NotBlank(energyType, "类型不能为空");
        AssertUtil.NotBlank(date, "日期不能为空");
        String companyType = "company";
        String areaType = "area";
        String pageSize = StringUtil.getString(paramMap.get("pageSize"));
        String pageNo = StringUtil.getString(paramMap.get("pageNo"));

        if (StringUtil.isNotEmpty(pageSize, pageNo)) {
            PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        }
        List<EnergySpeedDTO> resMap = Lists.newArrayList();
        if (companyType.equals(energyType)) {
            resMap = govMapper.findEnergySpeedByCompany(paramMap);
        } else if (areaType.equals(energyType)) {
            resMap = govMapper.findEnergySpeedByArea(paramMap);
        }
        if (CollectionUtils.isEmpty(resMap) || null == resMap.get(0)) {
            resMap = Lists.newArrayList();
        }
        PageInfo pageInfo = new PageInfo(resMap);
        if (pageInfo.getTotal() > 10) {
            pageInfo.setTotal(10);
        }

        result.setData(pageInfo);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 判断乡镇企业是否超标
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/getAreaTarget", method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaTarget(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        List<Map<String, Object>> targetMap = govMapper.getAreaTarget();
        result.setData(targetMap);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 获取企业能耗情况 用能进度
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/findCompanyEnergySpeed", method = RequestMethod.POST)
    @ResponseBody
    public Result findCompanyEnergySpeed(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        List<EnergySpeedDTO> energySpeedList = govMapper.findEnergySpeedByCompany(paramMap);
        EnergySpeedDTO energySpeedDTO = new EnergySpeedDTO();
        if (!CollectionUtils.isEmpty(energySpeedList)) {
            energySpeedDTO = energySpeedList.get(0);
        }
        result.setData(energySpeedDTO);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 获取电耗结构
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/findPowerStructure", method = RequestMethod.POST)
    @ResponseBody
    public Result findPowerStructure(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        Map<String, Object> resMap = govMapper.findPowerStructure(paramMap);
        result.setData(resMap);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 获取度电单价水平排行
     * 度电单价 = 总电费 / 总电量
     * 当月电费没有的话，就不算
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/findEleCostRank", method = RequestMethod.POST)
    @ResponseBody
    public Result findEleCostRank(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        // 获取当前企业的度电单价
        paramMap.put("order", "asc");
        List<Map<String, Object>> resMap = govMapper.findEleCostRank(paramMap);
        int rank = 1;
        int number = 0;
        String localUnitPrice = "0";
        if (!CollectionUtils.isEmpty(resMap)) {
            String industryType = StringUtil.getString(resMap.get(0).get("industryType"));
            localUnitPrice = StringUtil.getString(resMap.get(0).get("unitPrice"));
            paramMap.put("industryType", industryType);
            paramMap.put("companyId", "");
            List<Map<String, Object>> entAllInfoList = govMapper.findEleCostRank(paramMap);
            number = entAllInfoList.size();
            if (!StringUtil.isEmpty(localUnitPrice)) {
                BigDecimal localUnitPriced = new BigDecimal(localUnitPrice);
                for (int i = 0; i < entAllInfoList.size(); i++) {
                    BigDecimal unitPriced = new BigDecimal(StringUtil.getString(entAllInfoList.get(i).get("unitPrice")));
                    if (localUnitPriced.compareTo(unitPriced) <= 0) {
                        rank = i + 1;
                        break;
                    }
                }
            }
        }
        Map<String, Object> rankData = Maps.newHashMap();
        rankData.put("unitPrice", localUnitPrice);
        rankData.put("rank", rank + "/" + number);
        result.setData(rankData);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 查询企业负荷分析
     * 平均负荷 = 用电量 / 小时数
     * 当月用电量没有的话，就不算
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/findFuheAnalysis", method = RequestMethod.POST)
    @ResponseBody
    public Result findFuheAnalysis(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        // 查询户号额定容量
        String fixedContract = govMapper.getFixedContract(paramMap);
        //查询最高负荷
        List<Map<String, Object>> fuHeList = govMapper.getFuHeList(paramMap);
        //查询平均负荷
        List<Map<String, Object>> avgFuHeList = govMapper.getAvgFuHeList(paramMap);
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("fixedContract", fixedContract);
        resMap.put("fuHeList", fuHeList);
        resMap.put("avgFuHeList", avgFuHeList);
        result.setData(resMap);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 用电结构
     * //计算分数
     * 1/2*（（企业谷电占比-同行业谷电占比最小值）/（同行业谷电占比最大值-同行业谷电占比最小值）*100）
     * +1/2*（（同行业度电单价最大值-企业度电单价）/（同行业度电单价最大值-同行业度电单价最小值）*100）
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/findCompanyEleStructure", method = RequestMethod.POST)
    @ResponseBody
    public Result findCompanyStructure(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        Map<String, Object> resMap = Maps.newHashMap();
        String companyId = StringUtil.getString(paramMap.get("companyId"));
        String date = StringUtil.getString(paramMap.get("date"));
        String industryType = govMapper.getIndustryTypeByCompanyId(companyId);
        // 获取企业谷电占比
        List<Map<String, Object>> guProportion = govMapper.getCompanyGuEleProportion(paramMap);
        String companyProportion = StringUtil.getString(guProportion.get(0).get("proportionValue"));
        List<ScoreDTO> sortedEmp = getCompanyEleStructureList(industryType, companyId, date);
        // 排序
        int companyEmp = 0;
        int eleStructureScore = 0;
        for (int k = 0; k < sortedEmp.size(); k++) {
            String companyIdK = sortedEmp.get(k).getCompanyId();
            if (companyIdK.equals(companyId)) {
                companyEmp = k + 1;
                eleStructureScore = sortedEmp.get(k).getScore();
            }
        }
        //当前分数
        resMap.put("eleStructureScore", eleStructureScore);
        //超过同行业 排名
        resMap.put("industryRank", Integer.parseInt(StringUtil.getString((sortedEmp.size() - companyEmp) * 100 / sortedEmp.size())));
        Map<String, Object> efficiencyMap = Maps.newHashMap();
        //获取度电单价
        Map<String, Object> duDianMap = Maps.newHashMap();
        duDianMap.put("companyId", companyId);
        duDianMap.put("date", date);
        List<Map<String, Object>> duDiamRes = govMapper.findEleCostRank(duDianMap);
        String unitPrice = StringUtil.getString(duDiamRes.get(0).get("unitPrice"));
        efficiencyMap.put("elePrice", unitPrice);
        efficiencyMap.put("guProportion", companyProportion);
        resMap.put("efficiencyMap", efficiencyMap);
        result.setData(resMap);
        return result;
    }

    /**
     * @Author:lio
     * @Description:用电结构 分数
     * @Date :7:13 下午 2021/1/25
     */
    private List<ScoreDTO> getCompanyEleStructureList(String industryType, String companyId, String date) {
        //获取同行业谷电占比最小值
        Map<String, Object> allProportion = Maps.newHashMap();
        allProportion.put("industryType", industryType);
        allProportion.put("date", date);
        allProportion.put("order", "asc");
        List<Map<String, Object>> guProportion = govMapper.getCompanyGuEleProportion(allProportion);
        String guMinMinProportion = StringUtil.getStringZero(guProportion.get(0).get("proportionValue"));
        //同行业谷电占比最大值
        String guMaxProportion = StringUtil.getStringZero(guProportion.get(guProportion.size() - 1).get("proportionValue"));
        //获取同行业度电单价最大值
        allProportion.put("order", "desc");
        List<Map<String, Object>> industryUnitPrice = govMapper.findEleCostRank(allProportion);
        String maxUnitPrice = StringUtil.getStringZero(industryUnitPrice.get(0).get("unitPrice"));
        //获取同行业度电单价最小
        String industryMin = StringUtil.getStringZero(industryUnitPrice.get(industryUnitPrice.size() - 1).get("unitPrice"));
        //TODO 开始计算分数
        String industryGuSub = StringUtil.getStringZero(BigDecimalUtil.subtract(guMaxProportion, guMinMinProportion));
        String industrySub = StringUtil.getStringZero(BigDecimalUtil.subtract(maxUnitPrice, industryMin));
        //当前企业分数
        Map<String, Object> allMap = Maps.newHashMap();
        allMap.put("industryType", industryType);
        allMap.put("order", "asc");
        allMap.put("date", date);
        //获取同行业所有谷电占比
        List<Map<String, Object>> guAllProportion = govMapper.getCompanyGuEleProportion(allMap);
        //获取同行业 所有企业度电单价
        List<Map<String, Object>> unitAllPriceList = govMapper.findEleCostRank(allMap);
        //计算所有分数
        List<ScoreDTO> scoreMapList = Lists.newArrayList();
        for (int i = 0; i < guAllProportion.size(); i++) {
            String companyIdi = StringUtil.getString(guAllProportion.get(i).get("companyId"));
            for (int j = 0; j < unitAllPriceList.size(); j++) {
                String companyIdj = StringUtil.getString(unitAllPriceList.get(j).get("companyId"));
                String companyProportionI = StringUtil.getString(guAllProportion.get(i).get("proportionValue"));
                if (companyIdi.equals(companyIdj)) {
                    ScoreDTO scoreDTO = new ScoreDTO();
                    String companyUnitPriceJ = StringUtil.getString(unitAllPriceList.get(j).get("unitPrice"));
                    int scoreJ = getEleStructureScore(companyProportionI, companyUnitPriceJ, maxUnitPrice, guMinMinProportion, industryGuSub, industrySub);
                    scoreDTO.setScore(scoreJ);
                    scoreDTO.setCompanyId(companyIdj);
                    scoreMapList.add(scoreDTO);
                }
            }
        }
        List<ScoreDTO> sortedEmp = scoreMapList.stream().sorted(Comparator.comparing(ScoreDTO::getScore).reversed()).collect(Collectors.toList());
        return sortedEmp;
    }


    /**
     * @Author:lio
     * @Description: 根据企业企业谷电占比
     * //        1/2*（（企业谷电占比-同行业谷电占比最小值）/（同行业谷电占比最大值-同行业谷电占比最小值）*100）
     * //        +1/2*（（同行业度电单价最大值-企业度电单价）/（同行业度电单价最大值-同行业度电单价最小值）*100）
     * @Date :9:55 上午 2021/1/25
     */
    private int getEleStructureScore(String companyProportion, String companyUnitPrice, String maxUnitPrice, String guMinMinProportion, String industryGuSub, String industrySub) {
        int eleStructureScore = 0;
        String guScore = "0";
        String eleScore = "0";
        // 企业谷电占比-同行业谷电占比最小值）
        String companyGuSub = BigDecimalUtil.subtract(companyProportion, guMinMinProportion);
        if (Double.parseDouble(companyGuSub) > 0 && Double.parseDouble(industryGuSub) > 0) {
            guScore = BigDecimalUtil.device("1", BigDecimalUtil.multiply("2"
                    , BigDecimalUtil.device(companyGuSub, BigDecimalUtil.multiply("100", industryGuSub))));
        }
        String companyEleSub = BigDecimalUtil.subtract(maxUnitPrice, companyUnitPrice);
        if (Double.parseDouble(industrySub) > 0 && Double.parseDouble(companyEleSub) > 0) {
            eleScore = BigDecimalUtil.device("1", BigDecimalUtil.multiply("2"
                    , BigDecimalUtil.device(companyEleSub, BigDecimalUtil.multiply("100", industrySub))));
        }
        BigDecimal bd = new BigDecimal(BigDecimalUtil.add(guScore, eleScore));
        bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
        eleStructureScore = bd.intValue();
        return eleStructureScore;
    }


    /**
     * @Author:lio
     * @Description: 能效指标
     * /2*（（同行业能耗总量最大值-企业能耗总量）/（同行业能耗总量最大值-同行业能耗总量最小值）*100）
     * +1/2*（（同行业单位GDP能耗量最大值-企业单位GDP能耗量）/（同行业单位GDP能耗量最大值-同行业单位GDP能耗量最小值）*100）
     * 单位GDP能耗总量  能耗总量/ 开票额
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/findEnergyEfficiency", method = RequestMethod.POST)
    @ResponseBody
    public Result findEnergyEfficiency(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        //获取企业能耗总量
        String companyId = StringUtil.getString(paramMap.get("companyId"));
        String date = StringUtil.getString(paramMap.get("date"));
        Map<String, Object> reqCompanyMap = Maps.newHashMap();
        reqCompanyMap.put("companyId", companyId);
        reqCompanyMap.put("date", date);
        //获取企业能耗总量
        List<Map<String, Object>> companyEnergyMap = govMapper.getEntAllEnergyInfoByCompany(reqCompanyMap);
        // 企业能耗
        String companyTce = StringUtil.getString(companyEnergyMap.get(0).get("tce"));
        // 单位GDP能耗量
        String companyTceGDP = StringUtil.getString(companyEnergyMap.get(0).get("tceGDP"));
        String industryType = StringUtil.getString(companyEnergyMap.get(0).get("industryType"));
        List<ScoreDTO> sortedEmp = getEnergyEfficiencyList(companyId, date, industryType);
        int companyEmp = 0;
        int energyEfficiencyScore = 0;
        for (int k = 0; k < sortedEmp.size(); k++) {
            String companyIdK = sortedEmp.get(k).getCompanyId();
            if (companyIdK.equals(companyId)) {
                companyEmp = k + 1;
                energyEfficiencyScore = sortedEmp.get(k).getScore();
            }
        }
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("energyEfficiencyScore", energyEfficiencyScore);
        resMap.put("industryRank", Integer.parseInt(StringUtil.getString((sortedEmp.size() - companyEmp) * 100 / sortedEmp.size())));
        Map<String, Object> efficiencyMap = Maps.newHashMap();
        efficiencyMap.put("energySum", StringUtil.getStringZero(companyTce));
        efficiencyMap.put("energyGDPSum", StringUtil.getStringZero(companyTceGDP));
        resMap.put("efficiencyMap", efficiencyMap);
        result.setData(resMap);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 能效指标
     * @Date :7:15 下午 2021/1/25
     */
    private List<ScoreDTO> getEnergyEfficiencyList(String companyId, String date, String industryType) {
        Map<String, Object> energyMap = Maps.newHashMap();
        energyMap.put("date", date);
        energyMap.put("industryType", industryType);
        energyMap.put("order", "asc");
        List<Map<String, Object>> energyMapList = govMapper.getEntAllEnergyInfoByCompany(energyMap);
        //同行业能耗总量最小值
        String allTceMin = StringUtil.getStringZero(energyMapList.get(0).get("tce"));
        //同行业能耗总量最大值
        String allTceMax = StringUtil.getStringZero(energyMapList.get(energyMapList.size() - 1).get("tce"));
        //同行业单位GDP能耗量最小值
        String allTceGDPMin = StringUtil.getStringZero(energyMapList.get(0).get("tceGDP"));
        //同行业单位GDP能耗量大值
        String allTceGDPMax = StringUtil.getStringZero(energyMapList.get(energyMapList.size() - 1).get("tceGDP"));
        //开始计算分数
        String tceSub = BigDecimalUtil.subtract(allTceMax, allTceMin);
        String gdpSub = BigDecimalUtil.subtract(allTceGDPMax, allTceGDPMin);
        //超过同行业的用户百分比
        List<ScoreDTO> scoreList = Lists.newArrayList();
        for (int i = 0; i < energyMapList.size(); i++) {
            String tceI = StringUtil.getString(energyMapList.get(i).get("tce"));
            String gdpI = StringUtil.getString(energyMapList.get(i).get("tceGDP"));
            String companyI = StringUtil.getString(energyMapList.get(i).get("companyId"));
            int scoreI = getEnergyEfficiencyScore(tceI, gdpI, tceSub, gdpSub, allTceMax, allTceGDPMax);
            ScoreDTO scoreDto = new ScoreDTO();
            scoreDto.setCompanyId(companyI);
            scoreDto.setScore(scoreI);
            scoreList.add(scoreDto);
        }
        // 排序
        List<ScoreDTO> sortedEmp = scoreList.stream().sorted(Comparator.comparing(ScoreDTO::getScore).reversed()).collect(Collectors.toList());
        return sortedEmp;
    }

    /**
     * @Author:lio
     * @Description:能效指标 分数计算
     * @Date :4:16 下午 2021/1/25
     */
    private int getEnergyEfficiencyScore(String companyTce, String companyTceGDP, String tceSub, String gdpSub, String allTceMax, String allTceGDPMax) {
        String tceScore = "0";
        String gdpScore = "0";
        String industryTceSub = BigDecimalUtil.subtract(allTceMax, companyTceGDP);
        if (Double.parseDouble(tceSub) > 0 && Double.parseDouble(industryTceSub) > 0) {
            tceScore = BigDecimalUtil.device("1", BigDecimalUtil.multiply("2"
                    , BigDecimalUtil.device(industryTceSub, BigDecimalUtil.multiply("100", tceSub))));
        }

        String industryGDPSub = BigDecimalUtil.subtract(allTceGDPMax, companyTceGDP);
        if (Double.parseDouble(industryGDPSub) > 0 && Double.parseDouble(gdpSub) > 0) {
            gdpScore = BigDecimalUtil.device("1", BigDecimalUtil.multiply("2"
                    , BigDecimalUtil.device(industryGDPSub, BigDecimalUtil.multiply("100", tceSub))));
        }
        BigDecimal bd = new BigDecimal(BigDecimalUtil.add(tceScore, gdpScore));
        bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
        int energyEfficiencyScore = bd.intValue();
        return energyEfficiencyScore;
    }

    /**
     * @Author:lio
     * @Description: 用能水平同行业排名
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/findEnergyIndustryRank", method = RequestMethod.POST)
    @ResponseBody
    public Result findEnergyIndustryRank(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        String date = StringUtil.getString(paramMap.get("date"));
        String companyId = StringUtil.getString(paramMap.get("companyId"));
        String industryType = govMapper.getIndustryTypeByCompanyId(companyId);
        // 获取所有的企业能效指标分数
        List<ScoreDTO> scoreEfficiencyList = getEnergyEfficiencyList(companyId, date, industryType);
        List<ScoreDTO> scoreStructureList = getCompanyEleStructureList(industryType, companyId, date);
        //计算总分数
        List<ScoreDTO> scoreList = Lists.newArrayList();
        for (int i = 0; i < scoreEfficiencyList.size(); i++) {
            String companyIdI = scoreEfficiencyList.get(i).getCompanyId();
            for (int j = 0; j < scoreStructureList.size(); j++) {
                String companyIdJ = scoreStructureList.get(j).getCompanyId();
                if (companyIdI.equals(companyIdJ)) {
                    ScoreDTO scoreDto = new ScoreDTO();
                    int scoreI = scoreEfficiencyList.get(i).getScore();
                    int scoreJ = scoreStructureList.get(j).getScore();
                    scoreDto.setCompanyId(companyIdI);
                    scoreDto.setScore((scoreJ + scoreI) / 2);
                    scoreList.add(scoreDto);
                }
            }
        }
        List<ScoreDTO> sortedEmp = scoreList.stream().sorted(Comparator.comparing(ScoreDTO::getScore).reversed()).collect(Collectors.toList());
        int companyEmp = 0;
        int rankScore = 0;
        for (int k = 0; k < sortedEmp.size(); k++) {
            String companyIdK = sortedEmp.get(k).getCompanyId();
            if (companyIdK.equals(companyId)) {
                companyEmp = k + 1;
                rankScore = sortedEmp.get(k).getScore();
            }
        }
        Map<String, Object> maps = Maps.newHashMap();
        maps.put("rankScore", rankScore);
        maps.put("rank", companyEmp + "/" + sortedEmp.size());
        result.setData(maps);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 企业电力户号
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/findCompanyEleAccNumber", method = RequestMethod.POST)
    @ResponseBody
    public Result findCompanyAccNumber(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        String companyId = StringUtil.getString(paramMap.get("companyId"));
        List<String> accNumberList = govMapper.findCompanyAccNumber(companyId);
        result.setData(accNumberList);
        return result;
    }


    /**
     * @Author:lio
     * @Description: 获取企业填报信息
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/getCompanyData", method = RequestMethod.POST)
    @ResponseBody
    public Result getCompanyData(@RequestBody Map<String, Object> companyDataMap) {
        Result result = new Result();
        String companyId = StringUtil.getString(companyDataMap.get("companyId"));
        String date = StringUtil.getString(companyDataMap.get("date"));
        AssertUtil.NotBlank(companyId, "企业信息不能为空");
        AssertUtil.NotBlank(date, "日期不能为空");
        Map<String, Object> companyData = dataDetailService.getCompanyData(companyDataMap);
        result.setData(companyData);
        return result;
    }


    /**
     * @Author:lio
     * @Description: 企业数据填报
     * @Date :11:11 上午 2021/1/19
     */
    @RequestMapping(value = "/gov/updateCompanyData", method = RequestMethod.POST)
    @ResponseBody
    public Result updateCompanyData(@RequestBody Map<String, Object> companyDataMap) {
        Result result = new Result();
        //更新或者新增企业相关数据
        String date = StringUtil.getString(companyDataMap.get("date"));
        AssertUtil.NotBlank(companyDataMap.get("date"), "日期不能为空");
        AssertUtil.NotBlank(companyDataMap.get("companyId"), "企业信息不能为空");
        AssertUtil.NotBlank(companyDataMap.get("userId"), "用户ID不能为空");
        //判断修改的日期是否超过了当前日期
        // 超过了最大月份则不能导入
        String updateYear = date.split("-")[0];
        String updateMonth = date.split("-")[1];
        LocalDate localDate = LocalDate.now();
        int monthValue = localDate.getMonthValue();
        int yearValue = localDate.getYear();
        if (Integer.parseInt(updateYear) > yearValue || (Integer.parseInt(updateYear) == yearValue && Integer.parseInt(updateMonth) > monthValue)) {
            AssertUtil.ThrowSystemErr("选择的日期范围错误");
        }
        //更新或新增
        String message = dataDetailService.insertOrUpdateCompanyData(companyDataMap);
        if (StringUtil.isNotEmpty(message)) {
            result.failure();
            result.setMsg(message);
        }
        return result;
    }


}
