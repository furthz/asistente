<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


	<table>
		<tr>
			<td style="vertical-align:top"><img src="<c:out value="${objetos.imagen}"/>" height="75%"
				width="75%" /></td>
			<td style="vertical-align:top">Activos
				<table border="1">
					<tr>
						<td>Cuenta</td>
						<td>Valor</td>
					</tr>
					<c:forEach items="${objetos.plan.activo}" var="activo">
						<c:if test="${activo.value > 0 }">
							<tr>
								<td><c:out value="${activo.key}" /></td>
								<td><fmt:formatNumber type="number" pattern="###,###.###" value="${activo.value}" /></td>
								
							</tr>
						</c:if>
					</c:forEach>
				</table> Pasivos
				<table border="1">
					<tr>
						<td>Cuenta</td>
						<td>Valor</td>
					</tr>
					<c:forEach items="${objetos.plan.pasivo}" var="activo">
						<c:if test="${activo.value > 0 }">
							<tr>
								<td><c:out value="${activo.key}" /></td>
								<td><c:out value="${activo.value}" /></td>
							</tr>
						</c:if>
					</c:forEach>
				</table> Patrimonio
				<table border="1">
					<tr>
						<td>Cuenta</td>
						<td>Valor</td>
					</tr>
					<c:forEach items="${objetos.plan.patrimonio}" var="activo">
						<c:if test="${activo.value > 0 }">
							<tr>
								<td><c:out value="${activo.key}" /></td>
								<td><c:out value="${activo.value}" /></td>
							</tr>
						</c:if>
					</c:forEach>
				</table>


			</td>
		</tr>
	</table>




</body>
</html>