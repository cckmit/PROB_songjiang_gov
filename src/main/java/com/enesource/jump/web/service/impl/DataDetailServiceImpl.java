package com.enesource.jump.web.service.impl;

import com.enesource.jump.web.dao.ICommonMapper;
import com.enesource.jump.web.dao.IDataMaintenanceMapper;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dto.CompanyDataDTO;
import com.enesource.jump.web.dto.ParamDTO;
import com.enesource.jump.web.dto.UserInfoDTO;
import com.enesource.jump.web.enums.ENUM_DATA_TYPE;
import com.enesource.jump.web.enums.ENUM_DATE_TYPE;
import com.enesource.jump.web.enums.ENUM_USER_LEVEL;
import com.enesource.jump.web.service.DataDetailService;
import com.enesource.jump.web.utils.*;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* *
 * @author lio
 * @Description:
 * @date :created in 11:51 上午 2021/1/12
 */
@Service
@Transactional
public class DataDetailServiceImpl implements DataDetailService {


    @Autowired
    IGovMapper iGovMapper;

    @Autowired
    ICommonMapper commonMapper;

    @Autowired
    IDataMaintenanceMapper dataMaintenanceMapper;

    @Override
    public List<Map<String, Object>> getDataDetail(ParamDTO detailParam, List<Map<String, Object>> entList) {
        String dataType = detailParam.getDataType();
        List<Map<String, Object>> resMap = Lists.newArrayList();
        if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(dataType) || ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(dataType)
        ) {
            resMap = getEleDetail(entList, detailParam);
        } else if (ENUM_DATA_TYPE.KAIPIAO.getCode().equals(dataType) || ENUM_DATA_TYPE.DIANFEI.getCode().equals(dataType)
        ) {
            resMap = getEntInfo(entList, detailParam, dataType);
        } else if (ENUM_DATA_TYPE.YONGSHUI.getCode().equals(dataType) || ENUM_DATA_TYPE.TIANRANQI.getCode().equals(dataType)
                || ENUM_DATA_TYPE.QIYOU.getCode().equals(dataType) || ENUM_DATA_TYPE.CHAIYOU.getCode().equals(dataType)
                || ENUM_DATA_TYPE.MEITAN.getCode().equals(dataType) || ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(dataType)
                || ENUM_DATA_TYPE.YONGRE.getCode().equals(dataType)
        ) {
            resMap = getEntEnergyInfo(entList, detailParam);
        }
        return resMap;
    }


    /**
     * @Author:lio
     * @Description: 更新或新增企业数据信息
     * @Date :10:49 上午 2021/1/22
     */
    @Override
    public String insertOrUpdateCompanyData(Map<String, Object> stringObjectMap) {
        String userId = StringUtil.getString(stringObjectMap.get("userId"));
        AssertUtil.NotBlank(userId, "用户ID不能为空");
        String companyId = StringUtil.getString(stringObjectMap.get("companyId"));
        AssertUtil.NotBlank(companyId, "企业ID不能为空");
        String date = StringUtil.getString(stringObjectMap.get("date"));
        //新增或更新 开票额信息
        int kaiPiao = insertOrUpdateKaiPiaoData(stringObjectMap, companyId, date, userId);
        //新增或更新 累积用水
        Object updateValue = stringObjectMap.get("yongShui");
        int yongShui = insertOrUpdateEnergyData(ENUM_DATA_TYPE.YONGSHUI.getCode(), updateValue, companyId, date, userId);
        updateValue = stringObjectMap.get("tianRanQi");
        int tianRanQi = insertOrUpdateEnergyData(ENUM_DATA_TYPE.TIANRANQI.getCode(), updateValue, companyId, date, userId);
        updateValue = stringObjectMap.get("meiTan");
        int meiTan = insertOrUpdateEnergyData(ENUM_DATA_TYPE.MEITAN.getCode(), updateValue, companyId, date, userId);
        updateValue = stringObjectMap.get("qiYou");
        int qiYou = insertOrUpdateEnergyData(ENUM_DATA_TYPE.QIYOU.getCode(), updateValue, companyId, date, userId);
        updateValue = stringObjectMap.get("chaiYou");
        int chaiYou = insertOrUpdateEnergyData(ENUM_DATA_TYPE.CHAIYOU.getCode(), updateValue, companyId, date, userId);
        updateValue = stringObjectMap.get("shengWuZhi");
        int shengWuZhi = insertOrUpdateEnergyData(ENUM_DATA_TYPE.SHENGWUZHI.getCode(), updateValue, companyId, date, userId);
        //更新企业区域能耗信息
        updateCompanyEnergyInfo(date, companyId);
        //根据更新情况，更新能耗统计数据
        String message = checkCompanyEnergyInfo(kaiPiao, yongShui, tianRanQi, meiTan, qiYou, chaiYou, shengWuZhi);
        return message;
    }

    @Override
    public Map<String, Object> getCompanyData(Map<String, Object> companyDataMap) {
        Map<String, Object> resMap = Maps.newHashMap();
        //获取累计开票额
        Map<String, Object> entKaiPiaoInfo = dataMaintenanceMapper.getEntKaiPiaoInfoByDate(companyDataMap);
        String kaiPiao = "";
        if (null != entKaiPiaoInfo) {
            kaiPiao = StringUtil.getString(entKaiPiaoInfo.get("value"));
        }
        String yongShui = "";
        String tianRanQi = "";
        String meiTan = "";
        String qiYou = "";
        String chaiYou = "";
        String shengWuZhi = "";
        //获取能源数据
        List<Map<String, Object>> energyInfoList = dataMaintenanceMapper.getEntEnergyInfo(companyDataMap);
        if (!CollectionUtils.isEmpty(energyInfoList)) {
            for (int i = 0; i < energyInfoList.size(); i++) {
                String energyType = StringUtil.getString(energyInfoList.get(i).get("energyType"));
                String value = StringUtil.removeZero(StringUtil.getStringZero(energyInfoList.get(i).get("value")));
                if (!"0".equals(value)) {
                    if (ENUM_DATA_TYPE.YONGSHUI.getCode().equals(energyType)) {
                        yongShui = value;
                    } else if (ENUM_DATA_TYPE.TIANRANQI.getCode().equals(energyType)) {
                        tianRanQi = value;
                    } else if (ENUM_DATA_TYPE.MEITAN.getCode().equals(energyType)) {
                        meiTan = value;
                    } else if (ENUM_DATA_TYPE.QIYOU.getCode().equals(energyType)) {
                        qiYou = value;
                    } else if (ENUM_DATA_TYPE.CHAIYOU.getCode().equals(energyType)) {
                        chaiYou = value;
                    } else if (ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(energyType)) {
                        shengWuZhi = value;
                    }
                }
            }
        }
        resMap.put("kaiPiao", kaiPiao);
        resMap.put("yongShui", yongShui);
        resMap.put("tianRanQi", tianRanQi);
        resMap.put("meiTan", meiTan);
        resMap.put("qiYou", qiYou);
        resMap.put("chaiYou", chaiYou);
        resMap.put("shengWuZhi", shengWuZhi);
        return resMap;
    }

    /**
     * @Author:lio
     * @Description: 更新企业的能耗情况
     * 1：只有电网人才能更新和覆盖所有数据
     * @Date :10:38 上午 2021/2/3
     */
    private String checkCompanyEnergyInfo(int kaiPiao, int yongShui, int tianRanQi, int meiTan, int qiYou, int chaiYou, int shengWuZhi) {
        int success = 1;
        String message = "";
        // TODO
        // 2：如果电网已经更新了数据，企业不能覆盖
        if (kaiPiao != success) {
            message = StringUtil.addMessage(message, "累计开票额");
        }
        if (yongShui != success) {
            message = StringUtil.addMessage(message, "累计用水");
        }
        if (tianRanQi != success) {
            message = StringUtil.addMessage(message, "累计天然气");
        }
        if (meiTan != success) {
            message = StringUtil.addMessage(message, "累计煤炭");
        }
        if (qiYou != success) {
            message = StringUtil.addMessage(message, "累计汽油");
        }
        if (chaiYou != success) {
            message = StringUtil.addMessage(message, "累计柴油");
        }
        if (shengWuZhi != success) {
            message = StringUtil.addMessage(message, "累计生物质燃料");
        }
        if (StringUtil.isNotEmpty(message)) {
            message = message + "数据不能覆盖，更新失败";
        }
        return message;
    }


    private int insertOrUpdateEnergyData(String energyType, Object updateValue, String companyId, String date, String userId) {
        int flag = 0;
        if (null != updateValue) {
            String updateValues = StringUtil.removeZero(StringUtil.getStringZero(updateValue));
            Map<String, Object> resMap = Maps.newHashMap();
            resMap.put("value", updateValues);
            resMap.put("companyId", companyId);
            resMap.put("date", date);
            resMap.put("energyType", energyType);
            resMap.put("userId", userId);
            String tce = "0";
            if (StringUtil.isNotEmpty(updateValues) && StringUtil.isNotEmpty(ENUM_DATA_TYPE.getTceByCode(energyType))) {
                if (ENUM_DATA_TYPE.SHENGWUZHI.getCode().equals(energyType)) {
                    tce = StringUtil.getStringZero(updateValue);
                } else {
                    tce = BigDecimalUtil.multiply(updateValues, StringUtil.getString(ENUM_DATA_TYPE.getTceByCode(energyType)));
                }
            }
            resMap.put("tce", tce);
            Map<String, Object> entYongInfo = dataMaintenanceMapper.getEntEnergyInfoByDate(resMap);
            if (null != entYongInfo) {
                String updateUser = StringUtil.getString(entYongInfo.get("updateUser"));
                if (checkUpdateUser(updateUser, userId)) {
                    resMap.put("value", updateValues);
                    resMap.put("id", entYongInfo.get("id"));
                    flag = dataMaintenanceMapper.updateEntEnergyInfoByDate(resMap);
                }
            } else {
                flag = dataMaintenanceMapper.insetEnergyInfoByCompany(resMap);
            }
        } else {
            flag = 1;
        }
        return flag;
    }

    private void updateCompanyEnergyInfo(String date, String companyId) {
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("date", date);
        resMap.put("companyId", companyId);
        // 区域能耗 t_area_statistics
        iGovMapper.delAreaStatisticsByCompany(resMap);
        iGovMapper.insertAreaStatisticsByImportByCompany(resMap);
        // TODO 政府应用补充数据
        iGovMapper.deleteEntEnergyInfoComSumByCompany(resMap);
        iGovMapper.insertEntEnergyInfoComSumByCompany(resMap);
    }

    /**
     * @Author:lio
     * @Description:新增或更新开票信息
     * @Date :11:41 上午 2021/1/22
     */
    private int insertOrUpdateKaiPiaoData(Map<String, Object> reqMap, String companyId, String date, String userId) {
        int flag = 0;
        if (null != reqMap.get("kaiPiao")) {
            String updateValue = StringUtil.removeZero(StringUtil.getStringZero(reqMap.get("kaiPiao")));
            Map<String, Object> entKaiPiaoInfo = dataMaintenanceMapper.getEntKaiPiaoInfoByDate(reqMap);
            if (null != entKaiPiaoInfo) {
                // 如果更新的数据是国网则不能更新
                String updateUser = StringUtil.getString(entKaiPiaoInfo.get("updateUser"));
                if (checkUpdateUser(updateUser, userId)) {
                    entKaiPiaoInfo.put("value", updateValue);
                    entKaiPiaoInfo.put("userId", userId);
                    flag = dataMaintenanceMapper.updateKaiPiaoInfoByCompany(entKaiPiaoInfo);
                }

            } else {
                Map<String, Object> resMap = Maps.newHashMap();
                resMap.put("value", updateValue);
                resMap.put("companyId", companyId);
                resMap.put("date", date);
                resMap.put("userId", userId);
                flag = dataMaintenanceMapper.insetKaiPiaoByCompany(resMap);
            }
        } else {
            flag = 1;
        }
        return flag;
    }

    /**
     * @Author:lio
     * @Description: 验证当前用户是否能进行更新
     * @Date :4:44 下午 2021/1/27
     */
    private boolean checkUpdateUser(String updateUser, String localUserId) {
        boolean checkFlag = false;
        UserInfoDTO userInfoByUser = commonMapper.getUserInfoByUserId(localUserId);
        UserInfoDTO updateUserInfo = commonMapper.getUserInfoByUserId(updateUser);
        if ((ENUM_USER_LEVEL.ADMIN.getCode().equals(StringUtil.getString(userInfoByUser.getLevel())))
                || (null == updateUserInfo || !ENUM_USER_LEVEL.ADMIN.getCode().equals(StringUtil.getString(updateUserInfo.getLevel())))) {
            checkFlag = true;
        }
        return checkFlag;
    }


    /**
     * @Author:lio
     * @Description: 获取开票数据
     * @Date :2:10 下午 2021/1/14
     */
    private List<Map<String, Object>> getEntInfo(List<Map<String, Object>> entList, ParamDTO detailParam, String dataType) {
        List<Map<String, Object>> resList = Lists.newArrayList();
        if (!entList.isEmpty()) {
            for (Map<String, Object> ent : entList) {
                String sumValue = "0";
                Map<String, Object> resMap = Maps.newHashMap();
                Map<String, Object> reqMap = Maps.newHashMap();
                reqMap.put("companyId", ent.get("companyId"));
                reqMap.put("date", detailParam.getDate());
                List<Map<String, Object>> valueList = Lists.newArrayList();
                if (ENUM_DATA_TYPE.KAIPIAO.getCode().equals(dataType)) {
                    valueList = dataMaintenanceMapper.getEntKaiPiaoInfo(reqMap);
                } else if (ENUM_DATA_TYPE.DIANFEI.getCode().equals(dataType)) {
                    valueList = dataMaintenanceMapper.getEntDianFeiInfo(reqMap);
                } else if (ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(dataType)) {
                    valueList = dataMaintenanceMapper.getEntFuHeInfo(reqMap);
                }
                resMap.put("entName", StringUtil.getString(ent.get("entName")));
                List<Map<String, Object>> listMap = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(valueList)) {
                    Map<String, Object> changeMap = Maps.newHashMap();
                    changeMap.put("date", detailParam.getDate());
                    for (Map map : valueList) {
                        String value = StringUtil.getStringZero(map.get("value"));
                        changeMap.put(StringUtil.getString(map.get("month")), map.get("value"));
                        sumValue = BigDecimalUtil.add(value, sumValue);
                    }
                    changeMap.put("sum", sumValue);
                    listMap.add(changeMap);
                }
                resMap.put("valueList", listMap);
                resList.add(resMap);
            }
        }
        return resList;
    }


    /**
     * @Author:lio
     * @Description: 获取能源数据
     * @Date :2:09 下午 2021/1/14
     */
    private List<Map<String, Object>> getEntEnergyInfo(List<Map<String, Object>> entList, ParamDTO detailParam) {
        List<Map<String, Object>> resList = Lists.newArrayList();
        if (!entList.isEmpty()) {
            for (Map<String, Object> ent : entList) {
                String sumValue = "0";
                Map<String, Object> reqMap = Maps.newHashMap();
                reqMap.put("companyId", ent.get("companyId"));
                reqMap.put("dataType", detailParam.getDataType());
                reqMap.put("date", detailParam.getDate());
                List<Map<String, Object>> valueList = Lists.newArrayList();
                if (ENUM_DATA_TYPE.YONGRE.getCode().equals(detailParam.getDataType()) && detailParam.getSelectType().equals(ENUM_DATE_TYPE.DAY.getCode())) {
                    valueList = dataMaintenanceMapper.getEntYongReInfo(reqMap);
                } else {
                    valueList = dataMaintenanceMapper.dataEntEnergyInfo(reqMap);
                }
                Map<String, Object> resMap = Maps.newHashMap();
                resMap.put("entName", StringUtil.getString(ent.get("entName")));
                Map<String, Object> changeMap = Maps.newHashMap();
                changeMap.put("date", detailParam.getDate());
                List<Map<String, Object>> listMap = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(valueList)) {
                    for (Map map : valueList) {
                        if (null != map) {
                            String value = StringUtil.getStringZero(map.get("value"));
                            changeMap.put(StringUtil.getString(map.get("day")), value);
                            sumValue = BigDecimalUtil.add(sumValue, value);
                        }
                    }
                    changeMap.put("sum", sumValue);
                }
                listMap.add(changeMap);
                resMap.put("valueList", listMap);
                resList.add(resMap);
            }
        }
        return resList;
    }


    /**
     * @Author:lio
     * @Description: 组装电量详情数据 最高负荷户号和电量户号是一样的
     * @Date :11:37 上午 2021/1/14
     */
    public List<Map<String, Object>> getEleDetail(List<Map<String, Object>> entList, ParamDTO detailParam) {
        if (!entList.isEmpty()) {
            for (Map<String, Object> ent : entList) {
                // 户号列表
                List<Map<String, Object>> accList = dataMaintenanceMapper.dataMainAccList(ent);
                String totalSumValue = "0";
                int accnum = 0;
                if (!accList.isEmpty()) {
                    accnum = accList.size() + 1;
                    for (Map<String, Object> acc : accList) {
                        String sumValue = "0";
                        List<Map<String, Object>> valueList = Lists.newArrayList();
                        if (ENUM_DATE_TYPE.MONTH.getCode().equals(detailParam.getSelectType())) {
                            if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(detailParam.getDataType())) {
                                valueList = dataMaintenanceMapper.dataMainListByMonth(acc);
                            } else if (ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(detailParam.getDataType())) {
                                valueList = dataMaintenanceMapper.dataFuHeMainListByMonth(acc);
                            }
                            if (!valueList.isEmpty()) {
                                for (Map valueMap : valueList) {
                                    String value = StringUtil.getStringZero(valueMap.get("value"));
                                    sumValue = BigDecimalUtil.add(sumValue, value);
                                }
                                Map<String, Object> sumMap = Maps.newHashMap();
                                sumMap.put("sum", sumValue);
                                valueList.add(sumMap);
                                ent.put("acc_" + acc.get("accnumber"), valueList);
                            } else {
                                ent.put("acc_" + acc.get("accnumber"), new ArrayList());
                            }
                        } else if (ENUM_DATE_TYPE.DAY.getCode().equals(detailParam.getSelectType())) {
                            if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(detailParam.getDataType())) {
                                valueList = dataMaintenanceMapper.dataMainListByDay(acc);
                            } else if (ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(detailParam.getDataType())) {
                                valueList = dataMaintenanceMapper.dataFuHeMainListByDay(acc);
                            }
                            if (!valueList.isEmpty()) {
                                for (Map valueMap : valueList) {
                                    String value = StringUtil.getStringZero(valueMap.get("value"));
                                    sumValue = BigDecimalUtil.add(sumValue, value);
                                }
                                Map<String, Object> sumMap = Maps.newHashMap();
                                sumMap.put("sum", sumValue);
                                valueList.add(sumMap);
                                ent.put("acc_" + acc.get("accnumber"), valueList);
                            } else {
                                ent.put("acc_" + acc.get("accnumber"), new ArrayList());
                            }
                        }
                    }
                    Map<String, Object> acc = new HashMap<String, Object>();
                    acc.put("date", detailParam.getDate());
                    acc.put("companyId", ent.get("companyId"));
                    acc.put("accnumber", "total");
                    if (ENUM_DATE_TYPE.MONTH.getCode().equals(detailParam.getSelectType())) {
                        List<Map<String, Object>> sumValueList = Lists.newArrayList();
                        if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(detailParam.getDataType())) {
                            sumValueList = dataMaintenanceMapper.dataMainCompSumListByMonth(ent);
                        } else if (ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(detailParam.getDataType())) {
                            sumValueList = dataMaintenanceMapper.dataFuHeMainCompSumListByMonth(acc);
                        }
                        if (!sumValueList.isEmpty()) {
                            for (Map valueMap : sumValueList) {
                                String value = StringUtil.getStringZero(valueMap.get("value"));
                                totalSumValue = BigDecimalUtil.add(totalSumValue, value);
                            }
                            Map<String, Object> sumMap = Maps.newHashMap();
                            sumMap.put("sum", totalSumValue);
                            sumValueList.add(sumMap);
                            ent.put("acc_total", sumValueList);
                        } else {
                            ent.put("acc_total", new ArrayList());
                        }
                    } else if (ENUM_DATE_TYPE.DAY.getCode().equals(detailParam.getSelectType())) {
                        List<Map<String, Object>> sumValueList = Lists.newArrayList();
                        if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(detailParam.getDataType())) {
                            sumValueList = dataMaintenanceMapper.dataMainCompSumListByDay(ent);
                        } else if (ENUM_DATA_TYPE.ZUIGAOFUHE.getCode().equals(detailParam.getDataType())) {
                            sumValueList = dataMaintenanceMapper.dataFuHeMainCompSumListByDay(acc);
                        }
                        if (!sumValueList.isEmpty()) {
                            for (Map valueMap : sumValueList) {
                                String value = StringUtil.getStringZero(valueMap.get("value"));
                                totalSumValue = BigDecimalUtil.add(totalSumValue, value);
                            }
                            Map<String, Object> sumMap = Maps.newHashMap();
                            sumMap.put("sum", totalSumValue);
                            sumValueList.add(sumMap);
                            ent.put("acc_total", sumValueList);
                        } else {
                            ent.put("acc_total", new ArrayList());
                        }
                    }
                }
                ent.put("accnum", accnum);
            }
        }
        return entList;
    }


}
