package com.enesource.jump.web.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.util.ObjectUtil;
import com.enesource.jump.web.dao.IDataMaintenanceMapper;
import com.enesource.jump.web.dto.*;
import com.enesource.jump.web.enums.ENUM_DATA_TYPE;
import com.enesource.jump.web.interceptor.CheckExcelDayImportDTOExcelVerifyHandler;
import com.enesource.jump.web.interceptor.CheckExcelEleDayImportDTOExcelVerifyHandler;
import com.enesource.jump.web.interceptor.CheckExcelMonthFuHeImportDTOExcelVerifyHandler;
import com.enesource.jump.web.interceptor.CheckExcelMonthImportDTOExcelVerifyHandler;
import com.enesource.jump.web.utils.*;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.service.IAsyncService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import static java.time.temporal.ChronoUnit.DAYS;


@Service("asyncService")
@Transactional
public class AsyncServiceImpl implements IAsyncService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    IGovMapper govMapper;
    @Autowired
    IDataMaintenanceMapper iDataMaintenanceMapper;
    @Autowired
    private CheckExcelEleDayImportDTOExcelVerifyHandler checkExcelEleDayImportDTOExcelVerifyHandler;

    @Override
    @Async
    public void mergeEntInfo(List<EntInfoDTO> dtol) {

        for (EntInfoDTO entity : dtol) {
            govMapper.mergeEntInfo(entity);
        }

    }

    @Override
    @Async
    public void mergeEnergyinfo(List<EnergyinfoDTO> dtol, String energyType, String areaLabel) {

        for (EnergyinfoDTO entity : dtol) {
            govMapper.mergeEnergyinfo(entity);
        }

        // 同步中间计算表
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("energyType", energyType);
        param.put("areaLabel", areaLabel);

        govMapper.deleteAreaStatistics(param);

        govMapper.insertAreaStatistics(param);


        if ("0".equals(energyType)) {
            // 按企业与户号关联关系合并企业每月用电
            govMapper.saveEntEnergyinfoCompSum(new HashMap<String, Object>());

            // 更新产能状态
            govMapper.updateProductionStatus_0(new HashMap<String, Object>());
            govMapper.updateProductionStatus_1(new HashMap<String, Object>());
            govMapper.updateProductionStatus_2(new HashMap<String, Object>());
            govMapper.updateProductionStatus_3(new HashMap<String, Object>());
            govMapper.updateProductionStatus_4(new HashMap<String, Object>());
            govMapper.updateProductionStatus_5(new HashMap<String, Object>());

            // 更新环比值
            govMapper.updateMonthRate(new HashMap<String, Object>());

            // 更新同比值
            govMapper.updateYearRate(new HashMap<String, Object>());

            // 更新环比状态
            govMapper.updateGrowthRates_0(new HashMap<String, Object>());
            govMapper.updateGrowthRates_1(new HashMap<String, Object>());
            govMapper.updateGrowthRates_2(new HashMap<String, Object>());
            govMapper.updateGrowthRates_3(new HashMap<String, Object>());
        }

    }

    @Override
    @Async
    public void mergeEconomicsinfo(List<EconomicsinfoDTO> dtol, String areaLabel) {

        for (EconomicsinfoDTO entity : dtol) {
            govMapper.mergeEconomicsinfo(entity);
        }

        // 同步中间计算表
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("energyType", "ec");
        param.put("areaLabel", areaLabel);

        govMapper.deleteAreaStatistics(param);

        govMapper.insertAreaStatisticsEC(param);

    }

    @Override
    @Async
    public void mergeGovMapInfoDTO(List<GovMapInfoDTO> dtol) {
        for (GovMapInfoDTO entity : dtol) {

            if (entity.getSiteId() == null) {
                entity.setSiteId(govMapper.getSiteId());
            }

            govMapper.mergeGovMapInfoDTO(entity);

            DateColumnDTO tempDto = entity.getDateColumn();

            Map<String, Object> tempMap = MapUtils.java2Map(tempDto);

            GovMapInfoDetailDTO dtoCurve = new GovMapInfoDetailDTO();

            dtoCurve.setSiteId(entity.getSiteId());
            dtoCurve.setSiteName(entity.getSiteName());

            if (tempMap != null) {
                Set<String> keySet = tempMap.keySet();

                for (String key : keySet) {
                    String dateStr = key.substring(5, 9) + "-" + key.substring(9, 11) + "-01 00:00:00";

                    dtoCurve.setDate(dateStr);
                    dtoCurve.setValue(tempMap.get(key).toString());

                    govMapper.mergeGovMapCurveDTO(dtoCurve);

                }

            }

        }

    }


    @Override
    @Async
    public void mergePhotoInfoDTO(List<PhotoDTO> dtol) {
        for (PhotoDTO entity : dtol) {

            if (entity.getSiteId() == null) {
                entity.setSiteId(govMapper.getSiteId());
            }

            govMapper.mergeGovMapInfoDTO(entity);

            DateColumnDTO tempDto = entity.getDateColumn();

            Map<String, Object> tempMap = MapUtils.java2Map(tempDto);

            GovMapInfoDetailDTO dtoCurve = new GovMapInfoDetailDTO();

            dtoCurve.setSiteId(entity.getSiteId());
            dtoCurve.setSiteName(entity.getSiteName());

            if (tempMap != null) {
                Set<String> keySet = tempMap.keySet();

                for (String key : keySet) {
                    String dateStr = key.substring(5, 9) + "-" + key.substring(9, 11) + "-01 00:00:00";

                    dtoCurve.setDate(dateStr);
                    dtoCurve.setValue(tempMap.get(key).toString());

                    govMapper.mergeGovMapCurveDTO(dtoCurve);

                }

            }
        }

    }

    @Override
    @Async
    public void mergeChargeInfoDTO(List<ChargeDTO> dtol) {
        for (ChargeDTO entity : dtol) {

            if (entity.getSiteId() == null) {
                entity.setSiteId(govMapper.getSiteId());
            }

            govMapper.mergeGovMapInfoDTO(entity);

            DateColumnDTO tempDto = entity.getDateColumn();

            Map<String, Object> tempMap = MapUtils.java2Map(tempDto);

            GovMapInfoDetailDTO dtoCurve = new GovMapInfoDetailDTO();

            dtoCurve.setSiteId(entity.getSiteId());
            dtoCurve.setSiteName(entity.getSiteName());

            if (tempMap != null) {
                Set<String> keySet = tempMap.keySet();

                for (String key : keySet) {
                    String dateStr = key.substring(5, 9) + "-" + key.substring(9, 11) + "-01 00:00:00";

                    dtoCurve.setDate(dateStr);
                    dtoCurve.setValue(tempMap.get(key).toString());

                    govMapper.mergeGovMapCurveDTO(dtoCurve);

                }

            }
        }

    }

    @Override
//	@Async
    public void mergeDayValueDTO(List<ImportDayValueDTO> dtol, String date) {
        //  导入更新日电量表
        if (dtol == null || dtol.size() == 0) {
            return;
        }

        for (ImportDayValueDTO importDayValueDTO : dtol) {
            if (importDayValueDTO.getValue() != null) {
                govMapper.mergeEntEnergyinfoDay(importDayValueDTO);
            }
        }

        //  删除每日缺失户号
        govMapper.delLoseAccnumberDay(date);

        //  重新统计每日缺失户号
        govMapper.saveLoseAccnumberDay(date);


        //  删除统计每日缺失户号比例
        govMapper.delLoseStatisticsDay(date);
        //  重新统计每日缺失户号比例
        govMapper.saveLoseStatisticsDay(date);
    }

    @Override
    @Async
    public void updateEntEnergyinfoCompSumStatus() {
        // 更新产能状态
        govMapper.updateProductionStatus_0(new HashMap<String, Object>());
        govMapper.updateProductionStatus_1(new HashMap<String, Object>());
        govMapper.updateProductionStatus_2(new HashMap<String, Object>());
        govMapper.updateProductionStatus_3(new HashMap<String, Object>());
        govMapper.updateProductionStatus_4(new HashMap<String, Object>());
        govMapper.updateProductionStatus_5(new HashMap<String, Object>());

        govMapper.updateProductionStatus_week_0(new HashMap<String, Object>());
        govMapper.updateProductionStatus_week_1(new HashMap<String, Object>());
        govMapper.updateProductionStatus_week_2(new HashMap<String, Object>());
        govMapper.updateProductionStatus_week_3(new HashMap<String, Object>());
        govMapper.updateProductionStatus_week_4(new HashMap<String, Object>());
        govMapper.updateProductionStatus_week_5(new HashMap<String, Object>());

        // 更新环比值
        govMapper.updateMonthRate(new HashMap<String, Object>());

        // 更新同比值
        govMapper.updateYearRate(new HashMap<String, Object>());

        // 更新环比状态
        govMapper.updateGrowthRates_0(new HashMap<String, Object>());
        govMapper.updateGrowthRates_1(new HashMap<String, Object>());
        govMapper.updateGrowthRates_2(new HashMap<String, Object>());
        govMapper.updateGrowthRates_3(new HashMap<String, Object>());

        govMapper.updateGrowthRates_week_0(new HashMap<String, Object>());
        govMapper.updateGrowthRates_week_1(new HashMap<String, Object>());
        govMapper.updateGrowthRates_week_2(new HashMap<String, Object>());
        govMapper.updateGrowthRates_week_3(new HashMap<String, Object>());

    }

    @Override
    @Async
    public void updateProductionStatus(Map<String, Object> paramMap) {
        // 更新产能比例
        govMapper.updateProductionValue(paramMap);
        // 更新产能状态
        govMapper.updateProductionStatus_0(paramMap);
        govMapper.updateProductionStatus_1(paramMap);
        govMapper.updateProductionStatus_2(paramMap);
        govMapper.updateProductionStatus_3(paramMap);
        govMapper.updateProductionStatus_4(paramMap);
        govMapper.updateProductionStatus_5(paramMap);

    }

    /**
     * @Author:lio
     * @Description: 更新导入后的用能数据
     * 年的date = yyyy
     * 日的date = yyyy-mm
     * @Date :10:07 上午 2021/1/29
     */
    @Override
    @Async
    public void updateEnergyInfo(ExcelDataImportDTO dataImportDto) {
        String dataType = dataImportDto.getDataType();
        String tce = ENUM_DATA_TYPE.getTceByCode(dataImportDto.getDataType());
        if (!StringUtil.isNotEmpty(tce)) {
            tce = "1";
        }
        dataImportDto.setTce(tce);
        if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataType)) {
            // 更新汇总 t_ent_energyinfo
            //查询当前时间有没有电量信息，有则更新，没有则新增，
            // 这个是跟着企业来的,只用保留日表里面有的企业就行了
            //直接删除当前月的能耗，然后新增能耗数据
            govMapper.delEenergyInfo(dataImportDto);
            govMapper.insertImportEleEenergyInfo(dataImportDto);
//            //更新周产能
//            //获取当前日期是在周几 因为周数都是完整周 所以周数日期是整数
//            LocalDate startDate = getLocalDateByStr(date);
//            String startDayOfWeek = StringUtil.getString(startDate.getDayOfWeek());
//            int addStartDay = getAddStartDay(startDayOfWeek);
//            String startTime = DateUtil.getTimeDayAdd("yyyy-MM-dd", DateUtil.getDateByStringDate("yyyy-MM-dd", date), addStartDay);
//            //获取当前日期月底的
//            String endDay = DateUtil.getEndDayByMonth("yyyy-MM-dd", DateUtil.getDateByStringDate("yyyy-MM-dd", date));
//            LocalDate endDate = getLocalDateByStr(endDay);
//            String dayOfWeek = StringUtil.getString(endDate.getDayOfWeek());
//            int addEndDay = getAddEndDay(dayOfWeek);
//            String endTime = DateUtil.getTimeDayAdd("yyyy-MM-dd", DateUtil.getDateByStringDate("yyyy-MM-dd", StringUtil.getString(endDate)), addEndDay);
//            //根据开始时间和结束时间 更新周产能
//            // 根据日期删除周产能
//            Map<String, Object> weekMap = Maps.newHashMap();
//            weekMap.put("startTime", startTime);
//            weekMap.put("endTime", endTime);
//            govMapper.delWeekSum(weekMap);

            //    周产能 t_ent_energyinfo_comp_week_sum
            //累加周数据
            //  govMapper.insertEntEnergyInfoWeekSum(weekMap);
            //更新周同比数据
            //govMapper.uploadWeekSumRate(weekMap);
            // 更新产能状态
//            govMapper.updateWeekProductionStatus_0(weekMap);
//            govMapper.updateWeekProductionStatus_1(weekMap);
//            govMapper.updateWeekProductionStatus_2(weekMap);
//            govMapper.updateWeekProductionStatus_3(weekMap);
//            govMapper.updateWeekProductionStatus_4(weekMap);
//            govMapper.updateWeekProductionStatus_5(weekMap);
//            // 更新环比状态
//            govMapper.updateWeekGrowthRates_0(weekMap);
//            govMapper.updateWeekGrowthRates_1(weekMap);
//            govMapper.updateWeekGrowthRates_2(weekMap);
//            govMapper.updateWeekGrowthRates_3(weekMap);
            //更新 月产能 t_ent_energyinfo_comp_sum
            //生成本月电量数据
            // TODO 政府应用补充数据
            govMapper.deleteEntEnergyInfoComSum(dataImportDto);
            govMapper.insertEntEnergyInfoComSum(dataImportDto);
            // 更新产能状态
            govMapper.updateProductionValueByImport(dataImportDto);
            govMapper.updateProductionStatusMonth_0(dataImportDto);
            govMapper.updateProductionStatusMonth_1(dataImportDto);
            govMapper.updateProductionStatusMonth_2(dataImportDto);
            govMapper.updateProductionStatusMonth_3(dataImportDto);
            govMapper.updateProductionStatusMonth_4(dataImportDto);
            govMapper.updateProductionStatusMonth_5(dataImportDto);
            // 更新环比值
            govMapper.updateMonthRateByImport(dataImportDto);
            // 更新同比值
            govMapper.updateYearRateByImport(dataImportDto);
            // 更新环比状态
            govMapper.updateGrowthRatesMonth_0(dataImportDto);
            govMapper.updateGrowthRatesMonth_1(dataImportDto);
            govMapper.updateGrowthRatesMonth_2(dataImportDto);
            govMapper.updateGrowthRatesMonth_3(dataImportDto);

            // 日电量区域统计 t_ent_energyinfo_day_area
            govMapper.delEnergyinfoDayArea(dataImportDto);
            govMapper.insertEnergyinfoDayArea(dataImportDto);
            // 区域能耗 t_area_statistics
            govMapper.delAreaStatistics(dataImportDto);
            govMapper.insertAreaStatisticsByImport(dataImportDto);
        } else if (ENUM_DATA_TYPE.KAIPIAO.getCode().equals(dataType)) {
            //不用处理
        } else if (ENUM_DATA_TYPE.DIANFEI.getCode().equals(dataType)) {
            //不用处理
        } else if (ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(dataType)) {
            // 不用处理
        } else if (ENUM_DATA_TYPE.YONGRE.getCode().equals(dataType)) {
            //日的要汇总到月
            govMapper.delEenergyInfo(dataImportDto);
            govMapper.insertImportReLiEenergyInfo(dataImportDto);
            // 区域能耗 t_area_statistics
            govMapper.delAreaStatisticsByDay(dataImportDto);
            govMapper.insertAreaStatisticsByImportDay(dataImportDto);
            // TODO 政府应用补充数据
            govMapper.deleteEntEnergyInfoComSum(dataImportDto);
            govMapper.insertEntEnergyInfoComSum(dataImportDto);
        } else if (ENUM_DATA_TYPE.TIANRANQI.getCode().equals(dataType) || ENUM_DATA_TYPE.YONGSHUI.getCode().equals(dataType)
                || ENUM_DATA_TYPE.MEITAN.getCode().equals(dataType) || ENUM_DATA_TYPE.QIYOU.getCode().equals(dataType)
                || ENUM_DATA_TYPE.CHAIYOU.getCode().equals(dataType) || ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(dataType)) {
            // 区域能耗 t_area_statistics
            govMapper.delAreaStatisticsByMonth(dataImportDto);
            govMapper.insertAreaStatisticsByImportByMonth(dataImportDto);
            // TODO 政府应用补充数据
            govMapper.deleteEntEnergyInfoComSumByMonth(dataImportDto);
            govMapper.insertEntEnergyInfoComSumByMonth(dataImportDto);
        }
    }

    @Override
    public void importData(MultipartFile multipartFile, ExcelDataImportDTO dataImportDto, String dataTypeCode, ImportParams params) throws Exception {
//        if (ENUM_DATA_TYPE.KAIPIAO.getCode().equals(dataTypeCode)
//                || ENUM_DATA_TYPE.DIANFEI.getCode().equals(dataTypeCode)
//                || ENUM_DATA_TYPE.TIANRANQI.getCode().equals(dataTypeCode)
//                || ENUM_DATA_TYPE.YONGSHUI.getCode().equals(dataTypeCode)
//                || ENUM_DATA_TYPE.MEITAN.getCode().equals(dataTypeCode)
//                || ENUM_DATA_TYPE.QIYOU.getCode().equals(dataTypeCode)
//                || ENUM_DATA_TYPE.CHAIYOU.getCode().equals(dataTypeCode)
//                || ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(dataTypeCode)) {
//            // 验证导入
//            entInfoExcelImport(multipartFile, params, dataImportDto, entInfo, dataTypeCode);
//        } else if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataTypeCode)) {
//            entEleExcelImport(multipartFile, params, dataImportDto, entInfo);
//        } else if (ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(dataTypeCode)) {
//            entFuheExcelImport(multipartFile, params, dataImportDto, entInfo);
//        } else if (ENUM_DATA_TYPE.YONGRE.getCode().equals(dataTypeCode)) {
//            entYongReExcelImport(multipartFile, params, dataImportDto, entInfo);
//        }
    }

    /**
     * @Author:lio
     * @Description:热力数据导入
     * @Date :4:54 下午 2021/2/3
     */
    @Async
    public void entYongReExcelImport(MultipartFile multipartFile, ImportParams params, ExcelDataImportDTO dataImportDto, ExcelImportResult<ExcelDayImport> excelData) {
        //获取要比较的本地数据
        List<Map<String, Object>> localInfo = iDataMaintenanceMapper.getEntYongReInfoByImport(dataImportDto);
        //查询平台企业信息
        List<Map<String, Object>> entInfo = govMapper.getAllEntInfo();
        String date = dataImportDto.getDate();
        String msg = "";
        String userId = dataImportDto.getUserId();
        String dataTypeCode = dataImportDto.getDataType();
        //获取当月最后一天
        String nextMonth = date + "-01 00:00:00";
        String endDayByMonth = DateUtil.getEndDayByMonth("", DateUtil.getDateByStringDate("", nextMonth));
        // 验证成功后进行导入操作 有新增和更新
        if (!excelData.isVerfiyFail()) {
            List<Map<String, Object>> insertEntInfoDto = Lists.newArrayList();
            List<Map<String, Object>> finalLocalInfo = localInfo;
            excelData.getList().stream().forEach(k -> {
                Map<String, Object> tempMap = MapUtils.java2Map(k);
                Set<String> keySet = tempMap.keySet();
                String entName = k.getEntName();
                String companyId = "";
                String accNumber = "";
                String areaCode = "";
                for (int i = 0; i < entInfo.size(); i++) {
                    if (entName.equals(StringUtil.getString(entInfo.get(i).get("entName")))) {
                        companyId = StringUtil.getString(entInfo.get(i).get("companyId"));
                        if (dataTypeCode.equals(StringUtil.getString(entInfo.get(i).get("type")))) {
                            accNumber = StringUtil.getString(entInfo.get(i).get("accNumber"));
                            areaCode = StringUtil.getString(entInfo.get(i).get("areaCode"));
                        }
                    }
                }
                if (StringUtil.isNotEmpty(companyId, entName)) {
                    for (String key : keySet) {
                        if (key.contains("_Data")) {
                            String finalCompanyId = companyId;
                            String value = tempMap.get(key).toString();
                            String valueDate = "";
                            if (ENUM_DATA_TYPE.YONGRE.getCode().equals(dataTypeCode)) {
                                valueDate = date + "-" + key.replace("_Data", "") + " 00:00:00";
                            }
                            if (DateUtil.getBetweenDate(DateUtil.getDateByStringDate("yyyy-MM-dd HH:mm:ss", endDayByMonth)
                                    , DateUtil.getDateByStringDate("yyyy-MM-dd HH:mm:ss", valueDate), "yyyy-MM-dd HH:mm:ss") < 0) {
                                continue;
                            }
                            //不能大于今天
                            try {
                                if (DateUtil.getBetweenDate(DateUtil.getDateByStringDate("yyyy-MM-dd HH:mm:ss", DateUtil.getStringDateByDate("yyyy-MM-dd HH:mm:ss", new Date())), DateUtil.getDateByStringDate("yyyy-MM-dd HH:mm:ss", valueDate), "yyyy-MM-dd HH:mm:ss") < 0) {
                                    continue;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Map<String, Object> resMap = Maps.newHashMap();
                            resMap.put("date", valueDate);
                            resMap.put("value", StringUtil.getStringZero(value));
                            resMap.put("companyId", finalCompanyId);
                            resMap.put("energyType", dataTypeCode);
                            resMap.put("accnumber", accNumber);
                            resMap.put("entName", entName);
                            resMap.put("userId", userId);
                            resMap.put("areaCode", areaCode);
                            insertEntInfoDto.add(resMap);
                        }
                    }
                }
            });
            // 分别进行不同类型数据的新增和更新
            if (!CollectionUtils.isEmpty(insertEntInfoDto)) {
                batchInserInfo(insertEntInfoDto, "yongre");
            }
        } else {
            List<ExcelDayImport> failList = excelData.getFailList();
            if (!CollectionUtils.isEmpty(failList)) {
                for (ExcelDayImport entity : failList) {
                    msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
                }
                AssertUtil.ThrowSystemErr(msg);
            }
        }
        //更新汇总数据
        updateEnergyInfo(dataImportDto);
    }


    /**
     * @Author:lio
     * @Description: 数据导入
     * @Date :11:30 上午 2021/1/8
     */
    @Async
    public void entInfoExcelImport(MultipartFile multipartFile, ImportParams params, ExcelDataImportDTO dataImportDto, String dataTypeCode, ExcelImportResult<ExcelMonthImport> excelData) {
        //查询平台企业信息
        List<Map<String, Object>> entInfo = govMapper.getAllEntInfo();
        // 开启自定义校验功能
        List<Map<String, Object>> localInfo = Lists.newArrayList();
        String msg = "";
        String userId = dataImportDto.getUserId();
        if (ENUM_DATA_TYPE.KAIPIAO.getCode().equals(dataTypeCode)) {
            localInfo = iDataMaintenanceMapper.getEntTicketInfoByImport(dataImportDto);
        } else if (ENUM_DATA_TYPE.DIANFEI.getCode().equals(dataTypeCode)) {
            localInfo = iDataMaintenanceMapper.getEntDianFeiInfoByImport(dataImportDto);
        } else if (ENUM_DATA_TYPE.TIANRANQI.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.YONGSHUI.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.MEITAN.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.CHAIYOU.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.QIYOU.getCode().equals(dataTypeCode)) {
            localInfo = iDataMaintenanceMapper.getEntEnergyInfoByImport(dataImportDto);
        }
        String date = dataImportDto.getDate();
        // 验证成功后进行导入操作 有新增和更新
        if (!excelData.isVerfiyFail() && !CollectionUtils.isEmpty(excelData.getList())) {
            List<Map<String, Object>> insertEntInfoDto = Lists.newArrayList();
            List<Map<String, Object>> finalLocalInfo = localInfo;
            for (ExcelMonthImport k : excelData.getList()) {
                Map<String, Object> tempMap = MapUtils.java2Map(k);
                Set<String> keySet = tempMap.keySet();
                String entName = k.getEntName();
                String companyId = "";
                String accNumber = "";
                String areaCode = "";
                for (int i = 0; i < entInfo.size(); i++) {
                    if (entName.equals(StringUtil.getString(entInfo.get(i).get("entName")))) {
                        companyId = StringUtil.getString(entInfo.get(i).get("companyId"));
                        if (dataTypeCode.equals(StringUtil.getString(entInfo.get(i).get("type")))) {
                            accNumber = StringUtil.getString(entInfo.get(i).get("accNumber"));
                            areaCode = StringUtil.getString(entInfo.get(i).get("areaCode"));
                        }
                    }
                }
                if (StringUtil.isNotEmpty(companyId, entName)) {
                    for (String key : keySet) {
                        if (key.contains("_Data")) {
                            String finalCompanyId = companyId;
                            String value = tempMap.get(key).toString();
                            String valueDate = "";
                            String updateMonth = key.replace("_Data", "");
                            valueDate = date + "-" + key.replace("_Data", "") + "-01 00:00:00";
                            // 超过了最大月份则不能导入
                            LocalDate localDate = LocalDate.now();
                            int monthValue = localDate.getMonthValue();
                            int yearValue = localDate.getYear();
                            if (Integer.parseInt(date) > yearValue || (Integer.parseInt(date) == yearValue && Integer.parseInt(updateMonth) > monthValue)) {
                                continue;
                            }
                            // 查询tce
                            String tce = "0";
                            if (StringUtil.isNotEmpty(value) && StringUtil.isNotEmpty(ENUM_DATA_TYPE.getTceByCode(dataTypeCode))) {
                                if (ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(dataTypeCode)) {
                                    tce = StringUtil.getStringZero(value);
                                } else {
                                    tce = BigDecimalUtil.multiply(value, StringUtil.getString(ENUM_DATA_TYPE.getTceByCode(dataTypeCode)));
                                }
                            }
                            Map<String, Object> resMap = Maps.newHashMap();
                            resMap.put("tce", tce);
                            resMap.put("date", valueDate);
                            resMap.put("value", StringUtil.getStringZero(value));
                            resMap.put("companyId", finalCompanyId);
                            resMap.put("energyType", dataTypeCode);
                            resMap.put("accnumber", accNumber);
                            resMap.put("entName", entName);
                            resMap.put("userId", userId);
                            resMap.put("areaCode", areaCode);
                            insertEntInfoDto.add(resMap);
                        }
                    }
                }
            }
            // 分别进行不同类型数据的新增和更新
            if (!CollectionUtils.isEmpty(insertEntInfoDto)) {
                if (ENUM_DATA_TYPE.KAIPIAO.getCode().equals(dataTypeCode)) {
                    batchInserInfo(insertEntInfoDto, "kaipiao");
                } else if (ENUM_DATA_TYPE.DIANFEI.getCode().equals(dataTypeCode)) {
                    batchInserInfo(insertEntInfoDto, "dianfei");
                } else if (ENUM_DATA_TYPE.TIANRANQI.getCode().equals(dataTypeCode)
                        || ENUM_DATA_TYPE.YONGSHUI.getCode().equals(dataTypeCode)
                        || ENUM_DATA_TYPE.MEITAN.getCode().equals(dataTypeCode)
                        || ENUM_DATA_TYPE.CHAIYOU.getCode().equals(dataTypeCode)
                        || ENUM_DATA_TYPE.QIYOU.getCode().equals(dataTypeCode)
                        || ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(dataTypeCode)) {
                    batchInserInfo(insertEntInfoDto, "energy");
                }
            }
        } else {
            List<ExcelMonthImport> failList = excelData.getFailList();
            if (!CollectionUtils.isEmpty(failList)) {
                for (ExcelMonthImport entity : failList) {
                    msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
                }
                AssertUtil.ThrowSystemErr(msg);
            }
        }
        //更新汇总数据
        updateEnergyInfo(dataImportDto);
    }

    /**
     * @Author:lio
     * @Description: 批量更新
     * @Date :9:30 上午 2021/2/1
     */
    private void batchInserInfo(List<Map<String, Object>> entInfoList, String batchType) {
        // 批量更新操作
        int pointsDataLimit = 3000;//限制条数
        Integer size = entInfoList.size();
        if (pointsDataLimit < size) {
            int part = size / pointsDataLimit + 1;//分批数
            for (int i = 0; i < part; i++) {
                //1000条
                int startLimit = i * pointsDataLimit;
                int endLimit = (i + 1) * pointsDataLimit - 1;
                if (endLimit + 1 > size) {
                    endLimit = size;
                }
                List<Map<String, Object>> listPage = entInfoList.subList(startLimit, endLimit);
                updateMapper(listPage, batchType);
            }
        } else {
            updateMapper(entInfoList, batchType);
        }
    }

    private void updateMapper(List<Map<String, Object>> listPage, String batchType) {
        if (batchType.equals("insert")) {
            iDataMaintenanceMapper.insertEleInfo(listPage);
        } else if (batchType.equals("updateFeng")) {
            iDataMaintenanceMapper.updateFengEleInfo(listPage);
        } else if (batchType.equals("updateJian")) {
            iDataMaintenanceMapper.updateJianEleInfo(listPage);
        } else if (batchType.equals("updateGu")) {
            iDataMaintenanceMapper.updateGuEleInfo(listPage);
        } else if (batchType.equals("kaipiao")) {
            iDataMaintenanceMapper.insertEntTicketInfo(listPage);
        } else if (batchType.equals("dianfei")) {
            iDataMaintenanceMapper.insertDianFeiInfo(listPage);
        } else if (batchType.equals("fuhe")) {
            iDataMaintenanceMapper.insertFuHeInfo(listPage);
        } else if (batchType.equals("energy")) {
            iDataMaintenanceMapper.insertEnergyInfo(listPage);
        } else if (batchType.equals("yongre")) {
            iDataMaintenanceMapper.insertYongReInfo(listPage);
        }
    }


    /**
     * @Author:lio
     * @Description:电量数据导入
     * @Date :4:29 下午 2021/1/17
     */
    @Async
    @Override
    public void entEleExcelImport(MultipartFile multipartFile, ImportParams params, ExcelDataImportDTO dataImportDto, List<ExcelDayEleImport> readExcelList) {
        //获取要比较的本地数据
        List<Map<String, Object>> entInfo = govMapper.getAllEntInfo();
        // 验证成功后进行导入操作 有新增和更新
        String date = dataImportDto.getDate();
        //获取当月最后一天
        String nextMonth = date + "-01 00:00:00";
        String endDayByMonth = DateUtil.getEndDayByMonth("", DateUtil.getDateByStringDate("", nextMonth));
            List<Map<String, Object>> insertEntInfoDto = Lists.newArrayList();
            Map<String, BigDecimal> entRcvElecCapsMap = checkExcelEleDayImportDTOExcelVerifyHandler.getEntRcvElecCapsMap();
            for (ExcelDayEleImport k : readExcelList) {
                Map<String, Object> tempMap = MapUtils.java2Map(k);
                Set<String> keySet = tempMap.keySet();
                String entName = k.getEntName().replace(" ", "");
                String companyId = "", areaCode = "", accNumber = k.getAccNumber();
                // 如果户号不存在则不导入
                if (!entRcvElecCapsMap.containsKey(accNumber)) {
                    continue;
                }
                for (int i = 0; i < entInfo.size(); i++) {
                    if (entName.equals(entInfo.get(i).get("entName"))) {
                        companyId = StringUtil.getString(entInfo.get(i).get("companyId"));
                        areaCode = StringUtil.getString(entInfo.get(i).get("areaCode"));
                        break;
                    }
                }
                if (StringUtil.isNotEmpty(companyId, entName)) {
                    // 本月最后一天日期
                    LocalDate monthLastDay = LocalDate.parse(endDayByMonth, DateTimeFormatter.ofPattern(DateUtil.DEFAULT_TIME_PATTERN));
                    // 当前日期
                    LocalDate now = LocalDate.now();
                    for (String key : keySet) {
                        if (key.contains("_Data")) {
                            String value = tempMap.get(key).toString();
                            String valueDate = date + "-" + key.replace("_Data", "");
                            valueDate = valueDate.substring(0, valueDate.length() - 1) + " 00:00:00";
                            // 从excel导入的日期
                            LocalDate excelValueDate = LocalDate.parse(valueDate, DateTimeFormatter.ofPattern(DateUtil.DEFAULT_TIME_PATTERN));
                            // 不能超过当月最大日期 && 不能大于今天
                            if (excelValueDate.isAfter(monthLastDay) || excelValueDate.isAfter(now)) {
                                continue;
                            }
                            Map<String, Object> resMap = Maps.newHashMap();
                            resMap.put("date", valueDate);
                            resMap.put("companyId", companyId);
                            resMap.put("entName", entName);
                            resMap.put("areaCode", areaCode);
                            resMap.put("accnumber", accNumber);
                            resMap.put("energyType", "0");
                            resMap.put("userId", dataImportDto.getUserId());
                            //字段结尾标示
                            String jian = "1", feng = "2", gu = "3";
                            String endKey = key.substring(key.length() - 1);
                            if (jian.equals(endKey)) {
                                resMap.put("jian", value == null ? null : value.trim());
                            } else if (feng.equals(endKey)) {
                                resMap.put("feng", value == null ? null : value.trim());
                            } else if (gu.equals(endKey)) {
                                resMap.put("gu", value == null ? null : value.trim());
                            }
                            insertEntInfoDto.add(resMap);
                        }
                    }
                }
            }
            //对同一天数据尖峰谷进行汇总
            List<Map<String, Object>> insertEntInfo = changeData(insertEntInfoDto);
            // 分别做更新和新增操作
            if (!CollectionUtils.isEmpty(insertEntInfo)) {
                batchInserInfo(insertEntInfo, "insert");
            }
            //更新value
            // iDataMaintenanceMapper.updateValueEleInfo(dataImportDto);
        //更新汇总数据
        updateEnergyInfo(dataImportDto);
    }


    /**
     * @Author:lio
     * @Description: 负荷数据导入
     * @Date :9:52 上午 2021/2/1
     */
    @Async
    public void entFuheExcelImport(MultipartFile multipartFile, ImportParams params, ExcelDataImportDTO dataImportDto, ExcelImportResult<ExcelFuHeMonthImport> excelData) {
        List<Map<String, Object>> localInfo = iDataMaintenanceMapper.getEntFuHeByImport(dataImportDto);
        List<Map<String, Object>> entInfo = govMapper.getAllEntInfo();
        String date = dataImportDto.getDate();
        String msg = "";
        String dataTypeCode = ENUM_DATA_TYPE.ZUIGAOFUHE.getCode();
        String userId = dataImportDto.getUserId();
        // 验证成功后进行导入操作 有新增和更新
        if (!excelData.isVerfiyFail()) {
            List<Map<String, Object>> insertEntInfoDto = Lists.newArrayList();
            List<Map<String, Object>> finalLocalInfo = localInfo;
            excelData.getList().stream().forEach(k -> {
                Map<String, Object> tempMap = MapUtils.java2Map(k);
                Set<String> keySet = tempMap.keySet();
                String entName = k.getEntName();
                String companyId = "";
                String accNumber = k.getAccnumber();
                String areaCode = "";
                for (int i = 0; i < entInfo.size(); i++) {
                    if (entName.equals(entInfo.get(i).get("entName"))) {
                        companyId = StringUtil.getString(entInfo.get(i).get("companyId"));
                        if (dataTypeCode.equals(entInfo.get(i).get("type"))) {
                            areaCode = StringUtil.getString(entInfo.get(i).get("areaCode"));
                        }
                    }
                }
                if (StringUtil.isNotEmpty(companyId, entName)) {
                    for (String key : keySet) {
                        if (key.contains("_Data")) {
                            String finalCompanyId = companyId;
                            String value = tempMap.get(key).toString();
                            String valueDate = "";
                            valueDate = date + "-" + key.replace("_Data", "") + "-01 00:00:00";

                            // 超过了最大月份则不能导入
                            LocalDate localDate = LocalDate.now();
                            int monthValue = localDate.getMonthValue();
                            int yearValue = localDate.getYear();
                            String updateMonth = key.replace("_Data", "");
                            if (Integer.parseInt(date) > yearValue || (Integer.parseInt(date) == yearValue && Integer.parseInt(updateMonth) > monthValue)) {
                                continue;
                            }
                            Map<String, Object> resMap = Maps.newHashMap();
                            resMap.put("date", valueDate);
                            resMap.put("value", StringUtil.getStringZero(value));
                            resMap.put("companyId", finalCompanyId);
                            resMap.put("energyType", dataTypeCode);
                            resMap.put("accNumber", accNumber);
                            resMap.put("entName", entName);
                            resMap.put("userId", userId);
                            resMap.put("areaCode", areaCode);
                            insertEntInfoDto.add(resMap);
                        }
                    }
                }
            });
            // 分别进行不同类型数据的新增和更新
            if (!CollectionUtils.isEmpty(insertEntInfoDto)) {
                batchInserInfo(insertEntInfoDto, "fuhe");
            }
        } else {
            List<ExcelFuHeMonthImport> failList = excelData.getFailList();
            if (!CollectionUtils.isEmpty(failList)) {
                for (ExcelFuHeMonthImport entity : failList) {
                    msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
                }
                AssertUtil.ThrowSystemErr(msg);
            }
        }
        //更新汇总数据
        updateEnergyInfo(dataImportDto);
    }

    private List<Map<String, Object>> changeData(List<Map<String, Object>> infoList) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(infoList)) {
            //通过对户号的分组
            Map<Object, List<Map<String, Object>>> accNumberList = infoList.stream().collect(Collectors.groupingBy(m -> m.get("accnumber")));
            accNumberList.forEach((number, list) -> {
                //再通过对同一户号时间的分组
                Map<Object, List<Map<String, Object>>> dateList = list.stream().collect(Collectors.groupingBy(m -> m.get("date")));
                dateList.forEach((time, timeList) -> {
                    //对同一时间数据加上所有的数据
                    Map<String, Object> insMap = Maps.newHashMap();
                    insMap.put("companyId", timeList.get(0).get("companyId"));
                    insMap.put("entName", timeList.get(0).get("entName"));
                    insMap.put("accnumber", number);
                    insMap.put("date", time);
                    insMap.put("userId", timeList.get(0).get("userId"));
                    insMap.put("areaCode", timeList.get(0).get("areaCode"));
                    insMap.put("rcvElecCap", checkExcelEleDayImportDTOExcelVerifyHandler.getEntRcvElecCapsMap().get(number));
                    for (int i = 0; i < timeList.size(); i++) {
                        Object feng = timeList.get(i).get("feng"), jian = timeList.get(i).get("jian"), gu = timeList.get(i).get("gu");
                        // 存在有效值,0代表有效值
                        if (ObjectUtil.isNotEmpty(feng) && !StringUtils.equals("null", feng.toString())) {
                            insMap.put("feng", feng);
                        }
                        if (ObjectUtil.isNotEmpty(jian) && !StringUtils.equals("null", jian.toString())) {
                            insMap.put("jian", jian);
                        }
                        if (ObjectUtil.isNotEmpty(gu) && !StringUtils.equals("null", gu.toString())) {
                            insMap.put("gu", gu);
                        }
                    }
                    // 当前date下企业存在有效数据
                    if (!(insMap.get("feng") == null && insMap.get("jian") == null && insMap.get("gu") == null)) {
                        // 兼容上传不规范的数据(部分有值,部分没有) eg.
                        // 27日尖	27日峰	27日谷
                        // 18920
                        insMap.put("feng", ObjectUtils.defaultIfNull(insMap.get("feng"), "0"));
                        insMap.put("jian", ObjectUtils.defaultIfNull(insMap.get("jian"), "0"));
                        insMap.put("gu", ObjectUtils.defaultIfNull(insMap.get("gu"), "0"));
                        mapList.add(insMap);
                    }
                });
            });
        }
        return mapList;
    }


    private int getAddEndDay(String dayOfWeek) {
        int addDay = 0;
        if ("MONDAY".equals(dayOfWeek)) {
            addDay = 6;
        } else if ("TUESDAY".equals(dayOfWeek)) {
            addDay = 5;
        } else if ("WEDNESDAY".equals(dayOfWeek)) {
            addDay = 4;
        } else if ("THURSDAY".equals(dayOfWeek)) {
            addDay = 3;
        } else if ("FRIDAY".equals(dayOfWeek)) {
            addDay = 2;
        } else if ("SATURDAY".equals(dayOfWeek)) {
            addDay = 1;
        }
        return addDay;
    }

    private int getAddStartDay(String dayOfWeek) {
        int addDay = 0;
        if ("TUESDAY".equals(dayOfWeek)) {
            addDay = -1;
        } else if ("WEDNESDAY".equals(dayOfWeek)) {
            addDay = -2;
        } else if ("THURSDAY".equals(dayOfWeek)) {
            addDay = -3;
        } else if ("FRIDAY".equals(dayOfWeek)) {
            addDay = -4;
        } else if ("SATURDAY".equals(dayOfWeek)) {
            addDay = -5;
        } else if ("SUNDAY".equals(dayOfWeek)) {
            addDay = -6;
        }
        return addDay;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getEndDayByMonth("yyyy-MM-dd", DateUtil.getDateByStringDate("yyyy-MM-dd", "2020-01-02")));
    }


    public static LocalDate getLocalDateByStr(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, formatter);
    }


}
