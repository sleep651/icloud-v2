/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.security.action;
import java.util.List;

import com.teamsun.core.action.BaseAction;
import com.teamsun.security.domain.OrgVO;


/**
 * CPCIM
 * @author Administrator
 * @since Sep 16, 2008
 * @version 2.0 
 * TODO:机构管理操作
 */
public class OrgMngAction extends BaseAction {
	private String menuString;
	private String jsonString;
	private String parent_id;
	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getMenuString() {
		return menuString;
	}

	public void setMenuString(String menuString) {
		this.menuString = menuString;
	}

	public String browse()	throws Exception {

/*		int pageNo = 1;
		int pageSize = 15;
		String cateNameForQuery = "";

		String tcateNameForQuery = getRequest().getParameter("cateNameForQuery");*/

		return "list";

	}

	public String browseOrg() throws Exception {

		/*		int pageNo = 1;
				int pageSize = 15;
				String cateNameForQuery = "";
				String parentOid = getRequest().getParameter("parentOid");

				String tcateNameForQuery = getRequest().getParameter("cateNameForQuery");

				String tPageNo = getRequest().getParameter("pageNo");*/

				return SUCCESS;

			}
	public String browseChildOrgs() throws Exception {

/*		int pageNo = 1;
		int pageSize = 15;
		String cateNameForQuery = "";
		String parentOid = getRequest().getParameter("parentOid");

		String tcateNameForQuery = getRequest().getParameter("cateNameForQuery");

		String tPageNo = getRequest().getParameter("pageNo");*/

		return "childOrgList";

	}

	public String gotoAddUserOrg() throws Exception {
		logger.debug(" == in UserOrgAction.gotoAddUserOrg == ");

		return "gotoAddUserOrg";
	}

	public String gotoAddChildOrg() throws Exception {
		logger.debug(" == in UserOrgAction.gotoAddChildOrg == ");


		return "gotoAddChildOrg";
	}

	public String gotoAddBrotherOrg() throws Exception {
		logger.debug(" == in UserOrgAction.gotoAddBrotherOrg == ");



		return "gotoAddBrotherOrg";
	}

	public String addUserOrg()	throws Exception {
		logger.debug(" == in UserOrgAction.addUserOrg == ");

		return "browse";

	}

	public String addChildOrg()throws Exception {
		logger.debug(" == in UserOrgAction.addChildOrg == ");

		//String parentOid = getRequest().getParameter("parentOid");



		return "browseChildOrg";
	}

	public String gotoUpdateUserOrg() throws Exception {
		logger.debug(" == in UserOrgAction.gotoUpdateUserOrg == ");
		//String userOrgOid = getRequest().getParameter("oid");
		return "gotoUpdateUserOrg";
	}

	public String updateUserOrg()	throws Exception {
		logger.debug(" == in UserOrgAction.updateUserOrg == ");

		if ("1".equals("1")) {
			return "brow";
		} else {
			return "browseChildOrg";
		}

	}

	public String deleteUserOrg()throws Exception {
		logger.debug(" == in UserOrgAction.deleteUserOrg == ");

    	if ("1".equals("1")) {
			return "brow";
		} else {
			return "browseChildOrg";
		}

	}
    public String getOrgTrees() throws Exception{
    	StringBuffer sb = new StringBuffer();
/*    	sb.append("[{id:'leaf11',iconCls:'myicon',text:'数据表格',singleClickExpand:true,leaf:true,draggable:false,href:'monitor/browseIndicator.action'},");
    	sb.append("{id:'leaf12',iconCls:'icon-docs',text:'表单提交',singleClickExpand:true,leaf:true,draggable:false,href:'http://www.google.cn'},        ");
    	sb.append("{id:'leaf13',iconCls:'icon-docs',text:'系统管理',singleClickExpand:true,leaf:false,draggable:false,href:'http://www.google.cn'},       ");
    	sb.append("{id:'html',text:'静态页面',leaf:true}]                                                                                                 ");
*/    	
    	
    	sb.append("[{'cls':'folder','id':10,'leaf':false,checked:false,'children':[{'cls':'file','id':11,'leaf':true,checked:false,'children':null,'text':'S600'},{'cls':'file','id':12,'leaf':true,checked:false,'children':null,'text':'SLK200'}],'text':'Benz'}]");
    	menuString = sb.toString();
    	
    	return SUCCESS;
    	
    }
    

	public String getOrgChildren() throws Exception{
    	String sql ="select to_char(org_id) id , org_name name, post_cd code, status_cd phone , to_char(seq_nm) remark  from tb_sys_org";
    	List resList = getBaseService().qryForObjList(sql, OrgVO.class);
    	jsonString = listToJsonString(resList);
    	
		return SUCCESS;
    }

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

    }
