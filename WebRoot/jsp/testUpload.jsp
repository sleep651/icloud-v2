<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
%>
<html>
 <head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>上传文件接口测试页面</title>
 </head>
 <body>
	 <form method="post" action="<%=path%>/MobileAction.do?method=upload" enctype="multipart/form-data">
		<input type="file" name="fileName" size="25" />
		<span class="info">&nbsp;&nbsp;(文件类型：.txt,.doc,.docx,.xls,.xlsx,.csv,.xlsm,.ppt,.pptx,.pdf,.jpg,.jpeg,.png,.gif,.zip,.rar,.7z)</span>&nbsp;&nbsp;<input type="submit" value="上传"/><br>
		sessionId:<input type="input" name="sessionId"/><br>
		folderName:<input type="input" name="folderName"/><br>
		fileName:<input type="input" name="fileName" value="fileName"/><br>
		newFileName:<input type="input" name="newFileName"/><br>
		<br><br>
		返回值说明：0:成功; -1:服务端异常; 1:验证错误; -2:sessionId错误或session已失效;
	</form>
 </body>
</html>
