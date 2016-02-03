<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />

<footer class="page-footer">
	<div class="container">
		<div class="row">
        	<div class="col l6 s12">
            	<h5 class="white-text">EGC - Ágora US</h5>
                <h5 class="white-text">Recuento y modificación de resultados</h5>
                <p class="white-text">Este subsistema realizará el recuento de una votación determinada. Para llevarlo a cabo tendrá que pedir los votos al subsistema de almacenamiento y deberá lanzar la tarea de recuento sincronizando las diferentes autoridades.</p>
            </div>
        	<div class="col l4 offset-l2 s12">
        		<h5 class="white-text">Miembros</h5>
        		<ul class="white-text">
        			<li>Rafael del Río Santaella <b>(Coordinador)</b></li>
					<li>Víctor José Marín Aguilar</li>
					<li>Antonio Marcos Peña</li>
					<li>Juan Manuel Triguero Piñero</li>
					<li>Juan Ramón Ríos Fernández</li>
        		</ul>
			</div>
		</div>
	</div>
    <div class="footer-copyright">
    	<div class="container">
        	© <fmt:formatDate value="${date}" pattern="yyyy" /> Universidad de Sevilla
        	<a class="grey-text text-lighten-4 right" href="https://github.com/rafaeldrs/Recuento-Agora-US" target="blank">Repositorio del código en GitHub</a>
        </div>
	</div>
</footer>
