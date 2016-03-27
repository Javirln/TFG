<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:masterpage>
    <jsp:body>
        <!-- Main Content -->
        <div class="container-fluid">
            <div class="side-body padding-top">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-body no-padding">
                                <div role="tabpanel">
                                    <!-- Nav tabs -->
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li id="generalId" role="presentation" class="active"><a href="#urlRetriever"
                                                                                                 aria-controls="urlRetriever"
                                                                                                 role="tab"
                                                                                                 data-toggle="tab">General</a>
                                        </li>
                                        <c:if test="${not empty responseValues and empty errorMessages}">
                                            <li id="resultsId" role="presentation"><a
                                                    href="#results"
                                                    aria-controls="results"
                                                    role="tab"
                                                    data-toggle="tab">Results</a>
                                            </li>
                                            <li id="testId" role="presentation"><a href="#test"
                                                                                   aria-controls="test"
                                                                                   role="tab"
                                                                                   data-toggle="tab">Test</a></li>
                                        </c:if>
                                    </ul>
                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div role="tabpanel" class="tab-pane active" id="urlRetriever">
                                            <div class="col-lg-12 col-md-12">
                                                <div class="col-lg-10 col-md-10">
                                                    <div class="card">
                                                        <div class="card-header">
                                                            <div class="card-title">
                                                                <div class="title">URL tester</div>
                                                            </div>
                                                        </div>
                                                        <div class="card-body">
                                                            <form id="mainForm" class="form" action="sendRequest"
                                                                  method="post">
                                                                <div class="form-group">
                                                                    <div class="sub-title">URL:</div>
                                                                    <input id="urlField"
                                                                           class="text form-control <c:if test="${not empty errorMessages.get('url')}">panel-danger</c:if> "
                                                                           type="url" name="url"
                                                                           aria-required="true" value="${url}"
                                                                           required/>
                                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                                           value="${_csrf.token}"/>
                                                                    <div>
                                                                        <div class="sub-title">Method</div>
                                                                        <div class="radio3 radio-check radio-inline">
                                                                            <input type="radio" id="radio1"
                                                                                   name="method"
                                                                                   value="optionGET"
                                                                            <c:choose>
                                                                            <c:when test="${firstChecked}">
                                                                                   checked
                                                                            </c:when>
                                                                            <c:when test="${method == 'optionGET'}">
                                                                                   checked
                                                                            </c:when>
                                                                            </c:choose>>
                                                                            <label for="radio1">
                                                                                GET
                                                                            </label>
                                                                        </div>
                                                                        <div class="radio3 radio-check radio-inline">
                                                                            <input type="radio" id="radio2"
                                                                                   name="method" value="optionPOST"
                                                                                   <c:if test="${method == 'optionPOST'}">checked</c:if>>
                                                                            <label for="radio2">
                                                                                POST
                                                                            </label>
                                                                        </div>
                                                                        <div class="radio3 radio-check radio-inline">
                                                                            <input type="radio" id="radio3"
                                                                                   name="method" value="optionPUT"
                                                                                   <c:if test="${method == 'optionPUT'}">checked</c:if>>
                                                                            <label for="radio3">
                                                                                PUT
                                                                            </label>
                                                                        </div>
                                                                        <div class="radio3 radio-check radio-inline">
                                                                            <input type="radio" id="radio4"
                                                                                   name="method" value="optionDELETE">
                                                                            <label for="radio4"
                                                                                   <c:if test="${method == 'optionDELETE'}">checked</c:if>>
                                                                                DELETE
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row">
                                                                        <div class="col-lg-4 col-md-4">
                                                                            <div class="sub-title">Timeout</div>
                                                                            <label for="connectionTimeout">Connection
                                                                                timeout (ms)</label> <i
                                                                                class="fa fa-question-circle"
                                                                                data-toggle="tooltip"
                                                                                data-placement="right"
                                                                                title="It's the time the application takes to connect to the server."></i>
                                                                            <br>
                                                                            <small>By default 10000ms &middot; 0 or
                                                                                empty input - disable timeout
                                                                            </small>
                                                                            <input id="connectionTimeout"
                                                                                   class="text form-control "
                                                                                   type="number"
                                                                                   name="connectionTimeout"
                                                                                   aria-required="true"
                                                                                   value="${connectionTimeout}"
                                                                                   min="0"/>
                                                                            </i>
                                                                            <br>
                                                                            <label for="connectionTimeout">Socket
                                                                                timeout (ms)</label> <i
                                                                                class="fa fa-question-circle"
                                                                                data-toggle="tooltip"
                                                                                data-placement="right"
                                                                                title="It's the time the application takes to receive data from the server."></i>
                                                                            <br>
                                                                            <small>By default 60000ms &middot; 0 or
                                                                                empty input - disable timeout
                                                                            </small>
                                                                            <input id="socketTimeout"
                                                                                   class="text form-control"
                                                                                   type="number" name="socketTimeout"
                                                                                   aria-required="true"
                                                                                   value="${socketTimeout}"
                                                                                   min="0"/>

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-lg-6 col-md-6">
                                                                        <div class="sub-title"> Params</div>
                                                                        <div>
                                                                                <textarea id="params"
                                                                                          name="params"><c:out
                                                                                        value="${params}"></c:out></textarea>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-6 col-md-6">
                                                                        <div class="sub-title"> Request Headers
                                                                        </div>
                                                                        <div>
                                                                                <textarea id="headersToSend"
                                                                                          name="headersToSend"><c:out
                                                                                        value="${headers}"></c:out></textarea>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <input id="submit"
                                                                       class="btn btn-success pull-right"
                                                                       type="submit"
                                                                       value="Submit">
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-lg-2 col-md-2">
                                                    <c:if test="${not empty errorMessages.get('url')}">
                                                        <c:forEach items="${errorMessages}" var="entry">
                                                            <div class="alert alert-danger alert-dismissible"
                                                                 role="alert">
                                                                <button type="button" class="close" data-dismiss="alert"
                                                                        aria-label="Close"><span
                                                                        aria-hidden="true">&times;</span></button>
                                                                <strong>Warning!</strong> <c:out
                                                                    value="${entry.value}"/>
                                                            </div>
                                                        </c:forEach>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                        <div role="tabpanel" class="tab-pane" id="results">
                                            <c:if test="${not empty responseValues and empty errorMessages}">
                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <div class="card">
                                                            <div class="card-body">
                                                                <div class="row">
                                                                    <div class="col-lg-12 col-md-12">
                                                                        <div class="card">
                                                                            <div class="card-header">
                                                                                <div class="card-title">
                                                                                    <div class="title">Results <i
                                                                                            class="fa fa-question-circle"
                                                                                            data-toggle="tooltip"
                                                                                            data-placement="right"
                                                                                            title="By default, the response is formatted as JSON.">
                                                                                    </i> &middot;
                                                                                        <c:choose>
                                                                                            <c:when test="${responseValues.getResponseCode().startsWith('1')}">
                                                                                                <span
                                                                                                        class="fresh-color alert-info"><c:out
                                                                                                        value=" ${responseValues.getResponseCode()}"/></span> &middot;
                                                                                                <c:out value="${responseTime}"/>
                                                                                            </c:when>
                                                                                            <c:when test="${responseValues.getResponseCode().startsWith('2')}">
                                                                                                <span
                                                                                                        class="fresh-color alert-success"><c:out
                                                                                                        value=" ${responseValues.getResponseCode()}"/></span> &middot;
                                                                                                <c:out value="${responseTime}"/>
                                                                                            </c:when>
                                                                                            <c:when test="${responseValues.getResponseCode().startsWith('3')}">
                                                                                                <span
                                                                                                        class="fresh-color alert-waning"><c:out
                                                                                                        value=" ${responseValues.getResponseCode()}"/></span> &middot;
                                                                                                <c:out value="${responseTime}"/>
                                                                                            </c:when>
                                                                                            <c:when test="${responseValues.getResponseCode().startsWith('4')}">
                                                                                                <span
                                                                                                        class="fresh-color alert-danger"><c:out
                                                                                                        value=" ${responseValues.getResponseCode()}"/></span> &middot;
                                                                                                <c:out value="${responseTime}"/>
                                                                                            </c:when>
                                                                                            <c:when test="${responseValues.getResponseCode().startsWith('5')}">
                                                                                                <span
                                                                                                        class="fresh-color alert-danger"><c:out
                                                                                                        value=" ${responseValues.getResponseCode()}"/></span> &middot;
                                                                                                <c:out value="${responseTime}"></c:out>
                                                                                            </c:when>
                                                                                        </c:choose>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="card-body no-padding">
                                                                                <textarea id="codeViewer">
                                                                                        <c:out value="${response}"/>
                                                                                </textarea>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-lg-12 col-md-12">
                                                                        <div class="card">
                                                                            <div class="card-header">
                                                                                <div class="card-title">
                                                                                    <div class="title">Headers</div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="card-body no-padding">
                                                                                <div role="tabpanel">
                                                                                    <!-- Nav tabs -->
                                                                                    <ul class="nav nav-tabs"
                                                                                        role="tablist">
                                                                                        <li role="presentation"
                                                                                            class="active"><a
                                                                                                href="#responseHeaders"
                                                                                                aria-controls="responseHeaders"
                                                                                                role="tab"
                                                                                                data-toggle="tab">Response
                                                                                            Headers
                                                                                            <small> (<c:out
                                                                                                    value="${responseHeaders.size()}"/>)
                                                                                            </small>
                                                                                        </a></li>
                                                                                        <li role="presentation"><a
                                                                                                href="#requestHeaders"
                                                                                                aria-controls="requestHeaders"
                                                                                                role="tab"
                                                                                                data-toggle="tab">Request
                                                                                            Headers
                                                                                            <small> (<c:out
                                                                                                    value="${requestHeaders.size()}"/>)
                                                                                            </small>
                                                                                        </a></li>
                                                                                        <li role="presentation"><a
                                                                                                href="#generalInfo"
                                                                                                aria-controls="generalInfo"
                                                                                                role="tab"
                                                                                                data-toggle="tab">General
                                                                                            Info
                                                                                            <small> (<c:out
                                                                                                    value="${generalInfo.size()}"/>)
                                                                                            </small>
                                                                                        </a></li>
                                                                                    </ul>
                                                                                    <!-- Tab panes -->
                                                                                    <div class="tab-content">
                                                                                        <div role="tabpanel"
                                                                                             class="tab-pane active"
                                                                                             id="responseHeaders">
                                                                                            <c:forEach
                                                                                                    items="${responseHeaders}"
                                                                                                    var="entry">
                                                                                                <ul>
                                                                                                    <li>
                                                                                                        <b><span
                                                                                                                style="text-transform: capitalize"><c:out
                                                                                                                value="${fn:toLowerCase(entry.key)}:"/></span></b>
                                                                                                        <c:out
                                                                                                                value="${entry.value.toString().substring(1, entry.value.toString().length()-1)}"/>
                                                                                                    </li>
                                                                                                </ul>
                                                                                            </c:forEach>
                                                                                        </div>
                                                                                        <div role="tabpanel"
                                                                                             class="tab-pane"
                                                                                             id="requestHeaders">
                                                                                            <c:forEach
                                                                                                    items="${requestHeaders}"
                                                                                                    var="entry">
                                                                                                <ul>
                                                                                                    <li>
                                                                                                        <b><span
                                                                                                                style="text-transform: capitalize"><c:out
                                                                                                                value="${fn:toLowerCase(entry.key)}:"/></span></b>
                                                                                                        <c:out
                                                                                                                value="${entry.value.toString()}"/>
                                                                                                    </li>
                                                                                                </ul>
                                                                                            </c:forEach>
                                                                                        </div>
                                                                                        <div role="tabpanel"
                                                                                             class="tab-pane"
                                                                                             id="generalInfo">
                                                                                            <c:forEach
                                                                                                    items="${generalInfo}"
                                                                                                    var="entry">
                                                                                                <ul>
                                                                                                    <li>
                                                                                                        <b><c:out
                                                                                                                value="${entry.key}: "/></b>
                                                                                                        <c:out
                                                                                                                value="${entry.value}"/>
                                                                                                    </li>
                                                                                                </ul>
                                                                                            </c:forEach>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div role="tabpanel" class="tab-pane" id="test">
                                            <c:if test="${not empty responseValues and empty errorMessages}">
                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <div class="card">
                                                            <div class="card-body">
                                                                <div class="text-center"><h2 id="urlTest"><c:out
                                                                        value="${url}"></c:out></h2></div>
                                                                <ul>
                                                                    <c:forEach items="${test}" var="entry">
                                                                        <li>${entry.key}</li>
                                                                        <ul>
                                                                            <c:forEach items="${entry.value}"
                                                                                       var="child">
                                                                                <li>${child.key}
                                                                                    => ${child.value} </li>
                                                                            </c:forEach>
                                                                        </ul>
                                                                    </c:forEach>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- modal -->
                        <div class="modal fade modal-danger" id="modalConnection" tabindex="-1" role="dialog"
                             aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="myModalLabel">Connection error</h4>
                                    </div>
                                    <div class="modal-body">
                                        <h2>Could not get any response</h2>
                                        <c:out value="${errorMessages.get('con')}"></c:out> <a
                                            href="<c:out value="${url}"></c:out>" style="color: #2e6da4"><c:out
                                            value="${url}"></c:out></a>
                                        <br>
                                        Try the following suggestions:
                                        <ul>
                                            <li>Check if the backend is running.</li>
                                            <li>Check the URL it might be misspelled.</li>
                                            <li>Try changing the timeout.</li>
                                        </ul>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- fin modal -->
                    </div>
                </div>
            </div>
        </div>
        <script>
            "use strict";
            <c:if test="${not empty errorMessages.get('con')}">
            $('#modalConnection').modal('show');
            </c:if>

            var paramsMirror = CodeMirror.fromTextArea(document.getElementById("params"), {
                lineNumbers: true,
                mode: "application/json",
                autoCloseBrackets: true,
                gutters: ["CodeMirror-lint-markers"],
                lint: true
            });

            var paramsMirror = CodeMirror.fromTextArea(document.getElementById("headersToSend"), {
                lineNumbers: true,
                mode: "application/json",
                autoCloseBrackets: true,
                gutters: ["CodeMirror-lint-markers"],
                lint: true
            });

            var codeMirror = CodeMirror.fromTextArea(document.getElementById("codeViewer"), {
                lineNumbers: true,
                readOnly: true,
                autoFocus: false,
                autoRefresh: true,
                mode: <c:choose>
                        <c:when test="${index != -1}">
                        '${responseHeaders.get("content-type").get(0).substring(0,index)}'
                </c:when>
                <c:when test="${index == 1}">
                '${responseValues.getResponseHeaders().get("content-type").get(0)}'
                        </c:when>
                        <c:otherwise>"application/json"</c:otherwise>
                </c:choose>
            });
            function autoFormat() {
                var totalLines = codeMirror.lineCount();
                var totalChars = codeMirror.getTextArea().value.length;
                codeMirror.autoFormatRange({line: 0, ch: 0}, {line: totalLines, ch: totalChars});
            }
            autoFormat();
            codeMirror.scrollTo(0, 0);

            $('#mainForm').validate();

        </script>
    </jsp:body>
</t:masterpage>
