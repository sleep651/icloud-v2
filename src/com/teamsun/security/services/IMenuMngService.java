package com.teamsun.security.services;


public interface IMenuMngService {

	public String getMenuData(String id);

	public String getMenuData(String userId, String roleId) throws Exception;

}
