/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */

/**
 * cppims2
 * @author GuoJF
 * @since Nov 13, 2008
 * @version 2.0 
 * @Component :Spring 注入
 * TODO:
 */
package com.teamsun.core.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/** */
/**
 * 报表生成公共类(创建FusionCharts<flash cool>）
 * 
 * @author guojf
 * 
 */
public class ChartUtils {
	/** */
	/**
	 * 对Url数据转码的方法
	 * 
	 * @param strDataURL -
	 *            chart的数据url
	 * @param addNoCacheStr -
	 *            非缓存字符串
	 * @return
	 */

	public String encodeDataURL(String strDataURL, String addNoCacheStr,
			HttpServletResponse response) {
		String encodedURL = strDataURL;
		if (addNoCacheStr.equals("true")) {
			java.util.Calendar nowCal = java.util.Calendar.getInstance();
			java.util.Date now = nowCal.getTime();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH_mm_ss a");
			String strNow = sdf.format(now);
			if (strDataURL.indexOf("?") > 0) {
				encodedURL = strDataURL + "&FCCurrTime=" + strNow;
			} else {
				strDataURL = strDataURL + "?FCCurrTime=" + strNow;
			}
			encodedURL = response.encodeURL(strDataURL);

		}
		return encodedURL;
	}

	/** */
	/**
	 * 用HTML+JavaScript创建FusionCharts对象(用此方式需要导入FusionCharts.js文件)
	 * 
	 * @param chartSWF -
	 *            flash文件的位置，即chart图表类型
	 * @param strURL -
	 *            xml数据源
	 * @param strXML -
	 *            字符流
	 * @param chartId -
	 *            chart对象在HTML中的唯一标识
	 * @param chartWidth -
	 *            flash chart 的宽度(单位px)
	 * @param chartHeight -
	 *            flash chart 的高度(单位px)
	 * @param debugMode -
	 *            是否开启chart 调试模式
	 * @param registerWithJS -
	 *            是否注册自己
	 */
	public static String createChart(String chartSWF, String strURL, String strXML, String chartId,
			int chartWidth, int chartHeight, boolean debugMode, boolean registerWithJS) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("<!--START Script Block for Chart -->\n");
		strBuf.append("\t\t<div id='" + chartId + "Div' align='center'>\n");
		strBuf.append("\t\t\t\tChart.\n");
		strBuf.append("\t\t</div>\n");
		strBuf.append("\t\t<script type='text/javascript'>\n");
		Boolean registerWithJSBool = new Boolean(registerWithJS);
		Boolean debugModeBool = new Boolean(debugMode);
		int regWithJSInt = boolToNum(registerWithJSBool);
		int debugModeInt = boolToNum(debugModeBool);

		strBuf.append("\t\t\t\tvar chart_" + chartId + " = new FusionCharts('" + chartSWF + "', '"
				+ chartId + "', '" + chartWidth + "', '" + chartHeight + "', '" + debugModeInt
				+ "', '" + regWithJSInt + "');\n");
		if (strXML.equals("")) {
			strBuf.append("\t\t\t\t//Set the dataURL of the chart\n");
			strBuf.append("\t\t\t\tchart_" + chartId + ".setDataURL(\"" + strURL + "\");\n");
		} else {
			strBuf.append("\t\t\t\t//Provide entire XML data using dataXML method\n");
			strBuf.append("\t\t\t\tchart_" + chartId + ".setDataXML(\"" + strXML + "\");\n");
		}
		strBuf.append("\t\t\t\t//Finally, render the chart.\n");
		strBuf.append("\t\t\t\tchart_" + chartId + ".render(\"" + chartId + "Div\");\n");
		strBuf.append("\t\t</script>\n");
		strBuf.append("\t\t<!--END Script Block for Chart-->\n");
		return strBuf.substring(0);
	}

	/** */
	/**
	 * 创建swf charts对象(HTML)
	 * 
	 * @param chartSWF -
	 *            flash文件的位置，即chart图表类型
	 * 
	 * @param strURL -
	 *            xml数据源
	 * @param strXML -
	 *            If you intend to use dataXML method for this chart, pass the
	 *            XML data as this parameter. Else, set it to "" (in case of
	 *            dataURL method)
	 * @param chartId -
	 *            chart对象在HTML中的唯一标识
	 * @param chartWidth -
	 *            flash chart 的宽度(单位px)
	 * @param chartHeight -
	 *            flash chart 的高度(单位px)
	 * @param debugMode -
	 *            是否开启chart 调试模式
	 */

	public static String createChartHTML(String chartSWF, String strURL, String strXML,
			String chartId, int chartWidth, int chartHeight, boolean debugMode) {
		String strFlashVars = "";
		Boolean debugModeBool = new Boolean(debugMode);

		if (strXML.equals("")) {
			strFlashVars = "chartWidth=" + chartWidth + "&chartHeight=" + chartHeight
					+ "&debugMode=" + boolToNum(debugModeBool) + "&dataURL=" + strURL + "";
		} else {
			strFlashVars = "chartWidth=" + chartWidth + "&chartHeight=" + chartHeight
					+ "&debugMode=" + boolToNum(debugModeBool) + "&dataXML=" + strXML + "";
		}
		StringBuffer strBuf = new StringBuffer();
		// 开始输出Object chart
		strBuf.append("\t\t<!--START Code Block for Chart-->\n");
		strBuf.append("\t\t\t\t<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0' width='"
						+ chartWidth + "' height='" + chartHeight + "' id='" + chartId + "'>\n");
		strBuf.append("\t\t\t\t    <param name='allowScriptAccess' value='always' />\n");
		strBuf.append("\t\t\t\t    <param name='movie' value='" + chartSWF + "'/>\n");
		strBuf.append("\t\t\t\t<param name='FlashVars' value=\"" + strFlashVars + "\" />\n");
		strBuf.append("\t\t\t\t    <param name='quality' value='high' />\n");
		strBuf.append("\t\t\t\t<embed src='"
						+ chartSWF
						+ "' FlashVars=\""
						+ strFlashVars
						+ "\" quality='high' width='"
						+ chartWidth
						+ "' height='"
						+ chartHeight
						+ "' name='"
						+ chartId
						+ "' allowScriptAccess='always' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />\n");
		strBuf.append("\t\t</object>\n");
		strBuf.append("\t\t<!--END Code Block for Chart-->\n");
		return strBuf.substring(0);
	}

	/** */
	/**
	 * 提供快速输出Chart的便捷方法(基于字符流的方式)
	 * 
	 * @param chartSWF :
	 *            flash文件的位置，即chart图表类型
	 * @param strXml :
	 *            xml文件流(string格式)
	 * @param chartId :
	 *            chart对象在HTML中的唯一标识
	 * @param chartWidth :
	 *            flash chart 的宽度(单位px)
	 * @param chartHeight:
	 *            flash chart 的高度(单位px)
	 * @param response :
	 *            reponse对象
	 */
	public static void outChartHTML(String chartSWF, String strXml, String chartId, int chartWidth,
			int chartHeight, HttpServletResponse response) {
		String str = createChartHTML(chartSWF, "", strXml, chartId, chartWidth, chartHeight, false);
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * 提供快速输出Chart的便捷方法(基于xml文件的方式)
	 * 
	 * @param chartSWF :
	 *            flash文件的位置，即chart图表类型
	 * @param strURL :
	 *            xml数据源(路径格式)
	 * @param chartId :
	 *            chart对象在HTML中的唯一标识
	 * @param chartWidth :
	 *            flash chart 的宽度(单位px)
	 * @param chartHeight:
	 *            flash chart 的高度(单位px)
	 * @param response :
	 *            reponse对象
	 */
	public static void outChartSourceHTML(String chartSWF, String strURL, String chartId,
			int chartWidth, int chartHeight, HttpServletResponse response) {
		String str = createChartHTML(chartSWF, strURL, "", chartId, chartWidth, chartHeight, false);
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** */
	/**
	 * bollean转换为int
	 */
	private static int boolToNum(Boolean bool) {
		int num = 0;
		if (bool.booleanValue()) {
			num = 1;
		}
		return num;
	}
}
