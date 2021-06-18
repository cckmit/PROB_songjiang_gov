package com.enesource.jump.web.utils;

import com.enesource.jump.web.interceptor.ComponentException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by liuyy on 2019/6/19
 */
public class StringUtil {
    //精确到天
    private static final SimpleDateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 满足三种或者三种以上 数字  大写字母 小写字母  特殊符合(不是字母，数字，下划线，汉字的字符)的8位以上组合
     * 排除 大写字母小写字母 排除 大写字母特殊字符 排除 大写字母数字 排除 小写字母字符 排除 小写字母数字 排除 数字字符
     */
    private static final String PW_PATTERN = "^(?![A-Za-z]+$)(?![A-Z\\W]+$)(?![A-Z0-9]+$)(?![a-z\\W]+$)(?![a-z0-9]+$)(?![0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";

    private static final String NUM_PATTERN = "^[0-9]{10,}$";

    /**
     * 检查字符串是否不是<code>null</code>和空字符串<code>""</code>。
     *
     * <pre>
     * StringUtil.isEmpty(null)      = false
     * StringUtil.isEmpty("")        = false
     * StringUtil.isEmpty(" ")       = true
     * StringUtil.isEmpty("bob")     = true
     * StringUtil.isEmpty("  bob  ") = true
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果不为空, 则返回<code>true</code>
     */
    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }

    public static boolean isNotEmpty(String... str) {
        if (str != null && str.length > 0) {
            for (int i = 0; i < str.length; i++) {
                String message = str[i];
                if (null == message || message.length() == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static String addMessage(String message, String addMessage) {
        if (StringUtil.isNotEmpty(message)) {
            message = message + "," + addMessage;
        } else {
        	message = addMessage;
		}

        return message;
    }


    /**
     * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
     *
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }

    /**
     * 将object 转为 string value并去空格 若object为null返回空字串
     *
     * @param value
     * @return
     */
    public static String getString(Object value) {
        if (value == null || "null".equals(value)) {
            return "";
        }
        return String.valueOf(value).trim();
    }

    /**
     * 将object 转为 string value并去空格 若object为null返回空字串
     *
     * @param value
     * @return
     */
    public static String getStringZero(Object value) {
        if (value == null || "null".equals(value) || "".equals(value)) {
            return "0";
        }
        return String.valueOf(value).trim();
    }


    /**
     * 验证手机号码格式 注：手机号码格式更新太快，建议只校验尾数，避免重复更新校验规则
     *
     * @author liuyy
     * @date 2019年6月26日 下午7:31:24
     */
    public static boolean checkMobile(String phone) {
        String regex = "^[1][0-9]{10}$";
        boolean isPhoneCheck = false;
        if (StringUtil.isNotEmpty(phone)) {
            isPhoneCheck = Pattern.compile(regex).matcher(phone).matches();
        }
        return isPhoneCheck;
    }


    /**
     * 六位随机字符，包含26个字母大小写和数字的字符数组
     *
     * @return
     */
    public static String getNonce() {
        int i = 1234567890;
        String s = "qwertyuiopasdfghjklzxcvbnm";
        String S = s.toUpperCase();
        String word = s + S + i;
        char[] c = word.toCharArray();

        Random rd = new Random();
        String code = "";
        for (int k = 0; k < 6; k++) {
            int index = rd.nextInt(c.length);// 随机获取数组长度作为索引
            code += c[index];// 循环添加到字符串后面
        }
        return code;
    }


    /**
     * 获取上个月第一天的值
     *
     * @return
     */
    public static String getMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date m = c.getTime();
        String time = FORMAT_DAY.format(m);
        return time;
    }

    public static BigDecimal getmulValue(String a, String b) {
        BigDecimal res = new BigDecimal("0");
        if (StringUtil.isNotEmpty(a) && StringUtil.isNotEmpty(b)) {
            BigDecimal b1 = new BigDecimal(a);
            BigDecimal b2 = new BigDecimal(b);
            res = b1.multiply(b2);
        }
        return res;
    }


    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 验证密码强度
     *
     * @return
     */
    public static boolean checkPwd(String pwd) {
        Boolean checkPwd = false;
        if (StringUtil.isNotEmpty(pwd)) {
            checkPwd = pwd.matches(PW_PATTERN);
        }
        return checkPwd;
    }

    public static String removeZero(String s){
        if (s.isEmpty()) {
            return null;
        }
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }






}
