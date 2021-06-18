package com.enesource.jump.web.utils;


import java.math.BigDecimal;

/* *
 * @author liuyy
 * @Description:
 * @date :created in 9:20 下午 2020/2/23
 */
public class BigDecimalUtil {

    /**
     * a-b
     *
     * @param a
     * @param b
     * @return
     */
    public static String subtract(String a, String b) {
        String num = "0";
        if (StringUtil.isNotEmpty(a) && StringUtil.isNotEmpty(b)) {
            BigDecimal a1 = new BigDecimal(a);
            BigDecimal b1 = new BigDecimal(b);
            num = StringUtil.getString(a1.subtract(b1));
        }
        return num;
    }

    /**
     * @Author:liuyy
     * @Description: a*b
     * @Date :10:12 下午 2020/2/23
     */
    public static String multiply(String a, String b) {
        String num = "";
        if (StringUtil.isNotEmpty(a) && StringUtil.isNotEmpty(b)) {
            BigDecimal a1 = new BigDecimal(a);
            BigDecimal b1 = new BigDecimal(b);
            num = StringUtil.getString(a1.multiply(b1));
        }
        return num;
    }

    /**
     * @Author:liuyy
     * @Description: a+b
     * @Date :10:12 下午 2020/2/23
     */
    public static String add(String a, String b) {
        String num = "0";
        if (StringUtil.isNotEmpty(a) && StringUtil.isNotEmpty(b)) {
            BigDecimal a1 = new BigDecimal(a);
            BigDecimal b1 = new BigDecimal(b);
            num = StringUtil.getString(a1.add(b1));
        }
        return num;
    }

    /**
     * @Author:lio
     * @Description: a/b
     * @Date :6:52 下午 2021/1/24
     */
    public static String device(String a, String b){
        String num = "";
        if (StringUtil.isNotEmpty(a) && StringUtil.isNotEmpty(b)) {
            BigDecimal a1 = new BigDecimal(a);
            BigDecimal b1 = new BigDecimal(b);
            num = StringUtil.getString(a1.divide(b1,2,BigDecimal.ROUND_UP));
        }
        return num;

    }



}
