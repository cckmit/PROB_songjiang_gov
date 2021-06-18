package com.enesource.jump.web.common;

import java.beans.BeanInfo;


import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;


public class CommonUtil {

	public static DecimalFormat df = new DecimalFormat("######");
	public static DecimalFormat df1 = new DecimalFormat("#,###");
	public static DecimalFormat df2 = new DecimalFormat("####0.00");
	public static DecimalFormat df3 = new DecimalFormat("####0.0");
	public static DecimalFormat df4 = new DecimalFormat("#,###0.00");

	public static Map<?, ?> objectToMap(Object obj) {
		if (obj == null)
			return null;

		return new BeanMap(obj);
	}

	public static Map<String, Object> objectTOMap(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					if (value != null) {
						map.put(key, value);
					}

				}

			}
		} catch (Exception e) {
		}

		return map;

	}

	public static String transferLongToDate(String dateFormat, Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}

	public static String dateToStamp(String s) throws ParseException {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}
	
	
	public static String getPoint(String s){
		int i = Integer.valueOf(s);
		
		switch(i){
		case 1:
			return "Meter.EpPositive";
		case 16:
			return "Meter.EpNegative";
		case 31:
			return "Meter.Ptotal";
		case 35:
			return "Meter.Qtotal";
		case 39:
			return "Meter.Ua";
		case 40:
			return "Meter.Ub";
		case 41:
			return "Meter.Uc";
		case 42:
			return "Meter.Ia";
		case 43:
			return "Meter.Ib";
		case 44:
			return "Meter.Ic";
		case 49:
			return "Meter.PFaver";
		default:
			return "";
		}
		
	}
	
}
