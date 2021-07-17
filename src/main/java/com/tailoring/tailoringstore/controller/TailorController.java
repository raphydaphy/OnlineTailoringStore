package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.Pattern;
import com.tailoring.tailoringstore.model.TailorShop;
import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.service.CategoryService;
import com.tailoring.tailoringstore.service.TailorService;
import com.tailoring.tailoringstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TailorController {
  @Autowired
  private UserService userService;

  @Autowired
  private TailorService tailorService;

  @Autowired
  private CategoryService categoryService;

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

  @RequestMapping("/patterns")
  public String updateShopDetails(
    @ModelAttribute("user") User loginUser,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isTailor()) {
      model.put("error", "Only tailors can manage patterns");
      return "account";
    }

    model.put("patterns", tailorService.getPatterns(user.getUsername()));
    return "patterns";
  }

  @RequestMapping("/uploadPattern")
  public String uploadPattern(
    @ModelAttribute("user") User loginUser,
    @ModelAttribute("pattern") Pattern newPattern,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isTailor()) {
      model.put("error", "Only tailors can upload patterns");
      return "account";
    }

    model.put("categories", categoryService.getCategories());
    model.put("dressTypes", categoryService.getTailorDressTypes(user.getUsername()));
    return "uploadPattern";
  }

  @RequestMapping(value="/uploadPattern", method=RequestMethod.POST)
  public String uploadPatternResponse(
    @ModelAttribute("user") User loginUser,
    @ModelAttribute("pattern") Pattern pattern,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isTailor()) {
      model.put("error", "Only tailors can upload patterns");
      return "account";
    }

    pattern.setTailorUsername(user.getUsername());
    if (tailorService.uploadPattern(pattern)) {
      model.put("message", "New pattern uploaded successfully!");
    } else {
      model.put("error", "Failed to upload your pattern");
    }

    model.put("patterns", tailorService.getPatterns(user.getUsername()));
    return "patterns";
  }

  @GetMapping("/patternImage")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@RequestParam int patternId) {
    Pattern pattern = tailorService.getPattern(patternId);
    Resource file = new ByteArrayResource(pattern.getImageData());
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"pattern.jpg\"").body(file);
  }
}
