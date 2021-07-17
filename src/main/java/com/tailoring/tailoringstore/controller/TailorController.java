package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.TailorShop;
import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.service.TailorService;
import com.tailoring.tailoringstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TailorController {
  @Autowired
  private UserService userService;

  @Autowired
  private TailorService tailorService;

  @RequestMapping("/updateShopDetails")
  public String updateShopDetails(
    @ModelAttribute("user") User loginUser,
    @ModelAttribute("tailorShop") TailorShop tailorShop,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isTailor()) {
      model.put("error", "Only tailors can update their shop details");
      return "account";
    }

    TailorShop shop = tailorService.getShop(user.getUsername());

    if (shop == null) {
      shop = new TailorShop();
      shop.setName(user.getFirstName() + "'s Shop");
      shop.setContactNumber(user.getContactNumber());
    }

    model.put("shop", shop);
    return "updateShopDetails";
  }

  @RequestMapping(value="updateShopDetails", method= RequestMethod.POST)
  public String updateShopDetailsResponse(
    @ModelAttribute("user") User loginUser,
    @ModelAttribute("tailorShop") TailorShop tailorShop,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isTailor()) {
      model.put("error", "Only tailors can update their shop details");
      return "account";
    }

    tailorShop.setTailorUsername(user.getUsername());
    model.put("shop", tailorShop);

    if (!tailorService.updateShopDetails(tailorShop)) {
      model.put("error", "Failed to update your tailor shop details");
    } else {
      model.put("message", "Your shop details were successfully updated!");
    }

    return "updateShopDetails";
  }
}
