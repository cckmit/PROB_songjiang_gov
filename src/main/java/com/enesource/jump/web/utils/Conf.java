package com.enesource.jump.web.utils;

import java.text.DecimalFormat;

public class Conf {
    public static final String SUCCESS = "1";
    public static final String ERROR = "0";
    public static final String LOGINERROR = "2";
    public static final String SUCCESSMSG = "SUCCESS";
    public static final String OVERTIMECODE = "3";
    public static final String PERMISSIONERROR = "3";
    
//    public static final String EEOP_URL = "https://ag-cn5.envisioniot.com";
//    public static final String APP_KEY = "b5c25495-0ee3-4f4b-8436-4c92a312071c";
//    public static final String APP_SECRET = "f36f6f1f-4f42-4de1-867e-ea142f77d6ca";
    
    public static DecimalFormat df = new DecimalFormat("######");
    public static DecimalFormat df1 = new DecimalFormat("#,###");
    public static DecimalFormat df2 = new DecimalFormat("####0.00");
    public static DecimalFormat df3 = new DecimalFormat("####0.0");
    public static DecimalFormat df4 = new DecimalFormat("#,###0.00");
    public static DecimalFormat df5 = new DecimalFormat("####0.000000");
    
    
    
    /**
     **********************************redis key************************************************
     */
    public static final String LOGINTOKEN = "login_token:"; 
    

}
