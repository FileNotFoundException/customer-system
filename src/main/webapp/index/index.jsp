<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript">
function getPath(){
	return '<%=path %>';
}
</script>
<link rel="stylesheet" type="text/css" href="<%=path %>/js/ExtJS5.1.0GPL/packages/ext-theme-crisp/build/resources/ext-theme-crisp-all.css" />
<script type="text/javascript" src="<%=path %>/js/ExtJS5.1.0GPL/ext-all.js"></script>
<script type="text/javascript" src="<%=path %>/js/ExtJS5.1.0GPL/packages/ext-theme-crisp/build/ext-theme-crisp.js"></script>
</head>
<body>

</body>
<script type="text/javascript" src="<%=path %>/index/js/index.js"></script>
</html>