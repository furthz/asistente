<%@ include file="/WEB-INF/views/include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Multiple File Upload Result</title>
</head>
<body>
    <p>Uploading of file(s)
        <c:forEach items="${files}" var="file">
            ${file.getOriginalFilename()},
        </c:forEach>
   are successful </p>
</body>
</html>