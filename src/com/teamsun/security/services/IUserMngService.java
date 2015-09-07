/**
 * 
 */
package com.teamsun.security.services;

import java.util.List;
import java.util.Map;

import com.teamsun.core.domain.Pager;
import com.teamsun.security.domain.CurrUserVO;
import com.teamsun.security.domain.UserVO;


/**
 * CPCIM
 * @author GuoJingFu
 * @since Jun 26, 2008
 * @version 2.0 
 * TODO:用户管理接口
 */
public interface IUserMngService {

	public int createUser(UserVO userVO);

	public int deleteUser(UserVO userVO);

	public CurrUserVO getUser(UserVO userVO);

	public int getUserCount(UserVO userVO);

	public List<UserVO> getUserList(UserVO userVO);

	public int updateUser(UserVO userVO);

	public List<UserVO> getUserList(int start, int count, UserVO userVO);
	
	public Pager browseUser(int pageNo, int pageSize, Map parmMap);

	/**
	 * 记录登陆日志
	 * @param certUser
	 * @param logon_ip
	 * @param logon_name
	 */
	public void logUserDB(String staff_no,String name,String logon_ip,String logon_mode);

	public String getLogonUserRole(String userid);

	public String getLogonUserOrgName(String userid, String auth_lvl, String latn_id);

}
