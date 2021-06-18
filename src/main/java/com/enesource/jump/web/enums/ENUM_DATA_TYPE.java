package com.enesource.jump.web.enums;

/**
 * @Author:lio
 * @Description:导入数据类型
 * @Date :2:38 下午 2021/1/7
 */
public enum ENUM_DATA_TYPE {

	KAIPIAO("12", "开票额","kaiPiao",true,false,""),
	YONGDIAN("0", "用电量","yongDian",false,true,"0.0001229"),
	DIANFEI("13", "电费","dianFei",true,false,""),
	ZUIGAOFUHE("14", "最高负荷","fuHe",true,false,""),
	YONGRE("2", "用热量","reLi",false,true,"0.0341"),
	TIANRANQI("1", "天然气用量","tianRanQi",true,false,"11.136"),
	YONGSHUI("3", "用水量","yongShui",true,false,""),
	MEITAN("8", "煤炭用量","meiTan",true,false,"0.7143"),
	QIYOU("6", "汽油用量","qiYou",true,false,"1.4714"),
	CHAIYOU("4", "柴油用量","chaiYou",true,false,"1.4571"),
	SHENGWUZHI("11", "生物质燃料用量","shengWuZhi",true,false,"1"),

	;

	/**
	 * @Author:lio
	 * @Description: 判断数据类型年导入
	 * @Date :3:03 下午 2021/1/7
	 */
	public static boolean getDateYearFlagByCode(String code) {
		for (ENUM_DATA_TYPE e : ENUM_DATA_TYPE.values()) {
			if (e.code.equals(code)) {
				return e.year;
			}
		}
		return false;
	}

	/**
	 * @Author:lio
	 * @Description: 判断数据类型月导入
	 * @Date :3:08 下午 2021/1/7
	 */
	public static boolean getDateMonthFlagByCode(String code) {
		for (ENUM_DATA_TYPE e : ENUM_DATA_TYPE.values()) {
			if (e.code.equals(code)) {
				return e.month;
			}
		}
		return false;
	}

	/**
	 * @Author:lio
	 * @Description: 获取tce
	 * @Date :3:43 下午 2021/1/22
	 */
	public static String getTceByCode(String code){
		for (ENUM_DATA_TYPE e : ENUM_DATA_TYPE.values()) {
			if (e.code.equals(code)) {
				return e.tce;
			}
		}
		return "";
	}


	/**
	 * @Author:lio
	 * @Description: 判断数据类型月导入
	 * @Date :3:08 下午 2021/1/7
	 */
	public static String getSpellByCode(String code) {
		for (ENUM_DATA_TYPE e : ENUM_DATA_TYPE.values()) {
			if (e.code.equals(code)) {
				return e.spell;
			}
		}
		return "";
	}

	public static String getMsgByCode(String code) {
		for (ENUM_DATA_TYPE e : ENUM_DATA_TYPE.values()) {
			if (e.code.equals(code)) {
				return e.msg;
			}
		}
		return "";
	}


	private final String code;

	private final String msg;

	private final String spell;

	/**
	 * 导入年数据
	 */
	private final boolean year;

	/**
	 * 导入月数据
	 */
	private final boolean month;

	/**
	 * 折标系数 tce
	 * @return
	 */
	private final String tce;

	public String getTce(){
		return tce;
	}

	public boolean getYear(){
		return year;
	}

	public boolean getMonth(){
		return month;
	}

	public String getCode() {
		return code;
	}
	public String getSpell(){return spell;}


	public String getMsg() {
		return msg;
	}

	ENUM_DATA_TYPE(String code, String msg,String spell,boolean year ,boolean month,String tce) {
		this.code = code;
		this.msg = msg;
		this.spell=spell;
		this.year = year;
		this.month = month;
		this.tce= tce;
	}

}
