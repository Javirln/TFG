<!DOCTYPE html>
<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<html>
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<head>
    	<jsp:include page="../views/header.jsp" />
	</head>
    <body class="flat-blue">
        <div class="app-container">
            <div class="row content-container">
                <jsp:include page="../views/menu.jsp"/>
    	        <jsp:doBody/>
            </div>
        </div>
    <footer>
        <jsp:include page="../views/footer.jsp" />
    </footer>
    </body>
</html>