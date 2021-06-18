package com.enesource.jump.web.interceptor;

import org.apache.commons.lang3.StringUtils;

/* *
 * @author liuyy
 * @Description:
 * @date :created in 10:57 下午 2020/2/18
 */
public class ComponentException extends RuntimeException {


    private ErrCode errCode;

    private String message;


    public ComponentException(ErrCode errCode, String message) {
        this.errCode = errCode;
        if (StringUtils.isBlank(message)) {
            this.message = errCode.message();
        } else {
            this.message = message;
        }
    }

    public String getErrCode() {
        return errCode.code();
    }

    public void setErrCode(ErrCode errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return message;
    }

    public void setErrMessage(String message) {
        this.message = message;
    }


    /**
     * 异常定义
     */
    public interface ErrCode {

        /**
         * 异常码
         *
         * @return code
         */
        String code();

        /**
         * 异常码说明
         *
         * @return message
         */
        String message();

        /**
         * 请求超时
         */
        ErrCode TIME_ERR = new ErrCode() {
            @Override
            public String code() {
                return "-3";
            }

            @Override
            public String message() {
                return "请求超时";
            }
        };
        /**
         * 校验错误
         */
        ErrCode SING_ERR = new ErrCode() {
            @Override
            public String code() {
                return "-2";
            }

            @Override
            public String message() {
                return "校验错误";
            }
        };
        /**
         * 令牌错误
         */
        ErrCode TOKEN_ERR = new ErrCode() {
            @Override
            public String code() {
                return "-1";
            }

            @Override
            public String message() {
                return "令牌错误";
            }
        };

        /**
         * 参数非法
         */
        ErrCode PARAM_ERR = new ErrCode() {
            @Override
            public String code() {
                return "0";
            }

            @Override
            public String message() {
                return "参数非法";
            }
        };
        /**
         * 登陆失败
         */
        ErrCode LOGIN_ERR = new ErrCode() {
            @Override
            public String code() {
                return "40002";
            }

            @Override
            public String message() {
                return "请重新登陆";
            }
        };
        /**
         * 未注册
         */
        ErrCode NOT_REGISTER = new ErrCode() {
            @Override
            public String code() {
                return "40004";
            }

            @Override
            public String message() {
                return "未注册";
            }
        };
        /**
         * 需要设置密码
         */
        ErrCode WANT_SET_PWD = new ErrCode() {
            @Override
            public String code() {
                return "40005";
            }

            @Override
            public String message() {
                return "需要设置密码";
            }
        };
        /**
         * 系统繁忙
         */
        ErrCode SYSTEM_ERR = new ErrCode() {
            @Override
            public String code() {
                return "0";
            }

            @Override
            public String message() {
                return "系统繁忙,请稍后重试";
            }
        };
    }

}
