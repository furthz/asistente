<%@ include file="/WEB-INF/views/include.jsp" %>
<html>
  <head><title><fmt:message key="title"/></title></head>
  <body>
    <h1><fmt:message key="heading"/></h1>    
    <h3>Empresas</h3>
    <c:forEach items="${model.empresas}" var="prod">
      <i><c:out value="${prod.nombre}"/></i><br><br>
    </c:forEach>
    
    <!-- 
    <br>
    <a href="<c:url value="priceincrease.htm"/>">Increase Prices</a>
    <br>
     -->
  </body>
</html>