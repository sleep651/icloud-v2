/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.security.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teamsun.core.action.BaseAction;
import com.teamsun.core.domain.CertVO;
import com.teamsun.core.domain.FrontPageVo;
import com.teamsun.security.domain.CurrUserVO;
import com.teamsun.security.domain.MenuPanelVO;
import com.teamsun.security.domain.ResVO;
import com.teamsun.security.domain.RoleResVO;
import com.teamsun.security.services.IResMngService;


/**
 * CPCIM
 * @author Administrator
 * @since Sep 16, 2008
 * @version 2.0 
 * TODO:资源管理操作
 */
@Controller
@RequestMapping("/ResMngAction.do")
public class ResMngAction extends BaseAction {
	@Autowired
	private IResMngService resMngService;
	public String create(ResVO resVO,ModelMap map,HttpServletRequest request, HttpServletResponse response){
		try {
			resVO.setStatus("1");
			int count = resMngService.createResource(resVO);
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			if(count==1){
				json.put("success", "1");
			}else{
				json.put("success", "0");
			}
			out.print(json);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	public String update(ResVO resVO,ModelMap map,HttpServletRequest request, HttpServletResponse response) {

		try {
			int count = resMngService.updateResource(resVO);
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			if(count==1){
				json.put("success", "1");
			}else{
				json.put("success", "0");
			}
			out.print(json);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}

	public String delete(ResVO resVO,ModelMap map,HttpServletRequest request, HttpServletResponse response) {
		
		try {
			resVO.setStatus("2");
			int count = resMngService.updateResource(resVO);
			RoleResVO roleResVO = new RoleResVO();
			roleResVO.setS_id(resVO.getR_id());
			resMngService.deleteRoleResource(roleResVO);
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			if(count==1){
				json.put("success", "1");
			}else{
				json.put("success", "0");
			}
			out.print(json);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}

	
	@RequestMapping(params = "method=getTreeByType")
	public String getTreeByType(ResVO resVO,String treeType, ModelMap map,HttpServletRequest request, HttpServletResponse response){
     	CertVO certVO  =  getCert(request);
		CurrUserVO currUserVO = certVO.getCuservo();
		String nodeStr = request.getParameter("node");
		if("0".equals(nodeStr)){
			//nodeStr = String.valueOf(Integer.parseInt(nodeStr)+1);//因数据库时是以1开头，不是0开头的。
			nodeStr = treeType;
		}
		List<ResVO> resList = null;
		if(currUserVO!=null){ 
			//String usertype = currUserVO.getState();
			ResVO  res = new ResVO();
			res.setP_rid(nodeStr);
			res.setStatus("1");//有效的菜单
//			res.setRole_id(currUserVO.getRoleid().toString());
			/**
			 * 修改为多角色
			 */
			String role = "";
			for(String str:currUserVO.getRole_id())
				role += ","+str;
			res.setRole_id(role.substring(1));
			res.setUser_id(currUserVO.getUserid().toString());
			resList = resMngService.getResourceList(res,treeType);
			List<JSONObject> jsonList = new ArrayList<JSONObject>();
			JSONObject node = null;
			for(ResVO temp : resList){
				node = new JSONObject();
				node.put("id", temp.getR_id());
				node.put("text", temp.getR_name());
				node.put("r_desc", temp.getR_desc());
				node.put("r_type", temp.getR_type());
				node.put("link", temp.getLinkaddress());
				if("0".equals(temp.getR_type())){
					node.put("cls", "folder");
				}else{
					node.put("leaf", true);
				}
				jsonList.add(node);
			}
			JSONArray json = JSONArray.fromObject(jsonList);
			try {
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				out.print(json.toString());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	public String updateOrderBy(ResVO resVO,ModelMap map,HttpServletRequest request, HttpServletResponse response){
		
		try {
			String updateJson = request.getParameter("updateJson");
			JSONArray ja = JSONArray.fromObject(updateJson);
			List<ResVO> mlist = (ArrayList<ResVO>) JSONArray.toCollection(ja, ResVO.class);
			for(ResVO temp : mlist){
				resMngService.updateResource(temp);
			}
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("success", "1");
			out.print(json);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}

	
	@RequestMapping(params = "method=getMenuPanel")
	public String getMenuPanel(ModelMap map,HttpServletRequest request, HttpServletResponse response){
     	CertVO certVO  =  getCert(request);
		CurrUserVO currUserVO = certVO.getCuservo();
		ResVO resVO =  new ResVO();
		resVO.setUser_id(currUserVO.getUserid().toString());
		/**
		 * 修改为多角色
		 */
		String role = "";
		for(String str:currUserVO.getRole_id())
			role += ","+str;
//		resVO.setRole_id(currUserVO.getRoleid().toString());
		resVO.setRole_id(role.substring(1));
		List<MenuPanelVO> resList = resMngService.getResourceList(resVO);
		JSONArray json = JSONArray.fromObject(resList);
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.close();
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}

		return null;
	}

	/**
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 * @author ht
	 */
	@RequestMapping(params = "method=getFrontPagePanel")
	public String getFrontPagePanel(ModelMap map,HttpServletRequest request, HttpServletResponse response){
		CertVO certVO  =  getCert(request);
		CurrUserVO currUserVO = certVO.getCuservo();
		ResVO resVO =  new ResVO();
		resVO.setUser_id(currUserVO.getUserid().toString());
		/**
		 * 修改为多角色
		 */
		String role = "";
		for(String str:currUserVO.getRole_id())
			role += ","+str;
		resVO.setRole_id(role.substring(1));
//		resVO.setRole_id(currUserVO.getRoleid().toString());
		List<FrontPageVo> lst = resMngService.getFrontPageList(resVO);
		JSONArray json = JSONArray.fromObject(lst);
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.close();
		} catch (IOException e) {
			logger.error(e.getStackTrace().toString());
		}
		return null;
	}
}
