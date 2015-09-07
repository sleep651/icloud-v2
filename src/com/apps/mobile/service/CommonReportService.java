package com.apps.mobile.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apps.common.dao.TaskDao;
import com.apps.common.dao.TaskDao_DB2;
import com.apps.mobile.domain.ChartType;

@Service
public class CommonReportService {

	@Autowired
	private TaskDao taskDao;
	@Autowired
	private TaskDao_DB2 taskDao_DB2;
	
	//获取图表配置信息
    @SuppressWarnings("unchecked")
    public List<HashMap> getChartConfig(String chart_id,String dim_id) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("chart_id", chart_id);
		params.put("dim_id", dim_id);
		List<HashMap> list = taskDao.getSqlMapClientTemplate().queryForList("commonReport.getChartConfig", params);
        return list;
    }
    //获取图表标题信息
    @SuppressWarnings("unchecked")
    public String getChartTitle(String chart_id) {
    	String title = "";
    	HashMap<String, String> params = new HashMap<String, String>();
    	params.put("chart_id", chart_id);
    	List<String> list = taskDao.getSqlMapClientTemplate().queryForList("commonReport.getChartTitle", params);
    	if(list!=null&&list.size()>0){
    		title = list.get(0);
    	}
    	return title;
    }
    //构造前台页面需要的参数信息
    public HashMap getChart(String chart_id,String dim_id,String c_date,String org_id) throws SQLException{
    	HashMap ret = new HashMap();
		//构造返回值：：：
    	String ret_type = "";
    	String ret_label = "";
    	String ret_json = "";	
    	String ret_title = "";	
    	String ret_color = "";	
    	//读取配置信息
    	if(chart_id!=null&&!"".equals(chart_id)
    			&&dim_id!=null&&!"".equals(dim_id)){
    		String title = getChartTitle(chart_id);
    		ret_title = "'"+title+"'";
    		
    		List<HashMap> confList = getChartConfig(chart_id,dim_id);
			if (confList != null && confList.size() != 0) {
				HashMap confMap = confList.get(0);
				/*
				 * 当日期为空时：CHART_DATA_SQL
				 * 图例数据：CHART_TITLE_SQL
				 * 图表数据：DIM_SQL
				*/
				String chartDataSql = (String) confMap.get("CHART_DATA_SQL");// 图表数据日期SQL
				String chartTitleSql = (String) confMap.get("CHART_TITLE_SQL");// 图表业务标题SQL图例
				String chartColorSql = (String) confMap.get("CHART_COLOR_SQL");// 图表颜色
				String chartBackColorSql = (String) confMap.get("CHART_BACK_COLOR");//图表背景颜色
				String chartType = (String) confMap.get("CHART_TYPE");// 图表类型
				
				CLOB clob = (CLOB) confMap.get("DIM_SQL");
				String dimSql = clob.getSubString(1, (int) clob.length());// 图表SQL
				
				String data_source = (String) confMap.get("DATA_SOURCE");
				
				HashMap<String, String> params = new HashMap<String, String>();
				//当日期为空时，查询默认日期
				if(c_date==null||"".equals(c_date)){
					params.put("sql", chartDataSql);
					List<String> chartDateList = taskDao.getSqlMapClientTemplate().queryForList("mobile.getDynamicRepOfString",params);
					if(chartDateList!=null&&chartDateList.size()>0){
						c_date = chartDateList.get(0);
					}
				}
				
				//构造表头sql
				params.put("sql", chartTitleSql);
				List<String> chartTitleList = taskDao.getSqlMapClientTemplate().queryForList("mobile.getDynamicRepOfString",params);
				
				//构造背景颜色sql
				params.put("sql", chartBackColorSql);
				String chartBackColor = (String)taskDao.getSqlMapClientTemplate().queryForObject("mobile.getDynamicRepOfString",params);
				ret_color = "'"+chartBackColor+"'";
				
				//构造颜色sql
				params.put("sql", chartColorSql);
				List<String> chartColorList = taskDao.getSqlMapClientTemplate().queryForList("mobile.getDynamicRepOfString",params);
				
				
				// 构造数据sql（默认数据列名，按照c0,c1,c2,c3...规则编码）
				dimSql = dimSql.replace("$org_id$", org_id);
				dimSql = dimSql.replace("$stat_date$", c_date);
				params.put("sql", dimSql);	
				
				List dataList = new ArrayList();
				HashMap hashMap = new HashMap();
				List sqlRetList = null;
				if ("ORA1".equals(data_source)) {
					sqlRetList = taskDao.find("mobile.getDynamicRepOfHashMap", params);
				} else if ("DB2".equals(data_source)) {
					//sqlRetList = taskDao_DB2.find("mobile_DB2.getDynamicRepOfHashMap", params);
					sqlRetList = taskDao.find("mobile.getDynamicRepOfHashMap", params);
				}
				for (int i = 0; i < sqlRetList.size(); i++) {
					hashMap = (HashMap) sqlRetList.get(i);
					List<String> rowList = new ArrayList<String>();
					for (int j = 0; j <= hashMap.size(); j++) {
						rowList.add(mapToString(hashMap, "C" + j));
					}
					dataList.add(rowList);
				}
				//如果是饼图，构造返回值如下
				if("pie".equals(chartType.toLowerCase())){
					/*
					$('#container').chart({
						    color:'#CC99CC',
							title:'这是一个标题',
							type:'pie',
			        		json:[{
									name:'9日',
	                				data: [
											['1',500],
											['2',650],
											['3',700],
											['4',630],
											['5',640]
										]
	            				  }]
						});									 
					 * */
					List<String> rowList = (List<String>)dataList.get(0);//饼图只有一行数据
					ret_type = "'"+chartType+"'";
			    	ret_label += "''";
			    	ret_json  += "[";
			    	ret_json += "{\n";
			    	ret_json += "name:'"+rowList.get(0)+"',\n";
			    	ret_json += "data:[\n";
			    	if(chartTitleList!=null&&chartTitleList.size()>0){
			    		for(int i=0;i<chartTitleList.size();i++){
			    			ret_json += "['"+chartTitleList.get(i)+"',"+rowList.get(i+1)+"],";
			    		}
			    		ret_json = ret_json.substring(0, ret_json.length()-1);
			    	}
			    	ret_json += "\n]\n";
			    	ret_json += "}]";
				}else{
					/*
					$('#container').chart({
						color:'#CC99CC',
						title:'这是一个标题',
						type:'line',
						label:['1月','2月','3月','4月','5月'],
						json:[{
								color:'#CC99CC',
								name:'用户量',
                				data: [500,650,700,630,640]
            				  }, 
            				  {
								color:'',
								name:'业务数据',
		               			data: [480,540,530,500,580]
		           			  }]
					});	
					 * */
					ret_type = "'"+chartType+"'";
			    	ret_label += "[";
			    	ret_json  += "[";
			    	if(chartTitleList!=null&&chartTitleList.size()>0){
			    		for(int i=0;i<chartTitleList.size();i++){
			    			ret_json += "{\n";
			    			ret_json += "color:'"+chartColorList.get(i)+"',\n";
			    			ret_json += "name:'"+chartTitleList.get(i)+"',\n";
			    			ret_json += "data:[\n";
			    			for(int j=0;j<dataList.size();j++){
			    				List<String> rowList = (List<String>)dataList.get(j);
			    				if(i==0){
			    					ret_label += "'"+rowList.get(0)+"',";
			    				}
			    				ret_json += rowList.get(i+1)+",";
			    			}
			    			ret_json = ret_json.substring(0, ret_json.length()-1);
			    			ret_json += "]\n";
			    			ret_json += "},";
			    		}
			    		ret_json = ret_json.substring(0, ret_json.length()-1);
			    	}
			    	ret_json += "]";
			    	if(ret_label.length()>1){
		    			ret_label = ret_label.substring(0, ret_label.length()-1);
		    		}    			
	    			ret_label += "]";
				}
			}
    	}
    	ret.put("title", ret_title);
    	ret.put("type", ret_type);
    	ret.put("label", ret_label);
    	ret.put("json", ret_json);
    	ret.put("color", ret_color);
    	return ret;
    }
    
	// 将map获取的value转换成string
	@SuppressWarnings("rawtypes")
	private String mapToString(Map map, String key) {
		String ret = "";
		if (map.get(key) != null) {
			ret = map.get(key).toString();
		}
		return ret;
	}
}