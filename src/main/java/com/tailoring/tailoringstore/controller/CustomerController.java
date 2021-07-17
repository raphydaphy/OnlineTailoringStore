package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.*;
import com.tailoring.tailoringstore.service.CategoryService;
import com.tailoring.tailoringstore.service.CustomerService;
import com.tailoring.tailoringstore.service.TailorService;
import com.tailoring.tailoringstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomerController {
  @Autowired
  private UserService userService;

  @Autowired
  private TailorService tailorService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CategoryService categoryService;


  @RequestMapping("/tailors")
  public String tailors(
    @ModelAttribute("user") User loginUser,
    @ModelAttribute("tailorShopSearch") TailorShopSearch tailorShopSearch,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isCustomer()) {
      model.put("error", "Only customers can search for tailor shops!");
      return "account";
    }

    model.put("dressTypes", categoryService.getDressTypes());
    model.put("tailorShops", tailorService.getTailorShops(tailorShopSearch));
    return "tailors";
  }

  @RequestMapping("/tailor")
  public String tailor(
    @ModelAttribute("user") User loginUser,
    @ModelAttribute("tailorShopSearch") TailorShopSearch tailorShopSearch,
    @RequestParam String id, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isCustomer()) {
      model.put("error", "Only customers can view tailor shops!");
      return "account";
    }

    TailorShop shop = tailorService.getShop(id);
    if (shop == null) {
      model.put("error", "Invalid tailor shop");

      model.put("dressTypes", categoryService.getDressTypes());
      model.put("tailorShops", tailorService.getTailorShops(tailorShopSearch));
      return "tailors";
    }

    model.put("shop", shop);
    model.put("patterns", tailorService.getPatterns(id));
    return "tailor";
  }

  @RequestMapping("/makeOrder")
  public String makeOrder(
    @ModelAttribute("user") User loginUser, @ModelAttribute("tailorShopSearch") TailorShopSearch tailorShopSearch,
    @ModelAttribute("order") Order order, @RequestParam("tailor") String tailorUsername, @RequestParam("pattern") int patternId,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isCustomer()) {
      model.put("error", "Only customers can place orders!");
      return "account";
    }

    TailorShop shop = tailorService.getShop(tailorUsername);
    if (shop == null) {
      model.put("error", "Invalid tailor shop");

      model.put("dressTypes", categoryService.getDressTypes());
      model.put("tailorShops", tailorService.getTailorShops(tailorShopSearch));
      return "tailors";
    }

    model.put("shop", shop);
    Pattern pattern = tailorService.getPattern(patternId);

    if (pattern == null) {
      model.put("error", "Invalid pattern");
      model.put("patterns", tailorService.getPatterns(tailorUsername));
      return "tailor";
    }

    model.put("orderPattern", pattern);
    return "makeOrder";
  }

  @RequestMapping(value="/makeOrder", method= RequestMethod.POST)
  public String makeOrder(
    @ModelAttribute("user") User loginUser, @ModelAttribute("order") Order order,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isCustomer()) {
      model.put("error", "Only customers can place orders!");
      return "account";
    }

    TailorShop shop = tailorService.getShop(order.getTailorUsername());
    Pattern pattern = tailorService.getPattern(order.getPatternId());

    if (shop == null) {
      model.put("error", "Failed to place order: Invalid tailor shop");
    } else if (pattern == null) {
      model.put("error", "Failed to place order: Invalid pattern");
    } else if (!customerService.placeOrder(order)) {
      model.put("error", "Failed to place order");
    } else {
      model.put("message", "Your order was placed successfully");
    }

    return "account";
  }

  @RequestMapping("/orders")
  public String orders(@ModelAttribute("user") User loginUser, ModelMap model, HttpServletRequest req) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    model.put("orders", customerService.getOrders(user.isCustomer() ? user.getUsername() : null, user.isTailor() ? user.getUsername() : null));
    return "orders";
  }

  @RequestMapping("/deleteOrder")
  public String deleteOrder(
    @ModelAttribute("user") User loginUser, @RequestParam int id, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    Order order = customerService.getOrder(id);

    if (order == null) {
      model.put("error", "Failed to delete order: Invalid order ID");
    } else if (user.isCustomer() && !order.getCustomerUsername().equals(user.getUsername())) {
      model.put("error", "You don't have permission to delete that order!");
    } else if (user.isTailor() && !order.getTailorUsername().equals(user.getUsername())) {
      model.put("error", "You don't have permission to delete that order!");
    } else if (!customerService.deleteOrder(id)) {
      model.put("error", "Failed to delete order");
    } else {
      model.put("message", "Order #" + id + " successfully deleted!");
    }

    model.put("orders", customerService.getOrders(user.isCustomer() ? user.getUsername() : null, user.isTailor() ? user.getUsername() : null));
    return "orders";
  }
}
