package es.us.lsi.restest.controllers;

import es.us.lsi.restest.domain.APIResponse;
import es.us.lsi.restest.engine.RequestAnswer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RequestController {
    public static APIResponse responseValues = new APIResponse();

    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    public ModelAndView sendRequest(@RequestParam("url") String url) throws Exception {

        ModelAndView model = new ModelAndView("home");

        RequestAnswer.sendGet(url);

        model.addObject("requestHeaders", responseValues.getRequestHeaders());
        model.addObject("responseHeaders", responseValues.getResponseHeaders());
        model.addObject("response", responseValues.getResponse().toString());
        model.addObject("generalInfo", responseValues.getGeneralInfo());
        model.addObject("responseValues", responseValues);

        return model;
    }
}
