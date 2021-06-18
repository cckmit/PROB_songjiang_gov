package com.enesource.jump.web.service;

import java.util.List;
import java.util.Map;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.enesource.jump.web.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface IAsyncService {
	
	/**
	 * 更新企业信息
	 * @param dto
	 */
	public void mergeEntInfo(List<EntInfoDTO> dtol);
	
	
	/**
	 * 更新企业能源信息
	 * @param dtol
	 * @param energyType 	能源类型
	 * @param areaLabel		区域标签
	 */
	void mergeEnergyinfo(List<EnergyinfoDTO> dtol, String energyType, String areaLabel);
	
	/**
	 * 更新企业经济信息
	 * @param dtol
	 * @param areaLabel		区域标签
	 */
	void mergeEconomicsinfo(List<EconomicsinfoDTO> dtol, String areaLabel);
	
	
	/**
	 * 更新多能监测项目数据
	 * @param dto
	 */
	void mergeGovMapInfoDTO(List<GovMapInfoDTO> dtol);
	
	/**
	 * 更新多能监测项目数据
	 * @param dto
	 */
	void mergePhotoInfoDTO(List<PhotoDTO> dtol);
	
	/**
	 * 更新多能监测项目数据
	 * @param dto
	 */
	void mergeChargeInfoDTO(List<ChargeDTO> dtol);
	
	/**
	 * 更新缺失日电量数据
	 * @param dto
	 */
	void mergeDayValueDTO(List<ImportDayValueDTO> dtol, String date);
	
	
	/**
	 * 修改指标值时，统一更新状态值
	 */
	void updateEntEnergyinfoCompSumStatus();
	
	
	/**
	 * 重新计算产能比例、更新企业的产能状态
	 * @param paramMap
	 */
	void updateProductionStatus(Map<String, Object> paramMap);

	void updateEnergyInfo(ExcelDataImportDTO dataImportDto);

	void importData(MultipartFile multipartFile, ExcelDataImportDTO dataImportDto, String dataTypeCode, ImportParams params) throws Exception;

    void entInfoExcelImport(MultipartFile multipartFile, ImportParams params, ExcelDataImportDTO dataImportDto, String dataTypeCode, ExcelImportResult<ExcelMonthImport> excelData);

	void entEleExcelImport(MultipartFile multipartFile, ImportParams params, ExcelDataImportDTO dataImportDto, List<ExcelDayEleImport> readExcelList);

	void entFuheExcelImport(MultipartFile multipartFile, ImportParams params, ExcelDataImportDTO dataImportDto, ExcelImportResult<ExcelFuHeMonthImport> excelData);

	void entYongReExcelImport(MultipartFile multipartFile, ImportParams params, ExcelDataImportDTO dataImportDto, ExcelImportResult<ExcelDayImport> excelData);
}
