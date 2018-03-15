<%@ include file="/WEB-INF/views/include.jsp" %>

<script>
 
    function AddMoreFile(tableID) {
        var table = document.getElementById(tableID);
        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount);
        var col1 = row.insertCell(0);
        var colInput = document.createElement("input");
        colInput.type = "file";
        colInput.name="files["+(rowCount)+"]";
        colInput.size="50";
        col1.appendChild(colInput);
    }
 
  </script> 
  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cargar Imágenes</title>
</head>
<body>
    <div align="center">
        <h1>Subir imágenes para la Demo</h1>
        
        <form method="post" action="uploadMultipleFilesMA.htm" enctype="multipart/form-data">
        	<table>
        		<tr>
        			<td>Empresa</td>
        			<td>
        				<select id="empresa" name="empresa">
        					<c:forEach items="${model.empresas}" var="emp">
      							<option value="<c:out value="${emp.id}"/>"><c:out value="${emp.nombre}"/></option>
    						</c:forEach>
        				</select>
        			</td>
        		</tr>
        	</table>
            <table id="fuTable" border="1">
                <tr>                   
                    <td><input type="file" name="files[0]" size="50" /></td>
                </tr>
            </table>
            <br>    <input  type="button" value="Add More File"  onclick="AddMoreFile('fuTable')">
    		<input type="submit" value="Upload">
        </form>
    </div>
</body>
</html>