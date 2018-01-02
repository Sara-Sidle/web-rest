<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
</head>

<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="1"
		bgcolor="#e8ecf0" class="listtable">
		<tr>
			<th>返回码</th>
			<th>信息</th>
		</tr>
		<tr>
			<td align="center"><c:out value="${code}" />
			</td>
			<td align="center"><c:out value="${msg}" />
			</td>
		</tr>
	</table>

	<%@ include file="/inc/footer.jsp"%>