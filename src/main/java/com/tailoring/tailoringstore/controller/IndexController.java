package com.tailoring.tailoringstore.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

  @RequestMapping("/")
  public String home(Model model) {
    model.addAttribute("message", "User");
    return "index";
  }

  @RequestMapping("/next")
  public String next(Model model) {
    model.addAttribute("message", "from another page");
    return "next";
  }

}