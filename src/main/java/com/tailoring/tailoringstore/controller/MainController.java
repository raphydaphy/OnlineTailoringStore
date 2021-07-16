package com.tailoring.tailoringstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
  @RequestMapping("/")
  public String home(Model model) {
    return "index";
  }
}
