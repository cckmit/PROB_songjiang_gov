package com.enesource.jump.web.action;

import com.enesource.jump.web.common.Result;
import com.enesource.jump.web.dao.IDataMaintenanceMapper;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dto.*;
import com.enesource.jump.web.enums.ENUM_DATA_TYPE;
import com.enesource.jump.web.enums.ENUM_DATE_TYPE;
import com.enesource.jump.web.service.DataDetailService;
import com.enesource.jump.web.utils.AssertUtil;
import com.enesource.jump.web.utils.ExcelUtils;
import com.enesource.jump.web.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* *
 * @author lio
 * @Description:
 * @date :created in 11:12 上午 2021/1/12
 */
@RestController
@CrossOrigin
@RequestMapping("/dataMain")
public class DataDetailAction {

    @Autowired
    DataDetailService dataDetailService;

    @Autowired
    IGovMapper govMapper;

    @Autowired
    IDataMaintenanceMapper dataMaintenanceMapper;

    /**
     * @Author:lio
     * @Description: 获取详情信息
     * @Date :11:41 上午 2021/1/12
     */
    @RequestMapping(value = "/dataMainList", method = RequestMethod.POST)
    public Result dataDetail(@RequestBody ParamDTO detailParam) {
        Result result = new Result();
        String dataType = detailParam.getSelectType();
        AssertUtil.NotBlank(dataType, "数据类型不能为空");
        boolean dataFlag = true;
        //根据数据类型和日期类型判断
        if (ENUM_DATE_TYPE.MONTH.getCode().equals(dataType) && !ENUM_DATA_TYPE.YONGRE.getCode().equals(StringUtil.getString(detailParam.getDataType()))) {
            dataFlag = ENUM_DATA_TYPE.getDateYearFlagByCode(dataType);
        } else if (ENUM_DATE_TYPE.DAY.getCode().equals(dataType) && !ENUM_DATA_TYPE.YONGDIAN.getCode().equals(StringUtil.getString(detailParam.getDataType()))) {
            dataFlag = ENUM_DATA_TYPE.getDateMonthFlagByCode(dataType);
        }
        int totalSize = 0;
        PageInfo pageInfo = new PageInfo(Lists.newArrayList());
        if (dataFlag) {
            List<Map<String, Object>> total = dataMaintenanceMapper.dataMainCompanyList(detailParam);
            totalSize = total.size();
            if (detailParam.getPage() != null) {
                PageHelper.startPage(detailParam.getPage(), detailParam.getPageSize());
            }
            List<Map<String, Object>> entList = dataMaintenanceMapper.dataMainCompanyList(detailParam);
            pageInfo = new PageInfo(dataDetailService.getDataDetail(detailParam, entList));
        }
        pageInfo.setTotal(totalSize);
        result.setData(pageInfo);
        return result;
    }


    /**
     * @Author:lio
     * @Description: 获取导入模板
     * @Date :6:22 下午 2021/1/27
     */
    @GetMapping(value = "/getExcelModel")
    public Result getExcelModel(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "selectType", required = true) String selectType,
                                @RequestParam(value = "dataType", required = true) String dataType
    ) {
        Result result = new Result();
        AssertUtil.NotBlank(dataType, "数据类型不能为空");
        AssertUtil.NotBlank(selectType, "日期类型不能为空");
        if (ENUM_DATA_TYPE.YONGRE.getCode().equals(dataType) && ENUM_DATE_TYPE.DAY.getCode().equals(selectType)) {
            dataType = "15";
        }
        String modelUrl = govMapper.getExcelModelUrl(dataType);
        result.setData(StringUtil.isEmpty(modelUrl) ? "" : modelUrl);
        return result;
    }

    /**
     * @Author:lio
     * @Description: 导出企业用能全景
     * @Date :11:34 上午 2021/1/31
     */
    @GetMapping(value = "/exportEnergyData")
    public void exportEnergyData(HttpServletRequest request, HttpServletResponse response
            , @RequestParam(value = "speedType", required = true) String speedType
            , @RequestParam(value = "microPark", required = false) String microPark
            , @RequestParam(value = "date", required = true) String date) {
        AssertUtil.NotBlank(speedType, "企业能耗类型不能为空");
        Map<String, Object> paramMap = Maps.newHashMap();
//        String firstDate = govMapper.getFirstDate();
        
        paramMap.put("date", date);
        paramMap.put("speedType", speedType);
        paramMap.put("microPark", microPark);
        String fileName = "嘉善县用能全景";
        if ("complete".equals(speedType)){
            fileName = fileName +"超标企业";
        }else if ("unComplete".equals(speedType)){
            fileName = fileName +"未超标企业";
        }
        List<ExcelEnergyCompanyExport> excelEnergyCompanyExportList = govMapper.findexportEnergyData(paramMap);
        ExcelUtils.exportExcel(excelEnergyCompanyExportList, fileName, "sheet1", ExcelEnergyCompanyExport.class, fileName + ".xls", response);
    }


    /**
     * @Author:lio
     * @Description: 数据维护导出
     *  1:根据数据类型以及年月导出不一样的数据模板
     *  2：导出所有选择的企业
     * @Date :10:04 上午 2021/1/29
     */
    @GetMapping(value = "/exportMainData")
    public void exportMainData(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "selectType", required = true) String selectType,
                               @RequestParam(value = "dataType", required = true) String dataType,
                               @RequestParam(value = "type", required = true) String type,
                               @RequestParam(value = "key", required = false) String key,
                               @RequestParam(value = "areaCode", required = true) String areaCode,
                               @RequestParam(value = "industry", required = true) String industry,
                               @RequestParam(value = "date", required = true) String date
    ) throws UnsupportedEncodingException {
        AssertUtil.NotBlank(dataType, "数据类型不能为空");
        AssertUtil.NotBlank(selectType, "日期类型不能为空");
        ParamDTO paramDTO = new ParamDTO();
        paramDTO.setSelectType(selectType);
        paramDTO.setDataType(dataType);
        paramDTO.setType(type);
        paramDTO.setKey(key);
        paramDTO.setAreaCode(areaCode);
        paramDTO.setIndustry(industry);
        paramDTO.setDate(date);
        // 只有电量和最高负荷有户号需要单独处理
        List<Map<String, Object>> entList = Lists.newArrayList();
        if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataType) || ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(dataType)) {
            entList = dataMaintenanceMapper.dataMainCompanyAccList(paramDTO);
        } else {
            entList = dataMaintenanceMapper.dataMainCompanyList(paramDTO);
        }
        List<Map<String, Object>> dataDetail = Lists.newArrayList();
        String fileName = ENUM_DATA_TYPE.getMsgByCode(dataType) + ".xls";
        String titleName = ENUM_DATA_TYPE.getMsgByCode(dataType);
        //不同数据类型使用不同模版
        if (ENUM_DATA_TYPE.KAIPIAO.getCode().equals(dataType) || ENUM_DATA_TYPE.DIANFEI.getCode().equals(dataType)
                || ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(dataType) || ENUM_DATA_TYPE.TIANRANQI.getCode().equals(dataType)
                || ENUM_DATA_TYPE.YONGSHUI.getCode().equals(dataType) || ENUM_DATA_TYPE.MEITAN.getCode().equals(dataType)
                || ENUM_DATA_TYPE.QIYOU.getCode().equals(dataType)
                || ENUM_DATA_TYPE.CHAIYOU.getCode().equals(dataType)
                || ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(dataType)
                || (ENUM_DATA_TYPE.YONGRE.getCode().equals(dataType) && ENUM_DATE_TYPE.MONTH.getCode().equals(selectType)
                || (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataType) && ENUM_DATE_TYPE.MONTH.getCode().equals(selectType))
        )) {
            //获取基础数据 用电和最高负荷要带户号
            if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataType) || ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(dataType)) {
                Map<String, Object> companyMap = Maps.newHashMap();
                List<String> accNumberList = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(entList)) {
                    for (int i = 0; i < entList.size(); i++) {
                        accNumberList.add(StringUtil.getString(entList.get(i).get("accNumber")));
                    }
                    companyMap.put("accNumberList", accNumberList);
                    companyMap.put("date", date);
                    if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataType)) {
                        dataDetail = dataMaintenanceMapper.getEleDetail(companyMap);
                    } else if (ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(dataType)) {
                        dataDetail = dataMaintenanceMapper.getFuHeDetail(companyMap);
                    }
                }
                List<ExcelMonthEleExport> excelMonthEleExportList = getExcelMonthEleExport(dataDetail, entList);
                ExcelUtils.exportExcel(excelMonthEleExportList, "企业月" + titleName, "sheet1", ExcelMonthEleExport.class, fileName, response);
            } else {
                dataDetail = dataDetailService.getDataDetail(paramDTO, entList);
                List<ExcelMonthImport> excelMonthImportList = getExcelMonthExport(dataDetail);
                ExcelUtils.exportExcel(excelMonthImportList, "企业月" + titleName, "sheet1", ExcelMonthImport.class, fileName, response);
            }
        } else if ((ENUM_DATA_TYPE.YONGRE.getCode().equals(dataType) && ENUM_DATE_TYPE.DAY.getCode().equals(selectType))
                || (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataType) && ENUM_DATE_TYPE.DAY.getCode().equals(selectType))
        ) {
            List<String> gryList = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(entList)) {
                if (ENUM_DATA_TYPE.YONGRE.getCode().equals(dataType)) {
                    for (int i = 0; i < entList.size(); i++) {
                        gryList.add(StringUtil.getString(entList.get(i).get("companyId")));
                    }
                    Map<String, Object> companyMap = Maps.newHashMap();
                    companyMap.put("companyList", gryList);
                    companyMap.put("date", date);
                    dataDetail = dataMaintenanceMapper.getDataReLiDetail(companyMap);
                    List<ExcelDayImport> excelMonthEleExportList = getExcelDayExport(dataDetail,entList);
                    ExcelUtils.exportExcel(excelMonthEleExportList, "企业日" + titleName, "sheet1", ExcelDayImport.class, fileName, response);
                } else if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataType)) {
                    for (int i = 0; i < entList.size(); i++) {
                        gryList.add(StringUtil.getString(entList.get(i).get("accNumber")));
                    }
                    Map<String, Object> companyMap = Maps.newHashMap();
                    companyMap.put("accNumberList", gryList);
                    companyMap.put("date", date);
                    dataDetail = dataMaintenanceMapper.getDataEleDayDetail(companyMap);
                    List<ExcelDayImportDTO> excelMonthEleExportList = getExcelEleDayExport(dataDetail,entList);
                    ExcelUtils.exportExcel(excelMonthEleExportList, "企业日" + titleName, "sheet1", ExcelDayImportDTO.class, fileName, response);
                }
            }
        }
    }

    private List<ExcelDayImportDTO> getExcelEleDayExport(List<Map<String, Object>> dataDetail,List<Map<String,Object>> entList) {
        List<ExcelDayImportDTO> excelList = Lists.newArrayList();
        List<Map<String, Object>> dayEleList = Lists.newArrayList();
        Map<Object, List<Map<String, Object>>> accNumberList = dataDetail.stream().collect(Collectors.groupingBy(e -> e.get("accnumber")));
        accNumberList.forEach((k, v) -> {
            Map<String, Object> changeMap = Maps.newHashMap();
            changeMap.put("accnumber", k);
            for (Map map : v) {
                changeMap.put(StringUtil.getString(map.get("day")), map.get("value"));
            }
            dayEleList.add(changeMap);
        });
        if (!CollectionUtils.isEmpty(dayEleList)) {
            for (int i = 0; i < dayEleList.size(); i++) {
                ExcelDayImportDTO excelDayImport = new ExcelDayImportDTO();
                excelDayImport.setAccnumber(StringUtil.getString(dayEleList.get(i).get("accnumber")));
                excelDayImport.set_Data01(StringUtil.getString(dayEleList.get(i).get("1")));
                excelDayImport.set_Data02(StringUtil.getString(dayEleList.get(i).get("2")));
                excelDayImport.set_Data03(StringUtil.getString(dayEleList.get(i).get("3")));
                excelDayImport.set_Data04(StringUtil.getString(dayEleList.get(i).get("4")));
                excelDayImport.set_Data05(StringUtil.getString(dayEleList.get(i).get("5")));
                excelDayImport.set_Data06(StringUtil.getString(dayEleList.get(i).get("6")));
                excelDayImport.set_Data07(StringUtil.getString(dayEleList.get(i).get("7")));
                excelDayImport.set_Data08(StringUtil.getString(dayEleList.get(i).get("8")));
                excelDayImport.set_Data09(StringUtil.getString(dayEleList.get(i).get("9")));
                excelDayImport.set_Data10(StringUtil.getString(dayEleList.get(i).get("10")));
                excelDayImport.set_Data11(StringUtil.getString(dayEleList.get(i).get("11")));
                excelDayImport.set_Data12(StringUtil.getString(dayEleList.get(i).get("12")));
                excelDayImport.set_Data13(StringUtil.getString(dayEleList.get(i).get("13")));
                excelDayImport.set_Data14(StringUtil.getString(dayEleList.get(i).get("14")));
                excelDayImport.set_Data15(StringUtil.getString(dayEleList.get(i).get("15")));
                excelDayImport.set_Data16(StringUtil.getString(dayEleList.get(i).get("16")));
                excelDayImport.set_Data17(StringUtil.getString(dayEleList.get(i).get("17")));
                excelDayImport.set_Data18(StringUtil.getString(dayEleList.get(i).get("18")));
                excelDayImport.set_Data19(StringUtil.getString(dayEleList.get(i).get("19")));
                excelDayImport.set_Data20(StringUtil.getString(dayEleList.get(i).get("20")));
                excelDayImport.set_Data21(StringUtil.getString(dayEleList.get(i).get("21")));
                excelDayImport.set_Data22(StringUtil.getString(dayEleList.get(i).get("22")));
                excelDayImport.set_Data23(StringUtil.getString(dayEleList.get(i).get("23")));
                excelDayImport.set_Data24(StringUtil.getString(dayEleList.get(i).get("24")));
                excelDayImport.set_Data25(StringUtil.getString(dayEleList.get(i).get("25")));
                excelDayImport.set_Data26(StringUtil.getString(dayEleList.get(i).get("26")));
                excelDayImport.set_Data27(StringUtil.getString(dayEleList.get(i).get("27")));
                excelDayImport.set_Data28(StringUtil.getString(dayEleList.get(i).get("28")));
                excelDayImport.set_Data29(StringUtil.getString(dayEleList.get(i).get("29")));
                excelDayImport.set_Data30(StringUtil.getString(dayEleList.get(i).get("30")));
                excelDayImport.set_Data31(StringUtil.getString(dayEleList.get(i).get("31")));
                excelList.add(excelDayImport);
            }
        }
        List<ExcelDayImportDTO> excelMonthResList = Lists.newArrayList();
        for (Map<String, Object> map : entList) {
            String accNumber = StringUtil.getString(map.get("accNumber"));
            String entName = StringUtil.getString(map.get("entName"));
            boolean checkFlag = false;
            if (!CollectionUtils.isEmpty(dayEleList)) {
                for (ExcelDayImportDTO excelMontDto : excelList) {
                    if (accNumber.equals(excelMontDto.getAccnumber())) {
                        excelMontDto.setEntName(entName);
                        excelMonthResList.add(excelMontDto);
                        checkFlag = true;
                        continue;
                    }
                }
            }
            if (CollectionUtils.isEmpty(dayEleList) || !checkFlag) {
                ExcelDayImportDTO excelDayEleExport = new ExcelDayImportDTO();
                excelDayEleExport.setAccnumber(accNumber);
                excelDayEleExport.setEntName(entName);
                excelMonthResList.add(excelDayEleExport);
            }
        }

        return excelMonthResList;
    }

    private List<ExcelDayImport> getExcelDayExport(List<Map<String, Object>> dataDetail,List<Map<String, Object>> entList) {
        List<ExcelDayImport> excelList = Lists.newArrayList();
        List<Map<String, Object>> dayEleList = Lists.newArrayList();
        Map<Object, List<Map<String, Object>>> accNumberList = dataDetail.stream().collect(Collectors.groupingBy(e -> e.get("entName")));
        accNumberList.forEach((k, v) -> {
            Map<String, Object> changeMap = Maps.newHashMap();
            changeMap.put("entName", k);
            for (Map map : v) {
                changeMap.put(StringUtil.getString(map.get("day")), map.get("value"));
            }
            dayEleList.add(changeMap);
        });
        if (!CollectionUtils.isEmpty(dayEleList)) {
            for (int i = 0; i < dayEleList.size(); i++) {
                ExcelDayImport excelDayImport = new ExcelDayImport();
                excelDayImport.setEntName(StringUtil.getString(dayEleList.get(i).get("entName")));
                excelDayImport.set_Data01(StringUtil.getString(dayEleList.get(i).get("1")));
                excelDayImport.set_Data02(StringUtil.getString(dayEleList.get(i).get("2")));
                excelDayImport.set_Data03(StringUtil.getString(dayEleList.get(i).get("3")));
                excelDayImport.set_Data04(StringUtil.getString(dayEleList.get(i).get("4")));
                excelDayImport.set_Data05(StringUtil.getString(dayEleList.get(i).get("5")));
                excelDayImport.set_Data06(StringUtil.getString(dayEleList.get(i).get("6")));
                excelDayImport.set_Data07(StringUtil.getString(dayEleList.get(i).get("7")));
                excelDayImport.set_Data08(StringUtil.getString(dayEleList.get(i).get("8")));
                excelDayImport.set_Data09(StringUtil.getString(dayEleList.get(i).get("9")));
                excelDayImport.set_Data10(StringUtil.getString(dayEleList.get(i).get("10")));
                excelDayImport.set_Data11(StringUtil.getString(dayEleList.get(i).get("11")));
                excelDayImport.set_Data12(StringUtil.getString(dayEleList.get(i).get("12")));
                excelDayImport.set_Data13(StringUtil.getString(dayEleList.get(i).get("13")));
                excelDayImport.set_Data14(StringUtil.getString(dayEleList.get(i).get("14")));
                excelDayImport.set_Data15(StringUtil.getString(dayEleList.get(i).get("15")));
                excelDayImport.set_Data16(StringUtil.getString(dayEleList.get(i).get("16")));
                excelDayImport.set_Data17(StringUtil.getString(dayEleList.get(i).get("17")));
                excelDayImport.set_Data18(StringUtil.getString(dayEleList.get(i).get("18")));
                excelDayImport.set_Data19(StringUtil.getString(dayEleList.get(i).get("19")));
                excelDayImport.set_Data20(StringUtil.getString(dayEleList.get(i).get("20")));
                excelDayImport.set_Data21(StringUtil.getString(dayEleList.get(i).get("21")));
                excelDayImport.set_Data22(StringUtil.getString(dayEleList.get(i).get("22")));
                excelDayImport.set_Data23(StringUtil.getString(dayEleList.get(i).get("23")));
                excelDayImport.set_Data24(StringUtil.getString(dayEleList.get(i).get("24")));
                excelDayImport.set_Data25(StringUtil.getString(dayEleList.get(i).get("25")));
                excelDayImport.set_Data26(StringUtil.getString(dayEleList.get(i).get("26")));
                excelDayImport.set_Data27(StringUtil.getString(dayEleList.get(i).get("27")));
                excelDayImport.set_Data28(StringUtil.getString(dayEleList.get(i).get("28")));
                excelDayImport.set_Data29(StringUtil.getString(dayEleList.get(i).get("29")));
                excelDayImport.set_Data30(StringUtil.getString(dayEleList.get(i).get("30")));
                excelDayImport.set_Data31(StringUtil.getString(dayEleList.get(i).get("31")));
                excelList.add(excelDayImport);
            }
        }
        //拼装所有数据
        List<ExcelDayImport> excelMonthResList = Lists.newArrayList();
        for (Map<String, Object> map : entList) {
            String entName = StringUtil.getString(map.get("entName"));
            boolean checkFlag = false;
            if (!CollectionUtils.isEmpty(dayEleList)) {
                for (ExcelDayImport excelMontDto : excelList) {
                    if (entName.equals(excelMontDto.getEntName())) {
                        excelMonthResList.add(excelMontDto);
                        checkFlag = true;
                        continue;
                    }
                }
            }
            if (CollectionUtils.isEmpty(dayEleList) || !checkFlag) {
                ExcelDayImport excelMonthEleExport = new ExcelDayImport();
                excelMonthEleExport.setEntName(entName);
                excelMonthResList.add(excelMonthEleExport);
            }
        }
        return excelMonthResList;
    }

    private List<ExcelMonthEleExport> getExcelMonthEleExport(List<Map<String, Object>> dataDetail, List<Map<String, Object>> entList) {
        List<Map<String, Object>> monthEleList = Lists.newArrayList();
        Map<Object, List<Map<String, Object>>> accNumberList = dataDetail.stream().collect(Collectors.groupingBy(e -> e.get("accnumber")));
        accNumberList.forEach((k, v) -> {
            Map<String, Object> changeMap = Maps.newHashMap();
            changeMap.put("accNumber", k);
            changeMap.put("entName", v.get(0).get("entName"));
            for (Map map : v) {
                changeMap.put(StringUtil.getString(map.get("month")), map.get("value"));
            }
            monthEleList.add(changeMap);
        });
        //数据转换导出每日的数据
        List<ExcelMonthEleExport> excelMonthImportList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(monthEleList)) {
            for (int i = 0; i < monthEleList.size(); i++) {
                ExcelMonthEleExport excelMonthExport = new ExcelMonthEleExport();
                excelMonthExport.setEntName(StringUtil.getString(monthEleList.get(i).get("entName")));
                excelMonthExport.setAccNumber(StringUtil.getString(monthEleList.get(i).get("accNumber")));
                excelMonthExport.set_Data01(StringUtil.getString(monthEleList.get(i).get("1")));
                excelMonthExport.set_Data02(StringUtil.getString(monthEleList.get(i).get("2")));
                excelMonthExport.set_Data03(StringUtil.getString(monthEleList.get(i).get("3")));
                excelMonthExport.set_Data04(StringUtil.getString(monthEleList.get(i).get("4")));
                excelMonthExport.set_Data05(StringUtil.getString(monthEleList.get(i).get("5")));
                excelMonthExport.set_Data06(StringUtil.getString(monthEleList.get(i).get("6")));
                excelMonthExport.set_Data07(StringUtil.getString(monthEleList.get(i).get("7")));
                excelMonthExport.set_Data08(StringUtil.getString(monthEleList.get(i).get("8")));
                excelMonthExport.set_Data09(StringUtil.getString(monthEleList.get(i).get("9")));
                excelMonthExport.set_Data10(StringUtil.getString(monthEleList.get(i).get("10")));
                excelMonthExport.set_Data11(StringUtil.getString(monthEleList.get(i).get("11")));
                excelMonthExport.set_Data12(StringUtil.getString(monthEleList.get(i).get("12")));
                excelMonthImportList.add(excelMonthExport);
            }
        }
        //拼装所有数据
        List<ExcelMonthEleExport> excelMonthResList = Lists.newArrayList();
        for (Map<String, Object> map : entList) {
            String accNumber = StringUtil.getString(map.get("accNumber"));
            boolean checkFlag = false;
            if (!CollectionUtils.isEmpty(monthEleList)) {
                for (ExcelMonthEleExport excelMontDto : excelMonthImportList) {
                    if (accNumber.equals(excelMontDto.getAccNumber())) {
                        excelMonthResList.add(excelMontDto);
                        checkFlag = true;
                        continue;
                    }
                }
            }
            if (CollectionUtils.isEmpty(monthEleList) || !checkFlag) {
                ExcelMonthEleExport excelMonthEleExport = new ExcelMonthEleExport();
                String entName = StringUtil.getString(map.get("entName"));
                excelMonthEleExport.setAccNumber(accNumber);
                excelMonthEleExport.setEntName(entName);
                excelMonthResList.add(excelMonthEleExport);
            }
        }
        return excelMonthResList;
    }

    private List<ExcelMonthImport> getExcelMonthExport(List<Map<String, Object>> dataDetail) {
        //数据转换导出每日的数据
        List<ExcelMonthImport> excelMonthImportList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(dataDetail)) {
            for (int i = 0; i < dataDetail.size(); i++) {
                ExcelMonthImport excelMonthImport = new ExcelMonthImport();
                excelMonthImport.setEntName(StringUtil.getString(dataDetail.get(i).get("entName")));
                List<Map<String, Object>> valueList = (List<Map<String, Object>>) dataDetail.get(i).get("valueList");
                if (!CollectionUtils.isEmpty(valueList)) {
                    excelMonthImport.set_Data01(StringUtil.getString(valueList.get(0).get("1")));
                    excelMonthImport.set_Data02(StringUtil.getString(valueList.get(0).get("2")));
                    excelMonthImport.set_Data03(StringUtil.getString(valueList.get(0).get("3")));
                    excelMonthImport.set_Data04(StringUtil.getString(valueList.get(0).get("4")));
                    excelMonthImport.set_Data05(StringUtil.getString(valueList.get(0).get("5")));
                    excelMonthImport.set_Data06(StringUtil.getString(valueList.get(0).get("6")));
                    excelMonthImport.set_Data07(StringUtil.getString(valueList.get(0).get("7")));
                    excelMonthImport.set_Data08(StringUtil.getString(valueList.get(0).get("8")));
                    excelMonthImport.set_Data09(StringUtil.getString(valueList.get(0).get("9")));
                    excelMonthImport.set_Data10(StringUtil.getString(valueList.get(0).get("10")));
                    excelMonthImport.set_Data11(StringUtil.getString(valueList.get(0).get("11")));
                    excelMonthImport.set_Data12(StringUtil.getString(valueList.get(0).get("12")));
                }
                excelMonthImportList.add(excelMonthImport);
            }
        }
        return excelMonthImportList;
    }
}
