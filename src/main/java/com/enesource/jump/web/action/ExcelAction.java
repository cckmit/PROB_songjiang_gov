package com.enesource.jump.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import com.enesource.jump.web.dto.*;
import com.enesource.jump.web.enums.ENUM_DATA_TYPE;
import com.enesource.jump.web.enums.ENUM_DATE_TYPE;
import com.enesource.jump.web.service.ExcelService;
import com.enesource.jump.web.utils.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.enesource.jump.web.common.BaseAction;
import com.enesource.jump.web.common.CheckEntInfoDTOExcelVerifyHandler;
import com.enesource.jump.web.common.Result;
import com.enesource.jump.web.dao.ICommonMapper;
import com.enesource.jump.web.dao.IGovBigScreen;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dao.IInsightMapper;
import com.enesource.jump.web.service.IAsyncService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

@Controller
@CrossOrigin
public class ExcelAction extends BaseAction {

    @Autowired
    ICommonMapper commonMapper;

    @Autowired
    IGovBigScreen govBigScreen;

    @Autowired
    IGovMapper govMapper;

    @Autowired
    IInsightMapper insightMapper;

    @Autowired
    IAsyncService asyncService;

    @Autowired
    ExcelService excelService;




    /**
     * ******************************************************************企业数据导入导出******************************************************************
     */

    /**
     * 导出永嘉县企业数据
     *
     * @param response
     */
    @GetMapping("/excel/exportExcelEnergyinfo")
    public void exportExcelEnergyinfo(HttpServletResponse response,
                                      @RequestParam(value = "type", required = false) String type,
                                      @RequestParam(value = "areaLabel", required = false) String areaLabel) {
        List<EntInfoDTO> list = new ArrayList<EntInfoDTO>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", type);
        paramMap.put("areaLabel", areaLabel);
        // 导出全部
        if ("1".equals(type)) {
            list = govBigScreen.findEnergyinfoList(paramMap);
        }
        String fileName = "";
        if ("yongjia".equals(areaLabel)) {
            fileName += "永嘉县";
        } else if ("ruian".equals(areaLabel)) {
            fileName += "瑞安县";
        }
        if ("1".equals(type)) {
            fileName += "企业数据.xls";
        } else {
            fileName += "企业数据模板.xls";
        }
        // 导出操作
        ExcelUtils.exportExcel(list, null, "sheet1", EntInfoDTO.class, fileName, response);
    }

    /**
     * 导入永嘉县企业数据
     *
     */
    @PostMapping("/excel/uploadExcelEnergyinfo")
    @ResponseBody
    public Result uploadExcelEnergyinfo(@RequestParam("file") MultipartFile multipartFile, @RequestParam Map<String, Object> paramMap) throws Exception {
        Result result = new Result();

        ImportParams params = new ImportParams();
        // 设置表头行数
        params.setHeadRows(1);

        // 设置标题行数，默认 0
        // params.setTitleRows(0);

        // 开启自定义校验功能
        params.setVerifyHandler(new CheckEntInfoDTOExcelVerifyHandler());

        // 开启校验
        params.setNeedVerify(true);

        // 验证导入
        ExcelImportResult<EntInfoDTO> l = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), EntInfoDTO.class, params);

//		System.out.println("是否校验失败: " + l.isVerfiyFail());
//		System.out.println("校验失败的集合:" + JSONObject.toJSONString(l.getFailList()));
//		System.out.println("校验通过的集合:" + JSONObject.toJSONString(l.getList()));

        // 验证成功后进行导入操作
        if (!l.isVerfiyFail()) {
            for (EntInfoDTO entity : l.getList()) {
                entity.setAreaLabel(paramMap.get("areaLabel").toString());

                if (entity.getCompanyId() == null) {
                    entity.setCompanyId(govMapper.getCompanyId());
                }
            }

            asyncService.mergeEntInfo(l.getList());

        } else {
            String msg = "";

            for (EntInfoDTO entity : l.getFailList()) {
                msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
            }
            result.setCode("0");
            result.setMsg(msg);
        }

        return result;
    }

    /**
     * ******************************************************************企业能源消耗数据导入导出******************************************************************
     */


    /**
     * 导出永嘉县企业能源消耗数据
     *
     * @param response
     */
    @GetMapping("/excel/exportExcelConsumeinfo")
    @ResponseBody
    public Result exportExcelConsumeinfo(HttpServletResponse response,
                                         @RequestParam(value = "energyType", required = true) String energyType,
                                         @RequestParam(value = "companyId", required = false) String companyId,
                                         @RequestParam(value = "companyName", required = false) String companyName,
                                         @RequestParam(value = "areaLabel", required = true) String areaLabel) {

        Result result = new Result();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("energyType", energyType);
        paramMap.put("companyId", companyId);
        paramMap.put("areaLabel", areaLabel);

        List<EnergyinfoDTO> energyinfoList = new ArrayList<EnergyinfoDTO>();

        // 查询企业对应户号列表
        List<Map<String, Object>> list = govBigScreen.findAccnumberListByEnergrType(paramMap);

        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {

                EnergyinfoDTO listDTO = new EnergyinfoDTO();

                map.put("energyType", paramMap.get("energyType"));

                listDTO = MapUtils.map2Java(listDTO, map);

                List<Map<String, Object>> tempList = govMapper.findConsumeinfoByCompanyIdANDEnergyType(map);

                Map<String, Object> dtoValue = new HashMap<String, Object>();

                if (tempList != null) {
                    for (Map<String, Object> temp : tempList) {
                        String date = "_Data" + temp.get("date").toString();
                        String value = temp.get("value").toString();
                        dtoValue.put(date, value);
                    }
                    DateColumnDTO tempDTO = new DateColumnDTO();
                    tempDTO = MapUtils.map2Java(tempDTO, dtoValue);
                    listDTO.setDateColumn(tempDTO);

                }

                energyinfoList.add(listDTO);

            }

        }

        String fileName = "";

        String areaName = "";

        if ("yongjia".equals(areaLabel)) {
            areaName += "永嘉县";
        } else if ("ruian".equals(areaLabel)) {
            areaName += "瑞安县";
        }


        if (Integer.valueOf(energyType) == 0) {
            fileName = "2-1企业用能档案-" + areaName + "企业用电数据";
        }

        if (Integer.valueOf(energyType) == 1) {
            fileName = "2-4企业用能档案-" + areaName + "企业用气数据";
        }

        if (Integer.valueOf(energyType) == 2) {
            fileName = "2-5企业用能档案-" + areaName + "企业用热数据";
        }

        if (Integer.valueOf(energyType) == 3) {
            fileName = "2-9企业用能档案-" + areaName + "企业用水数据";
        }

        if (Integer.valueOf(energyType) == 8) {
            fileName = "2-6企业用能档案-" + areaName + "企业煤炭使用数据";
        }

        if (Integer.valueOf(energyType) == 6) {
            fileName = "2-7企业用能档案-" + areaName + "企业汽油使用数据";
        }

        if (Integer.valueOf(energyType) == 4) {
            fileName = "2-8企业用能档案-" + areaName + "企业柴油使用数据";
        }

        if (companyName == null) {
            fileName = fileName + "-全部.xls";
        } else {
            fileName = fileName + "-" + companyName + ".xls";
        }


        // 导出操作
        ExcelUtils.exportExcel(energyinfoList, null, "sheet1", EnergyinfoDTO.class, fileName, response);

        return result;
    }

    /**
     * 导入永嘉县企业能源消耗数据
     *
     * @param response
     */
    @PostMapping("/excel/uploadExcelConsumeinfo")
    @ResponseBody
    public Result uploadExcelConsumeinfo(@RequestParam("file") MultipartFile multipartFile, @RequestParam Map<String, Object> paramMap) throws Exception {
        Result result = new Result();

//    public Result uploadExcelConsumeinfo(@RequestParam("file") MultipartFile multipartFile) throws Exception{
//    	Result result = new Result();
//    	
//    	Map<String, Object> paramMap = new HashMap<String, Object>();
//    	
//    	paramMap.put("energyType", "0");

        String[] checkParamsMap = {"energyType", "areaLabel"};
        String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
        if (null != errorString) {
            result.setCode(Conf.ERROR);
            result.setMsg(errorString);

            return result;
        }

        String energyType = paramMap.get("energyType").toString();

        ImportParams params = new ImportParams();
        // 设置表头行数
        params.setHeadRows(1);

        // 设置标题行数，默认 0
        // params.setTitleRows(0);

        // 开启校验
        params.setNeedVerify(true);

        // 验证导入
        ExcelImportResult<EnergyinfoDTO> l = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), EnergyinfoDTO.class, params);

//		System.out.println("是否校验失败: " + l.isVerfiyFail());
//		System.out.println("校验失败的集合:" + JSONObject.toJSONString(l.getFailList()));
//		System.out.println("校验通过的集合:" + JSONObject.toJSONString(l.getList()));

        // 验证成功后进行导入操作
        if (!l.isVerfiyFail()) {
            for (EnergyinfoDTO entity : l.getList()) {
                DateColumnDTO tempDto = entity.getDateColumn();


                Map<String, Object> tempMap = MapUtils.java2Map(tempDto);

                if (tempMap != null) {
                    Set<String> keySet = tempMap.keySet();

                    List<EnergyinfoDTO> upDtoL = new ArrayList<EnergyinfoDTO>();

                    for (String key : keySet) {
                        String dateStr = key.substring(5, 9) + "-" + key.substring(9, 11) + "-01 00:00:00";

                        EnergyinfoDTO upDto = new EnergyinfoDTO();

                        upDto.setCompanyId(entity.getCompanyId());
                        upDto.setAccnumber(entity.getAccnumber());
                        upDto.setDate(dateStr);
                        upDto.setValue(tempMap.get(key).toString());
                        upDto.setEnergyType(paramMap.get("energyType").toString());

                        // TODO 标煤转换
                        // 电
                        if ("0".equals(energyType)) {
                            if (tempMap.get(key) != null && !"".equals(tempMap.get(key).toString())) {
                                Double value = Double.valueOf(tempMap.get(key).toString());
                                Double tce = value / 10000 * 1.229;

                                upDto.setTce(tce.toString());
                            }
                        }

                        // 天然气
                        if ("1".equals(energyType)) {
                            if (tempMap.get(key) != null && !"".equals(tempMap.get(key).toString())) {
                                Double value = Double.valueOf(tempMap.get(key).toString());
                                Double tce = value / 10000 * 12;

                                upDto.setTce(tce.toString());
                            }
                        }

                        // 热
                        if ("2".equals(energyType)) {
                            if (tempMap.get(key) != null && !"".equals(tempMap.get(key).toString())) {
                                Double value = Double.valueOf(tempMap.get(key).toString());
                                Double tce = value * 0.0341;

                                upDto.setTce(tce.toString());
                            }
                        }

                        // 柴油
                        if ("4".equals(energyType)) {
                            if (tempMap.get(key) != null && !"".equals(tempMap.get(key).toString())) {
                                Double value = Double.valueOf(tempMap.get(key).toString());
                                Double tce = value * 1.4571;

                                upDto.setTce(tce.toString());
                            }
                        }

                        // 汽油
                        if ("6".equals(energyType)) {
                            if (tempMap.get(key) != null && !"".equals(tempMap.get(key).toString())) {
                                Double value = Double.valueOf(tempMap.get(key).toString());
                                Double tce = value * 1.4714;

                                upDto.setTce(tce.toString());
                            }
                        }

                        // 煤
                        if ("8".equals(energyType)) {
                            if (tempMap.get(key) != null && !"".equals(tempMap.get(key).toString())) {
                                Double value = Double.valueOf(tempMap.get(key).toString());
                                Double tce = value * 0.7143;

                                upDto.setTce(tce.toString());
                            }
                        }

                        upDtoL.add(upDto);

                    }

                    asyncService.mergeEnergyinfo(upDtoL, paramMap.get("energyType").toString(), paramMap.get("areaLabel").toString());

                }

            }

        } else {
            String msg = "";

            for (EnergyinfoDTO entity : l.getFailList()) {
                msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
            }
            result.setCode("0");
            result.setMsg(msg);
        }

        return result;
    }


    /**
     * ******************************************************************企业经济数据导入导出******************************************************************
     */


    /**
     * 导出永嘉县企业经济数据
     *
     * @param response
     */
    @GetMapping("/excel/exportExceleConomicsinfo")
    @ResponseBody
    public Result exportExceleConomicsinfo(HttpServletResponse response, @RequestParam(value = "companyId", required = false) String companyId,
                                           @RequestParam(value = "companyName", required = false) String companyName,
                                           @RequestParam(value = "areaLabel", required = true) String areaLabel) {

        Result result = new Result();

        List<EconomicsinfoDTO> economicsinfoList = new ArrayList<EconomicsinfoDTO>();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("companyId", companyId);
        paramMap.put("areaLabel", areaLabel);

        // 查询企业列表
        List<EntInfoDTO> list = govBigScreen.findEnergyinfoList(paramMap);

        if (list != null && list.size() > 0) {
            for (EntInfoDTO dto : list) {

                EconomicsinfoDTO listDTO = new EconomicsinfoDTO();

                List<Map<String, Object>> tempList = govMapper.findEconomicsinfoByCompanyId(dto.getCompanyId());

                Map<String, Object> dtoValue = new HashMap<String, Object>();

                if (tempList != null) {
                    for (Map<String, Object> temp : tempList) {
                        String date = "_Data" + temp.get("date").toString();
                        String value = temp.get("value").toString();

                        dtoValue.put(date, value);

                    }

                    DateColumnDTO tempDTO = new DateColumnDTO();

                    tempDTO = MapUtils.map2Java(tempDTO, dtoValue);

                    listDTO.setDateColumn(tempDTO);
                    listDTO.setCompanyId(dto.getCompanyId());
                    listDTO.setEntName(dto.getEntName());

                }

                economicsinfoList.add(listDTO);

            }

        }

        String areaName = "";

        if ("yongjia".equals(areaLabel)) {
            areaName += "永嘉县";
        } else if ("ruian".equals(areaLabel)) {
            areaName += "瑞安县";
        } else if ("jiashan".equals(areaLabel)) {
            areaName += "嘉善县";
        }

        String fileName = "2-2企业用能档案-" + areaName + "企业经济数据";

        if (companyName == null) {
            fileName = fileName + "-全部.xls";
        } else {
            fileName = fileName + "-" + companyName + ".xls";
        }

        // 导出操作
        ExcelUtils.exportExcel(economicsinfoList, null, "sheet1", EconomicsinfoDTO.class, fileName, response);

        return result;
    }

    /**
     * 导入永嘉县企业经济数据
     *
     * @param response
     */
    @PostMapping("/excel/uploadExcelEconomicsinfo")
    @ResponseBody
    public Result uploadExcelEconomicsinfo(@RequestParam("file") MultipartFile multipartFile, @RequestParam Map<String, Object> paramMap) throws Exception {
        Result result = new Result();

        String[] checkParamsMap = {"areaLabel"};
        String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
        if (null != errorString) {
            result.setCode(Conf.ERROR);
            result.setMsg(errorString);

            return result;
        }

        ImportParams params = new ImportParams();
        // 设置表头行数
        params.setHeadRows(1);

        // 设置标题行数，默认 0
        // params.setTitleRows(0);

        // 开启校验
        params.setNeedVerify(true);

        // 验证导入
        ExcelImportResult<EconomicsinfoDTO> l = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), EconomicsinfoDTO.class, params);

//		System.out.println("是否校验失败: " + l.isVerfiyFail());
//		System.out.println("校验失败的集合:" + JSONObject.toJSONString(l.getFailList()));
//		System.out.println("校验通过的集合:" + JSONObject.toJSONString(l.getList()));

        // 验证成功后进行导入操作
        if (!l.isVerfiyFail()) {
            for (EconomicsinfoDTO entity : l.getList()) {
                DateColumnDTO tempDto = entity.getDateColumn();

                Map<String, Object> tempMap = MapUtils.java2Map(tempDto);

                if (tempMap != null) {
                    Set<String> keySet = tempMap.keySet();

                    List<EconomicsinfoDTO> updtoL = new ArrayList<EconomicsinfoDTO>();

                    for (String key : keySet) {
                        EconomicsinfoDTO updto = new EconomicsinfoDTO();

                        String dateStr = key.substring(5, 9) + "-" + key.substring(9, 11) + "-01 00:00:00";

                        updto.setCompanyId(entity.getCompanyId());
                        updto.setDate(dateStr);
                        updto.setValue(tempMap.get(key).toString());
                    }

                    asyncService.mergeEconomicsinfo(updtoL, paramMap.get("areaLabel").toString());

                }

            }

        } else {
            String msg = "";

            for (EconomicsinfoDTO entity : l.getFailList()) {
                msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
            }
            result.setCode("0");
            result.setMsg(msg);
        }

        return result;
    }

    /**
     * ******************************************************************多能监测站点数据导入导出******************************************************************
     */


    /**
     * 导出永嘉县多能监测站点数据
     *
     * @param response
     */
    @GetMapping("/excel/exportExceleGovMapinfo")
    @ResponseBody
    public Result exportExceleGovMapinfo(HttpServletResponse response,
                                         @RequestParam(value = "siteType", required = true) String siteType,
                                         @RequestParam(value = "areaLabel", required = true) String areaLabel) {

        Result result = new Result();

        List<GovMapInfoDTO> govMapinfoList = new ArrayList<GovMapInfoDTO>();

        Map<String, Object> pm = new HashMap<String, Object>();


        pm.put("siteType", siteType);
        pm.put("areaLabel", areaLabel);

        // 查询企业列表
        List<Map<String, Object>> projectlist = govBigScreen.findGovMapData(pm);

        // TODO 站点类型，，1 发电厂、2 光伏、3 电网、4 供热网、5 供冷网、6 储能、7 充电站、8 水电站、9 垃圾发电站、10 变电站

        if (projectlist != null && projectlist.size() > 0) {
            for (Map<String, Object> map : projectlist) {
//        		GovMapInfoDTO dto = new GovMapInfoDTO();

                if (Integer.valueOf(siteType) == 2) {
                    PhotoDTO dto = new PhotoDTO();

                    List<Map<String, Object>> tempList = govMapper.findPowerCurveBySiteId(map.get("siteId").toString());

                    Map<String, Object> dtoValue = new HashMap<String, Object>();

                    if (tempList != null) {
                        for (Map<String, Object> temp : tempList) {
                            String date = "_Data" + temp.get("date").toString();
                            String value = temp.get("value").toString();

                            dtoValue.put(date, value);

                        }

                        DateColumnDTO tempDTO = new DateColumnDTO();

                        tempDTO = MapUtils.map2Java(tempDTO, dtoValue);

                        dto.setDateColumn(tempDTO);

                        dto.setSiteId(map.get("siteId").toString());
                        dto.setSiteName(map.get("siteName").toString());
                        dto.setSiteType(Integer.valueOf(map.get("siteType").toString()));

                        dto.setAddress(map.get("address") == null ? "" : map.get("address").toString());
                        dto.setCommissionTime(map.get("commissionTime") == null ? "" : map.get("commissionTime").toString());
                        dto.setCapacity(map.get("capacity") == null ? "" : map.get("capacity").toString());
                        dto.setVoltlevel(map.get("voltlevel") == null ? "" : map.get("voltlevel").toString());
                        dto.setLng(map.get("lng") == null ? "" : map.get("lng").toString());
                        dto.setLat(map.get("lat") == null ? "" : map.get("lat").toString());

                        // 光伏特有字段
                        dto.setConsNo(map.get("consNo") == null ? "" : map.get("consNo").toString());
                        dto.setDeclaredEnt(map.get("declaredEnt") == null ? "" : map.get("declaredEnt").toString());
                        dto.setPhotoNumber(map.get("photoNumber") == null ? "" : map.get("photoNumber").toString());
                        dto.setInvestor(map.get("investor") == null ? "" : map.get("investor").toString());
                        dto.setOngridDate(map.get("ongridDate") == null ? "" : map.get("ongridDate").toString());

                    }

                    govMapinfoList.add(dto);

                }

                if (Integer.valueOf(siteType) == 7) {
                    ChargeDTO dto = new ChargeDTO();

                    List<Map<String, Object>> tempList = govMapper.findPowerCurveBySiteId(map.get("siteId").toString());

                    Map<String, Object> dtoValue = new HashMap<String, Object>();

                    if (tempList != null) {
                        for (Map<String, Object> temp : tempList) {
                            String date = "_Data" + temp.get("date").toString();
                            String value = temp.get("value").toString();

                            dtoValue.put(date, value);

                        }

                        DateColumnDTO tempDTO = new DateColumnDTO();

                        tempDTO = MapUtils.map2Java(tempDTO, dtoValue);

                        dto.setDateColumn(tempDTO);

                        dto.setSiteId(map.get("siteId").toString());
                        dto.setSiteName(map.get("siteName").toString());
                        dto.setSiteType(Integer.valueOf(map.get("siteType").toString()));

                        dto.setAddress(map.get("address") == null ? "" : map.get("address").toString());
                        dto.setCommissionTime(map.get("commissionTime") == null ? "" : map.get("commissionTime").toString());
                        dto.setCapacity(map.get("capacity") == null ? "" : map.get("capacity").toString());
                        dto.setVoltlevel(map.get("voltlevel") == null ? "" : map.get("voltlevel").toString());
                        dto.setLng(map.get("lng") == null ? "" : map.get("lng").toString());
                        dto.setLat(map.get("lat") == null ? "" : map.get("lat").toString());

                        // 充电站特有字段
                        dto.setChargeType(map.get("chargeType") == null ? "" : map.get("chargeType").toString());
                        dto.setChargeCountSum(map.get("chargeCountSum") == null ? "" : map.get("chargeCountSum").toString());
                        dto.setChangePowerSum(map.get("changePowerSum") == null ? "" : map.get("changePowerSum").toString());

                    }

                    govMapinfoList.add(dto);
                }

                if (Integer.valueOf(siteType) == 8 || Integer.valueOf(siteType) == 9 || Integer.valueOf(siteType) == 10) {
                    GovMapInfoDTO dto = new GovMapInfoDTO();

                    List<Map<String, Object>> tempList = govMapper.findPowerCurveBySiteId(map.get("siteId").toString());

                    Map<String, Object> dtoValue = new HashMap<String, Object>();

                    if (tempList != null) {
                        for (Map<String, Object> temp : tempList) {
                            String date = "_Data" + temp.get("date").toString();
                            String value = temp.get("value").toString();

                            dtoValue.put(date, value);

                        }

                        DateColumnDTO tempDTO = new DateColumnDTO();

                        tempDTO = MapUtils.map2Java(tempDTO, dtoValue);

                        dto.setDateColumn(tempDTO);

                        dto.setSiteId(map.get("siteId").toString());
                        dto.setSiteName(map.get("siteName").toString());
                        dto.setSiteType(Integer.valueOf(map.get("siteType").toString()));

                        dto.setAddress(map.get("address") == null ? "" : map.get("address").toString());
                        dto.setCommissionTime(map.get("commissionTime") == null ? "" : map.get("commissionTime").toString());
                        dto.setCapacity(map.get("capacity") == null ? "" : map.get("capacity").toString());
                        dto.setVoltlevel(map.get("voltlevel") == null ? "" : map.get("voltlevel").toString());
                        dto.setLng(map.get("lng") == null ? "" : map.get("lng").toString());
                        dto.setLat(map.get("lat") == null ? "" : map.get("lat").toString());

                    }

                    govMapinfoList.add(dto);
                }

            }
        }

        String areaName = "";

        if ("yongjia".equals(areaLabel)) {
            areaName += "永嘉县";
        } else if ("ruian".equals(areaLabel)) {
            areaName += "瑞安县";
        }

        String fileName = "";

        if (Integer.valueOf(siteType) == 2) {
            fileName = areaName + "光伏项目数据.xls";

            ExcelUtils.exportExcel(govMapinfoList, null, "sheet1", PhotoDTO.class, fileName, response);
        }

        if (Integer.valueOf(siteType) == 7) {
            fileName = areaName + "充电站项目数据.xls";

            ExcelUtils.exportExcel(govMapinfoList, null, "sheet1", ChargeDTO.class, fileName, response);
        }

        if (Integer.valueOf(siteType) == 8) {
            fileName = areaName + "水电站项目数据.xls";

            ExcelUtils.exportExcel(govMapinfoList, null, "sheet1", GovMapInfoDTO.class, fileName, response);
        }

        if (Integer.valueOf(siteType) == 9) {
            fileName = areaName + "垃圾电站项目数据.xls";

            ExcelUtils.exportExcel(govMapinfoList, null, "sheet1", GovMapInfoDTO.class, fileName, response);
        }

        if (Integer.valueOf(siteType) == 10) {
            fileName = areaName + "变电站项目数据.xls";

            ExcelUtils.exportExcel(govMapinfoList, null, "sheet1", GovMapInfoDTO.class, fileName, response);
        }

        return result;

    }

    /**
     * 导入永嘉县多能监测站点数据
     *
     * @param response
     */
    @PostMapping("/excel/uploadExcelGovMapinfo")
    @ResponseBody
    public Result uploadExcelGovMapinfo(@RequestParam("file") MultipartFile multipartFile, @RequestParam Map<String, Object> paramMap) throws Exception {
        Result result = new Result();

        String[] checkParamsMap = {"siteType", "areaLabel"};
        String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
        if (null != errorString) {
            result.setCode(Conf.ERROR);
            result.setMsg(errorString);

            return result;
        }

        ImportParams params = new ImportParams();
        // 设置表头行数
        params.setHeadRows(1);

        // 设置标题行数，默认 0
        // params.setTitleRows(0);

        // 开启校验
        params.setNeedVerify(true);

        Integer siteType = Integer.valueOf(paramMap.get("siteType").toString());


        if (siteType == 2) {
            // 验证导入
            ExcelImportResult<PhotoDTO> l = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), PhotoDTO.class, params);

//			System.out.println("是否校验失败: " + l.isVerfiyFail());
//			System.out.println("校验失败的集合:" + JSONObject.toJSONString(l.getFailList()));
//			System.out.println("校验通过的集合:" + JSONObject.toJSONString(l.getList()));

            // 验证成功后进行导入操作
            if (!l.isVerfiyFail()) {
                for (GovMapInfoDTO entity : l.getList()) {
                    entity.setAreaLabel(paramMap.get("areaLabel").toString());
                }

                asyncService.mergePhotoInfoDTO(l.getList());
            } else {
                String msg = "";

                for (GovMapInfoDTO entity : l.getFailList()) {
                    msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
                }
                result.setCode("0");
                result.setMsg(msg);
            }
        } else if (siteType == 7) {
            // 验证导入
            ExcelImportResult<ChargeDTO> l = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), ChargeDTO.class, params);

//			System.out.println("是否校验失败: " + l.isVerfiyFail());
//			System.out.println("校验失败的集合:" + JSONObject.toJSONString(l.getFailList()));
//			System.out.println("校验通过的集合:" + JSONObject.toJSONString(l.getList()));

            // 验证成功后进行导入操作
            if (!l.isVerfiyFail()) {
                for (GovMapInfoDTO entity : l.getList()) {
                    entity.setAreaLabel(paramMap.get("areaLabel").toString());
                }

                asyncService.mergeChargeInfoDTO(l.getList());
            } else {
                String msg = "";

                for (GovMapInfoDTO entity : l.getFailList()) {
                    msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
                }
                result.setCode("0");
                result.setMsg(msg);
            }
        } else {
            // 验证导入
            ExcelImportResult<GovMapInfoDTO> l = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), GovMapInfoDTO.class, params);

//			System.out.println("是否校验失败: " + l.isVerfiyFail());
//			System.out.println("校验失败的集合:" + JSONObject.toJSONString(l.getFailList()));
//			System.out.println("校验通过的集合:" + JSONObject.toJSONString(l.getList()));

            // 验证成功后进行导入操作
            if (!l.isVerfiyFail()) {

                // TODO
                for (GovMapInfoDTO entity : l.getList()) {
                    entity.setAreaLabel(paramMap.get("areaLabel").toString());
                }

                asyncService.mergeGovMapInfoDTO(l.getList());
            } else {
                String msg = "";

                for (GovMapInfoDTO entity : l.getFailList()) {
                    msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
                }
                result.setCode("0");
                result.setMsg(msg);
            }
        }

        return result;
    }


    /**
     * ******************************************************************缺失数据导入导出******************************************************************
     */
    @GetMapping("/excel/exportLoseCompanyInfo")
    @ResponseBody
    public void exportLoseCompanyInfo(HttpServletResponse response,
                                      @RequestParam(value = "date", required = true) String date,
                                      @RequestParam(value = "areaLabel", required = true) String areaLabel) {
        List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();
        beanList.add(new ExcelExportEntity("企业Id", "companyId"));
        beanList.add(new ExcelExportEntity("企业名称", "entName"));
        beanList.add(new ExcelExportEntity("户号", "accnumber"));
        beanList.add(new ExcelExportEntity("用电量", "value"));

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("date", date);
        param.put("areaLabel", areaLabel);


        List<Map<String, Object>> list = govMapper.exportLoseCompanyInfo(param);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), beanList, list);


        ExcelUtils.downLoadExcel(date + "-缺失数据企业.xls", response, workbook);

    }


    @PostMapping("/excel/importLoseCompanyInfo")
    @ResponseBody
    public Result importLoseCompanyInfo(@RequestParam("file") MultipartFile multipartFile
            , @RequestParam Map<String, Object> paramMap) throws Exception {
        Result result = new Result();

        String[] checkParamsMap = {"areaLabel"};
        String errorString = checkParams(paramMap, checkParamsMap, ERROR_STRING_MAP);
        if (null != errorString) {
            result.setCode(Conf.ERROR);
            result.setMsg(errorString);

            return result;
        }

        ImportParams params = new ImportParams();
        // 设置表头行数
        params.setHeadRows(1);

        // 设置标题行数，默认 0
//		params.setTitleRows(0);

        // 开启校验
        params.setNeedVerify(true);

        String fileName = multipartFile.getOriginalFilename();


        ExcelImportResult<ImportDayValueDTO> l = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), ImportDayValueDTO.class, params);

        String date = fileName.substring(0, 10);

        if (!this.isDate(date)) {
            result.setCode("0");
            result.setMsg("文件名格式不正确，YYYY-MM-DD-缺失数据企业.xls");

            return result;
        }

        // 验证成功后进行导入操作
        if (!l.isVerfiyFail()) {

            // TODO 获得值可能有问题
            for (ImportDayValueDTO entity : l.getList()) {
                entity.setDate(date);
            }

            asyncService.mergeDayValueDTO(l.getList(), date);
        } else {
            String msg = "";

            for (ImportDayValueDTO entity : l.getFailList()) {
                msg += "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg() + "\n";
            }
            result.setCode("0");
            result.setMsg(msg);
        }

        return result;

    }


    public static boolean isDate(String date) {
        /**
         * 判断日期格式和范围
         */
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))"
                + "[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))"
                + "|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
                + "[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();
        return dateType;
    }


    /**
     * ******************************************************************地区产能预警导出******************************************************************
     */


    @GetMapping("/excel/exportInsightCompanyList")
    @ResponseBody
    public void exportInsightCompanyList(HttpServletResponse response,
                                         @RequestParam(value = "date", required = true) String date,
                                         @RequestParam(value = "areaLabel", required = true) String areaLabel,
                                         @RequestParam(value = "type", required = false) String type,
                                         @RequestParam(value = "areaCode", required = false) String areaCode,
                                         @RequestParam(value = "industryType", required = false) String industryType) throws Exception {
        List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();
        beanList.add(new ExcelExportEntity("企业名称", "entName"));

        beanList.add(new ExcelExportEntity("关联户号", "accNumberList"));
        beanList.add(new ExcelExportEntity("统一社会信用代码", "creditCode"));
        beanList.add(new ExcelExportEntity("组织机构代码", "orgCode"));
        beanList.add(new ExcelExportEntity("行政区划", "areaName"));
        beanList.add(new ExcelExportEntity("详细地址", "address"));
        beanList.add(new ExcelExportEntity("所属园区", "micro_park"));
        beanList.add(new ExcelExportEntity("行业", "industryTypeName"));
        beanList.add(new ExcelExportEntity("业务活动", "business"));
        beanList.add(new ExcelExportEntity("产值规模", "output_value"));
        beanList.add(new ExcelExportEntity("企业标签", "tagList"));
        beanList.add(new ExcelExportEntity("企业联络人", "contactPerson"));
        beanList.add(new ExcelExportEntity("联系方式", "contactPhone"));
        beanList.add(new ExcelExportEntity("基准电量(kWh)", "unitEle"));
        beanList.add(new ExcelExportEntity("当月电量(kWh)", "thisvalue"));
        beanList.add(new ExcelExportEntity("当月同比(%)", "yearRate"));
        beanList.add(new ExcelExportEntity("当年累计电量(kWh)", "yearValue"));
        beanList.add(new ExcelExportEntity("产能状态", "productionStatusName"));
        beanList.add(new ExcelExportEntity("当月环比(%)", "monthRate"));
        beanList.add(new ExcelExportEntity("环比状态", "growthRateName"));

        InsightDTO dto = new InsightDTO();

        dto.setAreaLabel(areaLabel);
        dto.setDate(date);
        dto.setAreaCode(areaCode);
        dto.setType(type);
        dto.setIndustryType(industryType);

        List<Map<String, Object>> valueList = insightMapper.findInsightCompanyList(dto);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), beanList, valueList);


        ExcelUtils.downLoadExcel("地区产能预警数据.xls", response, workbook);

    }


    /**
     * ******************************************************************动态日期导出电量数据******************************************************************
     */
    @GetMapping("/excel/exportDynamicInfo")
    @ResponseBody
    public void exportLoseCompanyInfo(HttpServletResponse response,
                                      @RequestParam(value = "startDate", required = true) String startDate,
                                      @RequestParam(value = "endDate", required = true) String endDate,
                                      @RequestParam(value = "dataType", required = true) String dataType,
                                      @RequestParam(value = "areaLabel", required = true) String areaLabel,
                                      @RequestParam(value = "companyId", required = false) String companyId) {

        try {
            Map<String, Object> param = new HashMap<String, Object>();

            param.put("dataType", dataType);
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("areaLabel", areaLabel);
            param.put("companyId", companyId);

            List<String> list = govMapper.findDateList(param);


            // 动态生成表头，查询时间
            List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();
            beanList.add(new ExcelExportEntity("企业Id", "companyId"));
            beanList.add(new ExcelExportEntity("企业名称", "entName"));
            beanList.add(new ExcelExportEntity("户号", "accnumber"));

            beanList.add(new ExcelExportEntity("统一社会信用代码", "creditCode"));
            beanList.add(new ExcelExportEntity("组织机构代码", "orgCode"));
            beanList.add(new ExcelExportEntity("行政区划", "areaName"));
            beanList.add(new ExcelExportEntity("详细地址", "address"));
            beanList.add(new ExcelExportEntity("所属园区", "micro_park"));
            beanList.add(new ExcelExportEntity("行业", "industryTypeName"));
            beanList.add(new ExcelExportEntity("业务活动", "business"));
            beanList.add(new ExcelExportEntity("产值规模", "output_value"));
            beanList.add(new ExcelExportEntity("企业标签", "tagList"));
            beanList.add(new ExcelExportEntity("企业联络人", "contactPerson"));
            beanList.add(new ExcelExportEntity("联系方式", "contactPhone"));

            for (String dateStr : list) {
                beanList.add(new ExcelExportEntity(dateStr, dateStr));
            }

            // 动态添加数据
            List<Map<String, Object>> valueList = new ArrayList<Map<String, Object>>();

            if ("1".equals(dataType)) {
                valueList = govMapper.findDateValueListByMonth(param);
            } else {
                valueList = govMapper.findDateValueListByDay(param);
            }

            String accNumber = "";

            // 拼接下载数据
            List<Map<String, Object>> downList = new ArrayList<Map<String, Object>>();

            if (valueList != null && valueList.size() > 0) {
                for (Map<String, Object> valueMap : valueList) {

                    // 如果户号已经存在，查询列表为原有值添加字段
                    if (accNumber.contains(valueMap.get("accnumber").toString())) {
                        for (Map<String, Object> downMap : downList) {
                            if (downMap.get("accnumber").toString().equals(valueMap.get("accnumber").toString())) {
                                downMap.put(valueMap.get("datelist").toString(), valueMap.get("value") == null ? "" : valueMap.get("value").toString());
                            }
                        }
                    } else {
                        valueMap.put(valueMap.get("datelist").toString(), valueMap.get("value") == null ? "" : valueMap.get("value").toString());
                        downList.add(valueMap);

                        accNumber += valueMap.get("accnumber").toString() + ";";
                    }

                }
            }


            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), beanList, downList);


            ExcelUtils.downLoadExcel("企业电量数据.xls", response, workbook);

        } catch (Exception e) {
            logger.error("动态日期导出电量数据错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/exportDynamicInfo");
            exceptionLog.setName("动态日期导出电量数据错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

        }

    }


    /**
     * ******************************************************************3D数据******************************************************************
     */
    @GetMapping("/excel/export3DIndex")
    @ResponseBody
    public void export3DIndex(HttpServletResponse response,
                              @RequestParam(value = "date", required = true) String date,
                              @RequestParam(value = "areaLabel", required = true) String areaLabel) {

        try {
            Map<String, Object> param = new HashMap<String, Object>();

            param.put("date", date);
            param.put("areaLabel", areaLabel);

            List<Map<String, Object>> valueList = govMapper.findValueList(param);


            List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();
            beanList.add(new ExcelExportEntity("企业名称", "entName"));

            beanList.add(new ExcelExportEntity("基准电量(kWh)", "unitEle"));
            beanList.add(new ExcelExportEntity("当月用电量(kWh)", "value"));
            beanList.add(new ExcelExportEntity("能源消费总量", "tce"));
            beanList.add(new ExcelExportEntity("乡镇街道", "areaName"));
            beanList.add(new ExcelExportEntity("环比", "monthRate"));
            beanList.add(new ExcelExportEntity("同比", "yearRate"));
            beanList.add(new ExcelExportEntity("基准比", "productionValue"));
            beanList.add(new ExcelExportEntity("当年累计电量", "yearValue"));
            beanList.add(new ExcelExportEntity("行业类型", "industry"));
            beanList.add(new ExcelExportEntity("产值规模", "outputValueName"));
            beanList.add(new ExcelExportEntity("产能状态", "productionStatusName"));
            beanList.add(new ExcelExportEntity("环比状态", "growthRateName"));

            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), beanList, valueList);


            ExcelUtils.downLoadExcel("3D数据.xls", response, workbook);

        } catch (Exception e) {
            logger.error("3D数据导出错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/exportDynamicInfo");
            exceptionLog.setName("3D数据导出错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

        }

    }


    /**
     * ******************************************************************企业生产状态列表******************************************************************
     */
    @GetMapping("/excel/exportProductionStatusEntList")
    @ResponseBody
    public void exportProductionStatusEntList(HttpServletResponse response,
                                              @RequestParam(value = "productionStatus", required = true) String productionStatus,
                                              @RequestParam(value = "areaLabel", required = true) String areaLabel) {

        try {
            Map<String, Object> param = new HashMap<String, Object>();

            param.put("productionStatus", productionStatus);
            param.put("areaLabel", areaLabel);


            // 动态生成表头，查询时间
            List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();
            beanList.add(new ExcelExportEntity("企业名称", "name"));
            beanList.add(new ExcelExportEntity("户号", "accnumber"));

            beanList.add(new ExcelExportEntity("统一社会信用代码", "creditCode"));
            beanList.add(new ExcelExportEntity("组织机构代码", "orgCode"));
            beanList.add(new ExcelExportEntity("行政区划", "areaName"));
            beanList.add(new ExcelExportEntity("详细地址", "address"));
            beanList.add(new ExcelExportEntity("所属园区", "micro_park"));
            beanList.add(new ExcelExportEntity("行业", "industryTypeName"));
            beanList.add(new ExcelExportEntity("业务活动", "business"));
            beanList.add(new ExcelExportEntity("产值规模", "output_value"));
            beanList.add(new ExcelExportEntity("企业标签", "tagList"));
            beanList.add(new ExcelExportEntity("企业联络人", "contactPerson"));
            beanList.add(new ExcelExportEntity("联系方式", "contactPhone"));

            beanList.add(new ExcelExportEntity("基准电量(kWh)", "unitEle"));
            beanList.add(new ExcelExportEntity("当月电量(kWh)", "value"));
            beanList.add(new ExcelExportEntity("当月同比(%)", "yearRate"));
            beanList.add(new ExcelExportEntity("当年累计电量(kWh)", "yearValue"));
            beanList.add(new ExcelExportEntity("产能状态", "productionStatus"));
            beanList.add(new ExcelExportEntity("当月环比(%)", "monthRate"));
            beanList.add(new ExcelExportEntity("环比状态", "growthRate"));


            List<Map<String, Object>> valueList = govMapper.findProductionStatusEntListForExcel(param);

            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), beanList, valueList);

            ExcelUtils.downLoadExcel("企业产能状态数据.xls", response, workbook);

        } catch (Exception e) {
            logger.error("企业生产状态列表下载错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/exportDynamicInfo");
            exceptionLog.setName("企业生产状态列表下载错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");

            this.saveExceptionLog(exceptionLog);

        }

    }

    @GetMapping("/excel/exportTaxUnusualEntList")
    @ResponseBody
    public void exportTaxUnusualEntList(HttpServletResponse response,
                                        @RequestParam(value = "areaLabel", required = true) String areaLabel) {

        try {
            Map<String, Object> param = new HashMap<String, Object>();

            param.put("areaLabel", areaLabel);

            // 动态生成表头，查询时间
            List<ExcelExportEntity> beanList = new ArrayList<ExcelExportEntity>();
            beanList.add(new ExcelExportEntity("企业名称", "name"));
            beanList.add(new ExcelExportEntity("户号", "accnumber"));

            beanList.add(new ExcelExportEntity("统一社会信用代码", "creditCode"));
            beanList.add(new ExcelExportEntity("组织机构代码", "orgCode"));
            beanList.add(new ExcelExportEntity("行政区划", "areaName"));
            beanList.add(new ExcelExportEntity("详细地址", "address"));
            beanList.add(new ExcelExportEntity("所属园区", "micro_park"));
            beanList.add(new ExcelExportEntity("行业", "industryTypeName"));
            beanList.add(new ExcelExportEntity("业务活动", "business"));
            beanList.add(new ExcelExportEntity("产值规模", "output_value"));
            beanList.add(new ExcelExportEntity("企业标签", "tagList"));
            beanList.add(new ExcelExportEntity("企业联络人", "contactPerson"));
            beanList.add(new ExcelExportEntity("联系方式", "contactPhone"));

            beanList.add(new ExcelExportEntity("基准电量(kWh)", "unitEle"));
            beanList.add(new ExcelExportEntity("当月电量(kWh)", "value"));
            beanList.add(new ExcelExportEntity("当月同比(%)", "yearRate"));
            beanList.add(new ExcelExportEntity("当年累计电量(kWh)", "yearValue"));
            beanList.add(new ExcelExportEntity("当月环比(%)", "monthRate"));

            beanList.add(new ExcelExportEntity("当月报税销售额", "ecValue"));
            beanList.add(new ExcelExportEntity("上月报税销售额", "lastECValue"));
            beanList.add(new ExcelExportEntity("当月报税额环比(%)", "monthECRate"));


            List<Map<String, Object>> valueList = govMapper.findTaxUnusualEntListForExcel(param);

            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), beanList, valueList);

            ExcelUtils.downLoadExcel("企业产能状态数据.xls", response, workbook);

        } catch (Exception e) {
            logger.error("报税异常企业列表下载错误", e);
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setType(1);
            exceptionLog.setUrl("/gov/exportDynamicInfo");
            exceptionLog.setName("报税异常企业列表下载错误");
            exceptionLog.setLevel(1);
            exceptionLog.setInformation(e.getMessage());
            exceptionLog.setOperator("");
            this.saveExceptionLog(exceptionLog);
        }

    }

    /**
     * @Author:lio  ================嘉善用户数据维护 start
     */


    /**
     * @Author:lio
     * @Description: 嘉善用能数据维护 数据导入
     * @Date :11:14 上午 2021/1/7
     */
    @PostMapping("/excel/importExcelData")
    @ResponseBody
    public Result importExcelData(@RequestParam("file") MultipartFile multipartFile,
                                  @RequestParam Map<String, Object> dataImportMap) throws Exception {
        Result result = new Result();
        // 校验导入的数据
        ExcelDataImportDTO dataImportDto = new ExcelDataImportDTO();
        dataImportDto =  MapUtils.map2Java(dataImportDto,dataImportMap);
        AssertUtil.NotBlank(multipartFile, "文件不能为空");
        String dateType = StringUtil.getString(dataImportDto.getSelectType());
        String dateTypeCode = StringUtil.getString(dataImportDto.getDataType());
        AssertUtil.NotBlank(dataImportDto.getDate(), "日期不能为空");
        AssertUtil.NotBlank(dateType, "日期类型不能为空");
        AssertUtil.NotBlank(dataImportDto.getDataType(), "数据类型不能为空");
        if (ENUM_DATE_TYPE.MONTH.getCode().equals(dateType)) {
            AssertUtil.CheckVerify(ENUM_DATA_TYPE.getDateYearFlagByCode(dateTypeCode), "数据导入类型错误");
        } else if (ENUM_DATE_TYPE.DAY.getCode().equals(dateType)) {
            AssertUtil.CheckVerify(ENUM_DATA_TYPE.getDateMonthFlagByCode(dateTypeCode), "数据导入类型错误");
        }
        //数据导入
        excelService.importExcelData(multipartFile, dataImportDto);
        return result;
    }







    }
