package com.apps.mobile.action;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.apps.common.utils.CommonUploadTools;
import com.apps.common.utils.FileUploadInfo;
import com.apps.mobile.domain.InvalidTicketException;
import com.apps.mobile.domain.Ticket;
import com.apps.mobile.domain.UserAccount;
import com.teamsun.core.action.BaseAction;

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
            String newFileName = request.getParameter("newFileName"); //file控件的名称
            
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
