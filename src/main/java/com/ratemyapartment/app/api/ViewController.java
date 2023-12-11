package com.ratemyapartment.app.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {

    @GetMapping("search-page/apartment")
    public String searchPageApartment(Model model) {
        return "search-page-apartment";
    }

    @GetMapping("test-header")
    public String testHeader(Model model) {
        return "header";
    }
}
