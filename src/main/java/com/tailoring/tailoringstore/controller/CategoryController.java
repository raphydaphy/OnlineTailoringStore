package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.Category;
import com.tailoring.tailoringstore.model.Subcategory;
import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.service.CategoryService;
import com.tailoring.tailoringstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private UserService userService;

  @RequestMapping("/categories")
  public String categories(
    @ModelAttribute("user") User loginUser, @ModelAttribute("category") Category newCategory,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isAdmin()) {
      model.put("error", "You must be an admin to manage clothing categories!");
      return "account";
    }

    model.put("categories", categoryService.getCategories());
    return "categories";
  }

  @RequestMapping("/subcategories")
  public String categories(
    @ModelAttribute("user") User loginUser, @ModelAttribute("subcategory") Subcategory newSubcategory,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";
    else if (!user.isAdmin()) {
      model.put("error", "You must be an admin to manage clothing subcategories!");
      return "account";
    }

    model.put("categories", categoryService.getCategories());
    model.put("subcategories", categoryService.getSubcategories());
    return "subcategories";
  }

  @RequestMapping(value="/deleteCategory", method= RequestMethod.GET)
  public String deleteCategory(
    @ModelAttribute("user") User loginUser, @ModelAttribute("category") Category newCategory,
    @RequestParam int categoryId, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    if (!user.isAdmin()) {
      model.put("error", "Only admins can manage clothing categories!");
      return "accounts";
    } else if (categoryService.deleteCategory(categoryId)) {
      model.put("message", "Category Deleted!");
    } else {
      model.put("error", "Failed to delete category");
    }

    model.put("categories", categoryService.getCategories());
    return "categories";
  }

  @RequestMapping(value="/deleteSubcategory", method=RequestMethod.GET)
  public String deleteSubcategory(
    @ModelAttribute("user") User loginUser, @ModelAttribute("subcategory") Subcategory newSubcategory,
    @RequestParam int subcategoryId, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    if (!user.isAdmin()) {
      model.put("error", "Only admins can manage clothing subcategories!");
      return "accounts";
    } else if (categoryService.deleteSubcategory(subcategoryId)) {
      model.put("message", "Subcategory Deleted!");
    } else {
      model.put("error", "Failed to delete subcategory");
    }

    model.put("categories", categoryService.getCategories());
    model.put("subcategories", categoryService.getSubcategories());
    return "subcategories";
  }

  @RequestMapping(value="/createCategory", method=RequestMethod.POST)
  public String createCategory(
    @ModelAttribute("user") User loginUser, @ModelAttribute("category") Category category,
    BindingResult result, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    if (!user.isAdmin()) {
      model.put("error", "You have to be an admin to create a category");
      return "account";
    }

    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      model.put("error", "Failed to create category. Please try again.");
      model.put("categories", categoryService.getCategories());
      return "categories";
    }

    if (categoryService.addCategory(category.getName())) {
      model.put("message", "New category '" + category.getName() + "' created successfully!");
    } else {
      model.put("error", "Failed to create '" + category.getName() + "' category. Please try again");
    }

    model.put("categories", categoryService.getCategories());
    return "categories";
  }

  @RequestMapping(value="/createSubcategory", method=RequestMethod.POST)
  public String createSubcategory(
    @ModelAttribute("user") User loginUser, @ModelAttribute("subcategory") Subcategory subcategory,
    BindingResult result, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    if (!user.isAdmin()) {
      model.put("error", "You have to be an admin to create a subcategory");
      return "account";
    }

    model.put("categories", categoryService.getCategories());

    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      model.put("error", "Failed to create subcategory. Please try again.");
      model.put("subcategories", categoryService.getSubcategories());
      return "subcategories";
    }

    if (categoryService.addSubcategory(subcategory.getSubcategoryName(), subcategory.getCategoryId())) {
      model.put("message", "New subcategory '" + subcategory.getSubcategoryName() + "' created successfully!");
    } else {
      model.put("error", "Failed to create '" + subcategory.getSubcategoryName() + "' subcategory. Please try again");
    }

    model.put("subcategories", categoryService.getSubcategories());
    return "subcategories";
  }
}
