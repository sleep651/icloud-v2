package com.apps.mobile.service;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jws.WebParam;
import javax.jws.WebService;

import oracle.sql.CLOB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.apps.common.dao.TaskDao;
import com.apps.common.dao.TaskDao_DB2;
import com.apps.common.utils.CommonUtil;
import com.apps.common.utils.CryptUtil;
import com.apps.common.utils.WsConstants;
import com.apps.mobile.domain.ChartCondition;
import com.apps.mobile.domain.ChartConfig;
import com.apps.mobile.domain.ChartDim;
import com.apps.mobile.domain.ChartTitle;
import com.apps.mobile.domain.ChartType;
import com.apps.mobile.domain.ClientVersionBean;
import com.apps.mobile.domain.Customization;
import com.apps.mobile.domain.DataDateBean;
import com.apps.mobile.domain.HomeReportBean;
import com.apps.mobile.domain.InvalidTicketException;
import com.apps.mobile.domain.LhxcTradeDay;
import com.apps.mobile.domain.LhxcTradeMon;
import com.apps.mobile.domain.LhxcZiYou;
import com.apps.mobile.domain.MarketBean;
import com.apps.mobile.domain.MarketGroupBean;
import com.apps.mobile.domain.MenuBean;
import com.apps.mobile.domain.ReportBean;
import com.apps.mobile.domain.ResponseEmptyProperty;
import com.apps.mobile.domain.ResponseProperty;
import com.apps.mobile.domain.ResponsePropertyList;
import com.apps.mobile.domain.LhxcSheHui;
import com.apps.mobile.domain.TendencyMap;
import com.apps.mobile.domain.Ticket;
import com.apps.mobile.domain.TicketVO;
import com.apps.mobile.domain.UserAccount;

@Component
@WebService(serviceName = "MobileService", endpointInterface = "com.apps.mobile.service.IMobileService")
public class MobileService implements IMobileService {
	private static final Logger logger = LoggerFactory
			.getLogger(MobileService.class);

	@Autowired
	private TaskDao taskDao;
	@Autowired
	private TaskDao_DB2 taskDao_DB2;

	CryptUtil crypt = CryptUtil.getInstance();

	public ResponseEmptyProperty testNoSql(String callTime) {
		return new ResponseEmptyProperty(WsConstants.SHT_SUCCESS, "成功："
				+ callTime);
	}

	public ResponseEmptyProperty testSimpleSql(String callTime) {
		try {
			Integer count = taskDao.get("mobile.testSimpleSql", null);
			if (count == 1) {
				return new ResponseEmptyProperty(WsConstants.SHT_SUCCESS, "成功:"
						+ callTime);
			} else {
				return new ResponseEmptyProperty(WsConstants.SHT_VALIDATION,
						"失败:" + callTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEmptyProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	public ResponseEmptyProperty testSimpleSql_DB2(String callTime) {
		try {
			Integer count = taskDao_DB2.get("mobile_DB2.testSimpleSql_DB2",
					null);
			return new ResponseEmptyProperty(WsConstants.SHT_SUCCESS,
					"成功:ret=[" + count + "],callTime=" + callTime);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEmptyProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	public ResponseProperty<ClientVersionBean> isVersionAvailable(
			String clientVersion) {
		try {
			HashMap<String, String> params = new HashMap<String, String>();
	
			params.put("clientVersion", clientVersion);
			
			Integer count = taskDao.get("mobile.isVersionAvailable", params);
			// ///////////1.判断当前版本号是否有效（支持多个有效版本）
			if (count == 0) {// 需要更新
				return new ResponseProperty<ClientVersionBean>(
						WsConstants.SHT_VALIDATION, "版本过期，需要下载新版本",
						getLastVersion());
			} else {// 已经是可用版本
				return new ResponseProperty<ClientVersionBean>(
						WsConstants.SHT_SUCCESS, "当前版本可用，不需要下载新版本",
						getLastVersion());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty<ClientVersionBean>(
					WsConstants.SHT_ERROR, "服务端异常:" + e.toString());
		}
	}

	

	public ResponseProperty<ClientVersionBean> isVersionAvailableNew(
			String clientVersion,String packageName) {
		try {
			HashMap<String, String> params = new HashMap<String, String>();
			if(null==packageName&&packageName.equals("")){
				return new ResponseProperty<ClientVersionBean>(
						WsConstants.SHT_ERROR, "packageName不能为空");	
			}
			params.put("clientVersion", clientVersion);
			params.put("package_name", packageName);
			
			Integer count = taskDao.get("mobile.isVersionAvailableNew", params);
			// ///////////1.判断当前版本号是否有效（支持多个有效版本）
			if (count == 0) {// 需要更新
				return new ResponseProperty<ClientVersionBean>(
						WsConstants.SHT_VALIDATION, "版本过期，需要下载新版本",
						getLastVersion());
			} else {// 已经是可用版本
				return new ResponseProperty<ClientVersionBean>(
						WsConstants.SHT_SUCCESS, "当前版本可用，不需要下载新版本",
						getLastVersion());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty<ClientVersionBean>(
					WsConstants.SHT_ERROR, "服务端异常:" + e.toString());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	public ResponseProperty<String> isRegist(String imsiCode, String imeiCode,
			String systemVersion, String clientVersion) {
		logger.debug("debug_for_wxy:call isRegist(" + imsiCode + "," + imeiCode
				+ "," + systemVersion + "," + clientVersion + ")");
		try {
			// /////////////0.解密加密参数(imeiCode,imsiCode)
			CryptUtil crypt = CryptUtil.getInstance();
			if (imeiCode != null) {
				imeiCode = crypt.decryptAES(imeiCode, crypt.getKey());
			}
			if (imsiCode != null) {
				imsiCode = crypt.decryptAES(imsiCode, crypt.getKey());
			}

			ResponseProperty<String> response = new ResponseProperty<String>();
			TicketVO ticket = new TicketVO();
			if (imsiCode != null && !imsiCode.trim().equals("")
					&& imeiCode != null && !imeiCode.trim().equals("")) {
				UserAccount userAccount = isCodeLogonSuccess(imsiCode,
						imeiCode, null, null);
				// ///////////2.imsi是否已注册
				if (userAccount != null) {
					// ///////////3.imsi+密码是否正确
					ticket.setTicket(Ticket.getTicketNumber(userAccount));
					ticket.setAccount(userAccount);
					userAccount.setTicket(ticket.getTicket().toString());
					response.setStatus(WsConstants.SHT_SUCCESS);
					response.setMessage("IMSI和IMEI已注册");
				} else {
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("clientVersion", clientVersion);
					params.put("imeiCode", imeiCode);
					params.put("imsiCode", imsiCode);
					Integer count = taskDao.get("mobile.isApplyForRegist",
							params);
					// 查询，是否已经申请注册
					if (count == 0) {// 未申请注册
						response.setStatus(WsConstants.SHT_NO_SESSION);
						response.setMessage("IMSI和IMEI未注册,未申请注册！");
					} else {// 已经申请注册
						response.setStatus(WsConstants.SHT_VALIDATION);
						response.setMessage("IMSI和IMEI未注册,已经申请注册");
					}
				}
				// 如果未注册，则写入检查日志
				if (response.getStatus() != 0) {
					// 写入检测日志
					// String queryId = (String)
					// taskDao.getSqlMapClientTemplate().queryForObject("mobile.getNewRegistCheckLogId");
					String queryId = UUID.randomUUID().toString().trim()
							.replaceAll("-", "");
					new InsertRegistCheckLogThread(queryId, imsiCode, imeiCode,
							systemVersion, clientVersion, ""
									+ response.getStatus(),"","","","pak_name").start();
					response.setEntity(queryId);
				}
			} else {
				response.setStatus(WsConstants.SHT_ERROR);
				response.setMessage("IMSI和IMEI未注册：IMSI、IMEI都不能为空！");
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	/**
	 * 申请注册备份
	 */

	/*
	 * public ResponseEmptyProperty applyForRegist(String queryId,String
	 * phone_number,String user_name){ try { HashMap<String,String> params = new
	 * HashMap<String,String>(); params.put("id", queryId);
	 * params.put("phone_number", phone_number); params.put("user_name",
	 * user_name); int ret =
	 * taskDao.getSqlMapClientTemplate().update("mobile.applyForRegist",params);
	 * if(ret>0){ return new ResponseEmptyProperty(WsConstants.SHT_SUCCESS,
	 * "申请注册成功！"); } else{ return new
	 * ResponseEmptyProperty(WsConstants.SHT_VALIDATION, "申请注册失败！（原始密码错误）"); } }
	 * catch (Exception e) { e.printStackTrace(); return new
	 * ResponseEmptyProperty(WsConstants.SHT_ERROR, "服务端异常:" + e.toString()); }
	 * }
	 */

	/**
	 * 二期 申请注册修改 7月3日修改，新增一个参数"包名(pak_name)",由手机端传递过来并保存在数据库中。
	 */
	public ResponseEmptyProperty applyForRegist(String posorgid,
			String phone_number, String user_name, String imsiCode,
			String imeiCode, String systemVersion, String clientVersion,
			String pak_name) {
		try {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("posorgid", posorgid);
			params.put("phone_number", phone_number);
			params.put("user_name", user_name);
			params.put("pak_name", pak_name);
			if (phone_number != null && !phone_number.trim().equals("")) {
				int count = 0;
				count = taskDao.get("mobile.testApplyUser", params);
				if (count == 0) {
					count = taskDao.get("mobile.testApplyForRegist", params);
					if (count == 0) {
						String queryId = UUID.randomUUID().toString().trim()
								.replaceAll("-", "");

						new InsertRegistCheckLogThread(queryId, imsiCode,
								imeiCode, systemVersion, clientVersion, ""
										+ "-1", phone_number, user_name,
								posorgid, pak_name).start();
						return new ResponseEmptyProperty(
								WsConstants.SHT_SUCCESS, "注册成功，请等待审核！");
					} else {
						params.put("imsiCode", imsiCode);
						params.put("imeiCode", imeiCode);
						params.put("systemVersion", systemVersion);
						params.put("clientVersion", clientVersion);
						int ret = taskDao.getSqlMapClientTemplate().update(
								"mobile.applyForRegist", params);
						if (ret > 0) {
							return new ResponseEmptyProperty(
									WsConstants.SHT_ERROR, "您之前已经注册，请等待审核！");
						} else {
							return new ResponseEmptyProperty(
									WsConstants.SHT_ERROR, "申请注册失败!");
						}
					}
				} else {
					return new ResponseEmptyProperty(
							WsConstants.SHT_VALIDATION, "您已经通过审核，请直接登录！");
				}

			} else {
				return new ResponseEmptyProperty(WsConstants.SHT_VALIDATION,
						"区域编号,电话号码,用户姓名不能为空!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEmptyProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	/**
	 * 登录备份
	 */
	/*
	 * @SuppressWarnings({ "unchecked", "rawtypes", "static-access" }) public
	 * ResponseProperty<UserAccount> login(String password, String imsiCode,
	 * String imeiCode, String systemVersion, String clientVersion) { try {
	 * logger
	 * .debug("debug_for_wxy:call login("+password+","+imsiCode+","+imeiCode
	 * +","+systemVersion+","+clientVersion+")");
	 * ///////////////0.解密加密参数(password,imeiCode,imsiCode) CryptUtil crypt =
	 * CryptUtil.getInstance(); if(password != null) { password =
	 * crypt.decryptAES(password, crypt.getKey()); } if(imeiCode!=null) {
	 * imeiCode = crypt.decryptAES(imeiCode, crypt.getKey()); }
	 * if(imsiCode!=null) { imsiCode = crypt.decryptAES(imsiCode,
	 * crypt.getKey()); }
	 * 
	 * ResponseProperty<UserAccount> response = new
	 * ResponseProperty<UserAccount>();
	 * 
	 * TicketVO ticket = new TicketVO(); if(imsiCode!=null &&
	 * !imsiCode.trim().equals("") && imeiCode!=null &&
	 * !imeiCode.trim().equals("") && password != null &&
	 * !password.trim().equals("")) { UserAccount userAccount =
	 * isCodeLogonSuccess(imsiCode,imeiCode,password); /////////////2.imsi是否已注册
	 * if (userAccount!=null) { /////////////3.imsi+密码是否正确
	 * ticket.setTicket(Ticket.getTicketNumber(userAccount));
	 * ticket.setAccount(userAccount);
	 * userAccount.setTicket(ticket.getTicket().toString());
	 * response.setStatus(WsConstants.SHT_SUCCESS);
	 * response.setMessage("登录成功!"); response.setEntity(userAccount);
	 * //数据读取模式：1始终通过接口读取；2缓存机制读取 String date_read_mode = (String)
	 * taskDao.getSqlMapClientTemplate
	 * ().queryForObject("mobile.getDateReadModeConfig");
	 * if(date_read_mode!=null&&date_read_mode.equals("2")){
	 * userAccount.setIs_use_cache(true); }else{
	 * userAccount.setIs_use_cache(false); } } else {
	 * response.setStatus(WsConstants.SHT_VALIDATION);
	 * response.setMessage("登录失败：密码错误！"); } } else {
	 * response.setStatus(WsConstants.SHT_VALIDATION);
	 * response.setMessage("登录失败：IMSI、IMEI和密码都不能为空！"); } // 写入登陆日志 new
	 * InsertLogonLogThread(password, imsiCode, imeiCode, systemVersion,
	 * clientVersion, "" + response.getStatus()).start(); return response; }
	 * catch (Exception e) { e.printStackTrace(); return new
	 * ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:" + e.toString()); } }
	 */

	/**
	 * 二期修改新登录
	 * 7月23日修改，新增一个参数"包名(package_name)",由手机端传递过来验证用户身份。
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	public ResponseProperty<UserAccount> login(String password,
			String user_acct, String patterncode, String package_name) {
		UserAccount userAccount=new UserAccount();
		try {
			logger.debug("debug_for_wxy:call login(" + password + ","
					+ user_acct + "," + patterncode + ")");
			// /////////////0.解密加密参数(password,imeiCode,imsiCode)
			// 登录密码解密
			// if(password != null) {
			// password = crypt.decryptAES(password, crypt.getKey());
			// }
			CryptUtil crypt = CryptUtil.getInstance();

			ResponseProperty<UserAccount> response = new ResponseProperty<UserAccount>();

			TicketVO ticket = new TicketVO();
			
			if (password != null && !password.trim().equals("")) {
				userAccount = isCodeLogonSuccess(user_acct,
						password, patterncode, package_name);
				// ///////////2.imsi是否已注册
				if (userAccount != null) {
					// ///////////3.imsi+密码是否正确
					ticket.setTicket(Ticket.getTicketNumber(userAccount));
					ticket.setAccount(userAccount);
					userAccount.setTicket(ticket.getTicket().toString());
					response.setStatus(WsConstants.SHT_SUCCESS);
					response.setMessage("登录成功!");
					response.setEntity(userAccount);
					// 数据读取模式：1始终通过接口读取；2缓存机制读取
					String date_read_mode = (String) taskDao
							.getSqlMapClientTemplate().queryForObject(
									"mobile.getDateReadModeConfig");
					if (date_read_mode != null && date_read_mode.equals("2")) {
						userAccount.setIs_use_cache(true);
					} else {
						userAccount.setIs_use_cache(false);
					}
					new InsertLogonLogThread(userAccount.getUser_acct(), userAccount.getPattern_code(),userAccount.getPassword(),response.getStatus().toString()).start();
					
				} else {
					response.setStatus(WsConstants.SHT_VALIDATION);
					response.setMessage("登录失败：密码错误！");
				}
			} else {
				response.setStatus(WsConstants.SHT_VALIDATION);
				response.setMessage("登录失败：用户名或密码不能为空！");
			}
			// 写入登录日志
			//new InsertLogonLogThread(password,userAccount.getPattern_code(),userAccount.getLogin_type() ,response.getStatus()).start();
		return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	public ResponseEmptyProperty logout(String ticket) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				Ticket.destoryTicket(Long.valueOf(ticket));
				return new ResponseEmptyProperty(WsConstants.SHT_SUCCESS,
						"成功退出");
			} else {
				return new ResponseEmptyProperty(WsConstants.SHT_NO_SESSION,
						"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEmptyProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	// 修改密码备份
	/*
	 * @SuppressWarnings("static-access") public ResponseEmptyProperty
	 * updatePattern(String ticket, String oldPassword, String newPassword) {
	 * try { int ret = 0;//返回值：0修改失败（原始密码错误），1修改成功，-1无效的ticket UserAccount
	 * userAccount = checkTicket(ticket); if (userAccount != null) { if
	 * (oldPassword != null) { oldPassword = crypt.decryptAES(oldPassword,
	 * crypt.getKey()); } if (newPassword != null) { newPassword =
	 * crypt.decryptAES(newPassword, crypt.getKey()); }
	 * 
	 * HashMap<String, String> params = new HashMap<String, String>();
	 * params.put("user_id", userAccount.getUser_id());
	 * params.put("oldPassword", oldPassword); params.put("newPassword",
	 * newPassword); ret = taskDao.getSqlMapClientTemplate().update(
	 * "mobile.updatePattern", params); if (ret > 0) { return new
	 * ResponseEmptyProperty(WsConstants.SHT_SUCCESS, "修改手势码成功！"); } else {
	 * return new ResponseEmptyProperty(WsConstants.SHT_VALIDATION,
	 * "修改手势码失败！（原始密码错误）"); } } else { return new
	 * ResponseEmptyProperty(WsConstants.SHT_NO_SESSION, "无效的ticket:ticket=" +
	 * ticket); } } catch (DataAccessException e) { e.printStackTrace(); return
	 * new ResponseEmptyProperty(WsConstants.SHT_ERROR, "服务端异常:" +
	 * e.toString()); } }
	 */
	// 二期修改 修改密码
	@SuppressWarnings("static-access")
	public ResponseEmptyProperty updatePassword(String ticket,
			String newPassword) {
		try {
			int ret = 0;// 返回值：0修改失败（原始密码错误），1修改成功，-1无效的ticket
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				// if (newPassword != null) {
				// newPassword = crypt.decryptAES(newPassword, crypt.getKey());
				// }

				HashMap<String, String> params = new HashMap<String, String>();
				params.put("user_id", userAccount.getUser_id());
				params.put("newPassword", newPassword);
				ret = taskDao.getSqlMapClientTemplate().update(
						"mobile.updatePassword", params);
				if (ret > 0) {
					return new ResponseEmptyProperty(WsConstants.SHT_SUCCESS,
							"修改密码成功！");
				} else {
					return new ResponseEmptyProperty(
							WsConstants.SHT_VALIDATION, "修改密码失败！");
				}
			} else {
				return new ResponseEmptyProperty(WsConstants.SHT_NO_SESSION,
						"无效的ticket:ticket=" + ticket);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return new ResponseEmptyProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	// 二期修改 修改手势码
	@SuppressWarnings("static-access")
	public ResponseEmptyProperty updatePattern(String ticket, String newPassword) {
		try {
			int ret = 0;// 返回值：0修改失败（原始密码错误），1修改成功，-1无效的ticket
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				if (newPassword != null) {
					newPassword = crypt.decryptAES(newPassword, crypt.getKey());
				}

				HashMap<String, String> params = new HashMap<String, String>();
				params.put("user_id", userAccount.getUser_id());
				params.put("newPassword", newPassword);
				ret = taskDao.getSqlMapClientTemplate().update(
						"mobile.updatePattern", params);
				if (ret > 0) {
					return new ResponseEmptyProperty(WsConstants.SHT_SUCCESS,
							"修改手势码成功！");
				} else {
					return new ResponseEmptyProperty(
							WsConstants.SHT_VALIDATION, "修改手势码失败！");
				}
			} else {
				return new ResponseEmptyProperty(WsConstants.SHT_NO_SESSION,
						"无效的ticket:ticket=" + ticket);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return new ResponseEmptyProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponsePropertyList<DataDateBean> getDataDateList() {
		try {
			return new ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
					taskDao.getSqlMapClientTemplate().queryForList(
							"mobile.getDataDateList", null));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponsePropertyList<HomeReportBean> getHomeReportList(String ticket) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("user_id", userAccount.getUser_id());
				List<HomeReportBean> list = taskDao.getSqlMapClientTemplate()
						.queryForList("mobile.getHomeReportList", params);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						HomeReportBean b = list.get(i);
						String repDateSql = b.getMax_date();
						String data_source = b.getData_source();
						// 构造是否下钻sql
						params.put("sql", repDateSql);

						String max_date = "";
						if ("ORA1".equals(data_source)) {
							max_date = (String) taskDao
									.getSqlMapClientTemplate().queryForObject(
											"mobile.getDynamicRepOfString",
											params);
						} else if ("DB2".equals(data_source)) {
							max_date = (String) taskDao_DB2
									.getSqlMapClient_DB2().queryForObject(
											"mobile_DB2.getDynamicRepOfString",
											params);
						}
						if (max_date == null || "".equals(max_date)) {
							max_date = CommonUtil.getSomeday(-2);
						}
						b.setMax_date(max_date);
					}
				}
				return new ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
						list);
			} else {
				return new ResponsePropertyList(WsConstants.SHT_NO_SESSION,
						"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseProperty<ReportBean> getHomeReport(String ticket,
			String repId, String orgId, String statDate, String posorgId) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				// 根据报表ID，获取报表数据sql
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("rep_id", repId);
				List<HashMap> confList = taskDao.getSqlMapClientTemplate()
						.queryForList("mobile.getHomeRepConfList", params);
				if (confList != null && confList.size() != 0) {
					HashMap confMap = confList.get(0);
					// String repSql = (String)confMap.get("REP_SQL");//报表数据sql
					// String repSql =
					// StringUtils.converCLOBToString((CLOB)confMap.get("REP_SQL"));
					CLOB clob = (CLOB) confMap.get("REP_SQL");
					String repSql = clob.getSubString(1, (int) clob.length());
					String repHeadSql = (String) confMap.get("REP_HEAD_SQL");// 报表表头sql
					String repGoinSql = (String) confMap.get("REP_GOIN_SQL");// 报表是否下钻sql（大于0就可以下钻）

					String data_source = (String) confMap.get("DATA_SOURCE");

					// 构造表头sql
					repHeadSql = repHeadSql.replace("$rep_id$", repId);
					params.put("sql", repHeadSql);
					List<String> titleList = taskDao.getSqlMapClientTemplate()
							.queryForList("mobile.getDynamicRepOfString",
									params);

					// 构造是否下钻sql
					repGoinSql = repGoinSql.replace("$org_id$", orgId);
					params.put("sql", repGoinSql);
					Integer childNum = 0;
					if ("ORA1".equals(data_source)) {
						childNum = (Integer) taskDao
								.getSqlMapClientTemplate()
								.queryForObject(
										"mobile.getDynamicRepOfInteger", params);
					} else if ("DB2".equals(data_source)) {
						childNum = (Integer) taskDao_DB2.getSqlMapClient_DB2()
								.queryForObject(
										"mobile_DB2.getDynamicRepOfInteger",
										params);
					}
					// 构造数据sql（默认数据列名，按照column1,column2,column3...规则编码）
					repSql = repSql.replace("$org_id$", orgId);
					repSql = repSql.replace("$stat_date$", statDate);
					repSql = repSql.replace("$pos_org_id$", posorgId);
					params.put("sql", repSql);

					/*
					 * String oneSql =
					 * (String)taskDao.getSqlMapClientTemplate().
					 * queryForObject("mobile.getDynamicRepOfString",params);
					 * oneSql = oneSql.replace("$org_id$", orgId); oneSql =
					 * oneSql.replace("$stat_date$", statDate);
					 * params.put("sql", oneSql);
					 */

					List dataList = new ArrayList();
					HashMap hashMap = new HashMap();
					List sqlRetList = null;
					if ("ORA1".equals(data_source)) {
						sqlRetList = taskDao.find(
								"mobile.getDynamicRepOfHashMap", params);
					} else if ("DB2".equals(data_source)) {
						sqlRetList = taskDao_DB2.find(
								"mobile_DB2.getDynamicRepOfInteger", params);
					}
					for (int i = 0; i < sqlRetList.size(); i++) {
						hashMap = (HashMap) sqlRetList.get(i);
						List<String> rowList = new ArrayList<String>();
						for (int j = 1; j <= hashMap.size(); j++) {
							rowList.add(mapToString(hashMap, "C" + j));
						}
						dataList.add(rowList);
					}

					// 构造返回值
					ReportBean reportBean = new ReportBean();
					reportBean.setTitle(titleList);
					reportBean.setDataList(dataList);
					if (childNum > 0) {
						reportBean.setHasChild(true);
					} else {
						reportBean.setHasChild(false);
					}
					return new ResponseProperty<ReportBean>(
							WsConstants.SHT_SUCCESS, "成功", reportBean);
				} else {
					return new ResponseProperty<ReportBean>(
							WsConstants.SHT_VALIDATION, "没有当前报表的配置信息，请联系管理员！");
				}
			} else {
				return new ResponseProperty(WsConstants.SHT_NO_SESSION,
						"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	/**
	 * 
	 */
	/*******************************************************/
	// 原有菜单备份
	/*
	 * public ResponsePropertyList<MenuBean> getMenuList(String ticket, String
	 * tab_id) { try { UserAccount userAccount = checkTicket(ticket); if
	 * (userAccount != null) { HashMap<String, String> params = new
	 * HashMap<String, String>(); params.put("tab_id", tab_id); return new
	 * ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
	 * taskDao.getSqlMapClientTemplate().queryForList( "mobile.getMenuList",
	 * params)); } else { return new
	 * ResponsePropertyList(WsConstants.SHT_NO_SESSION, "无效的ticket:ticket=" +
	 * ticket); } } catch (Exception e) { e.printStackTrace(); return new
	 * ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:" + e.toString()); } }
	 */
	/**************************************************************************/
	// 二期修改:新菜单
	/*
	 * 7月1日再修改，获取菜单需要提供四个参数：ticket，tab_id，super_menu。均由手机端传递过来。
	 * ticket验证合法性并返回对应的userAccount。
	 */
	public ResponsePropertyList<MenuBean> getMenuList(String ticket,
			String tab_id, String super_menu, String user_id) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("tab_id", tab_id);
				params.put("super_menu", super_menu);
				params.put("user_id", userAccount.getUser_id());
				return new ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
						taskDao.getSqlMapClientTemplate().queryForList(
								"mobile.getMenuList", params));
			} else {
				return new ResponsePropertyList(WsConstants.SHT_NO_SESSION,
						"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	public ResponsePropertyList<HomeReportBean> getSubReportList(String ticket,
			String tab_id, String parentId) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("tab_id", tab_id);
				params.put("parentId", parentId);
				List<HomeReportBean> list = taskDao.getSqlMapClientTemplate()
						.queryForList("mobile.getSubReportList", params);
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						HomeReportBean b = list.get(i);
						String repDateSql = b.getMax_date();
						String data_source = b.getData_source();
						// 构造是否下钻sql
						params.put("sql", repDateSql);

						String max_date = "";
						if ("ORA1".equals(data_source)) {
							max_date = (String) taskDao
									.getSqlMapClientTemplate().queryForObject(
											"mobile.getDynamicRepOfString",
											params);
						} else if ("DB2".equals(data_source)) {
							max_date = (String) taskDao_DB2
									.getSqlMapClient_DB2().queryForObject(
											"mobile_DB2.getDynamicRepOfString",
											params);
						}
						if (max_date == null || "".equals(max_date)) {
							max_date = CommonUtil.getSomeday(-2);
						}
						b.setMax_date(max_date);
					}
				}
				return new ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
						list);
			} else {
				return new ResponsePropertyList(WsConstants.SHT_NO_SESSION,
						"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	public ResponseProperty<String> getTodayFocus(String ticket) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				// 根据报表ID，获取报表数据sql
				HashMap<String, String> params = new HashMap<String, String>();
				List<HashMap> confList = taskDao.getSqlMapClientTemplate()
						.queryForList("mobile.getTodayFocusSql", params);
				if (confList != null && confList.size() != 0) {
					HashMap confMap = confList.get(0);
					String statDateSql = (String) confMap.get("TIPS_DATE");// 统计日期sql
					String contentSql = (String) confMap.get("TIPS_SQL");// 今日关注内容sql

					params.put("sql", statDateSql);
					Integer stat_date = (Integer) taskDao
							.getSqlMapClientTemplate().queryForObject(
									"mobile.getDynamicRepOfInteger", params);

					contentSql = contentSql.replace("$org_id$",
							userAccount.getOrg_id());
					contentSql = contentSql.replace("$stat_date$", ""
							+ stat_date);
					params.put("sql", contentSql);
					List<String> list = taskDao.getSqlMapClientTemplate()
							.queryForList("mobile.getDynamicRepOfString",
									params);
					String content = "";
					if (list != null && list.size() > 0) {
						content = list.get(0);
					}
					return new ResponseProperty<String>(
							WsConstants.SHT_SUCCESS, "成功", content);
				} else {
					return new ResponseProperty<String>(
							WsConstants.SHT_VALIDATION, "没有当前报表的配置信息，请联系管理员！");
				}
			} else {
				return new ResponseProperty(WsConstants.SHT_NO_SESSION,
						"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}
	public ResponsePropertyList<MarketGroupBean> getMarketGroupList(String ticket) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("user_id", userAccount.getUser_id());
				return new ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
						taskDao.getSqlMapClientTemplate().queryForList("mobile.getMarketGroupList", params));
			} else {
				return new ResponsePropertyList(WsConstants.SHT_NO_SESSION,"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"+ e.toString());
		}
	}

	public ResponsePropertyList<MarketBean> getMarketList(String ticket,String market_id,String class_id) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("user_id", userAccount.getUser_id());
				params.put("market_id", market_id);
				params.put("class_id", class_id);
				return new ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
						taskDao.getSqlMapClientTemplate().queryForList("mobile.getMarketList", params));
			} else {
				return new ResponsePropertyList(WsConstants.SHT_NO_SESSION,"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"+ e.toString());
		}
	}

	public ResponseEmptyProperty detainMarketExec(String ticket,
			String market_id, String serv_number, String line_info,
			String isnt_trace, String exec_note) {
   		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("V_MARKET_ID", market_id);
				params.put("V_SERV_NUMBER", serv_number);
				params.put("V_LINE_INFO", line_info);
				params.put("V_USER_ID", userAccount.getUser_id());
				params.put("V_EXEC_NOTE", exec_note);
				params.put("V_ISNT_TRACE", isnt_trace);
				taskDao.getSqlMapClientTemplate().insert("mobile.exec_market", params);
				
				return new ResponseEmptyProperty(WsConstants.SHT_SUCCESS, "执行维系成功！");
			} else {
				return new ResponseEmptyProperty(WsConstants.SHT_NO_SESSION, "无效的ticket:ticket=" + ticket);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return new ResponseEmptyProperty(WsConstants.SHT_ERROR, "服务端异常:" + e.toString());
		}
	}
	
	public ResponsePropertyList<Map> getMarketGroupExecList(String ticket) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("user_id", userAccount.getUser_id());
				return new ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
						taskDao.getSqlMapClientTemplate().queryForList("mobile.getMarketGroupExecList", params));
			} else {
				return new ResponsePropertyList(WsConstants.SHT_NO_SESSION,"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"+ e.toString());
		}
	}

	public ResponsePropertyList<Map> getMarketExecList(String ticket,String market_id) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("user_id", userAccount.getUser_id());
				params.put("market_id", market_id);
				return new ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
						taskDao.getSqlMapClientTemplate().queryForList("mobile.getMarketExecList", params));
			} else {
				return new ResponsePropertyList(WsConstants.SHT_NO_SESSION,"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"+ e.toString());
		}
	}	
	public ResponsePropertyList<Map> getMarketClassList(String ticket,String market_id) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("user_id", userAccount.getUser_id());
				params.put("market_id", market_id);
				return new ResponsePropertyList(WsConstants.SHT_SUCCESS, "成功",
						taskDao.getSqlMapClientTemplate().queryForList("mobile.getMarketClassList", params));
			} else {
				return new ResponsePropertyList(WsConstants.SHT_NO_SESSION,"无效的ticket:ticket=" + ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"+ e.toString());
		}
	}	
	/***************************************************************************************
	 * 私有方法 begin=======================>>
	 *****************************************************************************************/
	/******************************************************
	 * 获得当前最新版本信息（单个版本）
	 ********************************************************/
	private ClientVersionBean getLastVersion() {
		ClientVersionBean clientVersionBean = (ClientVersionBean) taskDao
				.getSqlMapClientTemplate().queryForObject(
						"mobile.getLastVersion");
		return clientVersionBean;
	}

	/******************************************************
	 * 判断imsi,pattern_code是否登陆成功
	 ********************************************************/
	/*
	 * @SuppressWarnings("unchecked") private UserAccount
	 * isCodeLogonSuccess(String imsiCode, String imeiCode, String pattern_code)
	 * { HashMap<String,String> params = new HashMap<String,String>();
	 * params.put("imsiCode", imsiCode); params.put("imeiCode", imeiCode);
	 * params.put("pattern_code", pattern_code); ArrayList<UserAccount> list =
	 * (ArrayList<UserAccount>)taskDao.getSqlMapClientTemplate().queryForList(
	 * "mobile.isCodeLogonSuccess",params); if(list==null||list.size()==0) {
	 * return null; } else { return list.get(0); } }
	 */
	/**
	 * 二期修改:新登录
	 * 
	 * @param user_acct
	 * @param password
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private UserAccount isCodeLogonSuccess(String user_acct, String password,
			String pattern_code,String package_name) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_acct", user_acct);
		params.put("password", password);
		params.put("pattern_code", pattern_code);
		params.put("package_name", package_name);
		ArrayList<UserAccount> list = (ArrayList<UserAccount>) taskDao
				.getSqlMapClientTemplate().queryForList(
						"mobile.isCodeLogonSuccess", params);
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/******************************************************
	 * 记录登陆日志
	 ********************************************************/
/*	private void insertLogonLog(String password, String imsiCode,
			String imeiCode, String systemVersion, String clientVersion,
			String result) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("phone_number", "");
		params.put("password", password);
		params.put("imeiCode", imeiCode);
		params.put("imsiCode", imsiCode);
		params.put("systemVersion", systemVersion);
		params.put("clientVersion", clientVersion);
		params.put("result", result);
		taskDao.insert("mobile.insertLogonLog", params);
	}*/
	
	private void insertLogonLog(String user_acct, String password,
			String pattern_code,String result) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_acct", user_acct);
		params.put("password", password);
		params.put("pattern_code", pattern_code);
		params.put("result", result);
		taskDao.insert("mobile.insertLogonLog", params);
	}

	/******************************************************
	 * 记录检测是否注册的日志
	 ********************************************************/
	private String insertRegistCheckLog(String id, String imsiCode,
			String imeiCode, String systemVersion, String clientVersion,
			String result,String phoneNumber,String userName,String posOrgId,String pakname) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("phone_number",phoneNumber);
		params.put("user_name",userName);
		params.put("imeiCode", imeiCode);
		params.put("imsiCode", imsiCode);
		params.put("systemVersion", systemVersion);
		params.put("clientVersion", clientVersion);
		params.put("result", result);
		params.put("posOrgId", posOrgId);
		params.put("pakname", pakname);
		taskDao.insert("mobile.insertRegistCheckLog", params);
		return id;
	}

	/******************************************************
	 * 检查ticket是否合法，如果合法，则返回对应的UserAccount，不合法则抛出异常
	 ********************************************************/
	public UserAccount checkTicket(String ticket) {

		UserAccount userAccount = null;
		try {
			long ticketNumber = Long.valueOf(ticket);
			userAccount = Ticket.getUserAccount(ticketNumber);
			userAccount.setTicket(ticket);
		} catch (InvalidTicketException e) {
			e.printStackTrace();
		}
		return userAccount;
	}

	// 将map获取的value转换成string
	@SuppressWarnings("rawtypes")
	private String mapToString(Map map, String key) {
		String ret = "";
		if (map.get(key) != null) {
			ret = map.get(key).toString();
		}
		return ret;
	}

	/* 异步执行-记录登陆日志 */
	class InsertLogonLogThread extends Thread {

		private String user_acct;
		private String pattern_code;
		private String password;
		private String result;
		
		public InsertLogonLogThread() {

		}

		public InsertLogonLogThread(String user_acct, String pattern_code,
				String password, String result) {
			this.user_acct = user_acct;
			this.pattern_code = pattern_code;
			this.password = password;
			this.result = result;
		}

		public void run() {
			insertLogonLog(user_acct, password, pattern_code, result);
		}

	}

	/* 异步执行-记录检测是否注册的日志 */
	class InsertRegistCheckLogThread extends Thread {

		public InsertRegistCheckLogThread() {
		}

		public InsertRegistCheckLogThread(String id, String imsiCode,
				String imeiCode, String systemVersion, String clientVersion,
				String result, String phoneNumber, String userName,
				String posOrgId, String pakname) {
			this.id = id;
			this.imsiCode = imsiCode;
			this.imeiCode = imeiCode;
			this.systemVersion = systemVersion;
			this.clientVersion = clientVersion;
			this.result = result;
			// 后期追加
			this.phoneNumber = phoneNumber;
			this.userName = userName;
			this.posOrgId = posOrgId;
			this.pakname = pakname;
		}

		public void run() {
			insertRegistCheckLog(id, imsiCode, imeiCode, systemVersion,
					clientVersion, result, phoneNumber, userName, posOrgId,
					pakname);
		}

		private String phoneNumber;
		private String userName;
		private String id;
		private String imsiCode;
		private String imeiCode;
		private String systemVersion;
		private String clientVersion;
		private String result;
		private String posOrgId;
		private String pakname;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseProperty<LhxcSheHui> getRepDate(String ticket, String date) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			ResponseProperty<LhxcSheHui> response = new ResponseProperty<LhxcSheHui>();
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("rep_name", "量化薪酬社会53019");
				List<HashMap> confList = taskDao.getSqlMapClientTemplate()
						.queryForList("mobile.getRepDateSql", params);
				if (confList != null && confList.size() != 0) {
					HashMap confMap = confList.get(0);
					String statDateSql = (String) confMap.get("REP_DATE_SQL");// 日期sql
					CLOB clob = (CLOB) confMap.get("REP_SQL");
					int cloblength = (int) clob.length();
					String contentSql = clob.getSubString(1, cloblength);// 获得内容

					params.put("sql", statDateSql);
					Integer stat_date = (Integer) taskDao
							.getSqlMapClientTemplate().queryForObject(
									"mobile.getRepDate", params);
					if (date != null && !date.trim().equals("")) {
						contentSql = contentSql.replace("$stat_month$", date);
					} else {
						contentSql = contentSql.replace("$stat_month$", ""
								+ stat_date);
					}
					contentSql = contentSql.replace("$staff_id$",
							userAccount.getUser_acct());
					params.put("sql", contentSql);
					ArrayList<Map> list = (ArrayList<Map>) taskDao
							.getSqlMapClientTemplate().queryForList(
									"mobile.getSummaryIndHashMap", params);
					if (list != null && list.size() > 0) {
						Map<String, String> hash = list.get(0);
						LhxcSheHui lhxcSheHui = new LhxcSheHui();
						BeanInfo beans = java.beans.Introspector
								.getBeanInfo(LhxcSheHui.class);
						PropertyDescriptor[] propertyDescriptors = beans
								.getPropertyDescriptors();
						for (int i = 0; i < propertyDescriptors.length; i++) {
							String propertyName = (propertyDescriptors[i]
									.getDisplayName()).toUpperCase();
							if (hash.containsKey(propertyName)) {
								propertyDescriptors[i].getWriteMethod().invoke(
										lhxcSheHui,
										String.valueOf(hash.get(propertyName)));
							}

						}

						response.setStatus(WsConstants.SHT_SUCCESS);
						response.setMessage("成功!");
						response.setEntity(lhxcSheHui);
					} else {
						response.setStatus(WsConstants.SHT_NO_SESSION);
						response.setMessage("没有相关数据");
					}

				}
				return response;
			} else {
				response.setStatus(WsConstants.SHT_NO_SESSION);
				response.setMessage("无效的ticket:ticket=" + ticket);
				return response;

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseProperty<LhxcZiYou> getRepLhxcZiYou(String ticket,
			String date) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			ResponseProperty<LhxcZiYou> response = new ResponseProperty<LhxcZiYou>();
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();

				params.put("rep_name", "量化薪酬自有53003");
				List<HashMap> confList = taskDao.getSqlMapClientTemplate()
						.queryForList("mobile.getRepDateSql", params);
				if (confList != null && confList.size() != 0) {
					HashMap confMap = confList.get(0);
					String statDateSql = (String) confMap.get("REP_DATE_SQL");// 日期sql
					CLOB clob = (CLOB) confMap.get("REP_SQL");
					int cloblength = (int) clob.length();
					String contentSql = clob.getSubString(1, cloblength);// 获得内容
					params.put("sql", statDateSql);
					Integer stat_date = (Integer) taskDao
							.getSqlMapClientTemplate().queryForObject(
									"mobile.getRepDate", params);
					if (date != null && !date.trim().equals("")) {
						contentSql = contentSql.replace("$stat_month$", date);
					} else {
						contentSql = contentSql.replace("$stat_month$",
								+stat_date + "");
					}
					contentSql = contentSql.replace("$staff_id$",
							userAccount.getUser_acct());
					params.put("sql", contentSql);
					ArrayList<Map> list = (ArrayList<Map>) taskDao
							.getSqlMapClientTemplate().queryForList(
									"mobile.getSummaryIndHashMap", params);
					if (list != null && list.size() > 0) {
						Map<String, String> hash = list.get(0);
						LhxcZiYou lhxcZiYou = new LhxcZiYou();
						BeanInfo beans = java.beans.Introspector
								.getBeanInfo(LhxcZiYou.class);
						PropertyDescriptor[] propertyDescriptors = beans
								.getPropertyDescriptors();
						for (int i = 0; i < propertyDescriptors.length; i++) {
							String propertyName = (propertyDescriptors[i]
									.getDisplayName()).toUpperCase();
							if (hash.containsKey(propertyName)) {
								propertyDescriptors[i].getWriteMethod().invoke(
										lhxcZiYou,
										String.valueOf(hash.get(propertyName)));
							}

						}

						response.setStatus(WsConstants.SHT_SUCCESS);
						response.setMessage("成功!");
						response.setEntity(lhxcZiYou);
					} else {
						response.setStatus(WsConstants.SHT_NO_SESSION);
						response.setMessage("没有相关数据");
					}

				}

				return response;
			} else {
				response.setStatus(WsConstants.SHT_NO_SESSION);
				response.setMessage("无效的ticket:ticket=" + ticket);
				return response;

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseProperty<LhxcTradeDay> getRepLhxcTradeDay(String ticket,
			String date) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			ResponseProperty<LhxcTradeDay> response = new ResponseProperty<LhxcTradeDay>();

			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("rep_name", "53013营业员积分占比查询(日)");
				List<HashMap> confList = taskDao.getSqlMapClientTemplate()
						.queryForList("mobile.getRepDateSql", params);
				if (confList != null && confList.size() != 0) {
					HashMap confMap = confList.get(0);
					String statDateSql = (String) confMap.get("REP_DATE_SQL");// 日期sql
					CLOB clob = (CLOB) confMap.get("REP_SQL");
					int cloblength = (int) clob.length();
					String contentSql = clob.getSubString(1, cloblength);// 获得内容

					params.put("sql", statDateSql);
					Integer stat_date = (Integer) taskDao
							.getSqlMapClientTemplate().queryForObject(
									"mobile.getRepDate", params);
					if (date != null && !date.trim().equals("")) {
						contentSql = contentSql.replace("$stat_date$", date);
					} else {
						contentSql = contentSql.replace("$stat_date$", ""
								+ stat_date);
					}
					contentSql = contentSql.replace("$staff_id$",
							userAccount.getUser_acct());
					params.put("sql", contentSql);
					ArrayList<Map> list = (ArrayList<Map>) taskDao
							.getSqlMapClientTemplate().queryForList(
									"mobile.getSummaryIndHashMap", params);
					if (null != list && list.size() > 0) {
						Map<String, String> hash = list.get(0);
						LhxcTradeDay lhxcTradeDay = new LhxcTradeDay();
						BeanInfo beans = java.beans.Introspector
								.getBeanInfo(LhxcTradeDay.class);
						PropertyDescriptor[] propertyDescriptors = beans
								.getPropertyDescriptors();
						for (int i = 0; i < propertyDescriptors.length; i++) {
							String propertyName = (propertyDescriptors[i]
									.getDisplayName()).toUpperCase();
							if (hash.containsKey(propertyName)) {
								propertyDescriptors[i].getWriteMethod().invoke(
										lhxcTradeDay,
										String.valueOf(hash.get(propertyName)));
							}

						}

						response.setStatus(WsConstants.SHT_SUCCESS);
						response.setMessage("成功!");
						response.setEntity(lhxcTradeDay);
					} else {
						response.setStatus(WsConstants.SHT_NO_SESSION);
						response.setMessage("没有相关数据");
					}
				}
				return response;
			} else {
				response.setStatus(WsConstants.SHT_NO_SESSION);
				response.setMessage("无效的ticket:ticket=" + ticket);
				return response;

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseProperty<LhxcTradeMon> getRepLhxcTradeMon(String ticket,
			String date) {
		try {
			UserAccount userAccount = checkTicket(ticket);
			ResponseProperty<LhxcTradeMon> response = new ResponseProperty<LhxcTradeMon>();
			if (userAccount != null) {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("rep_name", "营业员积分占比(月)查询53001");
				List<HashMap> confList = taskDao.getSqlMapClientTemplate()
						.queryForList("mobile.getRepDateSql", params);
				if (confList != null && confList.size() != 0) {
					HashMap confMap = confList.get(0);
					String statDateSql = (String) confMap.get("REP_DATE_SQL");// 日期sql
					CLOB clob = (CLOB) confMap.get("REP_SQL");
					int cloblength = (int) clob.length();
					String contentSql = clob.getSubString(1, cloblength);// 获得内容

					params.put("sql", statDateSql);
					Integer stat_date = (Integer) taskDao
							.getSqlMapClientTemplate().queryForObject(
									"mobile.getRepDate", params);
					if (date != null && !date.trim().equals("")) {
						contentSql = contentSql.replace("$stat_month$", date);
					} else {
						contentSql = contentSql.replace("$stat_month$", ""
								+ stat_date);
					}
					contentSql = contentSql.replace("$staff_id$",
							userAccount.getUser_acct());
					params.put("sql", contentSql);
					ArrayList<Map> list = (ArrayList<Map>) taskDao
							.getSqlMapClientTemplate().queryForList(
									"mobile.getSummaryIndHashMap", params);
					if (list != null && list.size() > 0) {
						Map<String, String> hash = list.get(0);
						LhxcTradeMon lhxcTradeMon = new LhxcTradeMon();
						BeanInfo beans = java.beans.Introspector
								.getBeanInfo(LhxcTradeMon.class);
						PropertyDescriptor[] propertyDescriptors = beans
								.getPropertyDescriptors();
						for (int i = 0; i < propertyDescriptors.length; i++) {
							String propertyName = (propertyDescriptors[i]
									.getDisplayName()).toUpperCase();
							if (hash.containsKey(propertyName)) {
								propertyDescriptors[i].getWriteMethod().invoke(
										lhxcTradeMon,
										String.valueOf(hash.get(propertyName)));
							}

						}

						response.setStatus(WsConstants.SHT_SUCCESS);
						response.setMessage("成功!");
						response.setEntity(lhxcTradeMon);
					} else {
						response.setStatus(WsConstants.SHT_NO_SESSION);
						response.setMessage("没有相关数据");
					}
				}
				return response;
			} else {
				response.setStatus(WsConstants.SHT_NO_SESSION);
				response.setMessage("无效的ticket:ticket=" + ticket);
				return response;

			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseProperty<TendencyMap> getTendencyMapInfo(String order_id,
			String statis_date, String org_id) {
		try {
			ResponseProperty<TendencyMap> response = new ResponseProperty<TendencyMap>();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("order_id", order_id);
			params.put("statis_date", statis_date);
			params.put("org_id", org_id);
			ArrayList<TendencyMap> list = (ArrayList<TendencyMap>) taskDao
					.getSqlMapClientTemplate().queryForList(
							"mobile.getTendencyMapInfo", params);
			if (list != null && list.size() > 0) {
				response.setStatus(WsConstants.SHT_SUCCESS);
				response.setMessage("成功!");
				response.setEntity(list.get(0));
			} else {
				response.setStatus(WsConstants.SHT_NO_SESSION);
				response.setMessage("没有相关数据");
			}
			// 写入登陆日志
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponsePropertyList<Customization> getCustomizationMapList(String userId) {
		try {
			ResponsePropertyList<Customization> response = new ResponsePropertyList<Customization>();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("userId", userId);
			
			ArrayList<Customization> list = (ArrayList<Customization>) taskDao
					.getSqlMapClientTemplate().queryForList(
							"mobile.getCustomizationMapList", params);
			if (list != null && list.size() > 0) {
				response.setStatus(WsConstants.SHT_SUCCESS);
				response.setMessage("成功!");
				response.setEntityList(list);
			} else {
				response.setStatus(WsConstants.SHT_VALIDATION);
				response.setMessage("没有相关数据");
			}
			// 写入登陆日志
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponsePropertyList<ChartTitle> getChartTitleList(String chartId,
			String dimId) {
		try {
			ResponsePropertyList<ChartTitle> response = new ResponsePropertyList<ChartTitle>();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("chartId", chartId);
			params.put("dimId", dimId);
			HashMap confMap = getHashMap(params);
			if(confMap!=null){
				String chartTitleSql = (String) confMap.get("CHART_TITLE_SQL");
				params.put("sql", chartTitleSql);
				ArrayList<ChartTitle> list = (ArrayList<ChartTitle>) taskDao
						.getSqlMapClientTemplate().queryForList(
								"mobile.getChartTitleSql", params);
	
				if (list != null && list.size() > 0) {
					response.setStatus(WsConstants.SHT_SUCCESS);
					response.setMessage("成功!");
					response.setEntityList(list);
				}
				else {
					response.setStatus(WsConstants.SHT_VALIDATION);
					response.setMessage("没有相关数据");
				}
			} else {
				response.setStatus(WsConstants.SHT_VALIDATION);
				response.setMessage("没有相关数据");
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponsePropertyList<ChartDim> getChartDimList(String chartId,
			String dimId, String orgId, String cdate) {
		try {
			ResponsePropertyList<ChartDim> response = new ResponsePropertyList<ChartDim>();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("chartId", chartId);
			params.put("dimId", dimId);
			HashMap confMap = getHashMap(params);
			if (confMap != null) {
				CLOB clob = (CLOB) confMap.get("DIM_SQL");
				String dimSql = clob.getSubString(1, (int) clob.length());
				if (cdate != null && !cdate.trim().equals("")) {
					dimSql = dimSql.replace("$stat_date$", cdate);
				} else {
					// 得到时间
					String datSql = (String) confMap.get("CHART_DATA_SQL");
					params.put("sql", datSql);
					Integer datetime = (Integer) taskDao
							.getSqlMapClientTemplate().queryForObject(
									"mobile.getRepDate", params);
					dimSql = dimSql.replace("$stat_date$", datetime + "");
				}
				if (orgId != null && !orgId.trim().equals("")) {					
				 if(dimSql.indexOf("$org_id$")!=-1){
					dimSql= dimSql.replace("$org_id$",orgId);
				 }
				}
				params.put("sql", dimSql);
				ArrayList<ChartDim> list = (ArrayList<ChartDim>) taskDao
						.getSqlMapClientTemplate().queryForList(
								"mobile.getChartDimSql", params);

				if (list != null && list.size() > 0) {
					response.setStatus(WsConstants.SHT_SUCCESS);
					response.setMessage("成功!");
					response.setEntityList(list);
				}
				else {
					response.setStatus(WsConstants.SHT_VALIDATION);
					response.setMessage("没有相关数据");
				}
			} else {
				response.setStatus(WsConstants.SHT_VALIDATION);
				response.setMessage("没有相关数据");
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponsePropertyList<ChartCondition> getChartConditionList(
			String chartId, String dimId, String date) {
		try {
			ResponsePropertyList<ChartCondition> response = new ResponsePropertyList<ChartCondition>();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("chartId", chartId);
			params.put("dimId", dimId);
			HashMap confMap = getHashMap(params);
			if (confMap != null) {
				String conditionsql = (String) confMap
						.get("CHART_CONDITION_SQL");
				if (date != null && !date.trim().equals("")) {
					conditionsql = conditionsql.replace("$stat_date$", date);
				} else {
					// 得到时间
					String datSql = (String) confMap.get("CHART_DATA_SQL");
					params.put("sql", datSql);
					Integer datetime = (Integer) taskDao
							.getSqlMapClientTemplate().queryForObject(
									"mobile.getRepDate", params);
					conditionsql = conditionsql.replace("$stat_date$", datetime
							+ "");
				}
				params.put("sql", conditionsql);
				ArrayList<ChartCondition> list = (ArrayList<ChartCondition>) taskDao
						.getSqlMapClientTemplate().queryForList(
								"mobile.getChartConditionSql", params);
				if (list != null && list.size() > 0) {
					response.setStatus(WsConstants.SHT_SUCCESS);
					response.setMessage("成功!");
					response.setEntityList(list);
				}
				else {
					response.setStatus(WsConstants.SHT_VALIDATION);
					response.setMessage("没有相关数据");
				}
			} else {
				response.setStatus(WsConstants.SHT_VALIDATION);
				response.setMessage("没有相关数据");
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponsePropertyList(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}
	/**
	 * 等到对应的SQL语句
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap getHashMap(HashMap<String, String> params){
		
		List<HashMap> confList = taskDao.getSqlMapClientTemplate()
				.queryForList("mobile.getChartConfigSql", params);
		HashMap confMap=null;
		if (confList != null && confList.size() != 0) {
			confMap = confList.get(0);	
			return confMap;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseProperty<ChartType> getChartType(String chartId, String dimId) {
		try {
			ResponseProperty<ChartType> response = new ResponseProperty<ChartType>();
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("chartId", chartId);
			params.put("dimId", dimId);
			ArrayList<ChartType> list = (ArrayList<ChartType>) taskDao
					.getSqlMapClientTemplate().queryForList(
							"mobile.getChartType", params);
			if (list != null && list.size() > 0) {
				response.setStatus(WsConstants.SHT_SUCCESS);
				response.setMessage("成功!");
				response.setEntity(list.get(0));
			} else {
				response.setStatus(WsConstants.SHT_VALIDATION);
				response.setMessage("没有相关数据");
			}
			// 写入登陆日志
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseProperty(WsConstants.SHT_ERROR, "服务端异常:"
					+ e.toString());
		}
	}
}
