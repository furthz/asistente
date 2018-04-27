<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>SOA Professionals</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
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

<script>

function sendJson(datos) {
	var URL = "http://financiero-a7e2.azurewebsites.net/json";
    //var dataObject = { 'newWeekEntry': newEntry, 'oldWeekEntry': oldEntry };
    $.ajax({
        url: URL,
        type: 'PUT',    
        //headers: {"Content-Type": "application/json"},
        contentType: "application/json",
        data: JSON.stringify(datos),
        dataType: 'json',
        success: function(result) {
            //alert("exito");
        }
    });
}

</script>

<span class="skype-button bubble " data-bot-id="e2dc44f0-27e8-47eb-8c94-44c8cf125573"></span>
<script src="https://swc.cdn.skype.com/sdk/v1/sdk.min.js"></script>
</head>


<body
	style="background:url('<c:url value="/resources/img/FDO2.png"/>') right bottom no-repeat;"  onload ="sendJson(<c:out value="${objetos.json}"/>)">


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
		style="background: url('img/fdo1.png') no-repeat left top;">
		<div class="cont2">
			<a href="index.html"><img class="logo_m"
				src="<c:url value="/resources/images/logo-soa.png"/> "></a>
		</div>
		<div class="cont6 tit1">Asistente Financiero</div>
		<div class="cont2">
			<img class="logo_m"
				src="<c:url value="/resources/images/banco.jpg"/> ">
		</div>
	</div>


	<div class="btn_cont">

		<div class="row">
			<div class="col-8">
				<img class="rounded" style="width: 100% !important"
					src="data:image/jpg;base64,<c:out value="${objetos.imagen}"/>" />
			</div>
			<div class="col-4">
				<div class="panel panel-default">
					<div class="panel-body">
						<h4><a href="<c:url value="downloadExcel.htm"/>">Descargar Excel</a></h4>
						
						<h3>Activos</h3>
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">Cuenta</th>
									<th scope="col">Valor</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${objetos.plan[0].indActivo}" var="activo">
									<c:if test="${objetos.plan[0].activo[activo] > 0 }">
										<tr>
										
											<!-- 
											<c:set var="etsActivo" value="${fn.split('prueba 2', '')}" />
											<c:forEach items="${etsActivo}" var="current" varStatus="stat">
												<c:set var="miEtiqueta" value="${stat.first ? '': miEtiqueta } ${current }" />
											</c:forEach>
											 -->
											<th scope="row"><c:out value="${activo}" /></th>
											<td><fmt:formatNumber type="number"
													pattern="###,###.###" value="${objetos.plan[0].activo[activo]}" /></td>

										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>

					</div>


					<div class="panel-body">
						<h3>Pasivos</h3>
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">Cuenta</th>
									<th scope="col">Valor</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${objetos.plan[0].indPasivo}" var="activo">
									<c:if test="${objetos.plan[0].pasivo[activo] > 0 }">
										<tr>
											<th scope="row"><c:out value="${activo}" /></th>
											<td><fmt:formatNumber type="number"
													pattern="###,###.###" value="${objetos.plan[0].pasivo[activo]}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>

					</div>




					<div class="panel-body">
						<h3>Patrimonio</h3>
						<table class="table table-striped">
							<thead>
								<tr>
									<th scope="col">Cuenta</th>
									<th scope="col">Valor</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${objetos.plan[0].indPatrimonio}" var="activo">
									<c:if test="${objetos.plan[0].patrimonio[activo] > 0 }">
										<tr>
											<th scope="row"><c:out value="${activo}" /></th>
											<td><fmt:formatNumber type="number"
													pattern="###,###.###" value="${objetos.plan[0].patrimonio[activo]}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>

					</div>





				</div>
			</div>
		</div>



	</div>






</body>
</html>