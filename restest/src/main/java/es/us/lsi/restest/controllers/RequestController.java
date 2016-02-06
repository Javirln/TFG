package es.us.lsi.restest.controllers;

import es.us.lsi.restest.models.URLForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class RequestController {

    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    public String sendRequest(@ModelAttribute URLForm urlForm, Model model) {
        model.addAttribute("urlForm", model);
        return "home";
    }
}
