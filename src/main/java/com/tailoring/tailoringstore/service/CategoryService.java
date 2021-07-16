package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.Category;
import com.tailoring.tailoringstore.model.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryService {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Category> getCategories() {
    try {
      return jdbcTemplate.query("SELECT * from categories", new Category.CategoryRowMapper());
    } catch (Exception e) {
      System.err.println("Failed to get categories: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<Category>();
    }
  }

  public List<Subcategory> getSubcategories() {
    try {
      return jdbcTemplate.query("SELECT s.*, c.categoryName from subcategories s INNER JOIN categories c ON s.categoryId = c.categoryId", new Subcategory.SubcategoryRowMapper());
    } catch (Exception e) {
      System.err.println("Failed to get subcategories: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<Subcategory>();
    }
  }

  public boolean deleteCategory(int categoryId) {
    try {
      jdbcTemplate.update("DELETE FROM categories WHERE categoryId = ?", categoryId);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to delete category: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteSubcategory(int subcategoryId) {
    try {
      jdbcTemplate.update("DELETE FROM subcategories WHERE subcategoryId = ?", subcategoryId);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to delete subcategory: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean addCategory(String name) {
    try {
      jdbcTemplate.update("INSERT INTO categories (categoryName) VALUES (?)", name);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to add category '" + name + "': " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean addSubcategory(String name, int categoryId) {
    try {
      jdbcTemplate.update("INSERT INTO subcategories (subcategoryName, categoryId) VALUES (?, ?)", name, categoryId);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to add subcategory '" + name + "': " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}
