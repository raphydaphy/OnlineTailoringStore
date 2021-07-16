package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.service.UserService;
import com.tailoring.tailoringstore.struct.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/")
  public String home(Model model) {
    return "index";
  }

  @RequestMapping("/userLogin")
  public String userLogin(@ModelAttribute("user") User user, Model model) {
    return "userLogin";
  }

  @RequestMapping(value="/userSuccessLogin", method=RequestMethod.POST)
  public String userSuccessLogin(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      return "userLogin";
    }

    System.out.println("Logging in as " + user.toString());
    UserResponse response = userService.login(user);

    if (response.hasError()) {
      model.put("error", response.getError());
      return "userLogin";
    } else {
      model.put("user", response.getUser().getUsername());
      return "userSuccessLogin";
    }
  }

  @RequestMapping("/userRegister")
  public String userRegister(@ModelAttribute("user") User user, Model model) {
    return "userRegister";
  }

  @RequestMapping(value="/userSuccessRegister", method=RequestMethod.POST)
  public String userSuccessRegister(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      return "userRegister";
    }

    System.out.println("Registering " + user.toString());

    UserResponse response = userService.addUser(user);

    if (response.hasError()) {
      model.put("error", response.getError());
      return "userRegister";
    } else {
      model.put("user", response.getUser().getUsername());
      return "userSuccessRegister";
    }
  }

}