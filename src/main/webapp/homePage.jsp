<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/inc/header.jsp"%>
<title>首页</title>
<!-- Flot demo data -->
<script src="${pageContext.request.contextPath}/static/js/demo/flot-demo.js"></script>

<!-- Flot -->
<script src="${pageContext.request.contextPath}/static/js/plugins/flot/jquery.flot.js"></script>
<script src="${pageContext.request.contextPath}/static/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="${pageContext.request.contextPath}/static/js/plugins/flot/jquery.flot.pie.js"></script>
<style>
#index_content .ibox-content {
	min-height: 100px;
}

#index_table {
	margin-bottom: 0px;
}

#index_table>thead>tr>td,#index_table>tbody>tr>td {
	line-height: 1.305;
}


</style>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight"
		id="index_content">
		<div class="row">
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>表格</h5>
					</div>
					<div class="ibox-content">
						<table class="table" id="index_table">
							<thead>
								<tr>
									<th>#</th>
									<th>姓名</th>
									<th>性别</th>
									<th>年龄</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>张三</td>
									<td>男</td>
									<td>23</td>
								</tr>
								<tr>
									<td>2</td>
									<td>李四</td>
									<td>男</td>
									<td>27</td>
								</tr>
								<tr>
									<td>3</td>
									<td>王麻子</td>
									<td>男</td>
									<td>65</td>
								</tr>
								<tr>
									<td>4</td>
									<td>李四</td>
									<td>男</td>
									<td>27</td>
								</tr>
								<tr>
									<td>5</td>
									<td>李四</td>
									<td>男</td>
									<td>27</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>图表</h5>
					</div>
					<div class="ibox-content">
						<div class="flot-chart">
							<div class="flot-chart-content" id="flot-line-chart-moving"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>标题三</h5>
					</div>
					<div class="ibox-content"></div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>标题四</h5>
					</div>
					<div class="ibox-content"></div>
				</div>
			</div>
		</div>

	</div>

	<!-- Page-Level Scripts -->
	<script>
		$(document).ready(function() {

		});
	</script>
	
<%@ include file = "/inc/footer.jsp" %>
