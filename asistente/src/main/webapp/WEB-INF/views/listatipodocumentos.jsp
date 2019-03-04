<%@ include file="/WEB-INF/views/include.jsp"%>
<html>
<head>
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
		style="background:url('<c:url value="/resources/img/fdo1.png"/>') no-repeat left top;">
		<div class="cont2">
			<a href="index.html"><img class="logo_m"
				src="<c:url value="/resources/images/logo-soa.png"/>"></a>
		</div>
		<div class="cont6 tit1">Asistente Financiero</div>
		<div class="cont2">
			<img class="logo_m"
				src="<c:url value="/resources/images/banco.png"/>">
		</div>
	</div>


	<div class="btn_cont">


		<div class="container">
			<div class="panel panel-default">
				<div class="panel-body">
					<h3>Archivos Convertidos</h3>
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">Empresa</th>
								<th scope="col">ID Empresa</th>
								<th scope="col">Tipo Doc.</th>
								<th scope="col">Fecha</th>
								<th scope="col">Unidad</th>
								<th scope="col">ID Imagen</th>
								<th scope="col">ID Texto</th>
								<th scope="col">Accion</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${objetos.tipos}" var="file">
								<tr>
									<form method="post" action="updateTipoDocumentoMA.htm" id="<c:out value="${file.id}"/>">
									<td><c:out value="${objetos.empresa.nombre}" /></td>
									<td><c:out value="${objetos.empresa.idDoc}" /></td>
									
									<td>
										<input type="text" name="tipoDoc" value="<c:out value="${file.tipoDoc}" />"> 
										<input hidden name="id" value="<c:out value="${file.id}" />" >
									</td>
									
									<td><c:out value="${file.fechacreacion}" /></td>
									<td><c:out value="${file.unidad}" /></td>
									<td>
									<a  class="btn btn-outline-primary"
										href="http://181.65.158.27:8080/share/page/site/finanzas/document-details?nodeRef=workspace://SpacesStore/<c:out value="${file.objectId}"/>"
										target="_blank"> Imagen </a></td>
									<td><a
										class="btn btn-outline-primary"
										href="http://181.65.158.27:8080/share/page/site/finanzas/document-details?nodeRef=workspace://SpacesStore/<c:out value="${file.objectIdTxt}"/>"
										target="_blank"> Texto </a></td>
									<td>
										
											
											<button type="submit" class="btn btn-outline-primary">Update</button>

										
									</td>
									</form>
								</tr>

							</c:forEach>


						</tbody>
					</table>

				</div>
			</div>
		</div>


	</div>




</body>
</html>