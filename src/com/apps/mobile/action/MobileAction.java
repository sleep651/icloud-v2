package com.apps.mobile.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apps.common.utils.CommonUploadTools;
import com.apps.common.utils.FileUploadInfo;
import com.apps.common.utils.WsConstants;
import com.apps.mobile.domain.InvalidTicketException;
import com.apps.mobile.domain.ResponseUploadRet;
import com.apps.mobile.domain.Ticket;
import com.apps.mobile.domain.UserAccount;
import com.teamsun.core.action.BaseAction;
import com.teamsun.core.utils.FileUtils;

@Controller
@RequestMapping("/MobileAction.do")
public class MobileAction extends BaseAction{
    /**
     * @param request
     * @param response
     * @return status : 0:成功; -1:服务端异常; 1:验证错误; -2:sessionId错误或session已失效;
     * @throws IOException
     */
    @RequestMapping(params = "method=upload")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	PrintWriter out = response.getWriter();
        int status = 1;
        try {
            String sessionId = request.getParameter("sessionId"); //登陆信息sessionId
            String folderName = request.getParameter("folderName"); //文件目录
            String fileName = request.getParameter("fileName"); //file控件的名称
            String newFileName = request.getParameter("newFileName"); //上传后文件名称
            
            //判断当前sessionId
        	UserAccount userAccount = checkTicket(sessionId);
        	if(userAccount != null){
                FileUploadInfo file = CommonUploadTools.uploadMultipartFileReturnInfo(request, fileName,
                		newFileName,
                        ".txt,.doc,.docx,.xls,.xlsx,.csv,.xlsm,.ppt,.pptx,.pdf,.jpg,.jpeg,.png,.gif,.zip,.rar,.7z",
                        folderName);
                if(file!=null){
                	status = 0;            
                }
                else{
                	status = -1;
                }
        	}
        	else{
        		status = -2;
        	}
        } catch (Exception e) {
            status = -1;
            e.printStackTrace();
        }
        out.write(status + "");
        out.close();
    }
    /***********************************
     	uploadRetJson【文件上传接口】
		参数说明：
		    sessionId:登陆信息sessionId
		    folderName:为上传文件制定目录
		    newFileName:上传后文件名称
		    fileName:file控件的名称
		    isAdmin：是否管理员:1管理员(忽略sessionId验证),0不是管理员(需要sessionId验证)
		返回值说明：
      		json 对象{
						status : 0:成功; -1:服务端异常; 1:验证错误; -2:sessionId错误或session已失效;
						message:结果描述
						url :上传文件对应的url
      				}
      	例如:{"message":"上传成功！","status":0,"url":"http://135.148.72.211:9999/UPLOAD_DIR/other/IMG1412928698072.jpg"}
		post表单示例：
     	<form method="post" action="http://localhost:8080/icloud-v2/MobileAction.do?method=uploadRetJson" enctype="multipart/form-data">
			<input type="hidden"  name="sessionId"  value="1"/>
			<input type="hidden"  name=" folderName"  value="report"/>
			<input type="hidden"  name=" newFileName"  value=""/>
			<input type="hidden"  name="fileName"  value="logFile"/>
			<input type="hidden"  name="isAdmin" value="1"/>
			<input type="file"  name="logFile" />
			<input type="submit" value="提交"/>
		</form>
     **********************/
    @RequestMapping(params = "method=uploadRetJson")
    public void uploadRetJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
    	response.setCharacterEncoding("UTF-8");  
    	PrintWriter out = response.getWriter();
    	System.out.println("@uploadRetJson begin!");
    	ResponseUploadRet ret = new ResponseUploadRet();
    	try {
    		String sessionId = request.getParameter("sessionId"); //登陆信息sessionId
    		String fileName = request.getParameter("fileName"); //file控件的名称
    		String isAdmin = request.getParameter("isAdmin");//是否管理员:1管理员(忽略sessionId验证),0不是管理员(需要sessionId验证)
    		String folderName = request.getParameter("folderName");//手动设置文件夹
    		String host = FileUtils.getApplicationProperties("upload_host");
    		
    		if(isAdmin==null||"".equals(isAdmin)){
    			isAdmin = "0";//默认是非管理员
    		}
    		if(folderName==null||"".equals(folderName)){
    			folderName = "other";//默认文件夹是other
    		}
    		if("1".equals(isAdmin)){
				String newFileName = "f"+System.currentTimeMillis()+"_"+(int)(Math.random()*9000+1000);
				FileUploadInfo fui = CommonUploadTools.uploadMultipartFileReturnInfo(request, fileName, 
						newFileName, ".txt,.doc,.docx,.xls,.xlsx,.csv,.xlsm,.ppt,.pptx,.pdf,.jpg,.jpeg,.png,.gif,.zip,.rar,.7z", folderName);
				if(fui!=null){
					ret = new ResponseUploadRet(WsConstants.SHT_SUCCESS, "上传成功！", host+fui.getFilePath());
				}else{
					ret = new ResponseUploadRet(WsConstants.SHT_VALIDATION, "上传失败！");
				}
    		}else{
        		//判断当前sessionId
        		UserAccount userAccount = checkTicket(sessionId);
        		if(userAccount != null){
    				String newFileName = "f"+System.currentTimeMillis()+"_"+(int)(Math.random()*9000+1000);
    				FileUploadInfo fui = CommonUploadTools.uploadMultipartFileReturnInfo(request, fileName, 
    						newFileName, "", folderName);
    				if(fui!=null){
    					ret = new ResponseUploadRet(WsConstants.SHT_SUCCESS, "上传成功！", host+fui.getFilePath());
    				}else{
    					ret = new ResponseUploadRet(WsConstants.SHT_VALIDATION, "上传失败！");
    				}
        		}
        		else{
        			ret = new ResponseUploadRet(WsConstants.SHT_NO_SESSION, "sessionId错误或session已失效=" + sessionId);
        		}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		ret = new ResponseUploadRet(WsConstants.SHT_ERROR, "服务端异常:" + e.toString());
    	}
    	//System.out.append(objectToJsonString(ret));
    	out.write(objectToJsonString(ret));
    	out.close();
    }
    /**
     * @param request
     * @param response
     * @return json 对象{
     * 						status : 0:成功; -1:服务端异常; 1:验证错误; -2:sessionId错误或session已失效;
     * 						message:结果描述
     * 						url :上传文件对应的url
     * 					}
     * 例如:{"message":"删除成功！","status":0,"url":null}
     * @throws IOException
     */
    @RequestMapping(params = "method=delFileRetJson")
    public void delFileRetJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
    	response.setCharacterEncoding("UTF-8");  
    	PrintWriter out = response.getWriter();
    	
    	ResponseUploadRet ret = new ResponseUploadRet();
    	try {
    		String sessionId = request.getParameter("sessionId"); //登陆信息sessionId
    		String fileName = request.getParameter("fileName"); //文件名称
    		
    		String isAdmin = request.getParameter("isAdmin");//是否管理员:1管理员(忽略sessionId验证),0不是管理员(需要sessionId验证)
    		String folderName = request.getParameter("folderName");//手动设置文件夹
    		
    		if(isAdmin==null||"".equals(isAdmin)){
    			isAdmin = "0";//默认是非管理员
    		}
    		if(folderName==null||"".equals(folderName)){
    			folderName = "other";//默认文件夹是other
    		}
    		
            String filePath = FileUtils.getApplicationProperties("upload_path");
            String fileFolder = FileUtils.getApplicationProperties("upload_home_name") + "/" + folderName+ "/";
            
            String filePathName = filePath + fileFolder + fileName;
    		if("1".equals(isAdmin)){
    			FileUtils.delFile(filePathName);
    			ret = new ResponseUploadRet(WsConstants.SHT_SUCCESS, "删除成功！", null);
    		}else{
    			//判断当前sessionId
    			UserAccount userAccount = checkTicket(sessionId);
    			if(userAccount != null){
    				FileUtils.delFile(filePathName);
    				ret = new ResponseUploadRet(WsConstants.SHT_SUCCESS, "删除成功！", null);
    			}
    			else{
    				ret = new ResponseUploadRet(WsConstants.SHT_NO_SESSION, "sessionId错误或session已失效=" + sessionId);
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		ret = new ResponseUploadRet(WsConstants.SHT_ERROR, "服务端异常:" + e.toString());
    	}
    	//System.out.append(objectToJsonString(ret));
    	out.write(objectToJsonString(ret));
    	out.close();
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
}
