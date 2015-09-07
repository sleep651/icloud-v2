package com.teamsun.security.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.teamsun.core.action.BaseAction;
import com.teamsun.core.exception.BaseException;
import com.teamsun.security.Md5Make;
import com.teamsun.security.domain.CurrUserVO;
import com.teamsun.security.domain.UserVO;
import com.teamsun.security.services.IUserMngService;

/**
 * template4j
 * 
 * @author GuoJingFu
 * @since Jun 26, 2008
 * @version 2.0 TODO:用户登录管理
 *UserLoginAction.do?method=logon
 */

@Controller
@RequestMapping("/UserLoginAction.do")
public class UserLoginAction extends BaseAction {
	private static final Logger logger = LoggerFactory
	.getLogger(UserLoginAction.class);
	@Autowired
	private IUserMngService userMngService;

	/**
	 * GuoJingFu Jun 26, 2008 2:17:45 PM
	 * 
	 * @return
	 * @throws Exception
	 *             TODO:用户登录操作 ：注：此方法目前只是操作示意，真实业务要增加相应的业务逻辑． 入口参数：被Spring2.5
	 *             自动匹配到UserVO 中； ModelMap 是保存回值的容器。
	 */
	@RequestMapping(params = "method=logon")
	public String logon( ModelMap map,UserVO userVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if ((null != userVO.getLogon_name())  && (null != userVO.getPasswd())) {
			logger.info("账号【{}】尝试登陆！IP【{}】",userVO.getLogon_name(),request.getRemoteAddr());
			userVO.setLogon_name( userVO.getLogon_name().toLowerCase());
			userVO.setPasswd(Md5Make.crypt(userVO.getPasswd()));
			CurrUserVO certUser = userMngService.getUser(userVO);
			if (null != certUser) {
				HttpSession session = request.getSession();
				session.setAttribute("Certificate", certUser);
				map.put("currUser", certUser);
				map.put("currUserName", certUser.getName());
				return "portal/frameportal";
			} else {
				map.put("message", "用户名/密码有误，请重新登录！");
				return "portal/mainLogon";
			}
		} else {
			map.put("message", "用户名/密码有误，请重新登录！");
			return "portal/mainLogon";
		}
	}
	

	/**
	 * Administrator_guojf Oct 9, 2008 10:03:33 AM
	 * 
	 * @param cert
	 * @param request
	 *            TODO:保存当前登录用户到会话Session中，为以后认证使用。
	 */

	public String showIndexPage() throws BaseException {

		return SUCCESS;
	}

	/**
	 * Administrator_guojf Oct 9, 2008 10:05:33 AM
	 * 
	 * @return TODO:用户退出操作， 操作步骤：1：清除会话中的认证 2：记录登出日志
	 */
	@RequestMapping(params = "method=logoff")
	public String logoff( HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(null!=session){
			session.removeAttribute("Certificate");
			session.removeAttribute("currUserId");
			session.invalidate();
		}
		logger.debug("用户退出");
		return SUCCESS;
	}

	/**
	 * guojf Oct 9, 2008 10:00:12 AM TODO:
	 * 此方法，暂时不用，因Coolies方式使用会受限。但可为以后单点认证实现方式之一。
	 */
	@SuppressWarnings("unused")
	private void saveUserInSessionAndCookies(CurrUserVO currUserVO, HttpServletRequest request) {
		saveObjForSession(currUserVO, request, "Certificate");
	}

}
