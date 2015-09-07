/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.security.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.teamsun.core.domain.Pager;
import com.teamsun.core.services.BaseService;
import com.teamsun.core.utils.StringUtils;
import com.teamsun.security.domain.CurrUserVO;
import com.teamsun.security.domain.UserVO;
import com.teamsun.security.services.IUserMngService;

/**
 * template4j
 * 
 * @author GuoJingFu
 * @since Jun 26, 2008
 * @version 2.0 TODO:实现用户管理业务服务 实现与用户相关的所有业务，包括登录认证的业务．
 */
@Component
public class UserMngService extends BaseService implements IUserMngService {
	
	private static int PAGESIZE = 10;

	public int createUser(UserVO userVO) {
		return 0;
	}

	public int deleteUser(UserVO userVO) {
		return 0;
	}

	
	public CurrUserVO getUser(UserVO userVO) {

		String sql = "Select userid, logon_name, passwd, Name, org_id, last_upd_date, roleid  From vw_curruser  Where logon_name='" +

		userVO.getLogon_name() +"' And passwd ='" +userVO.getPasswd() +"'";
		List<CurrUserVO> userVOList = this.getBaseDao().qryForObjList(sql, CurrUserVO.class);
		
		if((!userVOList.isEmpty())&&(userVOList.size()>0)){
			CurrUserVO userVo = userVOList.get(0);
			/**
			 * 修改为多角色
			 */
			List<String> roleLst = new ArrayList<String>();
			for(CurrUserVO vo:userVOList){
				roleLst.add(vo.getRoleid().toString());
			}
			userVo.setRole_id(roleLst);
			
			return userVo;
		}else{
			return null;
		}
	}

	public String getLogonUserRole(String userid){
		String sql = " SELECT CONCATSTR(r.role_name || ';') AS role_name "
			+"  FROM tb_sys_user_role ur,tb_sys_role r  WHERE ur.roleid = r.roleid "
			+" AND ur.userid="+ userid +" AND r.roleid NOT IN (6,7)";
		List<UserVO> lst = this.getBaseDao().qryForObjList(sql, UserVO.class);
		String role_name="";
		for(UserVO vo:lst)
			role_name = vo.getRole_name();
		return role_name;
	}
	
	public String getLogonUserOrgName(String userid,String auth_lvl,String latn_id){
		String org_name = "";
		String sql = "";
		if(null == auth_lvl ||"".equals(auth_lvl) || "8".equals(auth_lvl)){
			sql = " SELECT latn_name as org_name FROM tb_cde_detain_latn  t WHERE t.latn_id ="+latn_id;
			List<UserVO> lst = this.getBaseDao().qryForObjList(sql, UserVO.class);
			for(UserVO vo:lst)
				org_name = vo.getOrg_name();
			return org_name;
		}
		if("4".equals(auth_lvl) || "1".equals(auth_lvl)){
			sql = " SELECT o.org_name as org_name FROM tb_sys_user u ,tb_loc_org o "
				+ " WHERE u.site_no = o.org_id and u.userid ="+userid;
			List<UserVO> lst = this.getBaseDao().qryForObjList(sql, UserVO.class);
			for(UserVO vo:lst)
				org_name = vo.getOrg_name();
			return org_name;
		}
		if("7".equals(auth_lvl)){
			sql = " SELECT o.org_name as org_name FROM tb_sys_user u ,tb_loc_org o "
				+ " WHERE u.bureau_no = o.org_id and u.userid ="+userid;
			List<UserVO> lst = this.getBaseDao().qryForObjList(sql, UserVO.class);
			for(UserVO vo:lst)
				org_name = vo.getOrg_name();
			return org_name;
		}
		if("9".equals(auth_lvl)){
			return "省公司";
		}
		
		return org_name;
	}
	
	/**
	 * 记录登陆日志
	 * @param certUser
	 * @param logon_ip
	 * @param logon_name
	 */
	public void logUserDB(String staff_no,String name,String logon_ip,String logon_mode){
		String sql = " insert into TB_SYS_LOGON_LOG (LOGON_ID,STAFF_NO,NAME,LOGON_IP,LOGON_Date,LOGON_Mode) "
			+" values (SEQ_LOGON_LOG.nextval,'"+staff_no+"','"+name+"','"+logon_ip+"',sysdate,"+logon_mode+") ";
		this.getBaseDao().exeBySql(sql);
	}
	
	public int getUserCount(UserVO userVO) {
		//String sql = "select Count(1) retInt  from tb_sys_user t Where t.logon_name='" + userVO.getLogon_name() +"' And t.passwd ='" +userVO.getPasswd() +"'";
		//Object retobj = this.getBaseDao().qryForFieldValue(sql);
		//int retInt = Integer.parseInt(retobj.toString());
		System.out.println("-------------到达------------------");
		return 0;
		//return retInt;
	}

	public List<UserVO> getUserList(UserVO userVO) {
		return null;
	}

	public List<UserVO> getUserList(int start, int count, UserVO userVO) {
		StringBuffer sb = new StringBuffer();
		Map parmMap = new HashMap();
		parmMap.put("logon_name", userVO.getLogon_name());
		sb.append("select userid,logon_name,passwd,name,sex,email,org_id,user_cd,status_cd,last_upd_date,staff_no from Tb_Sys_User  where 1=1  ");
		sb.append(StringUtils.map2SqlStr(parmMap));
		Pager pager = this.qryForPager(start, PAGESIZE, sb.toString(), UserVO.class, "oracle");
		return pager.getResultList();
	}

	public int updateUser(UserVO userVO) {
		return 0;
	}
	

	public Pager browseUser(int pageNo, int pageSize, Map parmMap) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select userid,logon_name,passwd,name,sex,email,org_id,user_cd,status_cd,last_upd_date,staff_no from Tb_Sys_User  where 1=1  ");
		sb.append(StringUtils.map2SqlStr(parmMap));
		Pager pager = this.qryForPager(pageNo, pageSize, sb.toString(), UserVO.class, "oracle");
		return pager;
	}
}
