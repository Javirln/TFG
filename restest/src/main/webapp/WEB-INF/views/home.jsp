<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage>
    <jsp:body>
    	<div class="container">
		<h1>Subsistema "Recuento y Modificación de Resultados"</h1>

		<p class="flow-text">Subsistema encargado de la realización del
			recuento y la modificación de los resultados de una votación
			determinada. Para la realización del recuento se accederá a la base
			de datos proporcionada por almacenamiento y utilizará los métodos
			proporcionados por verificación para descifrar los votos obtenidos.</p>

		<div class="card-panel light-green accent-1">
			<p class="flow-text">

				Nota:<br> Los tiempos de respuestas pueden ser bastante
				amplios.<br> No es responsabilidad del subsistema de recuento
				ni de ningún miembro relacionado con el subsistema.<br> Se
				trata de un problema sobre el lugar donde está desplegado el
				subsistema de Verificación, el cual debe ser consultado para el
				descifrado. <br> Aún así, disculpen las molestias causadas.
			</p>
		</div>
		<br>


		<h4>Listado de votaciones existentes:</h4>
		<table class="table-responsive striped centered bordered">
			<thead>
				<tr>
					<th>#</th>
					<th>JSON</th>
					<th>Gráfico</th>
					<th>Gráfico por edad</th>
					<th>Gráfico por género</th>
					<th>Gráfico por C. A.*</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td><a href="./count/1?cod=special">Ver</a></td>
					<td><a href="./count/1/charts?cod=special">Ver</a></td>
					<td><a href="./count/1/charts?cod=special&segment=age">Ver</a></td>
					<td><a href="./count/1/charts?cod=special&segment=gender">Ver</a></td>
					<td><a href="./count/1/charts?cod=special&segment=aut_com">Ver</a></td>
				</tr>

				<tr>
					<td>2</td>
					<td><a href="./count/2">Ver</a></td>
					<td><a href="./count/2/charts">Ver</a></td>
					<td><a href="./count/2/charts?segment=age">Ver</a></td>
					<td><a href="./count/2/charts?segment=gender">Ver</a></td>
					<td><a href="./count/2/charts?segment=aut_com">Ver</a></td>
				</tr>

				<tr>
					<td>3</td>
					<td><a href="./count/3">Ver</a></td>
					<td><a href="./count/3/charts">Ver</a></td>
					<td><a href="./count/3/charts?segment=age">Ver</a></td>
					<td><a href="./count/3/charts?segment=gender">Ver</a></td>
					<td><a href="./count/3/charts?segment=aut_com">Ver</a></td>
				</tr>

				<tr>
					<td>4</td>
					<td><a href="./count/4">Ver</a></td>
					<td><a href="./count/4/charts">Ver</a></td>
					<td><a href="./count/4/charts?segment=age">Ver</a></td>
					<td><a href="./count/4/charts?segment=gender">Ver</a></td>
					<td><a href="./count/4/charts?segment=aut_com">Ver</a></td>
				</tr>

				<tr>
					<td>5</td>
					<td><a href="./count/5">Ver</a></td>
					<td><a href="./count/5/charts">Ver</a></td>
					<td><a href="./count/5/charts?segment=age">Ver</a></td>
					<td><a href="./count/5/charts?segment=gender">Ver</a></td>
					<td><a href="./count/5/charts?segment=aut_com">Ver</a></td>
				</tr>
			</tbody>
		</table>
		<b>* C. A. es la abreviatura de Comunidad Autónoma</b> <br> <br>

		<h4>API REST del subsistema:</h4>
		<table class="table-responsive striped centered bordered">
			<thead>
				<tr>
					<th>Método URL</th>
					<th>Descripción</th>
					<th>Parámetros</th>
					<th>Respuesta</th>
					<th>Ejemplo</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>*/count/predefined</td>
					<td>Test de funcionamiento de una votación.</td>
					<td>No acepta parámetros.</td>
					<td>JSON con el resultado de una votación de prueba.</td>
					<td>[{"question":"¿Le ha gustado
						EGC?","yes":4,"no":2},{"question":"¿Es usuario de
						linux?","yes":0,"no":6}]</td>
				</tr>

				<tr>
					<td>*/count/{pollId}</td>
					<td>Recuento de un referéndum.</td>
					<td>{pollId}: id de la votación a la que hacer recuento</td>
					<td>JSON con el resultado del recuento de esa votación.</td>
					<td>[{"question":"¿Le ha gustado
						EGC?","yes":4,"no":2},{"question":"¿Es usuario de
						linux?","yes":0,"no":6}]</td>
				</tr>

				<tr>
					<td>*/count/{pollId}?cod={cod}</td>
					<td>Recuento indicando codificación, añadiendo el parámetro
						"cod"</td>
					<td>{cod}: valores normal (por defecto) o cualquier otro.</td>
					<td>JSON con el resultado del recuento de esa votación.</td>
					<td>{"code":1951,"message":"OK.","algorithm":{"codification":"special","segmentation":null,"result":[{"question":"¿Le
						ha gustado EGC?","yes":4,"no":2},{"question":"¿Es usuario de
						linux?","yes":0,"no":6}]}}</td>
				</tr>

				<tr>
					<td>*/count/{pollId}?segment={segment}</td>
					<td>Es posible segmentar el recuento mediante el parámetro
						"segment" por edad, género y comunidad autónoma.</td>
					<td>{segment}: valores age, gender y aut_com.</td>
					<td>JSON con el resultado del recuento de esa votación
						segmentado por algún criterio</td>
					<td>{"code":1951,"message":"OK.","algorithm":{"codification":"special","segmentation":"aut_com","result":[{"question":"¿Es
						usuario de
						linux?","segments":{"Andalucia":{"yes":0,"no":3},"Extremadura":{"yes":0,"no":3}}},{"question":"¿Le
						ha gustado
						EGC?","segments":{"Andalucia":{"yes":2,"no":1},"Extremadura":{"yes":2,"no":1}}}]}}</td>
				</tr>

				<tr>
					<td>*/count/{pollId}/charts</td>
					<td>Recuento de un referendum obteniendo una visualización
						gráfica del mismo.</td>
					<td>No acepta parámetros.</td>
					<td>Vista con gráficas del recuento</td>
					<td><a href="./count/1/charts?cod=special">Ejemplo</a></td>
				</tr>

				<tr>
					<td>*/count/{pollId}/charts?cod={cod}&amp;segment={segment}</td>
					<td>Ejemplo aplicando todo lo anterior.</td>
					<td>{cod} y {segment}.</td>
					<td>Vista con gráfica del recuento.</td>
					<td><a href="./count/1/charts?cod=special&segment=gender">Ejemplo</a></td>
				</tr>
			</tbody>
		</table>
	</div>
    </jsp:body>
</t:masterpage>
