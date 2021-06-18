package com.enesource.jump.web.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.enesource.jump.web.dao.IDataMaintenanceMapper;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dto.*;
import com.enesource.jump.web.enums.ENUM_DATA_TYPE;
import com.enesource.jump.web.enums.ENUM_DATE_TYPE;
import com.enesource.jump.web.interceptor.CheckExcelDayImportDTOExcelVerifyHandler;
import com.enesource.jump.web.interceptor.CheckExcelEleDayImportDTOExcelVerifyHandler;
import com.enesource.jump.web.interceptor.CheckExcelMonthFuHeImportDTOExcelVerifyHandler;
import com.enesource.jump.web.interceptor.CheckExcelMonthImportDTOExcelVerifyHandler;
import com.enesource.jump.web.service.ExcelService;
import com.enesource.jump.web.service.IAsyncService;
import com.enesource.jump.web.utils.*;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/* *
 * @author lio
 * @Description:
 * @date :created in 3:50 下午 2021/1/7
 */
@Service
@Transactional
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    IGovMapper govMapper;

    @Autowired
    IDataMaintenanceMapper iDataMaintenanceMapper;

    @Autowired
    IAsyncService asyncService;

    @Autowired
    private CheckExcelEleDayImportDTOExcelVerifyHandler checkExcelEleDayImportDTOExcelVerifyHandler;

    /**
     * @Author:lio
     * @Description:数据导入
     * @Date :3:57 下午 2021/1/7
     */
    @Override
    public void importExcelData(MultipartFile multipartFile, ExcelDataImportDTO dataImportDto) throws Exception {
        String dataTypeCode = dataImportDto.getDataType();
        ImportParams params = new ImportParams();
        // 开启校验
        params.setNeedVerify(true);
        params.setTitleRows(1);
        // 设置表头行数
        params.setHeadRows(1);
        StringBuilder msg = new StringBuilder();
        //数据校验 如果格式正常 再进行导入
        if (ENUM_DATA_TYPE.KAIPIAO.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.DIANFEI.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.TIANRANQI.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.YONGSHUI.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.MEITAN.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.QIYOU.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.CHAIYOU.getCode().equals(dataTypeCode)
                || ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(dataTypeCode)) {
            // 验证导入
            params.setVerifyHandler(new CheckExcelMonthImportDTOExcelVerifyHandler());
            ExcelImportResult<ExcelMonthImport> excelData = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), ExcelMonthImport.class, params);
            if (excelData.isVerfiyFail()) {
                List<ExcelMonthImport> failList = excelData.getFailList();
                if (!CollectionUtils.isEmpty(failList)) {
                    for (ExcelMonthImport entity : failList) {
                        msg.append(String.format("第%s行的错误是:%s %s", entity.getRowNum(), entity.getErrorMsg(), StrUtil.CRLF));
                    }
                    AssertUtil.ThrowSystemErr(msg.toString());
                }
            } else {
                // 异步数据导入
                asyncService.entInfoExcelImport(multipartFile, params, dataImportDto, dataTypeCode, excelData);
            }
        } else if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataTypeCode)) {
            params.setNeedVerify(false);
            List<ExcelDayEleImport> readExcelList = ExcelImportUtil.importExcel(multipartFile.getInputStream(), ExcelDayEleImport.class, params);
            if (CollectionUtil.isNotEmpty(readExcelList)) {
                msg = checkExcelEleDayImportDTOExcelVerifyHandler.verifyDataList(readExcelList, params);
                if (msg.length() != 0) {
                    AssertUtil.ThrowSystemErr(msg.toString());
                } else {
                    asyncService.entEleExcelImport(multipartFile, params, dataImportDto, readExcelList);
                }
            }
        } else if (ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(dataTypeCode)) {
            params.setVerifyHandler(new CheckExcelMonthFuHeImportDTOExcelVerifyHandler());
            ExcelImportResult<ExcelFuHeMonthImport> excelData = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), ExcelFuHeMonthImport.class, params);
            if (excelData.isVerfiyFail()) {
                List<ExcelFuHeMonthImport> failList = excelData.getFailList();
                if (!CollectionUtils.isEmpty(failList)) {
                    for (ExcelFuHeMonthImport entity : failList) {
                        msg.append(String.format("第%s行的错误是:%s %s", entity.getRowNum(), entity.getErrorMsg(), StrUtil.CRLF));
                    }
                    AssertUtil.ThrowSystemErr(msg.toString());
                }
            } else {
                asyncService.entFuheExcelImport(multipartFile, params, dataImportDto, excelData);
            }
        } else if (ENUM_DATA_TYPE.YONGRE.getCode().equals(dataTypeCode)) {
            params.setVerifyHandler(new CheckExcelDayImportDTOExcelVerifyHandler());
            ExcelImportResult<ExcelDayImport> excelData = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), ExcelDayImport.class, params);
            if (excelData.isVerfiyFail()) {
                List<ExcelDayImport> failList = excelData.getFailList();
                if (!CollectionUtils.isEmpty(failList)) {
                    for (ExcelDayImport entity : failList) {
                        msg.append(String.format("第%s行的错误是:%s %s", entity.getRowNum(), entity.getErrorMsg(), StrUtil.CRLF));
                    }
                    AssertUtil.ThrowSystemErr(msg.toString());
                }
            } else {
                asyncService.entYongReExcelImport(multipartFile, params, dataImportDto, excelData);
            }
        }
    }


}
