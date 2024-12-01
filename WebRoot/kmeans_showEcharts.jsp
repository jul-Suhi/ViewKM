<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>图形界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body>
	<div id="chartArea"
		style="height: 400px; border: 1px solid #ccc; padding: 10px;">图形正在加载中...</div>
	3114006064郑树海
	<br>
	<%String filename = request.getParameter("name");
	 %>
</body>
<!-- 标签引入 -->
<script src="js/esl.js" type="text/javascript"></script>
<script src="js/jquery-1.7.2.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	$(document).ready(function() {
		var url = location.search;
		var id = url.split("?")[1];
		$.ajax({
			type : "get",
			url : "kmeansCluster.action?filename=<%=filename%>",
			data : {
				id : id
			},
			dataType : "json",
			success : function(returndata) {
				var json = eval('(' + returndata.jOption + ')');
				require.config({
					paths : {
						echarts : 'js/echarts', //echarts.js的路径
						'echarts/chart/scatter' : './js/echarts'
					}
				});
				//入口
				require(
					[
						'echarts',
						'echarts/chart/scatter'
					],
					function(ec) {
						var myChart = ec.init(document.getElementById('chartArea'));
						myChart.setOption(json);
					}

				);
			}
		});
	});
</script>
</html>
