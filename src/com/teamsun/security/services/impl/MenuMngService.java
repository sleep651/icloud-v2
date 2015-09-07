package com.teamsun.security.services.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Component;

import com.teamsun.core.services.BaseService;
import com.teamsun.core.utils.StringUtils;
import com.teamsun.security.domain.NodeVO;
import com.teamsun.security.services.IMenuMngService;

@Component
public class MenuMngService extends BaseService implements IMenuMngService{
	
  	public String getMenuData(String nodeId) {
		String menuString = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct t.menuid id,                                      ");
		sb.append("t.menu_name text,                                                 ");
		sb.append("decode(t.isleaf, '1', 'true', '0', 'false') isleaf,               ");
		sb.append("decode(t.isleaf, '1', 'file', '0', 'folder') cls,                 ");
		sb.append("t.menuurl href,                                                   ");
		sb.append("t.menutarget hreftarget  from vw_user_role_func_menu t where 1 = 1 ");
		if ((StringUtils.isEmpty(nodeId)) || ("root".equals(nodeId))) {
			sb.append(" and t.userid =1 and t.own_menuid IS NULL            ");
		} else {
			sb.append(" and  t.own_menuid = '" + nodeId + "'  ");
		}
		logger.info("生成目录树的SQL："+sb.toString());
		List<NodeVO> nodes = getBaseDao().qryForObjList(sb.toString(), NodeVO.class);

		List nodeVOList = new ArrayList<NodeVO>();
		/*
		 * 数据类型转换,主要是oracle 中无法直接获得 boolean 类型
		 */
		for (NodeVO nodeVO : nodes) {
			
			NodeVO nodevonew = new NodeVO();
			nodevonew.setId(nodeVO.getId());
			nodevonew.setText(nodeVO.getText());
			nodevonew.setCls(nodeVO.getCls());
			boolean retleaf = ("true".equals(nodeVO.getIsleaf()))? true:false;
			nodevonew.setLeaf(retleaf);
			if (null != nodeVO.getHref()) {
				if (nodeVO.getHref().indexOf("?") == -1) {
					nodevonew.setHref(nodeVO.getHref() + "?menuid=" + nodeVO.getId());
				} else {
					nodevonew.setHref(nodeVO.getHref() + "&menuid=" + nodeVO.getId());
				}
			} else {
				nodevonew.setHref("");
			}
			nodeVOList.add(nodevonew);
		}
		JSONArray jsonObject = JSONArray.fromObject(nodeVOList);

		try {
			menuString = jsonObject.toString();
		} catch (Exception e) {
			menuString = "the treedate is null!";
			logger.info(menuString);
		}

		return menuString;
	}

	public String getMenuData(String roleId, String nodeId) throws Exception { 
		String menuString = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct t.menuid id,                                      ");
		sb.append("t.menu_name text,                                                 ");
		sb.append("decode(t.isleaf, '1', 'true', '0', 'false') isleaf,               ");
		sb.append("decode(t.isleaf, '1', 'file', '0', 'folder') cls,                 ");
		sb.append("t.menuurl href,                                                   ");
		sb.append("t.menutarget hreftarget  from vw_user_role_func_menu t where 1 = 1 ");
		if ((StringUtils.isEmpty(nodeId)) || ("root".equals(nodeId))) {
			sb.append(" and t.roleid = '" +	roleId +"' and t.own_menuid IS NULL      ");
		} else {
			sb.append(" and t.roleid = '" +	roleId + "' and t.own_menuid = '" + nodeId + "'  ");
		}
		//logger.info("生成目录树的SQL："+sb.toString());
		List<NodeVO> nodes = getBaseDao().qryForObjList(sb.toString(), NodeVO.class);

		List nodeVOList = new ArrayList<NodeVO>();
		/*
		 * 数据类型转换,主要是oracle 中无法直接获得 boolean 类型
		 */
		for (NodeVO nodeVO : nodes) {
			NodeVO nodevonew = new NodeVO();
			nodevonew.setId(nodeVO.getId());
			nodevonew.setText(nodeVO.getText());
			nodevonew.setCls(nodeVO.getCls());
			boolean retleaf = ("true".equals(nodeVO.getIsleaf()))? true:false;
			nodevonew.setLeaf(retleaf);
			if (null != nodeVO.getHref()) {
				if (nodeVO.getHref().indexOf("?") == -1) {
					nodevonew.setHref(nodeVO.getHref() + "?nodeId=" + nodeVO.getId());
				} else {
					nodevonew.setHref(nodeVO.getHref() + "&nodeId=" + nodeVO.getId());
				}
			} else {
				nodevonew.setHref("");
			}
			nodeVOList.add(nodevonew);
		}
		//======================================================
		//=======================================================
		
		
		JSONArray jsonObject = JSONArray.fromObject(nodeVOList);

		try {
			menuString = jsonObject.toString();
		} catch (Exception e) {
			menuString = "the treedate is null!";
			logger.info(menuString);
			
		}
		return menuString;
	}

}
