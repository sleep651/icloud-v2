/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.security.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teamsun.core.action.BaseAction;
import com.teamsun.core.domain.CertVO;
import com.teamsun.security.services.IMenuMngService;

/**
 * CPCIM
 * 
 * @author Administrator
 * @since Sep 16, 2008
 * @version 2.0 TODO:菜单管理操作
 */
@Controller
@RequestMapping("/menuMngAction.do")
public class MenuMngAction extends BaseAction {
	@Autowired
	private IMenuMngService menuMngService;
	
	@RequestMapping(params = "method=getTreeData")
	public String getTreeData(ModelMap map,HttpServletRequest request, HttpServletResponse response) throws Exception {
		CertVO cert = getCert(request);
		String roleId = cert.getCuservo().getRoleid().toString();
		String nodeId = request.getParameter("node");
		nodeId = ("0".equals(nodeId)?null:nodeId);
		String  jsonResult = menuMngService.getMenuData(roleId,nodeId);
		if(null!=jsonResult){
			request.setAttribute("jsonResult", jsonResult);
			map.put("jsonResult", jsonResult);
			logger.info(jsonResult);
			return "common/jsonResult";
		}else{
			return "common/error";
		}
	}
  
}
