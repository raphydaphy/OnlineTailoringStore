package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

  @RequestMapping("/")
  public String home(Model model) {
    return "index";
  }

  @RequestMapping("/userLogin")
  public String home(@ModelAttribute("user") User user, Model model) {
    return "userLogin";
  }

  @RequestMapping("/userRegister")
  public String next(@ModelAttribute("user") User user, Model model) {
    return "userRegister";
  }

  @RequestMapping(value="/userSuccessLogin", method=RequestMethod.POST)
  public String login(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      return "userLogin";
    }

    System.out.println("Username: " + user.getUsername());
    System.out.println("Password: " + user.getPassword());

    model.put("user", user.getUsername());
    return "userSuccessLogin";
  }

}