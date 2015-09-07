package com.apps.mobile.domain;

public class UserAccount {
	private String user_id;//用户ID
	private String phone_number;//手机号码
	private String user_name;	//名字
	private String org_id;		//所属区域ID
	private String org_name;	//所属区域
	private String imsi;		//手机串号
	private boolean is_use_cache;//数据读取模式（否：始终通过接口读取；是：缓存机制读取）
	//二期修改
	private String password;	//登录密码
	private String user_acct;	//登录用户名
	private String role_id;     //用户角色Id
	private String ticket;		//用户登陆成功后，获得的访问会话的ticket，调用功能接口需要填写该参数；【重要】
    private String   pos_org_id ;//机构标识_身份
    private String   pos_org_name;//机构名称_身份
    private String   imei;//手机序列号
    private String   pattern_code;//手势码
    private String   status;//在用状态
    private String   login_type;
	
	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public boolean isIs_use_cache() {
		return is_use_cache;
	}

	public void setIs_use_cache(boolean is_use_cache) {
		this.is_use_cache = is_use_cache;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_acct() {
		return user_acct;
	}

	public void setUser_acct(String user_acct) {
		this.user_acct = user_acct;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getPos_org_id() {
		return pos_org_id;
	}

	public void setPos_org_id(String pos_org_id) {
		this.pos_org_id = pos_org_id;
	}

	public String getPos_org_name() {
		return pos_org_name;
	}

	public void setPos_org_name(String pos_org_name) {
		this.pos_org_name = pos_org_name;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPattern_code() {
		return pattern_code;
	}

	public void setPattern_code(String pattern_code) {
		this.pattern_code = pattern_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogin_type() {
		return login_type;
	}

	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
}
