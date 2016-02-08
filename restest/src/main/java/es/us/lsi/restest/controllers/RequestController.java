package es.us.lsi.restest.controllers;

import es.us.lsi.restest.domain.APIResponse;
import es.us.lsi.restest.engine.RequestAnswer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RequestController {
    private static final Logger log = Logger.getLogger(RequestController.class);

    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    public ModelAndView sendRequest(@RequestParam("url") String url) throws Exception {
        APIResponse responseValues = null;

        ModelAndView model = new ModelAndView("home");

        responseValues = RequestAnswer.sendGet(url);

        model.addObject("requestHeaders", responseValues.getRequestHeaders());
        model.addObject("responseHeaders", responseValues.getResponseHeaders());
        model.addObject("response", responseValues.getResponse().toString());
        model.addObject("generalInfo", responseValues.getGeneralInfo());
        return model;
    }
}
