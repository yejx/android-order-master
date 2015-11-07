package com.yjx.order.common;

public class Parameters
{
	public static final String DB_NAME = "orderdish.db";
	public static final int DB_VERSION = 1;

	public static final String MENU_3103_DISH = "3103宿舍系列菜";
	public static final String MENU_CHUAN_DISH = "精品川菜";
	public static final String MENU_MIN_DISH = "精品闽菜";
	public static final String MENU_YUE_DISH = "精品粤菜";


	public static final String serviceURL = "http://192.168.1.111:8080/OrderServlet/";

//	public static final String serviceURL = "http://27.155.178.116:8080/OrderServlet/";

	/**网络请求成功后返回的状态码*/
	public static final String successRetCode = "0000";
	/**验证码验证失败*/
	public static final String vercodeError = "1001";
	/**用户不存在*/
	public static final String userNoExists = "1002";
	/**用户已经存在*/
	public static final String userHasExists = "1003";
	/**用户名或密码错误*/
	public static final String userOrPWError = "0001";
	/**无效的Token*/
	public static final String tokenOutOfDate = "1023";
	//交易超时统一code
	public static final String tranTimeOut = "1030";

}
