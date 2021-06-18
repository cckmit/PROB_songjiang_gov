package com.enesource.jump.web.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/* *
 * @author lio
 * @Description:
 * @date :created in 5:05 下午 2021/1/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDayEleImport extends ExcelBaseDTO {


    /**
     * 公司名称
     */
    @Excel(name = "*企业名称")
    @NotBlank(message = "[企业名称]不能为空")
    private String entName;

    @Excel(name = "*户号")
    @NotBlank(message = "[户号]不能为空")
    private String accNumber="";

    @Excel(name = "1日尖")
    private String _Data011="";
    @Excel(name = "1日峰")
    private String _Data012="";
    @Excel(name = "1日谷")
    private String _Data013="";

    @Excel(name = "2日尖")
    private String _Data021="";
    @Excel(name = "2日峰")
    private String _Data022="";
    @Excel(name = "2日谷")
    private String _Data023="";

    @Excel(name = "3日尖")
    private String _Data031="";
    @Excel(name = "3日峰")
    private String _Data032="";
    @Excel(name = "3日谷")
    private String _Data033="";

    @Excel(name = "4日尖")
    private String _Data041="";
    @Excel(name = "4日峰")
    private String _Data042="";
    @Excel(name = "4日谷")
    private String _Data043="";

    @Excel(name = "5日尖")
    private String _Data051="";
    @Excel(name = "5日峰")
    private String _Data052="";
    @Excel(name = "5日谷")
    private String _Data053="";

    @Excel(name = "6日尖")
    private String _Data061="";
    @Excel(name = "6日峰")
    private String _Data062="";
    @Excel(name = "6日谷")
    private String _Data063="";

    @Excel(name = "7日尖")
    private String _Data071="";
    @Excel(name = "7日峰")
    private String _Data072="";
    @Excel(name = "7日谷")
    private String _Data073="";

    @Excel(name = "8日尖")
    private String _Data081="";
    @Excel(name = "8日峰")
    private String _Data082="";
    @Excel(name = "8日谷")
    private String _Data083="";

    @Excel(name = "9日尖")
    private String _Data091="";
    @Excel(name = "9日峰")
    private String _Data092="";
    @Excel(name = "9日谷")
    private String _Data093="";

    @Excel(name = "10日尖")
    private String _Data101="";
    @Excel(name = "10日峰")
    private String _Data102="";
    @Excel(name = "10日谷")
    private String _Data103="";

    @Excel(name = "11日尖")
    private String _Data111="";
    @Excel(name = "11日峰")
    private String _Data112="";
    @Excel(name = "11日谷")
    private String _Data113="";

    @Excel(name = "12日尖")
    private String _Data121="";
    @Excel(name = "12日峰")
    private String _Data122="";
    @Excel(name = "12日谷")
    private String _Data123="";

    @Excel(name = "13日尖")
    private String _Data131="";
    @Excel(name = "13日峰")
    private String _Data132="";
    @Excel(name = "13日谷")
    private String _Data133="";

    @Excel(name = "14日尖")
    private String _Data141="";
    @Excel(name = "14日峰")
    private String _Data142="";
    @Excel(name = "14日谷")
    private String _Data143="";

    @Excel(name = "15日尖")
    private String _Data151="";
    @Excel(name = "15日峰")
    private String _Data152="";
    @Excel(name = "15日谷")
    private String _Data153="";

    @Excel(name = "16日尖")
    private String _Data161="";
    @Excel(name = "16日峰")
    private String _Data162="";
    @Excel(name = "16日谷")
    private String _Data163="";


    @Excel(name = "17日尖")
    private String _Data171="";
    @Excel(name = "17日峰")
    private String _Data172="";
    @Excel(name = "17日谷")
    private String _Data173="";


    @Excel(name = "18日尖")
    private String _Data181="";
    @Excel(name = "18日峰")
    private String _Data182="";
    @Excel(name = "18日谷")
    private String _Data183="";


    @Excel(name = "19日尖")
    private String _Data191="";
    @Excel(name = "19日峰")
    private String _Data192="";
    @Excel(name = "19日谷")
    private String _Data193="";

    @Excel(name = "20日尖")
    private String _Data201="";
    @Excel(name = "20日峰")
    private String _Data202="";
    @Excel(name = "20日谷")
    private String _Data203="";

    @Excel(name = "21日尖")
    private String _Data211="";
    @Excel(name = "21日峰")
    private String _Data212="";
    @Excel(name = "21日谷")
    private String _Data213="";

    @Excel(name = "22日尖")
    private String _Data221="";
    @Excel(name = "22日峰")
    private String _Data222="";
    @Excel(name = "22日谷")
    private String _Data223="";


    @Excel(name = "23日尖")
    private String _Data231="";
    @Excel(name = "23日峰")
    private String _Data232="";
    @Excel(name = "23日谷")
    private String _Data233="";


    @Excel(name = "24日尖")
    private String _Data241="";
    @Excel(name = "24日峰")
    private String _Data242="";
    @Excel(name = "24日谷")
    private String _Data243="";

    @Excel(name = "25日尖")
    private String _Data251="";
    @Excel(name = "25日峰")
    private String _Data252="";
    @Excel(name = "25日谷")
    private String _Data253="";

    @Excel(name = "26日尖")
    private String _Data261="";
    @Excel(name = "26日峰")
    private String _Data262="";
    @Excel(name = "26日谷")
    private String _Data263="";

    @Excel(name = "27日尖")
    private String _Data271="";
    @Excel(name = "27日峰")
    private String _Data272="";
    @Excel(name = "27日谷")
    private String _Data273="";

    @Excel(name = "28日尖")
    private String _Data281="";
    @Excel(name = "28日峰")
    private String _Data282="";
    @Excel(name = "28日谷")
    private String _Data283="";

    @Excel(name = "29日尖")
    private String _Data291="";
    @Excel(name = "29日峰")
    private String _Data292="";
    @Excel(name = "29日谷")
    private String _Data293="";

    @Excel(name = "30日尖")
    private String _Data301="";
    @Excel(name = "30日峰")
    private String _Data302="";
    @Excel(name = "30日谷")
    private String _Data303="";

    @Excel(name = "31日尖")
    private String _Data311="";
    @Excel(name = "31日峰")
    private String _Data312="";
    @Excel(name = "31日谷")
    private String _Data313="";

}
