package com.apps.mobile.domain;

public class ChartConfig {
	private String chartId;		//图表id
	private String dimId;	//维度id,1:时间,2:地域
	private String dimSql;	//维度SQL
	private String alarmSql; //预警SQL
	private String  dataSource;//数据源
	private String chartType;//1柱形图,2折线图,3饼图,4条形图,5面积图,6散点图,7股价图,8曲面图,9圆环图,10气泡图,11雷达图
	private String chartDataSql;//图表数据日期SQL
	private String chartTitleSql;
	private String chartConditionSql;//维度条件选择SQL
	public String getChartId() {
		return chartId;
	}
	public void setChartId(String chartId) {
		this.chartId = chartId;
	}
	public String getDimId() {
		return dimId;
	}
	public void setDimId(String dimId) {
		this.dimId = dimId;
	}
	public String getDimSql() {
		return dimSql;
	}
	public void setDimSql(String dimSql) {
		this.dimSql = dimSql;
	}
	public String getAlarmSql() {
		return alarmSql;
	}
	public void setAlarmSql(String alarmSql) {
		this.alarmSql = alarmSql;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public String getChartDataSql() {
		return chartDataSql;
	}
	public void setChartDataSql(String chartDataSql) {
		this.chartDataSql = chartDataSql;
	}
	public String getChartTitleSql() {
		return chartTitleSql;
	}
	public void setChartTitleSql(String chartTitleSql) {
		this.chartTitleSql = chartTitleSql;
	}
	public String getChartConditionSql() {
		return chartConditionSql;
	}
	public void setChartConditionSql(String chartConditionSql) {
		this.chartConditionSql = chartConditionSql;
	}
	
}
