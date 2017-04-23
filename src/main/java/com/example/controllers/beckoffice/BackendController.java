package com.example.controllers.beckoffice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tonym on 06.02.2017.
 */
@Controller
@RequestMapping("/admin")
public class BackendController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
