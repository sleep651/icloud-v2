package com.apps.mobile.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apps.mobile.domain.HomeReportBean;
import com.apps.mobile.service.CommonReportService;
import com.teamsun.core.action.BaseAction;

/**
 * 手机端报表
 * @author kobe
 *
 */
@Controller
@RequestMapping("/CommonReportAction.do")
public class CommonReportAction extends BaseAction {

    @Autowired
    private CommonReportService commonReportService;

    
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "method=getReportChart")
    public String getReportChart(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	//获取参数
    	String chart_id = request.getParameter("chart_id");//图表id (不能为空)
    	String dim_id = request.getParameter("dim_id");//维度id (不能为空) 
    	String c_date = request.getParameter("c_date");//日期 (第一次加载为空，除此之外不能为空) 
    	String org_id = request.getParameter("org_id");//组织结构id
    	
    	
    	//读取配置信息
    	if(chart_id!=null&&!"".equals(chart_id)
    			&&dim_id!=null&&!"".equals(dim_id)){

    		HashMap form = commonReportService.getChart(chart_id,dim_id,c_date, org_id);
    		map.put("form", form);
    	}
    	return "report/commonChart";
    }
  
}