package com.example.controllers.frontoffice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by an on 05.02.2017.
 */
@Controller
@RequestMapping("/")
public class PagesController {

    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public String showMap(Model model) {

        return "map";
    }

}
