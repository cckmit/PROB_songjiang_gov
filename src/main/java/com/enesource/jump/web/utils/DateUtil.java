package com.enesource.jump.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

	// 24 小时制格式 HH 24小时 hh 12小时
	public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String YMD_TIME_PATTERN = "yyyy-MM-dd";

    private static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 当月的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 上周一
	 * 
	 * @param date
	 * @return
	 */
	public static Date geLastWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}

	/**
	 * 本周一
	 * 
	 * @param date
	 * @return
	 */
	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}

	/**
	 * 下周一
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, 7);
		return cal.getTime();
	}

	/**
	 * java 时间格式转换 date 转string
	 * 
	 * @author liuyy
	 * @date 2019年7月5日 上午9:28:12
	 */
	public static String getStringDateByDate(String pattren, Date date) throws ParseException {
		String stringDate = "";
		if (StringUtil.isEmpty(pattren)) {
			pattren = DEFAULT_TIME_PATTERN;
		}
		if (null != date) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattren);
			stringDate = dateFormat.format(date);
		}
		return stringDate;
	}

	/**
	 * java 时间格式转换 string 转date
	 * 
	 * @author liuyy
	 * @date 2019年7月5日 上午9:28:12
	 */
	public static String getStringDateByString(String pattren, String date) throws ParseException {
		String stringDate = "";
		if (StringUtil.isEmpty(pattren)) {
			pattren = DEFAULT_TIME_PATTERN;
		}
		if (StringUtil.isNotEmpty(date)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattren);
			Date dates = dateFormat.parse(date);
			stringDate = dateFormat.format(dates);
		}
		return stringDate;
	}

	
	/**
	 * 日期转换为时间戳
	 * 
	 * @param timers
	 * @return
	 */
	public static long timeToStamp(String timers) {
		Date d = new Date();
		long timeStemp = 0;
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			d = sf.parse(timers);// 日期转换为时间戳
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timeStemp = d.getTime();
		return timeStemp;
	}

	/**
	 * 时间戳转换日期
	 * 
	 * @param stamp
	 * @return
	 */
	public static String stampToTime(String stamp) {
		String sd = "";
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sd = sdf.format(new Date(Long.parseLong(stamp))); // 时间戳转换日期
		return sd;
	}

	/**
	 * 日期新增小时数
	 * 
	 * @author liuyy
	 * @date 2019年7月13日 下午4:50:05
	 */
	public static String getTimeHourAdd(String pattren, Date date, int index) {
		String stringDate = "";
		if (StringUtil.isEmpty(pattren)) {
			pattren = DEFAULT_TIME_PATTERN;
		}
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattren);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(date);
			rightNow.add(Calendar.HOUR_OF_DAY, index);
			Date dt1 = rightNow.getTime();
			stringDate = sdf.format(dt1);
		}
		return stringDate;
	}

	/**
	 * 日期新增月数
	 * 
	 * @author liuyy
	 * @date 2019年7月13日 下午4:50:05
	 */
	public static String getTimeMonAdd(String pattren, Date date, int index) {
		String stringDate = "";
		if (StringUtil.isEmpty(pattren)) {
			pattren = DEFAULT_TIME_PATTERN;
		}
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattren);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(date);
			rightNow.add(Calendar.MONTH, index);
			Date dt1 = rightNow.getTime();
			stringDate = sdf.format(dt1);
		}
		return stringDate;
	}

	/**
	 * 日期新增天数
	 * 
	 * @author liuyy
	 * @date 2019年7月13日 下午4:50:05
	 */
	public static String getTimeDayAdd(String pattren, Date date, int index) {
		String stringDate = "";
		if (StringUtil.isEmpty(pattren)) {
			pattren = DEFAULT_TIME_PATTERN;
		}
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattren);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(date);
			rightNow.add(Calendar.DAY_OF_MONTH, index);
			Date dt1 = rightNow.getTime();
			stringDate = sdf.format(dt1);
		}
		return stringDate;
	}
	
	
	/**
     * 获取当月的第一天
     *
     * @param pattren
     * @return
     */
    public static String getfirstDayByMonth(String pattren,Date date) {
        if (StringUtil.isEmpty(pattren)) {
            pattren = DEFAULT_TIME_PATTERN;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattren);
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = format.format(cale.getTime());
        return firstDay;
    }

    /**
     * 获取当月的最后一天
     *
     * @param pattren
     * @return
     */
    public static String getEndDayByMonth(String pattren, Date date) {
        if (StringUtil.isEmpty(pattren)) {
            pattren = DEFAULT_TIME_PATTERN;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattren);
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String endDay = format.format(cale.getTime());
        return endDay;
    }

    public static Date getDateByStringDate(String pattren , String dateString) {
    	 if (StringUtil.isEmpty(pattren)) {
             pattren = DEFAULT_TIME_PATTERN;
         }
    	 SimpleDateFormat  simpleDateFormat=new SimpleDateFormat(pattren);
    	 Date date = new Date();
    	 try {
			 date=simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return date; 	
    }
    
    
	/**
	 * 生成24点时间刻度
	 * 
	 * @return
	 */
	public static List<String> getTimeScale24() {
		List<String> timeList = new ArrayList<String>();
		String hour = "";
		for (int i = 0; i < 24; i++) {
			hour = zeroizeDate(i);
			timeList.add(hour + ":00:00");
		}
		return timeList;
	}
	
	
	/**
	 * 时间格式补零
	 * 
	 * @param value
	 * @return
	 */
	public static String zeroizeDate(int value) {
		return value < 10 ? "0" + value : value + "";
	}
    
	
    /**
     * 字符串转时间
     *
     * @param rq 格式：yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static final Date parseYmdhms(String rq) throws Exception {
	    if(rq == null){
	        return null;
	    }
	    try {
	        return YMDHMS.parse(rq);
	    } catch (ParseException e) {
	        System.out.println("【字符转换时间异常】："+e.getMessage());
	        throw new Exception( "字符转换时间异常", e);
	    }
    }
    
    /**
     * 判断两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getBetweenDate(Date date1, Date date2, String pattren) {
        long betweendate = 0;
        if (StringUtil.isNotEmpty(pattren)) {
            pattren = YMD_TIME_PATTERN;
        }
        if (date1 != null && date2 != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattren);
            betweendate = (date1.getTime() - date2.getTime()) / (24*60 * 60 * 1000);
        }
        return betweendate;
    }



    /**
     * 判断两个日期之间相差的秒数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getBetweenDateForS(Date date1, Date date2, String pattren) {
        long betweendate = 0;
        if (StringUtil.isNotEmpty(pattren)) {
            pattren = DEFAULT_TIME_PATTERN;
        }
        if (date1 != null && date2 != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattren);
            betweendate = (date1.getTime() - date2.getTime()) / (1000);
        }
        return betweendate;
    }




}
