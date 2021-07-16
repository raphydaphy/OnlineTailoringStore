package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.service.UserService;
import com.tailoring.tailoringstore.struct.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/login")
  public String login(@ModelAttribute("user") User user, Model model) {
    return "login";
  }

  @RequestMapping(value="/login", method=RequestMethod.POST)
  public String loginResponse(@ModelAttribute("user") User user, BindingResult result, ModelMap model, HttpServletRequest req) {
    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      return "login";
    }

    UserResponse response = userService.login(user);
    if (response.hasError()) {
      model.put("error", response.getError());
      return "login";
    } else {
      model.put("user", response.getUser());
      req.getSession().setAttribute("user", response.getUser().getUsername());
      return "account";
    }
  }

  @RequestMapping("/register")
  public String register(@ModelAttribute("user") User user, Model model) {
    return "register";
  }

  @RequestMapping(value="/register", method=RequestMethod.POST)
  public String registerResponse(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      return "register";
    }

    UserResponse response = userService.addUser(user);
    if (response.hasError()) {
      model.put("error", response.getError());
      return "register";
    } else {
      model.put("message", "New user created successfully!");
      return "login";
    }
  }

  @RequestMapping("/adminRegister")
  public String adminRegister(@ModelAttribute("user") User user, Model model) {
    return "adminRegister";
  }

  @RequestMapping(value="/adminRegister", method=RequestMethod.POST)
  public String adminRegisterResponse(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      model.put("error", "An error occurred. Please try again.");
      return "adminRegister";
    }

    user.setCategory("admin");
    UserResponse response = userService.addUser(user);

    if (response.hasError()) {
      model.put("error", response.getError());
      return "adminRegister";
    } else {
      model.put("message", "New admin created successfully!");
      return "login";
    }
  }

  @RequestMapping("/account")
  public String account(@ModelAttribute("user") User loginUser, ModelMap model, HttpServletRequest req) {
    User user = userService.addUserToModel(model, req);
    return user != null ? "account" : "login";
  }

  @RequestMapping("/logout")
  public String logout(HttpServletRequest req) {
    req.getSession().removeAttribute("user");
    return "index";
  }
}