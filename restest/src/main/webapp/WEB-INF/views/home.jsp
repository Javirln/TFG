<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>

<t:masterpage>
    <jsp:body>
        <!-- Main Content -->
        <div class="container-fluid">
            <div class="side-body padding-top">
                <div class="row">
                    <div class="col-lg-8 col-md-8">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-title">
                                    <div class="title">URL tester</div>
                                </div>
                            </div>
                            <div class="card-body">
                                <form class="form" action="/sendRequest" method="post">
                                    <div class="form-group">
                                        <div class="sub-title">URL:</div>
                                        <input class="text form-control" type="text" name="url" required/></p>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                        <div class="sub-title">Method:</div>
                                        <div>
                                            <div class="radio3 radio-check radio-inline">
                                                <input type="radio" id="radio1" name="optionGET" value="option1"
                                                       checked>
                                                <label for="radio1">
                                                    GET
                                                </label>
                                            </div>
                                            <div class="radio3 radio-check radio-inline">
                                                <input type="radio" id="radio2" name="optionPOST" value="option2"
                                                       disabled>
                                                <label for="radio2">
                                                    POST
                                                </label>
                                            </div>
                                        </div>
                                        <input class="btn btn-success pull-right" type="submit" value="Submit">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-4">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-title">
                                    <div class="title">Request Headers
                                        <small> (${requestHeaders.size()})</small>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <c:forEach items="${requestHeaders}" var="entry">
                                    <ul>
                                        <li><b>${entry.key}:</b> ${entry.value}</li>
                                    </ul>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-title">
                                    <div class="title">Response Headers
                                        <small> (${responseHeaders.size()})</small>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <c:forEach items="${responseHeaders}" var="entry">
                                    <ul>
                                        <li><b>${entry.key}:</b> ${entry.value}</li>
                                    </ul>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="card">
                            <div class="card-header">
                                <div class="card-title">
                                    <div class="title">General Info
                                        <small> (${generalInfo.size()})</small>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <c:forEach items="${generalInfo}" var="entry">
                                    <ul>
                                        <li><b>${entry.key}:</b> ${entry.value}</li>
                                    </ul>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:masterpage>
