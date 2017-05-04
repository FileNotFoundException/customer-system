<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript">
function getPath(){
	return '<%=path %>';
}
</script>
<link rel="stylesheet" type="text/css" href="<%=path %>/system-manage/css/index.css" />
<script type="text/javascript" src="<%=path %>/system-manage/js/login.js"></script>
</head>
<body>
<div class="login_header">
	<span class="login_title">Customer-system</span>
</div>
<div class="wrap_login">
  <div class="wrap_top"></div>
  <div class="wrap_body">
  	<form id="form" method="post" action="<%=path%>/LoginController.do?login=true">
      <ul>
        <li class="name">
          <input id="username" name="username" type="text" onkeydown="if(event.keyCode==13){login()}" title="请输入用户名"/>
        </li>
        <li class="password">
          <input id="password" name="password" type="password" onkeydown="if(event.keyCode==13){login()}" title="请输入密码" autocomplete="off"/>
        </li>
        <li class="button">
          <input id="button" type="button" onclick="login()" value="登录"/>
        </li>
        <li class="remember">
          <label style="cursor: pointer;"><input name="remember" type="checkbox" value="true" onfocus="this.blur()"/>&nbsp;记住密码</label>
          <div class="error" id="errorinfo"></div>
        </li>
      </ul>
    </form>
  </div>
  <div class="wrap_bottom"></div>
</div>

</body>
</html>