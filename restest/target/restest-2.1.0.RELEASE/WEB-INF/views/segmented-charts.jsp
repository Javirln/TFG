<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
<title>Charts</title>
<script type="text/javascript"
	src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['corechart']}]}"></script>
<script>
	google.setOnLoadCallback(drawChart);
	function drawChart() {

		<c:forEach var="question" items="${data}" varStatus="loopCounter1">
			<c:forEach var="segment" items="${question.segments}" varStatus="loopCounter2">
				var arrays = [[ 'Answer', 'Count' ]];
				arrays.push([ 'Sí', parseInt("${segment.value.yes}") ]);
				arrays.push([ 'No', parseInt("${segment.value.no}") ]);

				var data = google.visualization.arrayToDataTable(arrays);

				var options = {
					title : '' + "${question.question}" + ' ${criteria}'.toUpperCase() + ' = ' + "${segment.key}" + '',
					is3D: true 
				};

				var chart = new google.visualization.PieChart(document
				.getElementById('piechart${loopCounter1.count}${loopCounter2.count}'));

				chart.draw(data, options);
			</c:forEach>
		</c:forEach>

	}
</script>
</head>
<body>

	<c:if test="${empty data}">No se ha emitido ningún voto todavía.</c:if>

	<c:forEach var="question" items="${data}" varStatus="loopCounter1">
		<c:forEach var="segment" items="${question.segments}" varStatus="loopCounter2">
			<div id="piechart${loopCounter1.count}${loopCounter2.count}" style="width: 700px; height: 300px;"></div>
		</c:forEach>
	</c:forEach>

</body>
</html>
