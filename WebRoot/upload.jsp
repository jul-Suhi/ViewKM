<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>文件上传</title>
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
 <s:form action="UploadFile"  method="post" enctype="multipart/form-data">
   	<s:file label="上传文件 " name="upload"/>
   	<s:textfield label="新文件名" name="filename"/>
   	<s:submit value="上传"/>
 </s:form>
 </div>
  </body>
</html>