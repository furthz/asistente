<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<html>
<head>
<title>SOA Professionals</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta charset='utf-8' />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Josefin+Sans" />
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Anton" />

<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/reclamacion.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style.css"/>">
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/> "></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</head>

<body
	style="background:url('<c:url value="/resources/img/FDO2.png"/>') right bottom no-repeat;">

	<div class="pos-f-t">
		<div class="collapse" id="navbarToggleExternalContent">
			<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarsExample08" aria-controls="navbarsExample08"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>

				<div class="collapse navbar-collapse justify-content-md-center"
					id="navbarsExample08">
					<ul class="navbar-nav">
						<li class="nav-item active"><a class="nav-link" href="#">Inicio
								<span class="sr-only">(current)</span>
						</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<c:url value="listaempresas.htm"/>">Ejecuciones</a></li>
						<li class="nav-item"><a class="nav-link"
							href="loadMultipleFileUpload.htm">Subir Archivo</a></li>
						<li class="nav-item"><a class="nav-link" href="#">Resultados</a>
						</li>
					</ul>
				</div>
			</nav>
		</div>
		<nav class="navbar navbar-dark bg-dark">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarToggleExternalContent"
				aria-controls="navbarToggleExternalContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
		</nav>

	</div>


	<div class="cabecera"
		style="background:url('<c:url value="/resources/img/fdo1.png"/> ') no-repeat left top;">
		<div class="cont2">
			<a href="index.html"><img class="logo_m"
				src="<c:url value="/resources/images/logo-soa.png"/>"></a>
		</div>
		<div class="cont6 tit1">Asistente Financiero</div>
		<div class="cont2">
			<img class="logo_m"
				src="<c:url value="/resources/images/banco.jpg"/>">
		</div>
	</div>


	<div class="btn_cont">

		<c:set var="pageListHolder" value="${pageList}" scope="request" />
		<!-- 
		<jsp:useBean id="pageList" scope="request" type="org.springframework.beans.support.PagedListHolder"/>
		<c:url value="/listaempresas.htm" var="pagedLink">
			<c:param name="p" value="~" />
		</c:url>
		
		 -->
		<div class="container">
			<div class="panel panel-default">
				<div class="panel-body">
					<h3>Ejecuciones</h3>
					<h4>
						<a href="<c:url value="downloadExcelAll.htm"/>">Descargar
							Excel</a>
					</h4>
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">Empresa</th>
								<th scope="col">ID Empresa</th>
								<th scope="col">Archivo</th>
								<th scope="col">Fecha</th>
								<th scope="col">Detalle</th>
								<th scope="col">Analizar</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageListHolder.pageList}" var="file">
								<tr>
									<td><c:out value="${file.empresa.nombre}" /></td>
									<td><c:out value="${file.empresa.idDoc}" /></td>
									<td><c:out value="${file.fileName}" /></td>
									<td><c:out value="${file.fecha}" /></td>
									<td><a
										href="<c:url value="listatipodocumentos.htm?id="/> <c:out value="${file.id}"/> ">Detalle</a>
									</td>
									<td><a
										href="<c:url value="resultados.htm?id="/> <c:out value="${file.id}"/> ">Procesar</a>
									</td>
								</tr>

							</c:forEach>
						</tbody>
					</table>

					<!-- 
					<c:if test="${model.files.pageCount > 1 }">
					
						<c:if test="${!model.files.firstPage }">
							<li>
								<
							</li>
						
						</c:if>
					
					</c:if>
					 -->
				</div>
			</div>
		</div>

		<!-- paginacion -->

		<div>
			<span style="float: left;"> <c:choose>
					<c:when test="${pageListHolder.firstPage}">Prev</c:when>
					<c:otherwise>
						<a href="listaempresas.htm?p=${pageurl}prev">Prev</a>
					</c:otherwise>
				</c:choose>
			</span> <span> <c:forEach begin="0"
					end="${pageListHolder.pageCount-1}" varStatus="loop">
 
    <c:choose>
						<c:when test="${loop.index == pageListHolder.page}">${loop.index+1}</c:when>
						<c:otherwise>
							<a href="listaempresas.htm?p=${pageurl}${loop.index}">${loop.index+1}</a>
						</c:otherwise>
					</c:choose>
 
    </c:forEach>
			</span> <span> <c:choose>
					<c:when test="${pageListHolder.lastPage}">Next</c:when>
					<c:otherwise>
						<a href="listaempresas.htm?p=${pageurl}next">Next</a>
					</c:otherwise>
				</c:choose>
			</span>
		</div>
	</div>
</body>
</html>