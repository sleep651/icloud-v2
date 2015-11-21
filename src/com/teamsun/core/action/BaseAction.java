package com.teamsun.core.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.teamsun.core.domain.CertVO;
import com.teamsun.core.exception.BaseException;
import com.teamsun.core.services.IBaseService;
import com.teamsun.security.domain.CurrUserVO;

@Component
public class BaseAction {
	protected static String SUCCESS = "common/messages";
	protected static String ERROR = "error";
	protected static String INPUT = "input";
	private static final String ACTION_MESSAGE = "ACTION_MESSAGE";
	protected static Logger logger =null ;  
	protected static String className =null ;
	protected static Integer PAGESIZE = 20;
	protected static String WARINGMSG = "msg";
	@Autowired
	@Qualifier("baseService")//因此接口有多个实现类，在此要指定一个实现
	private IBaseService  baseService;
	
	public BaseAction() {
		super();
		className = getClass().getName();
	    logger=LoggerFactory.getLogger(className);
	}
	
    protected CertVO getCert(HttpServletRequest request)  throws BaseException
    {
    	CurrUserVO  cert = (CurrUserVO)request.getSession().getAttribute("Certificate");
        
        if(cert == null){
        	saveMessages(request, "认证信息已失效，请退出重新登录!");
            throw new BaseException("认证信息已失效，请退出重新登录!");
        }
        else{
        	CertVO cvo = new CertVO();
        	cvo.setCuservo(cert);
            return cvo;
        }
    }
    
    protected void saveInfoForRequest(String messages, HttpServletRequest request)
    {
         saveMessages(request, messages);
    }
    protected void saveMessages(HttpServletRequest request, String messages)
    {
    	
        if(messages == null ||messages.equals(""))
        {
            request.removeAttribute(ACTION_MESSAGE);
            return;
        } else
        {
            request.setAttribute(ACTION_MESSAGE, messages);
            return;
        }
    }
    protected void saveObjForRequest(Object obj, HttpServletRequest request, String saveName)
    {
    	String className = saveName;
        if(obj == null)
        {
            request.removeAttribute(className);
            return;
        } else
        {
        	request.setAttribute(className, obj);
            return;
        }
    
    }
   // this.getCurSession().setAttribute( "Certificate",cert);
    
    protected void saveObjForSession(Object obj, HttpServletRequest request, String saveName)
    {
    	String className = saveName;
    	HttpSession session = request.getSession();
        if(obj == null)
        {
            session.removeAttribute(className);
            return;
        } else
        {
        	session.setAttribute(className, obj);
            return;
        }
    
    }
	public String listToJsonString(List listvo){
		/*
		 * 注意:
		 * JSONArray.fromObject(listvo):对listvo中的vo中如果有日期格式要特别注意
		 * */
		JSONArray jsonObject = JSONArray.fromObject(listvo);
		String jsonString = jsonObject.toString();
		return jsonString;
	}
	public String objectToJsonString(Object obj){
		JSONObject object = JSONObject.fromObject(obj);
		String jsonString = object.toString();
		return jsonString;
	}
	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

}

