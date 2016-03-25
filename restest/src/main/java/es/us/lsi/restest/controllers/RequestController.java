package es.us.lsi.restest.controllers;

import es.us.lsi.restest.domain.APIResponse;
import es.us.lsi.restest.engine.RequestAnswer;
import es.us.lsi.restest.engine.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RequestController {
    public static APIResponse responseValues = new APIResponse();

    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    public ModelAndView sendRequest(@RequestParam("url") String url,
                                    @RequestParam("method") String method,
                                    @RequestParam("params") String params,
                                    @RequestParam("headersToSend") String headers) throws Exception {

        ModelAndView model = new ModelAndView("home");
        switch (method) {
            case "optionGET":
                RequestAnswer.sendGet(url, headers);
                break;
            case "optionPOST":
                RequestAnswer.sendPost(url, params, headers);
                break;
            case "optionPUT":
                RequestAnswer.sendPut(url, params, headers);
                break;
            case "optionDELETE":
                RequestAnswer.sendDelete(url, params, headers);
                break;
            default:
                RequestAnswer.sendGet(url, headers);
        }
        model.addObject("url", url);
        model.addObject("params", params);
        model.addObject("method", method);
        model.addObject("headers", headers);
        model.addObject("requestHeaders", responseValues.getRequestHeaders());
        model.addObject("responseHeaders", responseValues.getResponseHeaders());
        model.addObject("response", responseValues.getResponse().toString());
        model.addObject("generalInfo", responseValues.getGeneralInfo());
        model.addObject("responseValues", responseValues);
        model.addObject("test", Test.resultMap);
        int index = responseValues.getResponseHeaders().get("content-type").get(0).indexOf(';');
        model.addObject("index", index);

        return model;
    }
}
