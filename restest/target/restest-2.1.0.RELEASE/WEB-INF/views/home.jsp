<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>

<t:masterpage>
    <jsp:body>
        <!-- Main Content -->
        <div class="container-fluid">
            <div class="side-body padding-top">
                <div class="row">
                    <div class="col-lg-8 col-md-8">
                        <form class="form" action="#" th:th:action="@{/sendRequest}" th:object="${urlForm}"
                              method="POST">
                            <div class="form-group">
                                <p>URL: <input class="text form-control" type="text" th:field="*{URL}"/></p>
                                <input type="hidden"
                                       name="${_csrf.parameterName}"
                                       value="${_csrf.token}"/>
                                <input class="btn btn-success" type="submit" value="Submit">
                            </div>
                        </form>
                    </div>
                    <div class="col-lg-4 col-md-4">
                            ${urlForm.URL}
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:masterpage>
