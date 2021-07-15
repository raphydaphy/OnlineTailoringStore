package com.tailoring.tailoringstore.controller;

import java.util.Map;

import com.tailoring.tailoringstore.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

  @RequestMapping("/")
  public String home(@ModelAttribute("user") User user) {
    return "index";
  }

  @RequestMapping("/next")
  public String next(Model model) {
    model.addAttribute("message", "from another page");
    return "next";
  }

  @RequestMapping(value="/login", method=RequestMethod.POST)
  public String login(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      return "index";
    }

    System.out.println("Username: " + user.getUsername());
    System.out.println("Password: " + user.getPassword());

    model.put("user", user.getUsername());
    return "login";
  }

}