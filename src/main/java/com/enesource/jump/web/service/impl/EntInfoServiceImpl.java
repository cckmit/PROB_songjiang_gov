package com.enesource.jump.web.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.enesource.jump.web.dto.MultiplesDTO;
import com.enesource.jump.web.enums.ENUM_DATA_TYPE;
import com.enesource.jump.web.utils.BigDecimalUtil;
import com.enesource.jump.web.utils.StringUtil;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enesource.jump.web.dao.IAccnumberMdmidRelMapper;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dao.ITagMapper;
import com.enesource.jump.web.dto.AccnumberMdmidRelDTO;
import com.enesource.jump.web.dto.EntInfoDTO;
import com.enesource.jump.web.dto.TagDTO;
import com.enesource.jump.web.service.IAsyncService;
import com.enesource.jump.web.service.IEntInfoService;
import com.enesource.jump.web.utils.MapUtils;
import org.springframework.util.CollectionUtils;


@Service("entInfoService")
public class EntInfoServiceImpl implements IEntInfoService {

    @Autowired
    IGovMapper govMapper;

    @Autowired
    ITagMapper tagMapper;

    @Autowired
    IAsyncService asyncService;

    @Autowired
    IAccnumberMdmidRelMapper accnumberMdmidRelMapper;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateEntInfo(Map<String, Object> paramMap, EntInfoDTO dto) {

        govMapper.updateEntInfo(paramMap);

        dto = MapUtils.map2Java(dto, paramMap);

        // 更新户号
        if (dto.getPowerNoList() != null) {
            paramMap.put("type", 0);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getPowerNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getGasNoList() != null) {
            paramMap.put("type", 1);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getGasNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getHotNoList() != null) {
            paramMap.put("type", 2);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getHotNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getWaterNoList() != null) {
            paramMap.put("type", 3);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getWaterNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getOilNoList() != null) {
            paramMap.put("type", 4);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getOilNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getPetrolNoList() != null) {
            paramMap.put("type", 6);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getPetrolNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getCoalNoList() != null) {
            paramMap.put("type", 8);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getCoalNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

//		
//		if("add".equals(paramMap.get("operation").toString())) {
//			govMapper.insertConsNo(paramMap);
//		}
//		
//		if("update".equals(paramMap.get("operation").toString())) {
//			govMapper.updateConsNo(paramMap);
//		}
//
//		if("del".equals(paramMap.get("operation").toString())) {
//			govMapper.delConsNo(paramMap);
//		}


    }

    @Override
    public void updateEntBaseInfo(Map<String, Object> paramMap, EntInfoDTO dto) {
        govMapper.updateEntBaseInfo(paramMap);

        this.updateEntTagInfo(paramMap, dto);

    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateEntTagInfo(Map<String, Object> paramMap, EntInfoDTO dto) {
        // TODO 删除原有标签关联关系
        TagDTO tagDTO = new TagDTO();

        tagDTO.setIdentifier(paramMap.get("companyId").toString());
        tagDTO.setType("ent");

        tagMapper.delTagRelByIdentifier(tagDTO);

        // TODO 建立新标签关联关系
        if (dto.getTagArr() != null) {
            for (String tagKey : dto.getTagArr()) {

                tagDTO.setTagKey(tagKey);

                tagMapper.saveTagRel(tagDTO);
            }
        }

    }

    @Override
    public void updateEntFileInfo(Map<String, Object> paramMap, EntInfoDTO dto) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateEntAccnumberInfo(Map<String, Object> paramMap, AccnumberMdmidRelDTO dto) {
        // merge 户号企业关联表
        govMapper.mergeEntAccnumberRel(paramMap);

        // TODO 删除原有标签关联关系
        accnumberMdmidRelMapper.delAMRel(dto);

        // TODO 建立新标签关联关系
        if (dto.getMdmidArr() != null) {
            for (String deviceId : dto.getMdmidArr()) {

                dto.setDeviceId(deviceId);

                accnumberMdmidRelMapper.saveAMRel(dto);
            }
        } else {
            accnumberMdmidRelMapper.saveAMRel(dto);
        }

        // TODO 计算企业的基准电量，并异步触发产能状态的调整
        govMapper.updateCompanyIdUnitEle(paramMap);


        asyncService.updateProductionStatus(paramMap);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateEntOtherAccnumberInfo(Map<String, Object> paramMap, EntInfoDTO dto) {
        dto = MapUtils.map2Java(dto, paramMap);

        // 更新户号
        if (dto.getGasNoList() != null) {
            paramMap.put("type", 1);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getGasNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getHotNoList() != null) {
            paramMap.put("type", 2);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getHotNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getWaterNoList() != null) {
            paramMap.put("type", 3);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getWaterNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getOilNoList() != null) {
            paramMap.put("type", 4);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getOilNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getPetrolNoList() != null) {
            paramMap.put("type", 6);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getPetrolNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }

        if (dto.getCoalNoList() != null) {
            paramMap.put("type", 8);

            govMapper.delConsNo(paramMap);

            for (String accNumber : dto.getCoalNoList()) {
                paramMap.put("accnumber", accNumber);
                govMapper.insertConsNo(paramMap);
            }
        }
    }


    @Override
    public List<Map<String,Object>> multipleStatistics(Map<String, Object> paramMap) {
        //先查询出来所有的区域企业数据
        List<Map<String, Object>> entTagMapList = govMapper.multipleStatistics(paramMap);
        //查询区域对应的能耗指标数据
        List<Map<String, Object>> entEnergyInfoList = govMapper.getEntAllEnergyInfoByYear(paramMap);
        //分组组装数据
        if (!CollectionUtils.isEmpty(entTagMapList)) {
            entTagMapList.stream().forEach(k -> {
                String localAreaCode = StringUtil.getString(k.get("areaCode"));
                String allTce = "0";
                for (int i = 0; i < entEnergyInfoList.size(); i++) {
                    String areaCode = StringUtil.getString(entEnergyInfoList.get(i).get("areaCode"));
                    String value = StringUtil.removeZero(StringUtil.getString(entEnergyInfoList.get(i).get("value")));
                    String energyType = StringUtil.getString(entEnergyInfoList.get(i).get("energyType"));
                    String tce = StringUtil.removeZero(StringUtil.getString(entEnergyInfoList.get(i).get("tce")));
                    if (localAreaCode.equals(areaCode)) {
                        if (ENUM_DATA_TYPE.YONGDIAN.getCode().equals(energyType)) {
                            if (!"0".equals(value)) {
                                value = BigDecimalUtil.device(value, "10000");
                            }
                            k.put(ENUM_DATA_TYPE.getSpellByCode(energyType), value);
                        } else if (StringUtil.isNotEmpty(ENUM_DATA_TYPE.getSpellByCode(energyType))) {
                            k.put(ENUM_DATA_TYPE.getSpellByCode(energyType), value);
                        }
                        allTce = BigDecimalUtil.add(allTce, tce);
                    }
                    if (null == k.get(ENUM_DATA_TYPE.getSpellByCode(energyType))) {
                        String spell = ENUM_DATA_TYPE.getSpellByCode(energyType);
                        if (StringUtil.isNotEmpty(spell)) {
                            k.put(spell, "0");
                        }
                    }
                }
                // 如果没有值 则填充空
                k.put("allEnergy", allTce);
            });
            //汇总所有数据
            Map<String, Object> allMap = Maps.newHashMap();
            String yongDian = "0";
            String allEnergy = "0";
            String reLi = "0";
            String qiYou = "0";
            String meiTan = "0";
            String yongShui = "0";
            String tianRanQi = "0";
            String chaiYou = "0";
            String shengWuZhi = "0";

            for (int i = 0; i < entTagMapList.size(); i++) {
                yongDian = BigDecimalUtil.add(StringUtil.removeZero(StringUtil.getStringZero(entTagMapList.get(i).get("yongDian"))), yongDian);
                allEnergy = BigDecimalUtil.add(StringUtil.removeZero(StringUtil.getStringZero(entTagMapList.get(i).get("allEnergy"))), allEnergy);
                reLi = BigDecimalUtil.add(StringUtil.removeZero(StringUtil.getStringZero(entTagMapList.get(i).get("reLi"))), reLi);
                qiYou = BigDecimalUtil.add(StringUtil.removeZero(StringUtil.getStringZero(entTagMapList.get(i).get("qiYou"))), qiYou);
                meiTan = BigDecimalUtil.add(StringUtil.removeZero(StringUtil.getStringZero(entTagMapList.get(i).get("meiTan"))), meiTan);
                yongShui = BigDecimalUtil.add(StringUtil.removeZero(StringUtil.getStringZero(entTagMapList.get(i).get("yongShui"))), yongShui);
                tianRanQi = BigDecimalUtil.add(StringUtil.removeZero(StringUtil.getStringZero(entTagMapList.get(i).get("tianRanQi"))), tianRanQi);
                chaiYou = BigDecimalUtil.add(StringUtil.removeZero(StringUtil.getStringZero(entTagMapList.get(i).get("chaiYou"))), chaiYou);
                shengWuZhi = BigDecimalUtil.add(StringUtil.removeZero(StringUtil.getStringZero(entTagMapList.get(i).get("shengWuZhi"))), shengWuZhi);
            }
            allMap.put("yongDian", yongDian);
            allMap.put("shengWuZhi", shengWuZhi);
            allMap.put("allEnergy", allEnergy);
            allMap.put("reLi", reLi);
            allMap.put("qiYou", qiYou);
            allMap.put("meiTan", meiTan);
            allMap.put("yongShui", yongShui);
            allMap.put("tianRanQi", tianRanQi);
            allMap.put("chaiYou", chaiYou);
            allMap.put("areaName", "全县");
            allMap.put("areaCode", "");
            allMap.put("isHexin", 0);
            allMap.put("isGaoxin", 0);
            allMap.put("isGaoduan", 0);
            allMap.put("isRegulation", 0);
            entTagMapList.add(allMap);
        }


        return entTagMapList;
    }

}
