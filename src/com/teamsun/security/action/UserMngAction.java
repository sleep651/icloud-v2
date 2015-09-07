package com.teamsun.security.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teamsun.core.action.BaseAction;
import com.teamsun.core.domain.CertVO;
import com.teamsun.core.domain.Pager;
import com.teamsun.core.utils.StringUtils;
import com.teamsun.security.domain.CurrUserVO;
import com.teamsun.security.domain.UserVO;
import com.teamsun.security.exception.UserMngException;
import com.teamsun.security.services.IUserMngService;


/**
 * CPCIM
 * 
 * @author GuoJingFu
 * @since Jun 26, 2008
 * @version 2.0 TODO:用户管理操作
 * 
 */
@Controller
@RequestMapping("/userMngAction.do")
public class UserMngAction extends BaseAction {

	private static final long serialVersionUID = -1741019972756729460L;
	private static final int PAGESIZE = 5;
	@Autowired
	private IUserMngService userMngService;

	@RequestMapping(params = "method=update")
	public String create(UserVO userVO,ModelMap map,HttpServletRequest request, HttpServletResponse response){
		int result = userMngService.createUser(userVO);
		logger.info(""+result);
		return null;  
	}
	@RequestMapping(params = "method=update")	
	public String update(UserVO userVO,ModelMap map,HttpServletRequest request, HttpServletResponse response) {
		int result = userMngService.updateUser(userVO);
		logger.info(""+result);
		return null;
	}

	@RequestMapping(params = "method=delete")	
	public String delete(UserVO userVO,ModelMap map,HttpServletRequest request, HttpServletResponse response) {
		
		String jsonData = request.getParameter("jsonData");
		JSONArray json = JSONArray.fromObject(jsonData);
		List<UserVO> list = (ArrayList<UserVO>) JSONArray.toCollection(json, UserVO.class);
		for(UserVO user : list){
			userMngService.deleteUser(user);
		}		
		return null;
	}
	@RequestMapping(params = "method=doLogin")
	public String doLogin(UserVO userVO,ModelMap map,HttpServletRequest request, HttpServletResponse response){
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			if(userVO.getLogon_name()!=null&&userVO.getPasswd()!=null){
				int count = userMngService.getUserCount(userVO);
				if(count==1){
					HttpSession session = request.getSession();
					CurrUserVO certUser = userMngService.getUser(userVO);
					session.setAttribute("Certificate", certUser);
					json.put("success", "1");
				}else{
					json.put("success", "0");
				}
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
	
	@RequestMapping(params = "method=checkLogin")
	public String checkLogin(UserVO userVO,ModelMap map,HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("login_user");
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			if(user!=null){
				json.put("timeout", "yes");
			}else{
				json.put("timeout", "no");
			}
			out.print(json);
			out.close();
		} catch (IOException e) {
			logger.error("UserMngAction||checkLogin:操作有误：---> "+e);
		}
		return null;
	}
	
	@RequestMapping(params = "method=list")
	public String list(UserVO userVO,ModelMap map,HttpServletRequest request, HttpServletResponse response) {
		CertVO cert = getCert(request);
		int start = ((StringUtils.isEmpty(request.getParameter("start"))==true)?1:Integer.parseInt(request.getParameter("start")));
		int limit = ((StringUtils.isEmpty(request.getParameter("limit"))==true)?1:Integer.parseInt(request.getParameter("limit")));
		Map parmMap = new HashMap();
		parmMap.put("logon_name", cert.getCuservo().getLogon_name());
		Pager pager =  userMngService.browseUser(start+1, limit, parmMap) ;//ext:start 分页是：0开始，java:start:1开始
		JSONObject obj = new JSONObject();
		JSONArray jsonusers = JSONArray.fromObject(pager.getResultList());
		obj.put("totalCount", pager.getTotalRows());
		obj.put("roots", jsonusers);
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(obj.toString());
			out.close();
		} catch (IOException e) {
			logger.error("UserMngAction||list:操作有误：---> "+e);
		}
		return null;

	}
	

	/**
	 * GuoJingFu Oct 7, 2008 6:01:09 PM
	 * 
	 * @return TODO:给用户设定角色
	 */
	public String doSetRole2User(UserVO userVO,ModelMap map,HttpServletRequest request, HttpServletResponse response) {

		return SUCCESS;
	}

	/**
	 * GuoJingFu Jun 26, 2008 2:38:21 PM
	 * 
	 * @return
	 * @throws UserMngException
	 *             TODO:浏览用户信息
	 */
	@RequestMapping(params = "method=browseUser")
	public String browseUser(ModelMap map,HttpServletRequest request, HttpServletResponse response) throws UserMngException {
		int pageSize = PAGESIZE;
		int pageNo = ((StringUtils.isEmpty(request.getParameter("pageNo"))==true)?1:Integer.parseInt(request.getParameter("pageNo")));
		Map parmMap = new HashMap();//装载查询条件的容器
        parmMap.put("logon_name", (StringUtils.isEmpty(request.getParameter("logon_name"))==true)?"":(request.getParameter("logon_name")).toString());
        parmMap.put("name", (StringUtils.isEmpty(request.getParameter("name"))==true)?"":(request.getParameter("name")).toString());
		Pager  pager = null;//this.userMngService.browseUser(pageNo,pageSize,parmMap);
		map.put("pager", pager);
		return "portal/test";
	}

}
