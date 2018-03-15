<%@ include file="/WEB-INF/views/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><fmt:message key="title"/></title>
  <style>
    .error { color: red; }
  </style>  
</head>
<body>
<h1><fmt:message key="crearempresa.heading"/></h1>
<form:form method="post" commandName="empresa">
  <table >
    <tr>
      <td align="right" width="20%">Ingrese Nombre:</td>
        <td width="20%">
          <form:input path="nombre"/>
        </td>
        <td width="60%">
          <form:errors path="nombre" cssClass="error"/>
        </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="Crear">
</form:form>
<a href="<c:url value="listaempresas.htm"/>">Empresas</a>
</body>
</html>