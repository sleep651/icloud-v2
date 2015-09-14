package com.apps.mobile.service;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.apps.mobile.domain.ChartCondition;
import com.apps.mobile.domain.ChartDim;
import com.apps.mobile.domain.ChartTitle;
import com.apps.mobile.domain.ChartType;
import com.apps.mobile.domain.ClientVersionBean;
import com.apps.mobile.domain.Customization;
import com.apps.mobile.domain.DataDateBean;
import com.apps.mobile.domain.HomeReportBean;
import com.apps.mobile.domain.LhxcSheHui;
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
import com.apps.mobile.domain.TendencyMap;
import com.apps.mobile.domain.UserAccount;

@WebService(serviceName = "mobileService")
public interface IMobileService {
    /**
     * 检测接口-不访问数据库
     * @param callTime:字符串类型
     * @return
     */
    @WebMethod(action = "testNoSql")
	public ResponseEmptyProperty testNoSql(@WebParam(name = "callTime") String callTime);
	
    /**
     * 检测接口-访问数据库
     * @param callTime:字符串类型
     * @return
     */
    @WebMethod(action = "testSimpleSql")
	public ResponseEmptyProperty testSimpleSql(@WebParam(name = "callTime") String callTime);
    
    @WebMethod(action = "testSimpleSql_DB2")
	public ResponseEmptyProperty testSimpleSql_DB2(@WebParam(name = "callTime") String callTime);
    
	/******************************************************
	《第1部分》 系统功能
	********************************************************/	
	/******************************************************
	1.1	isVersionAvailable 【检测当前版本是否可用】
	函数说明：检测当前版本是否可用
	参数说明：
		String  clientVersion  		客户端软件版本
			    
	返回值：检测结果及最新版本信息
		<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		   <soap:Body>
		      <ns1:isVersionAvailableResponse xmlns:ns1="http://service.mobile.apps.com">
		         <ns1:out>
		            <entity xsi:type="ClientVersionBean" xmlns="http://domain.mobile.apps.com">
		               <v_date>20131016</v_date>
		               <v_down>http://10.1.116.133:8080/Root/download/1.0.8_9999.apk</v_down>
		               <v_no>1.0.0</v_no>
		               <v_state>00A</v_state>
		            </entity>
		            <message xmlns="http://domain.mobile.apps.com">当前版本可用，不需要下载新版本</message>
		            <status xmlns="http://domain.mobile.apps.com">0</status>
		         </ns1:out>
		      </ns1:isVersionAvailableResponse>
		   </soap:Body>
		</soap:Envelope>

	字段说明：
		(1)status:返回状态；
			0:当前版本可用，不需要下载新版本;
			1:版本过期，需要下载新版本;
		   -1：服务端异常
		(2)message:返回结果描述
		(3)entity:返回当前最新的可用版本信息对象ClientVersionBean
			【  v_no:当前版本号
			   v_down:下载地址
			   v_date:上传日期
			   v_state:状态是否可用(00A-可用，00X-不可用)[此处返回的都是可用的]
			 】  
	********************************************************/
	public ResponseProperty<ClientVersionBean> isVersionAvailable(@WebParam(name = "clientVersion") String clientVersion);
	
	
	public ResponseProperty<ClientVersionBean> isVersionAvailableNew(@WebParam(name = "clientVersion") String clientVersion,@WebParam(name = "packageName") String packageName);
	
	
	/******************************************************
	1.2	isRegist 【检测当前移动终端的IMSI和IMEI是否已在系统中注册】
	函数说明：检测当前移动终端的IMSI和IMEI是否已在系统中注册
	参数说明：
		String  imsiCode     	SIM卡串码(需要加密)<--不能为空
		String  imeiCode      	手机序列号(需要加密)<--为空
		String  systemVersion  	手机操作系统版本
		String  clientVersion  		客户端软件版本
			    
	返回值：检测结果及当前检测日志ID
		<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		   <soap:Body>
		      <ns1:isRegistResponse xmlns:ns1="http://service.mobile.apps.com">
		         <ns1:out>
		            <entity xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
		            <message xmlns="http://domain.mobile.apps.com">IMSI和IMEI已注册</message>
		            <status xmlns="http://domain.mobile.apps.com">0</status>
		         </ns1:out>
		      </ns1:isRegistResponse>
		   </soap:Body>
		</soap:Envelope>

	字段说明：
		(1)status:返回状态；
			 0:IMSI和IMEI已注册;
			 1:IMSI和IMEI未注册,已经申请注册
			-2:IMSI和IMEI未注册,未申请注册;
			-1：服务端异常或者参数传入有误
		(2)message:返回结果描述
		(3)entity:本次查询ID（用于调用申请注册的必要参数-已注册时，该值为空）
	********************************************************/	
	public ResponseProperty<String> isRegist(@WebParam(name = "imsiCode") String imsiCode, 
			@WebParam(name = "imeiCode") String imeiCode, @WebParam(name = "systemVersion") String systemVersion, 
			@WebParam(name = "clientVersion") String clientVersion);
	
	/******************************************************
	1.3	applyForRegist 【申请注册】
	函数说明：获取短信数据的接口。
	参数说明：
        String posorgid         机构标识_身份
        String phone_number     用户号码
        String user_name        用户名称
        String imsiCode         手机序列号
        String imeiCode         SIM卡串号
        String systemVersion    
        String clientVersion
        String pak_name         应用包名
	返回值：是否登录成功
		<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		   <soap:Body>
		      <ns1:applyForRegistResponse xmlns:ns1="http://service.branch.apps.com">
		         <ns1:out>
		            <message xmlns="http://domain.mobile.apps.com">申请注册成功！</message>
		            <status xmlns="http://domain.mobile.apps.com">0</status>		         
		         </ns1:out>
		      </ns1:applyForRegistResponse>
		   </soap:Body>
		</soap:Envelope>
		
	字段说明：
		(1)status:返回状态；
			0:申请注册成功！
			1:申请注册失败！（原始密码错误）
		   -1：服务端异常	
		(2)message:返回结果描述
	 ********************************************************/	
    @WebMethod(action = "applyForRegist")
    //申请注册备份
    /*public ResponseEmptyProperty applyForRegist(@WebParam(name = "queryId") String queryId,
    		@WebParam(name = "phone_number") String phone_number,@WebParam(name = "user_name") String user_name);
	*/
    /**
     * 二期 申请注册
     * @param posorgid
     * @param phone_number
     * @param user_name
     * @return
     */
    public ResponseEmptyProperty applyForRegist(@WebParam(name = "posorgid") String posorgid,
    		@WebParam(name = "phone_number") String phone_number,@WebParam(name = "user_name") String user_name,@WebParam(name = "imsiCode") String imsiCode, 
			@WebParam(name = "imeiCode") String imeiCode, @WebParam(name = "systemVersion") String systemVersion, 
			@WebParam(name = "clientVersion") String clientVersion, @WebParam(name = "pak_name") String pak_name);
	
	/******************************************************
	1.4	login 【用户登录】
	函数说明：用户登录
	参数说明：
		String  password   		密码(手势码)(需要加密)
		String  imsiCode     	SIM卡串码(需要加密)<--不能为空
		String  imeiCode      	手机序列号(需要加密)<--不能为空
		String  systemVersion  	手机操作系统版本
		String  clientVersion  		客户端软件版本
			    
	返回值：登陆结果及其他属性
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	   <soap:Body>
	      <ns1:loginResponse xmlns:ns1="http://service.mobile.apps.com">
	         <ns1:out>
	            <entity xsi:type="UserAccount" xmlns="http://domain.mobile.apps.com">
	               <imsi>460036931117290</imsi>
	               <is_use_cache>false</is_use_cache>
	               <org_id>-1</org_id>
	               <org_name>省公司</org_name>
	               <phone_number>18919889828</phone_number>
	               <ticket>1392861752583</ticket>
	               <user_id>E8EC58457FB6912AE043879448E6912A</user_id>
	               <user_name>王锡勇</user_name>
	            </entity>
	            <message xmlns="http://domain.mobile.apps.com">登录成功!</message>
	            <status xmlns="http://domain.mobile.apps.com">0</status>
	         </ns1:out>
	      </ns1:loginResponse>
	   </soap:Body>
	</soap:Envelope>

	字段说明：
		(1)status:返回状态；
			0:登录成功;
			1:登录失败;
		   -1：服务端异常	
		(2)message:返回结果描述
		(3)entity：返回登陆用户的对象UserAccount
			user_id:用户ID
			phone_number:手机号码
		    user_name:名字
		    org_id:所属区域ID
		    org_name:所属区域
		    imsi:手机串号
			ticket:用户登陆成功后，获得的访问会话的ticket，调用功能接口需要填写该参数；【重要】
			is_use_cache:数据读取模式（true：缓存机制读取;false：始终通过接口读取）
	********************************************************/
    @WebMethod(action = "login")
/*	public ResponseProperty<UserAccount> login(@WebParam(name = "password") String password, 
			@WebParam(name = "imsiCode")String imsiCode, @WebParam(name = "imeiCode")String imeiCode, 
			@WebParam(name = "systemVersion")String systemVersion, @WebParam(name = "clientVersion")String clientVersion);*/
	
    
    /******************************************************
	1.4	login 【新用户登录】
	函数说明：用户登录
	参数说明：
		String  password   		密码(需要加密)
		String  patterncode     手势码(需要加密)<--不能为空
		String  user_acct      	用户名<--不能为空
		String  package_name    包名<-- 不能为空
			    
	返回值：登陆结果及其他属性
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   	<soap:Body>
      <ns1:loginResponse xmlns:ns1="http://service.mobile.apps.com">
         <ns1:out>
            <entity xsi:type="UserAccount" xmlns="http://domain.mobile.apps.com">
               <imsi>460010371600228</imsi>
               <is_use_cache>false</is_use_cache>
               <org_id>-1</org_id>
               <org_name>省公司</org_name>
               <password xsi:nil="true"/>
               <phone_number>18610371560</phone_number>
               <ticket>1414134873566</ticket>
               <user_acct xsi:nil="true"/>
               <user_id>E8EC58457FB0912AE043879448E6912A</user_id>
               <user_name>俞建熊</user_name>
            </entity>
            <message xmlns="http://domain.mobile.apps.com">登录成功!</message>
            <status xmlns="http://domain.mobile.apps.com">0</status>
         </ns1:out>
      </ns1:loginResponse>
   </soap:Body>
</soap:Envelope>

	字段说明：
		(1)status:返回状态；
			0:登录成功;
			1:登录失败;
		   -1：服务端异常	
		(2)message:返回结果描述
		(3)entity：返回登陆用户的对象UserAccount
			user_id:用户ID
			phone_number:手机号码
		    user_name:名字
		    org_id:所属区域ID
		    org_name:所属区域
		    imsi:手机串号
			ticket:用户登陆成功后，获得的访问会话的ticket，调用功能接口需要填写该参数；【重要】
			is_use_cache:数据读取模式（true：缓存机制读取;false：始终通过接口读取）
	********************************************************/
    
    public ResponseProperty<UserAccount> login(@WebParam(name = "password") String password, 
			@WebParam(name = "user_acct")String user_acct, 
			@WebParam(name = "patterncode")String patterncode,
			@WebParam(name = "package_name")String package_name);
    /******************************************************
	1.5	logout 【系统退出接口】
	函数说明：系统退出接口
	参数说明：
		String  ticket   	登陆成功时返回的ticket

	返回值：是否执行成功
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	   <soap:Body>
	      <ns1:logoutResponse xmlns:ns1="http://service.mobile.apps.com">
	         <ns1:out>
	            <message xmlns="http://domain.mobile.apps.com">成功退出</message>
	            <status xmlns="http://domain.mobile.apps.com">0</status>
	         </ns1:out>
	      </ns1:logoutResponse>
	   </soap:Body>
	</soap:Envelope>
		
	字段说明：
		(1)status:返回状态；
			0:成功退出；
		   -1：服务端异常
		   -2:无效的ticket	
		(2)message:返回结果描述			
	 ********************************************************/
    @WebMethod(action = "logout")
    public ResponseEmptyProperty logout(@WebParam(name = "ticket") String ticket);
	/******************************************************
	1.6	updatePattern 【修改手势码】
	函数说明：获取短信数据的接口。
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
		String newPassword		新手势密码字符串(需要加密)
	
	返回值：是否登录成功
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	   <soap:Body>
	      <ns1:updatePatternResponse xmlns:ns1="http://service.mobile.apps.com">
	         <ns1:out>
	            <message xmlns="http://domain.mobile.apps.com">修改手势码成功！</message>
	            <status xmlns="http://domain.mobile.apps.com">0</status>
	         </ns1:out>
	      </ns1:updatePatternResponse>
	   </soap:Body>
	</soap:Envelope>

	字段说明：
		(1)status:返回状态；
			0:修改手势码成功！
			1:修改手势码失败！（原始密码错误）
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述				
	 ********************************************************/	
	/*public ResponseEmptyProperty updatePattern(@WebParam(name = "ticket") String ticket, 
			@WebParam(name = "oldPassword") String oldPassword, @WebParam(name = "newPassword") String newPassword);
	*/
    public ResponseEmptyProperty updatePattern(@WebParam(name = "ticket") String ticket,
    		@WebParam(name = "newPassword") String newPassword);
    /******************************************************
	1.6	updatePassword 【修改密码】
	函数说明：获取短信数据的接口。
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
		String newPassword		新密码字符串(需要加密)
	
	返回值：是否登录成功
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	   <soap:Body>
	      <ns1:updatePatternResponse xmlns:ns1="http://service.mobile.apps.com">
	         <ns1:out>
	            <message xmlns="http://domain.mobile.apps.com">修改手势码成功！</message>
	            <status xmlns="http://domain.mobile.apps.com">0</status>
	         </ns1:out>
	      </ns1:updatePatternResponse>
	   </soap:Body>
	</soap:Envelope>

	字段说明：
		(1)status:返回状态；
			0:修改密码成功！
			1:修改密码失败！（原始密码错误）
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述				
	 ********************************************************/	
    public ResponseEmptyProperty updatePassword(@WebParam(name = "ticket") String ticket,
    		@WebParam(name = "newPassword") String newPassword);
    
   
	
	/******************************************************
	1.7	getDataDateList【获取报表数据的最新生成日期】 
		函数说明：获取报表数据的最新生成日期
		参数说明：无
	
		返回值：表单列表
		<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		   <soap:Body>
		      <ns1:getDataDateListResponse xmlns:ns1="http://service.mobile.apps.com">
		         <ns1:out>
		            <entityList xmlns="http://domain.mobile.apps.com">
		               <xsd:anyType xsi:type="DataDateBean">
		                  <data_date>20140416085549</data_date>
		                  <disp_view>数据门户     2014年04月16日#移动用户日发展9557户，宽带用户日发展1114户#累计份额20.05%，本月增幅0.07%#点击查看...</disp_view>
		                  <menu_id>APK_PUSH_DATE</menu_id>
		               </xsd:anyType>
		            </entityList>
		            <message xmlns="http://domain.mobile.apps.com">成功</message>
		            <pageNo xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
		            <pageSize xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
		            <status xmlns="http://domain.mobile.apps.com">0</status>
		            <totalCount xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
		         </ns1:out>
		      </ns1:getDataDateListResponse>
		   </soap:Body>
		</soap:Envelope>

		字段说明：
			(1)status:返回状态；
				0:修改手势码成功！
				1:修改手势码失败！（原始密码错误）
			   -1：服务端异常
			   -2:无效的ticket
			(2)message:返回结果描述	
			(3)entity：返回登陆用户的对象DataDateBean
				menu_id:菜单ID(报表ID)
				data_date:数据日期（格式：YYYYMMDD）
			    disp_view:数据更新摘要信息
	 ********************************************************/	
	public ResponsePropertyList<DataDateBean> getDataDateList();

	/******************************************************
	 《第2部分》 业务功能-报表
	********************************************************/	
	/******************************************************
	2.1	getHomeReportList【获取首页报表目录列表】 
	函数说明：获取首页报表目录列表
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
	
	返回值：表单列表
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	   <soap:Body>
	      <ns1:getHomeReportListResponse xmlns:ns1="http://service.mobile.apps.com">
	         <ns1:out>
	            <entityList xmlns="http://domain.mobile.apps.com">
	               <xsd:anyType xsi:type="HomeReportBean">
	                  <max_date>20101001</max_date>
	                  <rep_id>E8EC7F3DAC6D2168E043879448E72168</rep_id>
	                  <rep_name>用户计费时长</rep_name>
	               </xsd:anyType>
	            </entityList>
	            <message xmlns="http://domain.mobile.apps.com">成功</message>
	            <pageNo xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
	            <pageSize xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
	            <status xmlns="http://domain.mobile.apps.com">0</status>
	            <totalCount xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
	         </ns1:out>
	      </ns1:getHomeReportListResponse>
	   </soap:Body>
	</soap:Envelope>
	
	字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述
		(3)entity：返回登陆用户的对象HomeReportBean
			rep_id:报表ID
			rep_name:报表名称
		    max_date:最新数据日期(默认日期)
	 ********************************************************/	
	public ResponsePropertyList<HomeReportBean> getHomeReportList(@WebParam(name = "ticket") String ticket);
	/******************************************************
	2.2	getHomeReport 【获取通用报表数据列表的接口】
	函数说明： 根据父节点获取子菜单
	参数说明：
		String ticket			 用户ID（登陆成功时，系统返回的ticket）
		String repId			 报表ID
		String orgId			 组织机构ID
		String statDate			 数据日期
		String posorgId          组织机构身份ID
	返回值：菜单列表
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	   <soap:Body>
	      <ns1:getHomeReportResponse xmlns:ns1="http://service.mobile.apps.com">
	         <ns1:out>
	            <entity xsi:type="ReportBean" xmlns="http://domain.mobile.apps.com">
	               <dataList>
	                  <ns1:ArrayOfString>
	                     <ns1:string>-1</ns1:string>
	                     <ns1:string>全省</ns1:string>
	                     <ns1:string>194521493</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>930</ns1:string>
	                     <ns1:string>临夏</ns1:string>
	                     <ns1:string>11480110</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>931</ns1:string>
	                     <ns1:string>兰州</ns1:string>
	                     <ns1:string>46295193</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>932</ns1:string>
	                     <ns1:string>定西</ns1:string>
	                     <ns1:string>14526703</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>933</ns1:string>
	                     <ns1:string>平凉</ns1:string>
	                     <ns1:string>12512513</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>934</ns1:string>
	                     <ns1:string>庆阳</ns1:string>
	                     <ns1:string>20047373</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>935</ns1:string>
	                     <ns1:string>武威</ns1:string>
	                     <ns1:string>11715224</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>936</ns1:string>
	                     <ns1:string>张掖</ns1:string>
	                     <ns1:string>8059844</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>937</ns1:string>
	                     <ns1:string>酒泉</ns1:string>
	                     <ns1:string>10417127</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>938</ns1:string>
	                     <ns1:string>天水</ns1:string>
	                     <ns1:string>19393909</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>939</ns1:string>
	                     <ns1:string>陇南</ns1:string>
	                     <ns1:string>14396436</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>941</ns1:string>
	                     <ns1:string>甘南</ns1:string>
	                     <ns1:string>5976583</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>943</ns1:string>
	                     <ns1:string>白银</ns1:string>
	                     <ns1:string>12337133</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>945</ns1:string>
	                     <ns1:string>金昌</ns1:string>
	                     <ns1:string>4251784</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	                  <ns1:ArrayOfString>
	                     <ns1:string>947</ns1:string>
	                     <ns1:string>嘉峪关</ns1:string>
	                     <ns1:string>3111561</ns1:string>
	                     <ns1:string>0</ns1:string>
	                     <ns1:string>0</ns1:string>
	                  </ns1:ArrayOfString>
	               </dataList>
	               <hasChild>true</hasChild>
	               <title>
	                  <ns1:string>地域</ns1:string>
	                  <ns1:string>通话时长</ns1:string>
	                  <ns1:string>同比</ns1:string>
	                  <ns1:string>环比</ns1:string>
	               </title>
	            </entity>
	            <message xmlns="http://domain.mobile.apps.com">成功</message>
	            <status xmlns="http://domain.mobile.apps.com">0</status>
	         </ns1:out>
	      </ns1:getHomeReportResponse>
	   </soap:Body>
	</soap:Envelope>

	字段说明：
	(1)status:返回状态；
		0:成功;
		1:没有当前报表的配置信息，请联系管理员！
	   -1：服务端异常
	   -2:无效的ticket
	(2)message:返回结果描述
	(3)entity：返回登陆用户的对象ReportBean
		hasChild:是否下钻(true or false)
		List<String> title:报表表头List
	    List<List<String>> dataList:报表数据List,外层是行，内层是列
	********************************************************/
	public ResponseProperty<ReportBean> getHomeReport(@WebParam(name = "ticket") String ticket,
			@WebParam(name = "repId")String repId, @WebParam(name = "orgId")String orgId,
			@WebParam(name = "statDate")String statDate, @WebParam(name = "posorgId")String posorgId);	
	
	/******************************************************
	2.3	getMenuList【获取首页菜单列表】 
	函数说明：获取首页菜单列表
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
		String tab_id			Tab菜单ID
		String menu_lvl         菜单层级
		String user_id          用户ID
	返回值：表单列表
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	   <soap:Body>
	      <ns1:getMenuListResponse xmlns:ns1="http://service.mobile.apps.com">
	         <ns1:out>
	            <entityList xmlns="http://domain.mobile.apps.com">
	               <xsd:anyType xsi:type="MenuBean">
	                  <isnew xsi:nil="true"/>
	                  <menu_id>1</menu_id>
	                  <menu_lvl>1</menu_lvl>
	                  <menu_name>用户发展</menu_name>
	                  <menu_pic_name>1.png</menu_pic_name>
	                  <menu_pos>1</menu_pos>
	                  <menu_style xsi:nil="true"/>
	                  <rep_id xsi:nil="true"/>
	                  <super_menu>0</super_menu>
	               </xsd:anyType>
	               <xsd:anyType xsi:type="MenuBean">
	                  <isnew xsi:nil="true"/>
	                  <menu_id>2</menu_id>
	                  <menu_lvl>1</menu_lvl>
	                  <menu_name>业务量</menu_name>
	                  <menu_pic_name>2.png</menu_pic_name>
	                  <menu_pos>2</menu_pos>
	                  <menu_style xsi:nil="true"/>
	                  <rep_id xsi:nil="true"/>
	                  <super_menu>0</super_menu>
	               </xsd:anyType>
	               <xsd:anyType xsi:type="MenuBean">
	                  <isnew xsi:nil="true"/>
	                  <menu_id>3</menu_id>
	                  <menu_lvl>1</menu_lvl>
	                  <menu_name>收入情况</menu_name>
	                  <menu_pic_name>3.png</menu_pic_name>
	                  <menu_pos>3</menu_pos>
	                  <menu_style xsi:nil="true"/>
	                  <rep_id xsi:nil="true"/>
	                  <super_menu>0</super_menu>
	               </xsd:anyType>
	            </entityList>
	            <message xmlns="http://domain.mobile.apps.com">成功</message>
	            <pageNo xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
	            <pageSize xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
	            <status xmlns="http://domain.mobile.apps.com">0</status>
	            <totalCount xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
	         </ns1:out>
	      </ns1:getMenuListResponse>
	   </soap:Body>
	</soap:Envelope>
	
	字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述
		(3)entity：返回菜单的对象MenuBean
			menu_id:菜单ID
			menu_name:菜单名称
			super_menu:父ID（根目录的父ID是0）
			menu_lvl:菜单层次（menu_lvl=0的不是菜单，是报表的表头）
			menu_pic_name:菜单对应的图片名称（与本地图片库中的图片名称对应）
			menu_pos:菜单组内排序
			menu_style:菜单样式（保留，暂时不启用）
			isnew:标示是否是新增菜单（保留，暂时不启用）
			rep_id:报表ID
			remark:菜单描述
	 ********************************************************/		
	//public ResponsePropertyList<MenuBean> getMenuList(@WebParam(name = "ticket") String ticket,@WebParam(name = "tab_id") String tab_id);
	public ResponsePropertyList<MenuBean> getMenuList(@WebParam(name = "ticket") String ticket,@WebParam(name = "tab_id") String tab_id,@WebParam(name = "super_menu") String super_menu,@WebParam(name = "user_id") String user_id);
	
	/******************************************************
	2.4	getSubReportList【获取一级菜单下的报表列表】 
	函数说明：获取首页报表目录列表
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
		String tab_id			Tab菜单ID
		String parentId			一级菜单的菜单ID
		
	返回值：表单列表
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	   <soap:Body>
	      <ns1:getHomeReportListResponse xmlns:ns1="http://service.mobile.apps.com">
	         <ns1:out>
	            <entityList xmlns="http://domain.mobile.apps.com">
	               <xsd:anyType xsi:type="HomeReportBean">
	                  <max_date>20101001</max_date>
	                  <rep_id>E8EC7F3DAC6D2168E043879448E72168</rep_id>
	                  <rep_name>用户计费时长</rep_name>
	               </xsd:anyType>
	            </entityList>
	            <message xmlns="http://domain.mobile.apps.com">成功</message>
	            <pageNo xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
	            <pageSize xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
	            <status xmlns="http://domain.mobile.apps.com">0</status>
	            <totalCount xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
	         </ns1:out>
	      </ns1:getHomeReportListResponse>
	   </soap:Body>
	</soap:Envelope>
	
	字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述
		(3)entity：返回登陆用户的对象HomeReportBean
			rep_id:报表ID
			rep_name:报表名称
		    max_date:最新数据日期(默认日期)
	 ********************************************************/	
	public ResponsePropertyList<HomeReportBean> getSubReportList(@WebParam(name = "ticket") String ticket,
			@WebParam(name = "tab_id") String tab_id,@WebParam(name = "parentId") String parentId);
	/******************************************************
	2.4	getTodayFocus【获取今日焦点信息】 
	函数说明：获取今日焦点信息
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
		
	返回值：表单列表
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	   <soap:Body>
	      <ns1:getTodayFocusResponse xmlns:ns1="http://service.mobile.apps.com">
	         <ns1:out>
	            <entity xsi:type="xsd:string" xmlns="http://domain.mobile.apps.com"><![CDATA[当日新增<font color=#00ccff>19452.1</font>万户;当月新增<font color=#00ccff></font>万户,环比<font color=#00ccff>0%</font><br>当日离网<font color=#00ccff>19452.1</font>万户;当月离网<font color=#00ccff></font>万户,环比<font color=#00ccff>%</font><br>当月通话时长<font color=#00ccff>0</font>万分钟,环比下降<font color=#00ccff>0%</font><br>当日收入<font color=#00ccff>19452.1</font>万元;当月收入<font color=#00ccff></font>万户,环比<font color=#00ccff>0%</font>]]></entity>
	            <message xmlns="http://domain.mobile.apps.com">成功</message>
	            <status xmlns="http://domain.mobile.apps.com">0</status>
	         </ns1:out>
	      </ns1:getTodayFocusResponse>
	   </soap:Body>
	</soap:Envelope>
	
	字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述
		(3)entity：返回今日焦点信息内容(html字符串)
	 ********************************************************/	
	public ResponseProperty<String> getTodayFocus(@WebParam(name = "ticket") String ticket);
	
	/*********************************
	 	函数说明：获得量化薪酬社会53019
	参数说明：
		String ticket	用户ID（登陆成功时，系统返回的ticket）
		
	返回值：表单列表
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soap:Body>
      <ns1:getRepDateResponse xmlns:ns1="http://service.mobile.apps.com">
         <ns1:out>
            <entityList xmlns="http://domain.mobile.apps.com">
               <xsd:anyType xsi:type="ns1:anyType2anyTypeMap">
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">数据日期</key>
                     <value xsi:type="xsd:decimal">201409</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">市区总积分</key>
                     <value xsi:type="xsd:decimal">453</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">所在市区量化薪酬总额</key>
                     <value xsi:type="xsd:decimal">5028</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">团队奖励系数</key>
                     <value xsi:type="xsd:decimal">1</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">片区名称</key>
                     <value xsi:type="xsd:string">景泰县长城片区</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">职级工资</key>
                     <value xsi:type="xsd:decimal">1336</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">量化薪酬</key>
                     <value xsi:type="xsd:decimal">876</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">地市名称</key>
                     <value xsi:type="xsd:string">白银</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">县区名称</key>
                     <value xsi:type="xsd:string">景泰县</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">工号名称</key>
                     <value xsi:type="xsd:string">辛彦丽</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">所在市区人均量化薪酬额度</key>
                     <value xsi:type="xsd:decimal">838</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">个人积分</key>
                     <value xsi:type="xsd:decimal">79</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">员工积分占比</key>
                     <value xsi:type="xsd:decimal">17</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">工号编码</key>
                     <value xsi:type="xsd:string">43021005</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">员工职位</key>
                     <value xsi:type="xsd:string">渠道及终端经理</value>
                  </entry>
               </xsd:anyType>
            </entityList>
            <message xmlns="http://domain.mobile.apps.com">成功</message>
            <pageNo xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
            <pageSize xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
            <status xmlns="http://domain.mobile.apps.com">0</status>
            <totalCount xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
         </ns1:out>
      </ns1:getRepDateResponse>
   </soap:Body>
</soap:Envelope>	
	字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述
		(3)entity：返回内容集合(html字符串)
	 *********************************************************************************/
	public ResponseProperty<LhxcSheHui> getRepDate(@WebParam(name = "ticket") String ticket,@WebParam(name = "date")String date);
	
	/*********************************
 	函数说明：获得量化薪酬自有53019
参数说明：
	String ticket	用户ID（登陆成功时，系统返回的ticket）
	String date 	时间date
返回值：表单列表
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soap:Body>
      <ns1:getRepLhxcZiYouResponse xmlns:ns1="http://service.mobile.apps.com">
         <ns1:out>
            <entityList xmlns="http://domain.mobile.apps.com">
               <xsd:anyType xsi:type="ns1:anyType2anyTypeMap">
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">绩效奖金占比</key>
                     <value xsi:type="xsd:decimal">1</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">所在县区人均量化薪酬额度</key>
                     <value xsi:type="xsd:decimal">1290</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员编码</key>
                     <value xsi:type="xsd:string">30410064</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">所在县区总积分</key>
                     <value xsi:type="xsd:decimal">40486</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">基础积分</key>
                     <value xsi:type="xsd:decimal">0</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">绩效得分</key>
                     <value xsi:type="xsd:decimal">90</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">职级工资</key>
                     <value xsi:type="xsd:decimal">995</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">量化薪酬</key>
                     <value xsi:type="xsd:decimal">667</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业厅绩效系数</key>
                     <value xsi:type="xsd:decimal">1</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">县区营业员人均绩效得分</key>
                     <value xsi:type="xsd:decimal">90</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业厅编码</key>
                     <value xsi:type="xsd:string">30000081</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">县区名称</key>
                     <value xsi:type="xsd:string">永靖县</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员职级</key>
                     <value xsi:type="xsd:string">2</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业厅绩效分值</key>
                     <value xsi:type="xsd:decimal">89</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员职位</key>
                     <value xsi:type="xsd:string">营业员</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业厅名称</key>
                     <value xsi:type="xsd:string">永靖县广场中心营业厅</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">数据日期</key>
                     <value xsi:type="xsd:decimal">201409</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">团队奖励系数</key>
                     <value xsi:type="xsd:decimal">1</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">岗位系数</key>
                     <value xsi:type="xsd:decimal">2</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">地市名称</key>
                     <value xsi:type="xsd:string">临夏</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">挂钩绩效比例</key>
                     <value xsi:type="xsd:decimal">1</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员积分占比</key>
                     <value xsi:type="xsd:decimal">3</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业厅绩效等级</key>
                     <value xsi:type="xsd:string">待提升</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员名称</key>
                     <value xsi:type="xsd:string">祁平兄</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">业务总积分</key>
                     <value xsi:type="xsd:decimal">1020</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">个人总积分</key>
                     <value xsi:type="xsd:decimal">1020</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">所在县区营业员数</key>
                     <value xsi:type="xsd:decimal">26</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">所在县区量化薪酬总额</key>
                     <value xsi:type="xsd:decimal">33540</value>
                  </entry>
               </xsd:anyType>
            </entityList>
            <message xmlns="http://domain.mobile.apps.com">成功</message>
            <pageNo xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
            <pageSize xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
            <status xmlns="http://domain.mobile.apps.com">0</status>
            <totalCount xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
         </ns1:out>
      </ns1:getRepLhxcZiYouResponse>
   </soap:Body>
</soap:Envelope>
字段说明：
	(1)status:返回状态；
		0:成功;
	   -1：服务端异常
	   -2:无效的ticket
	(2)message:返回结果描述
	(3)entity：返回内容集合(html字符串)
 *********************************************************************************/

	public ResponseProperty<LhxcZiYou> getRepLhxcZiYou(@WebParam(name = "ticket") String ticket,@WebParam(name = "date")String date);
	/*********************************
 	函数说明：53013营业员积分占比查询(日)
参数说明：
	String ticket	用户ID（登陆成功时，系统返回的ticket）
	String date 	时间date
返回值：表单列表
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soap:Body>
      <ns1:getRepLhxcTradeDayResponse xmlns:ns1="http://service.mobile.apps.com">
         <ns1:out>
            <entityList xmlns="http://domain.mobile.apps.com">
               <xsd:anyType xsi:type="ns1:anyType2anyTypeMap">
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业厅名称</key>
                     <value xsi:type="xsd:string">武山县洛门镇洛门营业厅</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">数据日期</key>
                     <value xsi:type="xsd:decimal">20141027</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员积分县区内排名</key>
                     <value xsi:type="xsd:decimal">14</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">县区总积分</key>
                     <value xsi:type="xsd:decimal">421</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">县区名称</key>
                     <value xsi:type="xsd:string">武山县</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">员工积分占比(%)</key>
                     <value xsi:type="xsd:decimal">0</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员工号</key>
                     <value xsi:type="xsd:string">38060061</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员名称</key>
                     <value xsi:type="xsd:string">王文霞</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">员工类型</key>
                     <value xsi:type="xsd:string">营业员</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">业务积分</key>
                     <value xsi:type="xsd:decimal">1</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">县区人均积分值</key>
                     <value xsi:type="xsd:decimal">30</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">地市名称</key>
                     <value xsi:type="xsd:string">天水</value>
                  </entry>
               </xsd:anyType>
            </entityList>
            <message xmlns="http://domain.mobile.apps.com">成功</message>
            <pageNo xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
            <pageSize xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
            <status xmlns="http://domain.mobile.apps.com">0</status>
            <totalCount xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
         </ns1:out>
      </ns1:getRepLhxcTradeDayResponse>
   </soap:Body>
</soap:Envelope>
字段说明：
	(1)status:返回状态；
		0:成功;
	   -1：服务端异常
	   -2:无效的ticket
	(2)message:返回结果描述
	(3)entity：返回内容集合(html字符串)
 *********************************************************************************/

	public ResponseProperty<LhxcTradeDay> getRepLhxcTradeDay(@WebParam(name = "ticket") String ticket,@WebParam(name = "date")String date);

	/*********************************
 	函数说明：获得53013营业员积分占比查询(月)
参数说明：
	String ticket	用户ID（登陆成功时，系统返回的ticket）
	String date 	时间date
返回值：表单列表
	<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soap:Body>
      <ns1:getRepLhxcTradeMonResponse xmlns:ns1="http://service.mobile.apps.com">
         <ns1:out>
            <entityList xmlns="http://domain.mobile.apps.com">
               <xsd:anyType xsi:type="ns1:anyType2anyTypeMap">
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业厅名称</key>
                     <value xsi:type="xsd:string">外语学院营业厅</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">数据日期</key>
                     <value xsi:type="xsd:decimal">201409</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员积分县区内排名</key>
                     <value xsi:type="xsd:decimal">5</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">县区总积分</key>
                     <value xsi:type="xsd:decimal">41869</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">县区名称</key>
                     <value xsi:type="xsd:string">榆中县公司</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">员工积分占比(%)</key>
                     <value xsi:type="xsd:decimal">7</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员工号</key>
                     <value xsi:type="xsd:string">31700425</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">营业员名称</key>
                     <value xsi:type="xsd:string">刘宗英</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">员工类型</key>
                     <value xsi:type="xsd:string">营业员</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">业务积分</key>
                     <value xsi:type="xsd:decimal">0</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">县区人均积分值</key>
                     <value xsi:type="xsd:decimal">2204</value>
                  </entry>
                  <entry xmlns="http://service.mobile.apps.com">
                     <key xsi:type="xsd:string">地市名称</key>
                     <value xsi:type="xsd:string">兰州</value>
                  </entry>
               </xsd:anyType>
            </entityList>
            <message xmlns="http://domain.mobile.apps.com">成功</message>
            <pageNo xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
            <pageSize xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
            <status xmlns="http://domain.mobile.apps.com">0</status>
            <totalCount xsi:nil="true" xmlns="http://domain.mobile.apps.com"/>
         </ns1:out>
      </ns1:getRepLhxcTradeMonResponse>
   </soap:Body>
</soap:Envelope>
字段说明：
	(1)status:返回状态；
		0:成功;
	   -1：服务端异常
	   -2:无效的ticket
	(2)message:返回结果描述
	(3)entity：返回内容集合(html字符串)
 *********************************************************************************/

	public ResponseProperty<LhxcTradeMon> getRepLhxcTradeMon(@WebParam(name = "ticket") String ticket,@WebParam(name = "date")String date);


	/*********************************
	 	函数说明：获得趋势图
	参数说明：
		order_id
		statis_date
		org_id
	返回值：表单列表
	*************************************************************************************/
	public ResponseProperty<TendencyMap> getTendencyMapInfo(@WebParam(name = "order_id") String order_id,@WebParam(name = "statis_date") String statis_date,@WebParam(name = "org_id")String org_id);


	/*********************************
	 	函数说明：用户定制应用列表信息
	参数说明：
		String userId	用户ID（登陆成功时，系统返回的ticket）
	返回值：表单列表
	*************************************************************************************/
	public ResponsePropertyList<Customization> getCustomizationMapList(@WebParam(name = "userId") String userId);

	
	
	/*********************************
	 	函数说明：图表配置信息ChartTitle
	参数说明：
		String chartId	图表id
		String dimId	维度id,1:时间,2:地域
	返回值：表单列表
	 *************************************************************************************/
	public ResponsePropertyList<ChartTitle> getChartTitleList(@WebParam(name = "chartId") String chartId,@WebParam(name = "dimId") String dimId);


	/*********************************
		函数说明：图表配置信息ChartMid
		参数说明：
			String chartId	图表id
			String dimId	维度id,1:时间,2:地域
			String orgId	区域id
			String cdate	    时间
		返回值：表单列表
	*************************************************************************************/
	public ResponsePropertyList<ChartDim> getChartDimList(@WebParam(name = "chartId") String chartId,@WebParam(name = "dimId") String dimId
		,@WebParam(name = "orgId") String orgId,@WebParam(name = "cdate") String cdate );

	

	/*********************************
		函数说明：图表配置信息ChartCondition
		参数说明：
			String chartId	图表id
			String dimId	维度id,1:时间,2:地域
			String date	    时间
		返回值：表单列表
	*************************************************************************************/
	public ResponsePropertyList<ChartCondition> getChartConditionList(@WebParam(name = "chartId") String chartId,@WebParam(name = "dimId") String dimId
		,@WebParam(name = "date") String date );

	
	/*********************************
	函数说明：图表类型信息ChartType
	参数说明：
		String chartId	图表id
		String dimId	维度id,1:时间,2:地域
	返回值：单个图表类型对象
*************************************************************************************/
public ResponseProperty<ChartType> getChartType(@WebParam(name = "chartId") String chartId,
												@WebParam(name = "dimId") String dimId);
/******************************************************
《第3部分》 新增功能接口
********************************************************/	
/******************************************************
3.1 getMarketGroupList【获取营销活动列表】 
	函数说明：获取营销活动列表
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
	返回值字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述	
		(3)entityList：返回MarketGroupBean列表
			market_id:营销活动ID
			market_name:营销活动名称
			is_class:新增加，是否需要过滤条件，1是，0否
 ********************************************************/	
public ResponsePropertyList<MarketGroupBean> getMarketGroupList(@WebParam(name = "ticket") String ticket);
/******************************************************
3.2 getMarketList【获取营销列表】 
	函数说明：获取营销活动列表
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
		String market_id		营销活动ID
		String class_id			过滤条件（不启用时传空字符串）
	返回值字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述	
		(3)entityList：返回MarketBean列表
			serv_number:号码
			user_name:姓名
			brand_name:品牌
			marketing_case_name:营销案
			credit_name:信誉度等级
			net_dur:网龄
			usim_flag:是否USIM卡
			arpu:ARPU
			call_dur:通话时长
			call_cnt:通话次数
			flux_2g:2G流量
			flux_3g:3G流量
			flux_4g:4G流量
			point_cnt:积分余额
			campseg_content:营销用语
			detain_info_cur:维系内容			
 ********************************************************/	
public ResponsePropertyList<MarketBean> getMarketList(@WebParam(name = "ticket") String ticket,
		@WebParam(name = "market_id") String market_id,@WebParam(name = "class_id") String class_id);
/******************************************************
3.3 detainMarketExec【获取营销活动列表】 
	函数说明：获取营销活动列表
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
		String market_id		营销活动ID
		String serv_number		电话号码
		String line_info		联系方式（电话|短信|上门））
		String isnt_trace	是否跟踪（0：否，1：是）	
		String exec_note	备注		
	返回值字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述		
 ********************************************************/	
public ResponseEmptyProperty detainMarketExec(@WebParam(name = "ticket") String ticket,
		@WebParam(name = "market_id") String market_id,
		@WebParam(name = "serv_number") String serv_number,
		@WebParam(name = "line_info") String line_info,
		@WebParam(name = "isnt_trace") String isnt_trace,
		@WebParam(name = "exec_note") String exec_note);

/******************************************************
3.4 已营销列表【获取营销活动列表】 
	函数说明：获取营销活动列表
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
	返回值字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述	
		(3)entityList：返回Map列表
			MARKET_ID:营销活动ID
			MARKET_NAME:营销活动名称
			FINISH_RATE:当前完成情况
 ********************************************************/	
public ResponsePropertyList<Map> getMarketGroupExecList(@WebParam(name = "ticket") String ticket);
/******************************************************
3.5 getMarketExecList【获取已营销清单列表】 
	函数说明：获取营销活动列表
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
		String market_id		营销活动ID
	返回值字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述	
		(3)entityList：返回Map列表
			SERV_NUMBER:号码
			USER_NAME:姓名
			BRAND_NAME:品牌
			MARKETING_CASE_NAME:营销案
			CREDIT_NAME:信誉度等级
			NET_DUR:网龄
			USIM_FLAG:是否USIM卡
			ARPU:ARPU
			CALL_DUR:通话时长
			CALL_CNT:通话次数
			FLUX_2G:2G流量
			FLUX_3G:3G流量
			FLUX_4G:4G流量
			POINT_CNT:积分余额
			CAMPSEG_CONTENT:营销用语
		    EXEC_TIME:营销时间
		    LINE_INFO:营销方式
		    EXEC_DETAIL:营销备注				
 ********************************************************/	
public ResponsePropertyList<Map> getMarketExecList(@WebParam(name = "ticket") String ticket,
		@WebParam(name = "market_id") String market_id);
/******************************************************
3.6 getMarketClassList【获取过滤条件】 
	函数说明：获取营销活动列表
	参数说明：
		String ticket			用户ID（登陆成功时，系统返回的ticket）
		String market_id		营销活动ID
	返回值字段说明：
		(1)status:返回状态；
			0:成功;
		   -1：服务端异常
		   -2:无效的ticket
		(2)message:返回结果描述	
		(3)entityList：返回Map列表
			CLASS_ID:条件ID
			CLASS_NAME:条件名称
 ********************************************************/	
public ResponsePropertyList<Map> getMarketClassList(@WebParam(name = "ticket") String ticket,
		@WebParam(name = "market_id") String market_id);
}
