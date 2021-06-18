package com.enesource.jump.web.service;

import com.enesource.jump.web.dto.ExcelDataImportDTO;
import org.springframework.web.multipart.MultipartFile;

/* *
 * @author lio
 * @Description:
 * @date :created in 3:49 下午 2021/1/7
 */
public interface ExcelService {

    /**
     * @Author:lio
     * @Description: 数据导入
     * @Date :3:56 下午 2021/1/7
     */
    void importExcelData(MultipartFile multipartFile, ExcelDataImportDTO dataImportDto) throws Exception;
}
