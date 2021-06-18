package com.enesource.jump.web.dao;

import com.enesource.jump.web.dto.ExcelDataImportDTO;
import com.enesource.jump.web.dto.ParamDTO;

import java.util.List;
import java.util.Map;

/* *
 * @author lio
 * @Description:
 * @date :created in 11:06 上午 2021/1/14
 */
public interface IDataMaintenanceMapper {


    /**
     * 企业列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> dataMainCompanyList(ParamDTO dto);


    List<Map<String, Object>> dataMainAccList(Map<String, Object> ent);

    List<Map<String, Object>> dataMainListByMonth(Map<String, Object> acc);

    List<Map<String, Object>> dataMainListByDay(Map<String, Object> acc);

    List<Map<String, Object>> dataMainCompSumListByMonth(Map<String, Object> ent);

    List<Map<String, Object>> dataMainCompSumListByDay(Map<String, Object> ent);

    List<Map<String, Object>> dataEntEnergyInfo(Map<String, Object> reqMap);

    List<Map<String, Object>> getEntDianFeiInfo(Map<String, Object> reqMap);

    List<Map<String, Object>> getEntFuHeInfo(Map<String, Object> reqMap);

    List<Map<String, Object>> getEntDianFeiInfoByImport(ExcelDataImportDTO dataImportDto);

    List<Map<String, Object>> getEntFuHeByImport(ExcelDataImportDTO dataImportDto);

    List<Map<String, Object>> getEntEnergyInfoByImport(ExcelDataImportDTO dataImportDto);

    List<Map<String, Object>> getEntTicketInfoByImport(ExcelDataImportDTO dataImportDto);

    void insertEntTicketInfo(List<Map<String, Object>> insertTicketInfoDto);

    void updateEntTicketInfo(List<Map<String, Object>> updateTicketInfoDto);

    List<Map<String, Object>> getEntKaiPiaoInfo(Map<String, Object> reqMap);

    void insertDianFeiInfo(List<Map<String, Object>> insertEntInfoDto);

    void insertFuHeInfo(List<Map<String, Object>> insertEntInfoDto);

    void insertEnergyInfo(List<Map<String, Object>> insertEntInfoDto);

    void updateDianFeiInfo(List<Map<String, Object>> updateEntInfoDto);

    void updateFuHeInfo(List<Map<String, Object>> updateEntInfoDto);

    void updateEnergyInfo(List<Map<String, Object>> updateEntInfoDto);

    void insertYongReInfo(List<Map<String, Object>> insertEntInfoDto);

    void updateYongReInfo(List<Map<String, Object>> insertEntInfoDto);

    List<Map<String, Object>> getEntYongReInfoByImport(ExcelDataImportDTO dataImportDto);

    List<Map<String, Object>> getEntEleInfoByImport(ExcelDataImportDTO dataImportDto);

    void insertEleInfo(List<Map<String, Object>> insertEntInfoDto);

    void updateFengEleInfo(List<Map<String, Object>> updateEntInfoDto);

    void updateJianEleInfo(List<Map<String, Object>> updateJianEntInfoDto);

    void updateGuEleInfo(List<Map<String, Object>> updateGuEntInfoDto);

    void updateValueEleInfo(ExcelDataImportDTO dataImportDto);

    List<Map<String, Object>> getEntFuHeInfoMonthList(ParamDTO detailParam);

    Map<String, Object> getEntKaiPiaoInfoByDate(Map<String, Object> stringObjectMap);

    int updateKaiPiaoInfoByCompany(Map<String, Object> entKaiPiaoInfo);

    int insetKaiPiaoByCompany(Map<String, Object> resMap);

    Map<String, Object> getEntEnergyInfoByDate(Map<String, Object> resMap);

    int insetEnergyInfoByCompany(Map<String, Object> resMap);

    int updateEntEnergyInfoByDate(Map<String, Object> resMap);

    List<Map<String, Object>> getEleDetail(Map<String, Object> resMap);

    List<Map<String, Object>> getFuHeDetail(Map<String, Object> companyMap);

    List<Map<String, Object>> getDataReLiDetail(Map<String, Object> companyMap);

    List<Map<String, Object>> getDataEleDayDetail(Map<String, Object> companyMap);

    List<Map<String, Object>> dataFuHeMainListByMonth(Map<String, Object> acc);

    List<Map<String, Object>> dataFuHeMainListByDay(Map<String, Object> acc);

    List<Map<String, Object>> dataFuHeMainCompSumListByMonth(Map<String, Object> acc);

    List<Map<String, Object>> dataFuHeMainCompSumListByDay(Map<String, Object> acc);

    List<Map<String, Object>> getEntYongReInfo(Map<String, Object> reqMap);

    List<Map<String, Object>> dataMainCompanyAccList(ParamDTO paramDTO);

    List<Map<String, Object>> getEntEnergyInfo(Map<String, Object> companyDataMap);
}
