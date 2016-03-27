package es.us.lsi.restest.controllers;

import es.us.lsi.restest.domain.APIResponse;
import es.us.lsi.restest.engine.RequestAnswer;
import es.us.lsi.restest.engine.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
public class RequestController {
    public static APIResponse responseValues = new APIResponse();
    public static Map<String, String> exceptionMessages = new HashMap<>();

    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    public ModelAndView sendRequest(@RequestParam("url") String url,
                                    @RequestParam("method") String method,
                                    @RequestParam("params") String params,
                                    @RequestParam("headersToSend") String headers,
                                    @RequestParam("connectionTimeout") Integer connectionTimeout,
                                    @RequestParam("socketTimeout") Integer socketTimeout) throws Exception {

        ModelAndView model = new ModelAndView("home");
        switch (method) {
            case "optionGET":
                RequestAnswer.sendGet(url, headers, connectionTimeout, socketTimeout);
                break;
            case "optionPOST":
                RequestAnswer.sendPost(url, params, headers, connectionTimeout, socketTimeout);
                break;
            case "optionPUT":
                RequestAnswer.sendPut(url, params, headers, connectionTimeout, socketTimeout);
                break;
            case "optionDELETE":
                RequestAnswer.sendDelete(url, params, headers, connectionTimeout, socketTimeout);
                break;
            default:
                RequestAnswer.sendGet(url, headers, connectionTimeout, socketTimeout);
        }
        model.addObject("firstChecked", false);
        model.addObject("url", url);
        model.addObject("params", params);
        model.addObject("method", method);
        model.addObject("headers", headers);
        model.addObject("requestHeaders", responseValues.getRequestHeaders());
        model.addObject("responseHeaders", responseValues.getResponseHeaders());
        model.addObject("response", responseValues.getResponse().toString());
        model.addObject("generalInfo", responseValues.getGeneralInfo());
        model.addObject("responseValues", responseValues);
        model.addObject("responseTime", responseValues.getResponseTime().toString() + " " + "ms");
        model.addObject("test", Test.resultMap);
        model.addObject("index", responseValues.getContentType());
        model.addObject("errorMessages", new HashMap<>(exceptionMessages));

        exceptionMessages.clear();

        return model;
    }
}
