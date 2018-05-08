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

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg" crossorigin="anonymous">

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

					<div class="col-5">
						<!-- FORMULARIO PARA BUSQUEDAS -->
						<form class="form-inline my-2 my-lg-0" method="post" action="findEmpresa.htm">
							<input class="form-control mr-sm-2" type="text" name="texto"
								placeholder="Buscar ..." aria-label="Buscar ...">
							<button class="btn btn-dark clearfix">
								<i class="fas fa-search fa-lg"></i> Buscar
							</button>
						</form>
					</div>
					<div class="col-3">
              			
              			<a class="btn btn-success clearfix" href="<c:url value="downloadExcelAll.htm"/>">
              				<i class="fas fa-file-excel fa-lg"></i> 
              				Descargar Excel
              			</a>
            		</div>
					<h4>
						
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
								<th scope="col">An. Notas</th> 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageListHolder.pageList}" var="file">
								<tr>
									<td><c:out value="${file.empresa.nombre}" /></td>
									<td><c:out value="${file.empresa.idDoc}" /></td>
									<td><c:out value="${file.fileName}" /></td>
									<td><c:out value="${file.fecha}" /></td>
									<td>
										<button type="button" class="btn btn-outline-primary"
											onclick="window.location.href='<c:url value="listatipodocumentos.htm?id="/> <c:out value="${file.id}"/> '">Detalle</button>

									</td>
									<td>
										<button type="button" class="btn btn-outline-warning"
											onclick="window.location.href='<c:url value="resultadosRegex.htm?id="/> <c:out value="${file.id}"/> '">Procesar</button>

									</td>
									
									 
									<td>
										<button type="button" class="btn btn-outline-warning"
											onclick="window.location.href='<c:url value="resultadosNotas.htm?id="/> <c:out value="${file.id}"/> '">Procesar</button>

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
			<nav aria-label="Page navigation example">
				<ul class="pagination justify-content-center">

					<c:choose>
						<c:when test="${pageListHolder.firstPage}">
							<li class="page-item disabled"><a class="page-link" href="#"
								tabindex="-1">Previous</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="listaempresas.htm?p=${pageListHolder.page-1}"
								tabindex="-1">Previous</a></li>
						</c:otherwise>
					</c:choose>

					<c:forEach begin="0" end="${pageListHolder.pageCount-1}"
						varStatus="loop">

						<c:choose>
							<c:when test="${loop.index == pageListHolder.page}">
								<li class="page-item active"><span class="page-link">
										${loop.index+1} <span class="sr-only">(current)</span>
								</span></li>


							</c:when>
							<c:otherwise>
								<li class="page-item"><a class="page-link"
									href="listaempresas.htm?p=${pageurl}${loop.index}">${loop.index+1}</a></li>
								<!-- <a href="listaempresas.htm?p=${pageurl}${loop.index}">${loop.index+1}</a>-->
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					<c:choose>
						<c:when test="${pageListHolder.lastPage}">
							<li class="page-item disabled"><a class="page-link" href="#"
								tabindex="-1">Previous</a></li>
						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="listaempresas.htm?p=${pageListHolder.page+1}"
								tabindex="-1">Next</a></li>
						</c:otherwise>
					</c:choose>

				</ul>
			</nav>
		</div>
	</div>
</body>
</html>