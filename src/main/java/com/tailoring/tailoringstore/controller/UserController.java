package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.SecurityQuestion;
import com.tailoring.tailoringstore.model.SecurityQuestionPrompt;
import com.tailoring.tailoringstore.model.SecurityQuestions;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/")
  public String home(ModelMap model, HttpServletRequest req) {
    User user = userService.addUserToModel(model, req);
    return user != null ? "account" : "index";
  }

  @RequestMapping("/login")
  public String login(@ModelAttribute("user") User user, Model model) {
    return "login";
  }

  @RequestMapping(value="/login", method=RequestMethod.POST)
  public String loginResponse(
    @ModelAttribute("user") User user,
    @ModelAttribute("securityQuestions") SecurityQuestions newSecurityQuestions,
    BindingResult result, ModelMap model, HttpServletRequest req
  ) {
    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      return "login";
    }

    UserResponse response = userService.login(user);
    if (response.hasError()) {
      model.put("error", response.getError());
      return "login";
    }

    model.put("user", response.getUser());
    req.getSession().setAttribute("user", response.getUser().getUsername());

    List<SecurityQuestion> existingSecurityQuestions = userService.getSecurityQuestions(response.getUser());
    if (existingSecurityQuestions.size() == 0) {
      model.put("prompts", userService.getSecurityQuestionPrompts());
      return "createSecurityQuestions";
    }

    return "account";
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

  @RequestMapping("/createSecurityQuestions")
  public String createSecurityQuestions(
    @ModelAttribute("user") User loginUser,
    @ModelAttribute("securityQuestions") SecurityQuestions newSecurityQuestions,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    List<SecurityQuestion> questions = userService.getSecurityQuestions(user);
    if (questions.size() > 0) {
      model.put("error", "You've already chosen your security questions!");
      return "account";
    }

    model.put("prompts", userService.getSecurityQuestionPrompts());
    return "createSecurityQuestions";
  }

  @RequestMapping(value="/createSecurityQuestions",method=RequestMethod.POST)
  public String createSecurityQuestionsResponse(
    @ModelAttribute("user") User loginUser,
    @ModelAttribute("securityQuestions") SecurityQuestions newSecurityQuestions,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    List<SecurityQuestion> existingQuestions = userService.getSecurityQuestions(user);
    if (existingQuestions.size() > 0) {
      model.put("error", "You've already chosen your security questions!");
      return "account";
    }

    List<Integer> promptIds = new ArrayList<>();
    List<SecurityQuestion> securityQuestions = newSecurityQuestions.getQuestionList();

    for (SecurityQuestion question : securityQuestions) {
      if (promptIds.contains(question.getPromptId())) {
        model.put("error", "You can't use the same question twice!");
        model.put("prompts", userService.getSecurityQuestionPrompts());
        return "createSecurityQuestions";
      }
      promptIds.add(question.getPromptId());
    }

    for (SecurityQuestion question : securityQuestions) {
      userService.addSecurityQuestion(user, question);
    }

    model.put("message", "Your new security questions have been saved");
    return "account";
  }

  @RequestMapping("/forgotUsername")
  public String forgotUsername(@ModelAttribute("user") User user, ModelMap model) {
    return "forgotUsername";
  }

  @RequestMapping(value="/forgotUsername", method=RequestMethod.POST)
  public String forgotUsernameResponse(
    @ModelAttribute("user") User user,
    @ModelAttribute("securityQuestions") SecurityQuestions securityQuestions,
    ModelMap model
  ) {
    if ((user.getEmail() == null || user.getEmail().length() == 0) && (user.getContactNumber() == null || user.getContactNumber().length() == 0)) {
      model.put("error", "Please enter your email or contact number to continue");
      return "forgotUsername";
    }

    User foundUser = userService.getUserWithEmailOrNumber(user.getEmail(), user.getContactNumber());
    if (foundUser == null) {
      model.put("error", "There are no users that match those details!");
      return "forgotUsername";
    }

    List<SecurityQuestion> questions = userService.getSecurityQuestions(foundUser);
    if (questions.size() != 3) {
      model.put("error", "You haven't set any security questions for your account, so it's impossible to retrieve your username.");
      return "forgotUsername";
    }

    model.put("questions", questions);
    model.put("user", user);
    return "answerSecurityQuestions";
  }

  @RequestMapping(value="/forgotPassword", method=RequestMethod.GET)
  public String resetPassword(@ModelAttribute("securityQuestions") SecurityQuestions securityQuestions, @RequestParam String username, ModelMap model) {
    // TODO: reset password
    return "forgotUsername";
  }

  @RequestMapping(value="/answerSecurityQuestions", method=RequestMethod.POST)
  public String answerSecurityQuestions(
    @ModelAttribute("user") User loginUser, @ModelAttribute("securityQuestions") SecurityQuestions securityQuestions, ModelMap model
  ) {
    boolean forPassword = true;
    User user = userService.getUser(securityQuestions.getUsername());

    if (user == null) {
      forPassword = false;
      user = userService.getUserWithEmailOrNumber(securityQuestions.getEmail(), securityQuestions.getContactNumber());
    }

    String action = forPassword ? "reset your password": "retrieve your username";

    if (user == null) {
      model.put("error", "Failed to " + action + ": An unexpected error occurred");
      return "login";
    }


    List<SecurityQuestion> realQuestions = userService.getSecurityQuestions(user);
    List<SecurityQuestion> submittedQuestions = securityQuestions.getQuestionList();

    model.put("username", securityQuestions.getUsername());
    model.put("email", securityQuestions.getEmail());
    model.put("contactNumber", securityQuestions.getContactNumber());
    model.put("questions", realQuestions);

    for (SecurityQuestion realQuestion : realQuestions) {
      boolean matched = false;
      for (SecurityQuestion submittedQuestion : submittedQuestions) {
        if (submittedQuestion.getPromptId() == realQuestion.getPromptId()) {
          System.out.println(submittedQuestion.getPromptId() + " == " + realQuestion.getPromptId());
          matched = true;

          if (!submittedQuestion.getAnswer().equalsIgnoreCase(realQuestion.getAnswer())) {
            model.put("error", "One or more of your answers was incorrect!");
            return "answerSecurityQuestions";
          }

          break;
        } else {
          System.out.println(submittedQuestion.getPromptId() + " != " + realQuestion.getPromptId());
        }
      }
      if (!matched) {
        model.put("error", "You didn't answer all of your security questions! (" + realQuestion.getQuestion() + ")");
        return "answerSecurityQuestions";
      }
    }

    if (!forPassword) {
      model.put("message", "Your username is " + user.getUsername());
      return "login";
    }

    // TODO
    model.put("message", "Password reset is unimplemented..");
    return "login";
  }
}