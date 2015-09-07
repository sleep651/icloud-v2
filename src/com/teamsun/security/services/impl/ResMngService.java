package com.teamsun.security.services.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.teamsun.core.domain.FrontPageVo;
import com.teamsun.core.services.BaseService;
import com.teamsun.core.utils.FileUtils;
import com.teamsun.core.utils.StringUtils;
import com.teamsun.security.domain.MenuPanelVO;
import com.teamsun.security.domain.ResVO;
import com.teamsun.security.domain.RoleResVO;
import com.teamsun.security.services.IResMngService;

@Component
public class ResMngService extends BaseService implements IResMngService{

	public int createResource(ResVO resVO) {
		String sql="";
		int retInt = this.baseDao.exeBySql(sql);
		return retInt;
	}

	public void deleteRoleResource(RoleResVO roleResVO) {
		String sql="";
		this.baseDao.exeBySql(sql);
		return;
	}

	public List getResourceList(ResVO res) {
		String sql="Select a.Id, a.title, a.treeid, a.roottext, a.url, a.roleid ,a.loadType "
			    +" From tb_sys_menu_panel a,"
			    +" (select  id from tb_sys_menu_panel where roleid in (" + res.getRole_id() +	")"
			    +"  ) b WHERE a.id=b.ID";
		List<MenuPanelVO> retList = this.baseDao.qryForObjList(sql, MenuPanelVO.class);
		return retList;
	}
	
	/**
	 * 取首页面配置信息
	 * @param res
	 * @return
	 * @author ht
	 */
	public List<FrontPageVo> getFrontPageList(ResVO res){
		String sql =" SELECT a.ID, a.title, a.url, a.columnwidth, a.style, a.roleid, a.pos,a.height "
				   +" FROM tb_sys_frontpage_panel a, "
				   +" ( select max(id) id from tb_sys_frontpage_panel where roleid in ("+res.getRole_id()+") group by url ) b"
			       +" where a.id = b.id "
			       +" ORDER BY a.pos ";
		List<FrontPageVo> lst = this.baseDao.qryForObjList(sql, FrontPageVo.class);
		return lst;
	}
	
	public int updateResource(ResVO resVO) {
		String sql = "";
		int retInt = baseDao.exeBySql(sql);
		return retInt;
	}

	public List<ResVO> getResourceList(ResVO res, String treeType) {
//		String sql = FileUtils.getSqlProperties(treeType);
		//systemtree=Select * From (Select menuid r_id,own_menuid p_rid, menu_name  r_name,menu_name r_desc ,isleaf  r_type, menuurl  linkaddress, seq_num r_order, status_cd status, roleid, menucls,userid From vw_user_role_func_menu  Where menucls ='1'  Order By r_order) vw Where  vw.p_rid = '?' And vw.roleid = '?' and userid='?'
		String sql ="SELECT a.R_ID,a.P_RID,a.R_NAME,a.R_DESC,a.R_TYPE,a.LINKADDRESS,a.R_ORDER,a.STATUS,a.ROLEID,a.MENUCLS,a.USERID from"
			+" (Select VW.*,row_number() over (partition by R_ID order by R_ID) rank From "
			+" (Select menuid r_id,own_menuid p_rid, menu_name  r_name,menu_name r_desc ,isleaf  r_type, "
			+" menuurl  linkaddress, seq_num r_order, status_cd status, roleid, menucls,userid "
			+" From vw_user_role_func_menu  Where menucls ='2' And Status_Cd ='1' Order By r_order) "
			+" vw Where  vw.p_rid = "+res.getP_rid()+" And vw.roleid in ("+res.getRole_id()+") and userid="+res.getUser_id()+" ) a WHERE a.rank =1 ";
		/*Object parms[] = new Object[3];//有二个？号要替换
		parms[0] = res.getP_rid();
		parms[1] = res.getRole_id();
		parms[2] = res.getUser_id();
		sql = StringUtils.replaceSqlByArray(sql, "?", parms);*/
		logger.debug("============================"+sql);
		List<ResVO> retList = this.baseDao.qryForObjList(sql, ResVO.class);
		return retList;

	}

}
