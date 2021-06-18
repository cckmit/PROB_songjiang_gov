package com.enesource.jump.web.utils;


import com.enesource.jump.web.interceptor.ComponentException;
import org.apache.commons.lang3.StringUtils;

/* *
 * @author liuyy
 * @Description: 断言工具
 * @date :created in 10:55 下午 2020/2/18
 */
public final class AssertUtil {


    private AssertUtil() {
    }

    /**
     * @Author:liuyy
     * @Description: 字符串是否为空
     * @Date :9:37 上午 2020/5/19
     */
    public static void NotBlank(String text, String message) {
        if (!StringUtils.isNotBlank(text)) {
            ThrowException(ComponentException.ErrCode.PARAM_ERR, message);
        }
    }

    /**
     * @Author:liuyy
     * @Description:对象是否为空
     * @Date :9:37 上午 2020/5/19
     */
    public static void NotBlank(Object object, String message) {
        if (null == object) {
            ThrowException(ComponentException.ErrCode.PARAM_ERR, message);
        }
    }

    /**
     * @Author:liuyy
     * @Description:多个对象非空判断
     * @Date :9:37 上午 2020/5/19
     */
    public static void NotBlankLis(String message, Object... object) {
        if (object != null && object.length > 0) {
            for (int i = 0; i < object.length; i++) {
                if (null == object[i]) {
                    ThrowException(ComponentException.ErrCode.PARAM_ERR, message);
                }
            }
        }
    }


    public static void ThrowSystemErr(String message) {
        ThrowException(ComponentException.ErrCode.PARAM_ERR, message);
    }

    private static void ThrowException(ComponentException.ErrCode errCode, String message) {
        throw new ComponentException(errCode, message);
    }

    /**
     * @Author:liuyy
     * @Description:如果参数不为空则校验
     * @Date :10:39 上午 2019/12/13
     */
    public static void CheckVerifyIfNotEmpty(String str, boolean checkBoolean, String message) {
        if (StringUtils.isNotBlank(str) && !checkBoolean) {
            ThrowException(ComponentException.ErrCode.PARAM_ERR, message);
        }
    }

    public static void CheckVerify(boolean checkBoolean, String message) {
        if (!checkBoolean) {
            ThrowException(ComponentException.ErrCode.PARAM_ERR, message);
        }
    }


}
