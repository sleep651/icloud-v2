package com.apps.common.auth;
/*
 访问时需要加以下头信息:
    <soapenv:Header>
	<AuthenticationToken>
		<Username>30c65b5da6f90f803ca24475046755de87daa7611c0140cfc95c4f900e1d53d0</Username>
		<Password>a8597721371b13c0e2e06105fa68ee8a</Password>
	</AuthenticationToken>
   </soapenv:Header>
 * */
import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;
import org.springframework.stereotype.Component;
@Component
public class AuthenticationHandler extends AbstractHandler {

	public void invoke(MessageContext cfx) throws Exception {
		//for no error
	}
	/*public void invoke(MessageContext cfx) throws Exception {
		if (cfx.getInMessage().getHeader() == null) {
			throw new org.codehaus.xfire.fault.XFireFault("请求必须包含验证信息",
					org.codehaus.xfire.fault.XFireFault.SENDER);
		}
		Element token = cfx.getInMessage().getHeader().getChild(
				"AuthenticationToken");
		if (token == null) {
			throw new org.codehaus.xfire.fault.XFireFault("请求必须包含身份验证信息",
					org.codehaus.xfire.fault.XFireFault.SENDER);
		}

		String username = token.getChild("Username").getValue();
		String password = token.getChild("Password").getValue();
		
		CryptUtil crypt = CryptUtil.getInstance();  
		if(username!=null)
		{
			username = crypt.decryptAES(username, crypt.getKey()); 
		}		
		if(password!=null)
		{
			password = crypt.decryptAES(password, crypt.getKey()); 
		}		
		try {
			// 进行身份验证 ，只有gscdm_client_001@teamsun1505的用户为授权用户
	        //gscdm_client_001@teamsun1505
	        //30c65b5da6f90f803ca24475046755de87daa7611c0140cfc95c4f900e1d53d0
	        //a8597721371b13c0e2e06105fa68ee8a	
			if (username.equals("gscdm_client_001") && password.equals("teamsun1505"))
				// 这语句不显示
				System.out.println("身份验证通过");
			else
				throw new Exception();
		} catch (Exception e) {
			throw new org.codehaus.xfire.fault.XFireFault("非法的用户名和密码",
					org.codehaus.xfire.fault.XFireFault.SENDER);
		}
	}*/
}