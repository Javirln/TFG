package es.us.lsi.restest.controllers;

import es.us.lsi.restest.engine.Assertions;
import es.us.lsi.restest.engine.RequestAnswer;
import es.us.lsi.restest.engine.SemanticAnalysis;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static es.us.lsi.restest.engine.RequestAnswer.responseValues;


@Controller
public class RequestController {
    public static Map<String, String> exceptionMessages = new HashMap<>();

    @ExceptionHandler(Throwable.class)
    public String handleAnyException(Throwable ex, HttpServletRequest request) {
        return ClassUtils.getShortName(ex.getClass());
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public ModelAndView sendRequest(@RequestParam("url") String url,
                                    @RequestParam("method") String method,
                                    @RequestParam("params") String params,
                                    @RequestParam("headersToSend") String headers,
                                    @RequestParam("connectionTimeout") Integer connectionTimeout,
                                    @RequestParam("socketTimeout") Integer socketTimeout,
                                    @RequestParam("testsToPerform") Object testsToPerform) throws Exception {

        ModelAndView model = new ModelAndView("home");
        switch (method) {
            case "optionGET":
                RequestAnswer.sendGet(url, headers, connectionTimeout, socketTimeout, testsToPerform);
                break;
            case "optionPOST":
                RequestAnswer.sendPost(url, params, headers, connectionTimeout, socketTimeout, testsToPerform);
                break;
            case "optionPUT":
                RequestAnswer.sendPut(url, params, headers, connectionTimeout, socketTimeout, testsToPerform);
                break;
            case "optionDELETE":
                RequestAnswer.sendDelete(url, params, headers, connectionTimeout, socketTimeout, testsToPerform);
                break;
            default:
                RequestAnswer.sendGet(url, headers, connectionTimeout, socketTimeout, testsToPerform);
        }
        model.addObject("firstChecked", false);
        model.addObject("url", url);
        model.addObject("params", params);
        model.addObject("method", method);
        model.addObject("headers", headers);
        model.addObject("testsToPerform", testsToPerform);
        model.addObject("requestHeaders", responseValues.getRequestHeaders());
        model.addObject("responseHeaders", responseValues.getResponseHeaders());
        model.addObject("response", responseValues.getResponse().toString());
        model.addObject("generalInfo", responseValues.getGeneralInfo());
        model.addObject("responseValues", responseValues);
        model.addObject("responseTime", responseValues.getResponseTime().toString() + " " + "ms");
        model.addObject("testQuery", SemanticAnalysis.resultMapQuery);
        model.addObject("testPath", SemanticAnalysis.resultMapPath);
        model.addObject("index", responseValues.getContentType());
        model.addObject("resultAssertions", Assertions.resultAssertions);
        model.addObject("resultAssertionsHeaders", Assertions.resultAssertionsHeaders);
        model.addObject("resultVersioningHeaders", Assertions.resultVersioningHeader);
        model.addObject("resultAssertionsBody", Assertions.resultAssertionsBody);
        model.addObject("errorMessages", new HashMap<>(exceptionMessages));

        exceptionMessages.clear();

        return model;
    }
}
