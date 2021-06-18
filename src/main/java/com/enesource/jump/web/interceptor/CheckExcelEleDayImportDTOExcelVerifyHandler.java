package com.enesource.jump.web.interceptor;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.enesource.jump.web.dao.IGovMapper;
import com.enesource.jump.web.dto.ExcelDayEleImport;
import com.enesource.jump.web.enums.ENUM_DATA_TYPE;
import com.enesource.jump.web.utils.StringUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Component
public class CheckExcelEleDayImportDTOExcelVerifyHandler implements IExcelVerifyHandler<ExcelDayEleImport> {

    @Autowired
    IGovMapper govMapper;

    /**
     * 企业受电容量<户号,受电容量>
     */
    private Map<String, BigDecimal> entRcvElecCapsMap;

    public Map<String, BigDecimal> getEntRcvElecCapsMap() {
        return entRcvElecCapsMap;
    }

    /**
     * 加载企业受电容量
     */
    @PostConstruct
    public void loadEntRcvElecCapsMap() {
        // 查询用电类型的所有企业关联关系
        List<Map<String, Object>> allEntAccnumberRels = govMapper.findAllEntAccnumberRelsByType(Integer.valueOf(ENUM_DATA_TYPE.YONGDIAN.getCode()));
        // 企业受电容量<企业户号,受电容量>
        entRcvElecCapsMap = Maps.newHashMap();
        // 初始化企业受电容量表
        for (Map<String, Object> item : allEntAccnumberRels) {
            entRcvElecCapsMap.put(String.valueOf(item.get("accNumber")), item.get("rcvElecCap") == null ? null : new BigDecimal(String.valueOf(item.get("rcvElecCap"))));
        }
    }


    public static void main(String[] args) throws Exception {
    }

    public StringBuilder verifyDataList(List<ExcelDayEleImport> readExcelList, ImportParams params) {
        StringBuilder msg = new StringBuilder();
        // 前置行数
        int preRowNum = params.getTitleRows() + params.getHeadRows();
        // 保存错误信息
        List<String> errMsgs = Lists.newArrayList();
        for (int i = 0; i < readExcelList.size(); i++) {
            // 当前行数
            int currentRowNum = preRowNum + i + 1;
            // 清空错误信息
            errMsgs.clear();
            ExcelDayEleImport excelDayEleImport = readExcelList.get(i);
            if (StringUtils.isBlank(excelDayEleImport.getEntName())) {
                errMsgs.add("[户号]不能为空");
            }
            if (StringUtils.isBlank(excelDayEleImport.getAccNumber())) {
                errMsgs.add("[企业名称]不能为空");
            }
            ExcelVerifyHandlerResult excelVerifyHandlerResult = verifyHandler(excelDayEleImport);
            if (!excelVerifyHandlerResult.isSuccess()) {
                errMsgs.add(excelVerifyHandlerResult.getMsg());
            }
            if (!errMsgs.isEmpty()) {
                msg.append(String.format("第%s行的错误是:%s %s", currentRowNum, Joiner.on(StrUtil.COMMA).join(errMsgs), StrUtil.CRLF));
            }
        }
        return msg;
    }

    @Override
    public ExcelVerifyHandlerResult verifyHandler(ExcelDayEleImport obj) {

        if (!StringUtil.isEmpty(obj.get_Data011()) && !Validator.isNumber(obj.get_Data011())) {
            return new ExcelVerifyHandlerResult(false, "1日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data012()) && !Validator.isNumber(obj.get_Data012())) {
            return new ExcelVerifyHandlerResult(false, "1日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data013()) && !Validator.isNumber(obj.get_Data013())) {
            return new ExcelVerifyHandlerResult(false, "1日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data021()) && !Validator.isNumber(obj.get_Data021())) {
            return new ExcelVerifyHandlerResult(false, "2日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data022()) && !Validator.isNumber(obj.get_Data021())) {
            return new ExcelVerifyHandlerResult(false, "2日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data022()) && !Validator.isNumber(obj.get_Data021())) {
            return new ExcelVerifyHandlerResult(false, "2日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data031()) && !Validator.isNumber(obj.get_Data031())) {
            return new ExcelVerifyHandlerResult(false, "3日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data032()) && !Validator.isNumber(obj.get_Data032())) {
            return new ExcelVerifyHandlerResult(false, "3日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data033()) && !Validator.isNumber(obj.get_Data033())) {
            return new ExcelVerifyHandlerResult(false, "3日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data041()) && !Validator.isNumber(obj.get_Data041())) {
            return new ExcelVerifyHandlerResult(false, "4日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data042()) && !Validator.isNumber(obj.get_Data042())) {
            return new ExcelVerifyHandlerResult(false, "4日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data043()) && !Validator.isNumber(obj.get_Data043())) {
            return new ExcelVerifyHandlerResult(false, "4日数据类型错误！");
        }


        if (!StringUtil.isEmpty(obj.get_Data051()) && !Validator.isNumber(obj.get_Data051())) {
            return new ExcelVerifyHandlerResult(false, "5日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data052()) && !Validator.isNumber(obj.get_Data052())) {
            return new ExcelVerifyHandlerResult(false, "5日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data052()) && !Validator.isNumber(obj.get_Data052())) {
            return new ExcelVerifyHandlerResult(false, "5日数据类型错误！");
        }


        if (!StringUtil.isEmpty(obj.get_Data061()) && !Validator.isNumber(obj.get_Data061())) {
            return new ExcelVerifyHandlerResult(false, "6日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data062()) && !Validator.isNumber(obj.get_Data062())) {
            return new ExcelVerifyHandlerResult(false, "6日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data063()) && !Validator.isNumber(obj.get_Data063())) {
            return new ExcelVerifyHandlerResult(false, "6日数据类型错误！");
        }


        if (!StringUtil.isEmpty(obj.get_Data071()) && !Validator.isNumber(obj.get_Data071())) {
            return new ExcelVerifyHandlerResult(false, "7日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data072()) && !Validator.isNumber(obj.get_Data072())) {
            return new ExcelVerifyHandlerResult(false, "7日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data073()) && !Validator.isNumber(obj.get_Data073())) {
            return new ExcelVerifyHandlerResult(false, "7日数据类型错误！");
        }


        if (!StringUtil.isEmpty(obj.get_Data081()) && !Validator.isNumber(obj.get_Data081())) {
            return new ExcelVerifyHandlerResult(false, "8日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data082()) && !Validator.isNumber(obj.get_Data082())) {
            return new ExcelVerifyHandlerResult(false, "8日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data083()) && !Validator.isNumber(obj.get_Data083())) {
            return new ExcelVerifyHandlerResult(false, "8日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data091()) && !Validator.isNumber(obj.get_Data091())) {
            return new ExcelVerifyHandlerResult(false, "9日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data092()) && !Validator.isNumber(obj.get_Data092())) {
            return new ExcelVerifyHandlerResult(false, "9日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data093()) && !Validator.isNumber(obj.get_Data093())) {
            return new ExcelVerifyHandlerResult(false, "9日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data101()) && !Validator.isNumber(obj.get_Data101())) {
            return new ExcelVerifyHandlerResult(false, "10日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data102()) && !Validator.isNumber(obj.get_Data102())) {
            return new ExcelVerifyHandlerResult(false, "10日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data103()) && !Validator.isNumber(obj.get_Data103())) {
            return new ExcelVerifyHandlerResult(false, "10日数据类型错误！");
        }


        if (!StringUtil.isEmpty(obj.get_Data111()) && !Validator.isNumber(obj.get_Data111())) {
            return new ExcelVerifyHandlerResult(false, "11日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data112()) && !Validator.isNumber(obj.get_Data112())) {
            return new ExcelVerifyHandlerResult(false, "11日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data113()) && !Validator.isNumber(obj.get_Data113())) {
            return new ExcelVerifyHandlerResult(false, "11日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data121()) && !Validator.isNumber(obj.get_Data121())) {
            return new ExcelVerifyHandlerResult(false, "12日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data122()) && !Validator.isNumber(obj.get_Data122())) {
            return new ExcelVerifyHandlerResult(false, "12日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data123()) && !Validator.isNumber(obj.get_Data123())) {
            return new ExcelVerifyHandlerResult(false, "12日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data131()) && !Validator.isNumber(obj.get_Data131())) {
            return new ExcelVerifyHandlerResult(false, "13日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data132()) && !Validator.isNumber(obj.get_Data132())) {
            return new ExcelVerifyHandlerResult(false, "13日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data133()) && !Validator.isNumber(obj.get_Data133())) {
            return new ExcelVerifyHandlerResult(false, "13日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data141()) && !Validator.isNumber(obj.get_Data141())) {
            return new ExcelVerifyHandlerResult(false, "14日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data142()) && !Validator.isNumber(obj.get_Data142())) {
            return new ExcelVerifyHandlerResult(false, "14日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data143()) && !Validator.isNumber(obj.get_Data143())) {
            return new ExcelVerifyHandlerResult(false, "14日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data151()) && !Validator.isNumber(obj.get_Data151())) {
            return new ExcelVerifyHandlerResult(false, "15日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data152()) && !Validator.isNumber(obj.get_Data152())) {
            return new ExcelVerifyHandlerResult(false, "15日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data153()) && !Validator.isNumber(obj.get_Data153())) {
            return new ExcelVerifyHandlerResult(false, "15日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data161()) && !Validator.isNumber(obj.get_Data161())) {
            return new ExcelVerifyHandlerResult(false, "16日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data162()) && !Validator.isNumber(obj.get_Data162())) {
            return new ExcelVerifyHandlerResult(false, "16日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data163()) && !Validator.isNumber(obj.get_Data163())) {
            return new ExcelVerifyHandlerResult(false, "16日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data171()) && !Validator.isNumber(obj.get_Data171())) {
            return new ExcelVerifyHandlerResult(false, "17日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data172()) && !Validator.isNumber(obj.get_Data172())) {
            return new ExcelVerifyHandlerResult(false, "17日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data173()) && !Validator.isNumber(obj.get_Data173())) {
            return new ExcelVerifyHandlerResult(false, "17日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data181()) && !Validator.isNumber(obj.get_Data181())) {
            return new ExcelVerifyHandlerResult(false, "18日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data182()) && !Validator.isNumber(obj.get_Data182())) {
            return new ExcelVerifyHandlerResult(false, "18日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data183()) && !Validator.isNumber(obj.get_Data183())) {
            return new ExcelVerifyHandlerResult(false, "18日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data191()) && !Validator.isNumber(obj.get_Data191())) {
            return new ExcelVerifyHandlerResult(false, "19日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data192()) && !Validator.isNumber(obj.get_Data192())) {
            return new ExcelVerifyHandlerResult(false, "19日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data193()) && !Validator.isNumber(obj.get_Data193())) {
            return new ExcelVerifyHandlerResult(false, "19日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data201()) && !Validator.isNumber(obj.get_Data201())) {
            return new ExcelVerifyHandlerResult(false, "20日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data202()) && !Validator.isNumber(obj.get_Data202())) {
            return new ExcelVerifyHandlerResult(false, "20日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data203()) && !Validator.isNumber(obj.get_Data203())) {
            return new ExcelVerifyHandlerResult(false, "20日数据类型错误！");
        }


        if (!StringUtil.isEmpty(obj.get_Data211()) && !Validator.isNumber(obj.get_Data211())) {
            return new ExcelVerifyHandlerResult(false, "21日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data212()) && !Validator.isNumber(obj.get_Data212())) {
            return new ExcelVerifyHandlerResult(false, "21日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data213()) && !Validator.isNumber(obj.get_Data213())) {
            return new ExcelVerifyHandlerResult(false, "21日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data221()) && !Validator.isNumber(obj.get_Data221())) {
            return new ExcelVerifyHandlerResult(false, "22日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data222()) && !Validator.isNumber(obj.get_Data222())) {
            return new ExcelVerifyHandlerResult(false, "22日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data223()) && !Validator.isNumber(obj.get_Data223())) {
            return new ExcelVerifyHandlerResult(false, "22日数据类型错误！");
        }


        if (!StringUtil.isEmpty(obj.get_Data231()) && !Validator.isNumber(obj.get_Data231())) {
            return new ExcelVerifyHandlerResult(false, "23日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data232()) && !Validator.isNumber(obj.get_Data232())) {
            return new ExcelVerifyHandlerResult(false, "23日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data233()) && !Validator.isNumber(obj.get_Data233())) {
            return new ExcelVerifyHandlerResult(false, "23日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data241()) && !Validator.isNumber(obj.get_Data241())) {
            return new ExcelVerifyHandlerResult(false, "24日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data242()) && !Validator.isNumber(obj.get_Data242())) {
            return new ExcelVerifyHandlerResult(false, "24日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data243()) && !Validator.isNumber(obj.get_Data243())) {
            return new ExcelVerifyHandlerResult(false, "24日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data251()) && !Validator.isNumber(obj.get_Data251())) {
            return new ExcelVerifyHandlerResult(false, "25日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data252()) && !Validator.isNumber(obj.get_Data252())) {
            return new ExcelVerifyHandlerResult(false, "25日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data253()) && !Validator.isNumber(obj.get_Data253())) {
            return new ExcelVerifyHandlerResult(false, "25日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data261()) && !Validator.isNumber(obj.get_Data261())) {
            return new ExcelVerifyHandlerResult(false, "26日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data262()) && !Validator.isNumber(obj.get_Data262())) {
            return new ExcelVerifyHandlerResult(false, "26日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data263()) && !Validator.isNumber(obj.get_Data263())) {
            return new ExcelVerifyHandlerResult(false, "26日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data271()) && !Validator.isNumber(obj.get_Data271())) {
            return new ExcelVerifyHandlerResult(false, "27日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data272()) && !Validator.isNumber(obj.get_Data272())) {
            return new ExcelVerifyHandlerResult(false, "27日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data273()) && !Validator.isNumber(obj.get_Data273())) {
            return new ExcelVerifyHandlerResult(false, "27日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data281()) && !Validator.isNumber(obj.get_Data281())) {
            return new ExcelVerifyHandlerResult(false, "28日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data282()) && !Validator.isNumber(obj.get_Data282())) {
            return new ExcelVerifyHandlerResult(false, "28日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data283()) && !Validator.isNumber(obj.get_Data283())) {
            return new ExcelVerifyHandlerResult(false, "28日数据类型错误！");
        }


        if (!StringUtil.isEmpty(obj.get_Data291()) && !Validator.isNumber(obj.get_Data291())) {
            return new ExcelVerifyHandlerResult(false, "29日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data292()) && !Validator.isNumber(obj.get_Data292())) {
            return new ExcelVerifyHandlerResult(false, "29日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data293()) && !Validator.isNumber(obj.get_Data293())) {
            return new ExcelVerifyHandlerResult(false, "29日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data301()) && !Validator.isNumber(obj.get_Data301())) {
            return new ExcelVerifyHandlerResult(false, "30日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data302()) && !Validator.isNumber(obj.get_Data302())) {
            return new ExcelVerifyHandlerResult(false, "30日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data303()) && !Validator.isNumber(obj.get_Data303())) {
            return new ExcelVerifyHandlerResult(false, "30日数据类型错误！");
        }

        if (!StringUtil.isEmpty(obj.get_Data311()) && !Validator.isNumber(obj.get_Data311())) {
            return new ExcelVerifyHandlerResult(false, "31日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data312()) && !Validator.isNumber(obj.get_Data312())) {
            return new ExcelVerifyHandlerResult(false, "31日数据类型错误！");
        }
        if (!StringUtil.isEmpty(obj.get_Data313()) && !Validator.isNumber(obj.get_Data313())) {
            return new ExcelVerifyHandlerResult(false, "31日数据类型错误！");
        }
        return new ExcelVerifyHandlerResult(true);
    }


}
