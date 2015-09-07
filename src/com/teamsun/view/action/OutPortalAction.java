package com.teamsun.view.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teamsun.core.action.BaseAction;

@Controller
@RequestMapping("/outPortalAction.do")
public class OutPortalAction extends BaseAction{
	
	@RequestMapping(params = "method=display") 
	public String display(String key,ModelMap map) throws Exception{
		/*Map retMap = portalViewService.getPortalViewToMap();
		map.put("outPortalViewMap", retMap);*/
		return "portal/mainLogon"; 
	}
}

