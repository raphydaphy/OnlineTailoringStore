package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.Category;
import com.tailoring.tailoringstore.model.DressType;
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
      return new ArrayList<>();
    }
  }

  public List<Subcategory> getSubcategories() {
    try {
      return jdbcTemplate.query("SELECT * from subcategories", new Subcategory.SubcategoryRowMapper());
    } catch (Exception e) {
      System.err.println("Failed to get subcategories: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public List<DressType> getDressTypes() {
    try {
      return jdbcTemplate.query("SELECT d.*, s.subcategoryName from dressTypes d INNER JOIN subcategories s ON d.subcategoryId = s.subcategoryId", new DressType.DressTypeRowMapper());
    } catch (Exception e) {
      System.err.println("Failed to get dress types: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public boolean deleteCategory(int categoryId, boolean sub) {
    try {
      String table = sub ? "subcategories" : "categories";
      String id = sub ? "subcategoryId" : "categoryId";
      jdbcTemplate.update("DELETE FROM " + table + " WHERE " + id + " = ?", categoryId);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to delete category: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteCategory(int categoryId) {
    return deleteCategory(categoryId, false);
  }

  public boolean deleteSubcategory(int subcategoryId) {
    return deleteCategory(subcategoryId, true);
  }

  public boolean deleteDressType(int dressTypeId) {
    try {
      jdbcTemplate.update("DELETE FROM dressTypes WHERE dressTypeId = ?", dressTypeId);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to delete dress type #" + dressTypeId + ": " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean addCategory(String name, boolean sub) {
    try {
      String table = sub ? "subcategories" : "categories";
      String id = sub ? "subcategoryName" : "categoryName";
      jdbcTemplate.update("INSERT INTO " + table + " (" + id + ") VALUES (?)", name);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to add category '" + name + "': " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean addCategory(String name) {
    return addCategory(name, false);
  }

  public boolean addSubcategory(String name) {
    return addCategory(name, true);
  }

  public boolean addDressType(String name, int subcategoryId) {
    try {
      jdbcTemplate.update("INSERT INTO dressTypes (dressTypeName, subcategoryId) VALUES (?, ?)", name, subcategoryId);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to add dress type '" + name + "': " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}
