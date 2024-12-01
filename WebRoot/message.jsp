<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>结果</title>
<style type="text/css">
.center-in-center {
	position: absolute;
	top: 50%;
	left: 50%;
	-webkit-transform: translate(-50%, -50%);
	-moz-transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	-o-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
}
</style>
</head>
<body>
	<div class="center-in-center">
		<s:property value="result" />
		<form id="form1" name="from1" method="post" action="kmeansCluster">
			<% String filename = (String)request.getAttribute("sfilename"); 
		%>
			文件：<%=filename%>
			<input type=hidden name="filename" value=<%=filename%> readonly="readonly" /><br /> 
			簇数：<input type="text" name="num" /><br />
			<input type="submit" onclick="ShowBtn()" value="显示" />
		</form>
	</div>
	<script defer="defer" language="JavaScript" type="text/javascript">
		function ShowBtn() {
			window.open("/ViewKM/kmeans_showEcharts.jsp?name=<%=filename%>");
		}
	</script>
</body>
</html>