<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<html>
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<head>
    	<jsp:include page="../views/header.jsp" />
	</head>
    <body class="flat-blue">
        <div class="app-container expanded">
            <div class="row content-container">
                <jsp:include page="../views/menu.jsp"/>
    	        <jsp:doBody/>
            </div>
        </div>
    <footer>
        <jsp:include page="../views/footer.jsp" />
    </footer>
        <script type="text/javascript" src="/assets/js/jquery.min.js"></script>
        <script type="text/javascript" src="/assets/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/assets/js/Chart.min.js"></script>
        <script type="text/javascript" src="/assets/js/bootstrap-switch.min.js"></script>
        <script type="text/javascript" src="/assets/js/jquery.matchHeight-min.js"></script>
        <script type="text/javascript" src="/assets/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="/assets/js/js/dataTables.bootstrap.min.js"></script>
        <script type="text/javascript" src="/assets/js/select2.full.min.js"></script>
        <script type="text/javascript" src="/assets/js/ace/ace.js"></script>
        <script type="text/javascript" src="/assets/js/ace/mode-html.js"></script>
        <script type="text/javascript" src="/assets/js/ace/theme-github.js"></script>
        <!-- Javascript -->
        <script type="text/javascript" src="/assets/js/app.js"></script>
        <script type="text/javascript" src="/assets/js/index.js"></script>
    </body>
</html>