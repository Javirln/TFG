package es.us.lsi.restest.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger log = Logger.getLogger(HomeController.class);

    public ModelAndView getView() {
        return new ModelAndView("home");
    }

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {

        ModelAndView model = getView();
        model.addObject("firstChecked", true);
        return model;
    }
}
