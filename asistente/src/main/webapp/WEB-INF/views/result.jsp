<%@ include file="/WEB-INF/views/include.jsp"%>
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

<script type="text/javascript">
	function mensaje(valor){
		alert(valor);
	}
</script>
</head>

<body
	style="background:url('<c:url value="/resources/img/FDO2.png"/>') right bottom no-repeat;" >

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
				src="<c:url value="/resources/images/banco.jpg"/>">
		</div>
	</div>


	<div class="btn_cont">


		<div class="container">
			<div class="panel panel-default">
				<div class="panel-body">
					<h3>Resultado del la Subida</h3>


					<p>
						Resultado:
						<c:out value="${mensaje}"></c:out>
					</p>

				</div>
			</div>
		</div>


	</div>
</body>
</html>