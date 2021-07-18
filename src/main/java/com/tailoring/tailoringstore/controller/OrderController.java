package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.*;
import com.tailoring.tailoringstore.service.CategoryService;
import com.tailoring.tailoringstore.service.OrderService;
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
public class OrderController {
  @Autowired
  private UserService userService;

  @Autowired
  private TailorService tailorService;

  @Autowired
  private OrderService orderService;

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
    model.put("areas", tailorService.getTailorShopAreas());
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
      model.put("areas", tailorService.getTailorShopAreas());
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
      model.put("areas", tailorService.getTailorShopAreas());
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
    } else if (!orderService.placeOrder(order)) {
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

    orderService.addOrdersToModel(user, model);
    return "orders";
  }

  @RequestMapping("/deleteOrder")
  public String deleteOrder(
    @ModelAttribute("user") User loginUser, @RequestParam int id, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    Order order = orderService.getOrder(id);

    if (order == null) {
      model.put("error", "Failed to delete order: Invalid order ID");
    } else if (user.isCustomer() && !order.getCustomerUsername().equals(user.getUsername())) {
      model.put("error", "You don't have permission to delete that order!");
    } else if (user.isTailor() && !order.getTailorUsername().equals(user.getUsername())) {
      model.put("error", "You don't have permission to delete that order!");
    } else if (!orderService.deleteOrder(id)) {
      model.put("error", "Failed to delete order");
    } else {
      model.put("message", "Order #" + id + " successfully deleted!");
    }

    orderService.addOrdersToModel(user, model);
    return "orders";
  }

  @RequestMapping("/order")
  public String order(
    @ModelAttribute("user") User loginUser, @RequestParam int id, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    Order order = orderService.getOrder(id);
    String error = null;

    if (order == null) {
      error = "Failed to view order: Invalid order ID";
    } else if (user.isCustomer() && !order.getCustomerUsername().equals(user.getUsername())) {
      error = "You don't have permission to view that order!";
    } else if (user.isTailor() && !order.getTailorUsername().equals(user.getUsername())) {
      error = "You don't have permission to view that order!";
    }

    if (error != null) {
      orderService.addOrdersToModel(user, model);
      return "orders";
    }

    model.put("order", order);
    return "order";
  }

  @RequestMapping("/acceptOrder")
  public String acceptOrder(
    @ModelAttribute("user") User loginUser, @RequestParam int id, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isTailor()) {
      model.put("error", "Only tailors can accept orders!");
      orderService.addOrdersToModel(user, model);
      return "orders";
    }

    Order order = orderService.getOrder(id);

    if (order == null) {
      model.put("error", "Failed to accept order: Invalid Order ID");
      orderService.addOrdersToModel(user, model);
      return "orders";
    }

    Pattern pattern = tailorService.getPattern(order.getPatternId());
    String error = null;

    if (pattern == null) {
      error = "Failed to accept order: An unexpected error occurred";
    } if (!order.getTailorUsername().equals(user.getUsername())) {
      error = "You don't have permission to accept that order!";
    } else if (order.getOrderStatusId() != 1) {
      error = "You can't accept an order that isn't new!";
    }

    if (error != null) {
      model.put("error", error);
      orderService.addOrdersToModel(user, model);
      return "orders";
    }

    model.put("order", order);
    model.put("orderPattern", pattern);
    model.put("mode", "accept");

    return "updateOrder";
  }

  @RequestMapping(value="/acceptOrder", method=RequestMethod.POST)
  public String acceptOrderResponse(
    @ModelAttribute("User") User loginUser, @ModelAttribute("Order") Order order, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isTailor()) {
      model.put("error", "Only tailors can accept orders!");
      orderService.addOrdersToModel(user, model);
      return "orders";
    }

    Order storedOrder = orderService.getOrder(order.getOrderId());
    String error = null;

    if (storedOrder == null) {
      error = "Failed to accept order: Invalid Order ID";
    } else if (!storedOrder.getTailorUsername().equals(user.getUsername())) {
      error = "You don't have permission to accept that order!";
    } else if (storedOrder.getOrderStatusId() != 1) {
      error = "You can't accept an order that isn't new!";
    } else if (!orderService.acceptOrder(order)) {
      error = "Failed to accept order!";
    } else {
      model.put("message", "Order accepted!");
    }

    if (error != null) {
      model.put("error", error);
    }

    orderService.addOrdersToModel(user, model);
    return "orders";
  }

  @RequestMapping(value="/updateOrder", method=RequestMethod.GET)
  public String updateOrder(
    @ModelAttribute("user") User loginUser, @RequestParam int id, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isTailor()) {
      model.put("error", "Only tailors can update orders!");
      orderService.addOrdersToModel(user, model);
      return "orders";
    }

    Order order = orderService.getOrder(id);

    if (order == null) {
      model.put("error", "Failed to update order: Invalid Order ID");
      orderService.addOrdersToModel(user, model);
      return "orders";
    }

    Pattern pattern = tailorService.getPattern(order.getPatternId());
    String error = null;

    if (pattern == null) {
      error = "Failed to update order: An unexpected error occurred";
    } if (!order.getTailorUsername().equals(user.getUsername())) {
      error = "You don't have permission to update that order!";
    } else if (order.getOrderStatusId() != 2) {
      error = "You can't update an order that isn't active!";
    }

    if (error != null) {
      model.put("error", error);
      orderService.addOrdersToModel(user, model);
      return "orders";
    }

    model.put("order", order);
    model.put("orderPattern", pattern);
    model.put("mode", "update");

    return "updateOrder";
  }

  @RequestMapping(value="/updateOrder", method=RequestMethod.POST)
  public String updateOrderResponse(
    @ModelAttribute("User") User loginUser, @ModelAttribute("Order") Order order, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isTailor()) {
      model.put("error", "Only tailors can update orders!");
      orderService.addOrdersToModel(user, model);
      return "orders";
    }

    Order storedOrder = orderService.getOrder(order.getOrderId());
    String error = null;

    if (storedOrder == null) {
      error = "Failed to update order: Invalid Order ID";
    } else if (!storedOrder.getTailorUsername().equals(user.getUsername())) {
      error = "You don't have permission to update that order!";
    } else if (storedOrder.getOrderStatusId() != 2) {
      error = "You can't accept an update that isn't active!";
    } else if (!orderService.updateOrder(order)) {
      error = "Failed to update order!";
    } else {
      model.put("message", "Order updated!");
    }

    if (error != null) {
      model.put("error", error);
    }

    orderService.addOrdersToModel(user, model);
    return "orders";
  }

  @RequestMapping("/completeOrder")
  public String completeOrder(
    @ModelAttribute("user") User loginUser, @RequestParam int id, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    Order order = orderService.getOrder(id);

    if (!user.isTailor()) {
      model.put("error", "Only tailors can complete orders");
    } else if (!order.getTailorUsername().equals(user.getUsername())) {
      model.put("error", "You don't have permission to complete that order!");
    } else if (!orderService.completeOrder(id)) {
      model.put("error", "Failed to complete order");
    } else {
      model.put("message", "Order #" + id + " successfully completed!");
    }

    orderService.addOrdersToModel(user, model);
    return "orders";
  }

  @RequestMapping(value="/writeReview", method=RequestMethod.POST)
  public String writeReviewResponse(
    @ModelAttribute("User") User loginUser, @ModelAttribute("review") Review review, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    Order order = orderService.getOrder(review.getOrderId());
    if (order == null) {
      model.put("error", "Failed to save your review: Invalid order ID");
      return "account";
    }

    Review existingReview = orderService.getReview(order);

    if (!order.getCustomerUsername().equals(user.getUsername())) {
      model.put("error", "Failed to save your review: You don't have permission to review that order!");
    } else if (existingReview != null) {
      model.put("error", "Failed to save your review: You can't review an order more than once!");
    } else if (!orderService.addReview(review)) {
      model.put("error", "Failed to save your review!");
    } else {
      model.put("message", "Thank you for your review!");
    }

    return "account";
  }

  @RequestMapping("/pay")
  public String pay(
    @ModelAttribute("user") User loginUser, @ModelAttribute("payment") Payment payment,
    @RequestParam("order") int orderId, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    Order order = orderService.getOrder(orderId);

    if (!user.isCustomer()) {
      model.put("error", "Only customers can pay for orders");
    } else if (!order.getCustomerUsername().equals(user.getUsername())) {
      model.put("error", "You don't have permission to pay for that order!");
    } else if (order.isPaid()) {
      model.put("error", "You've already paid for that order!");
    } else {
      model.put("order", order);
      return "pay";
    }

    orderService.addOrdersToModel(user, model);
    return "orders";
  }

  @RequestMapping(value="/pay", method=RequestMethod.POST)
  public String payResponse(
    @ModelAttribute("user") User loginUser, @ModelAttribute("payment") Payment payment,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    Order order = orderService.getOrder(payment.getOrderId());

    if (!user.isCustomer()) {
      model.put("error", "Only customers can pay for orders");
    } else if (!order.getCustomerUsername().equals(user.getUsername())) {
      model.put("error", "You don't have permission to pay for that order!");
    } else if (order.isPaid()) {
      model.put("error", "You've already paid for that order!");
    } else if (!orderService.makePayment(payment)) {
      model.put("error", "Payment failed!");
    } else {
      model.put("message", "Payment successful!");
    }

    orderService.addOrdersToModel(user, model);
    return "orders";
  }
}
