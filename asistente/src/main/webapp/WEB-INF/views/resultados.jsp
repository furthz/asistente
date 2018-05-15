<%@ include file="/WEB-INF/views/include.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>SOA Professionals</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/> "></script>
<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet">
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Josefin+Sans" />
<link rel="stylesheet" type="text/css"
	href="https://fonts.googleapis.com/css?family=Anton" />

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/reclamacion.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style.css"/>">



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


function MostrarImgNext(index) {
	try{
	  var j = Number($("#size").val());	 
      var i = Number(index) +1;
      if(j == i){
    	
	  	$("#Imagen" + index).show();	  	
      }else{
    	$("#Imagen" + index).hide();
  	  	$("#Imagen" + i).show();
      }
	}catch(err){
		 alert(err);
	 }
	}
	
function MostrarImgPrev(index) {
	try{
	  var j = 0;
	  var i = Number(index)-1;
	  
	  if( j == index){	  
	  	$("#Imagen" + index).show();
	  }else{
		  $("#Imagen" + index).hide();
		  	$("#Imagen" + i).show();
	  }
	}catch(err){
		alert(err);
	}
	}
	
</script>

<script type="text/javascript">
  	$(document).ready(function(){
      $('.carousel').carousel({
        interval: 0000,
        pause: "false"
      })
    });
  </script>

<span class="skype-button bubble "
	data-bot-id="e2dc44f0-27e8-47eb-8c94-44c8cf125573"></span>
<script src="https://swc.cdn.skype.com/sdk/v1/sdk.min.js"></script>


</head>


<style>
.carousel-control-prev-icon {
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M5.25 0l-4 4 4 4 1.5-1.5-2.5-2.5 2.5-2.5-1.5-1.5z'/%3E%3C/svg%3E");
}

.carousel-control-next-icon {
    background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000' viewBox='0 0 8 8'%3E%3Cpath d='M2.75 0l-1.5 1.5 2.5 2.5-2.5 2.5 1.5 1.5 4-4-4-4z'/%3E%3C/svg%3E");
}
</style>

<body
	style="background:url('<c:url value="/resources/img/FDO2.png"/>') right bottom no-repeat;"
	onload="sendJson(<c:out value="${objetos.json}"/>)">


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
		style="background: url('<c:url value="/resources/img/fdo1.png"/>')  no-repeat left top;">
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
			<!-- IMAGENES -->
			<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
				<div class="carousel-inner">					
					<c:set var="count" value="0" scope="page" />
					<c:forEach items="${objetos.imagen}" var="image">
						<c:choose>
							<c:when test="${count=='0'}">
								<div class="carousel-item active">
							      <img class="d-block w-100" src="data:image/jpg;base64,<c:out value="${image}"/>" alt="slide <c:out value="${count}"/>"    >
							    </div>						
							</c:when>
							<c:otherwise>
								<div class="carousel-item">
								      <img class="d-block w-100" src="data:image/jpg;base64,<c:out value="${image}"/>" alt="slide <c:out value="${count}"/>">
								 </div>
							</c:otherwise>
						</c:choose>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>						
				</div>
				<a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
				    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
				    <span class="sr-only">Previous</span>
				</a>
				<a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
				    <span class="carousel-control-next-icon" aria-hidden="true"></span>
				    <span class="sr-only">Next</span>
				</a>
			</div>
			<!-- FIN IMAGENES -->
			
			
			<input id="size" type="hidden" name="size"
						value="<c:out value="${count }"/>">
			</div>
			<div id="accordion">
				<div class="card">
					<!-- 
					<h4>
						<a href="<c:url value="downloadExcel.htm"/>">Descargar Excel</a>
					</h4>
					 -->
					<div class="card-header" id="headingOne">
						<h5 class="mb-0">
							<button class="btn btn-link" data-toggle="collapse"
								data-target="#collapseOne" aria-expanded="true"
								aria-controls="collapseOne">
								<h3>Activos</h3>
							</button>
						</h5>
					</div>

					<div id="collapseOne" class="collapse show"
						aria-labelledby="headingOne" data-parent="#accordion">
						<div class="card-body">

							<table class="table-responsive table-striped">
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
														pattern="###,###.###"
														value="${objetos.plan[0].activo[activo]}" /></td>

											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>


						</div>

					</div>

				</div>
				<!-- Fin del card -->

				<div class="card">
					<div class="card-header" id="headingTwo">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" data-toggle="collapse"
								data-target="#collapseTwo" aria-expanded="false"
								aria-controls="collapseTwo">
								<h3>Pasivos</h3>
							</button>
						</h5>
					</div>

					<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
						data-parent="#accordion">
						<div class="card-body">
							<table class="table-responsive table-striped">
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
														pattern="###,###.###"
														value="${objetos.plan[0].pasivo[activo]}" /></td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>

						</div>
					</div>
				</div>

				<div class="card">
					<div class="card-header" id="headingThree">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" data-toggle="collapse"
								data-target="#collapseThree" aria-expanded="false"
								aria-controls="collapseThree">
								<h3>Patrimonio</h3>
							</button>
						</h5>
					</div>

					<div id="collapseThree" class="collapse"
						aria-labelledby="headingThree" data-parent="#accordion">
						<div class="card-body">
							<table class="table-responsive table-striped">
								<thead>
									<tr>
										<th scope="col">Cuenta</th>
										<th scope="col">Valor</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${objetos.plan[0].indPatrimonio}"
										var="activo">
										<c:if test="${objetos.plan[0].patrimonio[activo] > 0 }">
											<tr>
												<th scope="row"><c:out value="${activo}" /></th>
												<td><fmt:formatNumber type="number"
														pattern="###,###.###"
														value="${objetos.plan[0].patrimonio[activo]}" /></td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!-- Fin del acordeon -->


		</div>



	</div>






</body>
</html>