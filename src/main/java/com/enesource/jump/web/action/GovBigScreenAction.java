package com.enesource.jump.web.action;

import java.time.LocalDate;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.enesource.jump.web.cache.EnergyInfoLocalCacheManager;
import com.enesource.jump.web.enums.ENUM_DATA_TYPE;
import com.enesource.jump.web.redis.IRedisService;
import com.enesource.jump.web.service.IEntInfoService;
import com.enesource.jump.web.utils.StringUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import groovy.lang.ObjectRange;
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
import com.enesource.jump.web.dao.IGovBigScreen;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dto.ExceptionLog;
import com.enesource.jump.web.dto.GovMapInfoDTO;
import com.enesource.jump.web.dto.ParamDTO;
import com.enesource.jump.web.utils.Conf;
import com.enesource.jump.web.utils.MapUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import springfox.documentation.spring.web.json.Json;

/**
 * 政府大屏
 *
 * @author EDZ
 */
@Controller
@CrossOrigin
public class GovBigScreenAction extends BaseAction {

    @Autowired
    IGovBigScreen govBigScreen;

    @Autowired
    IGovMapper govMapper;

    @Autowired
    IEntInfoService entInfoService;

    @Autowired
    private IRedisService redisService;

    /**
     * 多能监测全景，地图数据展示
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping("/show/findGovMapData")
    @ResponseBody
    public Result findGovMapData(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        String[] checkParamsMap = {"userId"};
        String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
        if (null != errorString) {
            result.setCode(Conf.ERROR);
            result.setMsg(errorString);
            return result;
        }

        try {
            List<Map<String, Object>> rm = govBigScreen.findGovMapData(paramMap);


            result.setData(rm);

        } catch (Exception e) {
            logger.error("查询政府页面数据错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/findGovMapData");
            exceptionLog.setName("查询政府页面数据错误");
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
     * 多能监测全景，统计数据
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping("/show/findGovStatisticsData")
    @ResponseBody
    public Result findGovStatisticsData(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        String[] checkParamsMap = {"userId", "areaLabel"};
        String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
        if (null != errorString) {
            result.setCode(Conf.ERROR);
            result.setMsg(errorString);
            return result;
        }
        try {
            Map<String, Object> am = govBigScreen.findGovStatisticsData(paramMap);
            Map<String, Object> bm = govBigScreen.findGovCountStatisticsData(paramMap);
            if (am != null) {
                am.putAll(bm);
            } else {
                am = bm;
            }
            result.setData(am);

        } catch (Exception e) {
            logger.error("查询政府统计数据错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/findGovStatisticsData");
            exceptionLog.setName("查询政府统计数据错误");
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
     * 多能监测全景，统计数据维护
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping("/show/updateGovStatisticsData")
    @ResponseBody
    public Result updateGovStatisticsData(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        String[] checkParamsMap = {"userId",
                "societyConsumeAct", "societyConsumeTag",
                "societyPowerAct", "societyPowerTag",
                "societyWaterAct", "societyWaterTag",
                "gaeRateAct", "gaeRateTag",
                "cleanRateAct", "cleanRateTag",
                "photovoSum", "batterySum", "chargeSum", "waterPowerSum", "garbagePowerSum", "entSum",
                "societyIndustryAct",
                "fuheMax",
                "fuheAvg",
                "areaContract",
                "capacityProportion",
                "societyIndustryTag",};
        String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
        if (null != errorString) {
            result.setCode(Conf.ERROR);
            result.setMsg(errorString);
            return result;
        }

        // TODO
        try {
            Double.valueOf(paramMap.get("societyConsumeAct").toString());
            Double.valueOf(paramMap.get("societyConsumeTag").toString());
            Double.valueOf(paramMap.get("societyPowerAct").toString());
            Double.valueOf(paramMap.get("societyPowerTag").toString());
            Double.valueOf(paramMap.get("societyWaterAct").toString());
            Double.valueOf(paramMap.get("societyWaterTag").toString());
            Double.valueOf(paramMap.get("gaeRateAct").toString());
            Double.valueOf(paramMap.get("gaeRateTag").toString());
            Double.valueOf(paramMap.get("cleanRateAct").toString());
            Double.valueOf(paramMap.get("cleanRateTag").toString());
            Double.valueOf(paramMap.get("societyIndustryAct").toString());
            Double.valueOf(paramMap.get("societyIndustryTag").toString());
            Double.valueOf(paramMap.get("fuheMax").toString());
            Double.valueOf(paramMap.get("fuheAvg").toString());
            Double.valueOf(paramMap.get("areaContract").toString());
            Double.valueOf(paramMap.get("capacityProportion").toString());
            Integer.valueOf(paramMap.get("photovoSum").toString());
            Integer.valueOf(paramMap.get("batterySum").toString());
            Integer.valueOf(paramMap.get("chargeSum").toString());
            Integer.valueOf(paramMap.get("waterPowerSum").toString());
            Integer.valueOf(paramMap.get("garbagePowerSum").toString());
            Integer.valueOf(paramMap.get("entSum").toString());

        } catch (NumberFormatException e) {
            result.setCode(Conf.ERROR);
            result.setMsg("输入数据类型不正确");

            return result;
        }

        try {
            govBigScreen.updateGovCountStatisticsData(paramMap);

        } catch (Exception e) {
            logger.error("统计数据维护错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/updateGovStatisticsData");
            exceptionLog.setName("统计数据维护错误");
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
     * 多能监测全景，点曲线
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/show/siteCurve", method = RequestMethod.POST)
    @ResponseBody
    public Result siteCurve(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"siteId"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            List<Map<String, Object>> m = govBigScreen.siteIdCurve(paramMap);

            result.setData(m);
        } catch (Exception e) {
            logger.error("查询场站曲线错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/show/siteCurve");
            exceptionLog.setName("查询场站曲线错误");
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
    @RequestMapping(value = "/show/getGovList", method = RequestMethod.POST)
    @ResponseBody
    public Result getGovList(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            String[] checkParamsMap = {"key"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            List<Map<String, Object>> am = govBigScreen.getGovList(paramMap);

            result.setData(am);

        } catch (Exception e) {
            logger.error("查询政府列表数据错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/getGovList");
            exceptionLog.setName("查询政府列表数据错误");
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
     * 企业用能全景--地图展示数据
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/parkManage/getParkAddress", method = RequestMethod.POST)
    @ResponseBody
    public Result getParkAddress(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
//        String firstDate = govMapper.getFirstDate();
        
        String firstDate = paramMap.get("date").toString();
        paramMap.put("date", firstDate);
        
        List<Map<String, Object>> am = govMapper.findEnergySpeedByEnergyTarget(paramMap);
        result.setData(am);
        return result;
    }


    /**
     * 企业用能全景--地图展示数据
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/parkManage/getParkAddressRate", method = RequestMethod.POST)
    @ResponseBody
    public Result getParkAddressRate(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        String firstDate = paramMap.get("date").toString();
        Map<String, Object> am = Maps.newHashMap();
        if (!StringUtil.isEmpty(firstDate)) {
            String dateChange = "";
            paramMap.put("date", firstDate);
            am = govBigScreen.getParkTceAddressRate(paramMap);
            if (StringUtil.isNotEmpty(firstDate)) {
                dateChange = firstDate.split("-")[0] + "年" + firstDate.split("-")[1] + "月";
            }
            am.put("date", dateChange);
        }
        result.setData(am);
        return result;
    }

    /**
     * 企业用能全景--地图展示数据
     * 方法废弃了，不用展示右侧的根据日期来说轮播数据
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/getParkAddressSum", method = RequestMethod.POST)
    @ResponseBody
    public Result getParkAddressSum(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 获取最新数据月份
        String date = govMapper.getFirstDate();
        paramMap.put("date", date);
        List<Map<String, Object>> am = govBigScreen.getParkAddress(paramMap);
        // 企业筛选
        List<Map<String, Object>> energySpeedByCompany = govMapper.findEnergySpeedByEnergyTarget(paramMap);
        resultMap.put("", "");
        result.setData(resultMap);
        return result;
    }

    /**
     * 企业用能全景--企业明细数据
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/parkManage/pointDetail", method = RequestMethod.POST)
    @ResponseBody
    public Result pointDetail(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"energyType", "companyId"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);
                return result;
            }
            Map<String, Object> resultMap = new HashMap<String, Object>();
            List<Map<String, Object>> resMap = Lists.newArrayList();
            // 电力数据 要根据户号筛选
            if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(StringUtil.getString(paramMap.get("energyType")))) {
                resMap = govBigScreen.pointEleDetail(paramMap);
            } else {
                resMap = govBigScreen.pointDetail(paramMap);
            }

            Double sumValue = 0.0;
            if (resMap != null && resMap.size() > 0) {
                for (Map<String, Object> map : resMap) {
                    sumValue += Double.valueOf(map.get("value").toString());
                }
            }
            resultMap.put("sumValue", Double.valueOf(df2.format(sumValue)));
            resultMap.put("list", resMap);

            List<Map<String, Object>> thisl = Lists.newArrayList();
            if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(StringUtil.getString(paramMap.get("energyType")))) {
                thisl = govBigScreen.pointEleThisDetail(paramMap);
            } else {
                thisl = govBigScreen.pointThisDetail(paramMap);
            }
            Double thisSumValue = 0.0;
            if (thisl != null && thisl.size() > 0) {
                for (Map<String, Object> map : thisl) {
                    thisSumValue += Double.valueOf(map.get("value").toString());
                }
            }
            resultMap.put("thisSumValue", Double.valueOf(df2.format(thisSumValue)));
            resultMap.put("thisList", thisl);
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("企业大屏公司明细数据错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/show/pointDetail");
            exceptionLog.setName("企业大屏公司明细数据错误");
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
     * 企业用能全景--企业能耗排名
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/entOrderList", method = RequestMethod.POST)
    @ResponseBody
    public Result entOrderList(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"date", "microPark"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            ParamDTO dto;

            dto = (ParamDTO) MapUtils.map2JavaBean(ParamDTO.class, paramMap);

//			if (dto.getPage() != null) {
//				PageHelper.startPage(dto.getPage(), dto.getPageSize());
//			}

            List<Map<String, Object>> l = govBigScreen.entOrderList(dto);

            resultMap.put("list", l);

            result.setData(new PageInfo(l));
        } catch (Exception e) {
            logger.error("企业大屏公司明细数据错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/show/pointDetail");
            exceptionLog.setName("企业大屏公司明细数据错误");
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
     * 区域能耗全景，统计数据
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/getParkCompanyCounts", method = RequestMethod.POST)
    @ResponseBody
    public Result getParkCompanyCounts(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"userId", "areaLabel"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();

            if ("wenzhou".equals(paramMap.get("areaLabel").toString())) {
                l = govBigScreen.getParkCompanyCountsBywhenzhou(paramMap);
            } else {
                l = govBigScreen.getParkCompanyCounts(paramMap);
            }

            resultMap.put("list", l);

            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("能耗全景错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/getParkCompanyCounts");
            exceptionLog.setName("能耗全景错误");
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
     * 区域能耗全景，能源总量
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/getParkTotalGroup", method = RequestMethod.POST)
    @ResponseBody
    public Result getParkCompanyGroup(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"userId"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            List<Map<String, Object>> l = govBigScreen.getParkCompanyCounts(paramMap);

            resultMap.put("list", l);

            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("企业大屏公司明细数据错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/show/pointDetail");
            exceptionLog.setName("企业大屏公司明细数据错误");
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
     * ***************************************************************多能全景监测项目统计*********************************************************************************************
     */


    /**
     * 多能全景统计
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/multiEnergyStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Result multiEnergyStatistics(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"userId"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            Map<String, Object> bm = govBigScreen.findGovCountStatisticsData(paramMap);

            Map<String, Object> am = govBigScreen.multiEnergyStatistics(paramMap);

            resultMap.put("capacity", am);
            resultMap.put("project", bm);

            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("多能全景统计错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/multiEnergyStatistics");
            exceptionLog.setName("多能全景统计错误");
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
     * 能源项目列表
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/parkManage/projectList", method = RequestMethod.POST)
    @ResponseBody
    public Result projectListBysiteType(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"userId", "siteType"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            GovMapInfoDTO dto = new GovMapInfoDTO();

            dto = MapUtils.map2Java(dto, paramMap);

            if (dto.getPage() != null) {
                PageHelper.startPage(dto.getPage(), dto.getPageSize());
            }

            List<Map<String, Object>> bm = govBigScreen.projectListBysiteType(dto);

            result.setData(new PageInfo(bm));
        } catch (Exception e) {
            logger.error("能源项目列表错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/projectList");
            exceptionLog.setName("能源项目列表错误");
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
     * 能源项目更新
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/updateProjectInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result updateProjectInfo(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"siteId", "siteType", "siteName"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            GovMapInfoDTO dto = new GovMapInfoDTO();

//			private String chargeCountSum; 	
//			private String changePowerSum; 

            if (paramMap.get("chargeCountSum") != null && "".equals(paramMap.get("chargeCountSum").toString())) {
                paramMap.put("chargeCountSum", "0");
            }

            if (paramMap.get("changePowerSum") != null && "".equals(paramMap.get("changePowerSum").toString())) {
                paramMap.put("changePowerSum", "0");
            }

            if (paramMap.get("capacity") != null && "".equals(paramMap.get("capacity").toString())) {
                paramMap.put("capacity", "0");
            }
            if (paramMap.get("capacityProportion") != null && "".equals(paramMap.get("capacityProportion").toString())) {
                paramMap.put("capacityProportion", "0");
            }

            dto = MapUtils.map2Java(dto, paramMap);

            govMapper.mergeGovMapInfoDTO(dto);

        } catch (Exception e) {
            logger.error("能源项目更新错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/updateProjectInfo");
            exceptionLog.setName("能源项目更新错误");
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
     * ***************************************************************小微园区弹窗*********************************************************************************************
     */

    /**
     * 小微园区列表
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/findMicroParkList", method = RequestMethod.POST)
    @ResponseBody
    public Result findMicroParkList(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

//            result.setData(govMapper.findMicroParkList(paramMap));
            paramMap.put("type", "park");
            result.setData(govMapper.getValueList(paramMap));

        } catch (Exception e) {
            logger.error("小微园区查询错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/findMicroParkList");
            exceptionLog.setName("小微园区查询错误");
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
     * 小微园区企业查询
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/findCompanyListMicroPark", method = RequestMethod.POST)
    @ResponseBody
    public Result findCompanyListMicroPark(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"microPark"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            result.setData(govMapper.findCompanyListMicroPark(paramMap));

        } catch (Exception e) {
            logger.error("小微园区查询错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/findCompanyListMicroPark");
            exceptionLog.setName("小微园区企业查询错误");
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
     * 小微园区企业统计
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/microParkStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Result microParkStatistics(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"microPark", "year"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            List<Map<String, Object>> monthStatistics = govMapper.findEngryConsumeByMonth_tce(paramMap);

            List<Map<String, Object>> thisList = new ArrayList<Map<String, Object>>();

            thisList = monthStatistics;

            if (monthStatistics != null && monthStatistics.size() > 0) {
                Double temp = 0.0;

                for (Map<String, Object> map : monthStatistics) {
                    temp += Double.valueOf(map.get("thisValue").toString());
                    map.put("total", Double.valueOf(df2.format(temp)));
                }
            }

            // TODO
            List<Map<String, Object>> yearList = govMapper.findEngryConsumeByYear(paramMap);

            List<Map<String, Object>> structStatistics = govMapper.findEngryConsumeByStruct(paramMap);

            List<Map<String, Object>> quarterList = govMapper.findEngryConsumeByQuarter(paramMap);

            // 累计值
            resultMap.put("monthStatistics", monthStatistics);

            resultMap.put("structStatistics", structStatistics);

            resultMap.put("quarterList", quarterList);

            resultMap.put("yearList", yearList);


            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("能源项目更新错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/updateProjectInfo");
            exceptionLog.setName("能源项目更新错误");
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
     * **********************************************************************街区统计*************************************************************************************************
     */

    /**
     * 街区能耗总量
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/findStreetEngryConsume", method = RequestMethod.POST)
    @ResponseBody
    public Result findStreetEngryConsume(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"areaCode", "year"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            List<Map<String, Object>> yearList = govMapper.findStreetEngryConsumeByYear(paramMap);
            List<Map<String, Object>> monthList = govMapper.findStreetEngryConsumeByMonth_tce(paramMap);
            // TODO
            if (monthList != null && monthList.size() > 0) {
                Double temp = 0.0;
                for (Map<String, Object> map : monthList) {
                    temp += Double.valueOf(map.get("thisValue").toString());
                    map.put("total", Double.valueOf(df2.format(temp)));
                }
            }
            resultMap.put("yearList", yearList);
            resultMap.put("monthList", monthList);
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("街区能耗错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/findStreetEngryConsume");
            exceptionLog.setName("街区能耗错误");
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
     * 街区能耗强度
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/findStreetEngryConsumeStrength", method = RequestMethod.POST)
    @ResponseBody
    public Result findStreetEngryConsumeStrength(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"areaCode", "year"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            List<Map<String, Object>> yearList = govMapper.findStreetEngryConsumeByYear(paramMap);

            List<Map<String, Object>> quarterList = govMapper.engryConsumeIncreaseByQuarter(paramMap);

            resultMap.put("yearList", yearList);

            resultMap.put("quarterList", quarterList);

            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("街区能耗强度错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/findStreetEngryConsumeStrength");
            exceptionLog.setName("街区能耗强度错误");
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
     * 街区能耗结构
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/findStreetEngryConsumeStruct", method = RequestMethod.POST)
    @ResponseBody
    public Result findStreetEngryConsumeStruct(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"areaCode", "year"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            List<Map<String, Object>> yearList = govMapper.findStreetEngryConsumeByYear(paramMap);

            List<Map<String, Object>> monthList = govMapper.findStreetEngryConsumeByMonth_tce(paramMap);

            if (monthList != null && monthList.size() > 0) {
                Double temp = 0.0;

                for (Map<String, Object> map : monthList) {
                    temp += Double.valueOf(map.get("thisValue").toString());
                    map.put("total", Double.valueOf(df2.format(temp)));
                }
            }

            Integer lastYear = Integer.valueOf(paramMap.get("year").toString()) - 1;

            paramMap.put("year", lastYear);

            List<Map<String, Object>> structList = govMapper.findStreetEngryConsumeByStruct(paramMap);

            resultMap.put("monthList", monthList);

            resultMap.put("yearList", yearList);

            resultMap.put("structList", structList);

            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("街区能耗错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/findStreetEngryConsume");
            exceptionLog.setName("街区能耗错误");
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
     * 电量数据完整程度统计
     *
     * @param paramMap
     * @return
     */
    @JwtToken
    @RequestMapping(value = "/parkManage/findEleLoseCounts", method = RequestMethod.POST)
    @ResponseBody
    public Result findEleLoseCounts(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();

        try {
            // 查询企业信息和数据明细值
            String[] checkParamsMap = {"userId", "date"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);

                return result;
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();

            List<Map<String, Object>> l = govBigScreen.findEleLoseCounts(paramMap);

            resultMap.put("list", l);

            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("电量数据完整程度统计错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/findEleLoseCounts");
            exceptionLog.setName("电量数据完整程度统计错误");
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
     * 综合统计查询
     *
     * @param paramMap
     * @return
     */
//	@JwtToken
    @RequestMapping(value = "/parkManage/integrativeStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Result integrativeStatistics(@RequestBody Map<String, Object> paramMap) {
        Result result = new Result();
        try {
            String[] checkParamsMap = {"areaCode"};
            String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
            if (null != errorString) {
                result.setCode(Conf.ERROR);
                result.setMsg(errorString);
                return result;
            }
//			List<Map<String, Object>> returnList = govMapper.integrativeStatistics(paramMap);
            String value = redisService.get("multipleStatistics");
            if (StringUtil.isNotEmpty(value)) {
                result.setData(JSON.parseArray(value));
            } else {
                List<Map<String, Object>> returnList = entInfoService.multipleStatistics(paramMap);
                redisService.set("multipleStatistics", JSON.toJSONString(returnList));
                result.setData(returnList);
            }
        } catch (Exception e) {
            logger.error("综合统计查询错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/parkManage/integrativeStatistics");
            exceptionLog.setName("综合统计查询错误");
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

}
