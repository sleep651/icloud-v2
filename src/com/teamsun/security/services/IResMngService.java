package com.teamsun.security.services;

import java.util.List;

import com.teamsun.core.domain.FrontPageVo;
import com.teamsun.security.domain.ResVO;
import com.teamsun.security.domain.RoleResVO;

public interface IResMngService {

	public int createResource(ResVO resVO);

	public int updateResource(ResVO resVO);

	public void deleteRoleResource(RoleResVO roleResVO);

	public List getResourceList(ResVO res);

	public List<ResVO> getResourceList(ResVO res, String treeType);

	/**
	 * 取首页面配置信息
	 * @param res
	 * @return
	 * @author ht
	 */
	public List<FrontPageVo> getFrontPageList(ResVO res);

}
