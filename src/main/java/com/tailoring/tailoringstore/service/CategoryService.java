package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
    String sql = "SELECT d.*, s.subcategoryName from dressTypes d INNER JOIN subcategories s ON d.subcategoryId = s.subcategoryId ORDER BY s.subcategoryName, d.dressTypeName";
    try {
      return jdbcTemplate.query(sql, new DressType.DressTypeRowMapper(false));
    } catch (Exception e) {
      System.err.println("Failed to get dress types: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public List<DressType> getTailorDressTypes(String tailorUsername) {
    String sql = "SELECT d.*, s.subcategoryName, t.enabled from dressTypes d INNER JOIN subcategories s ON d.subcategoryId = s.subcategoryId ";
    sql += "LEFT JOIN (SELECT * FROM tailorDressTypes WHERE tailorUsername = ?) t ON d.dressTypeId = t.dressTypeId ORDER BY s.subcategoryName, d.dressTypeName";
    try {
      return jdbcTemplate.query(sql, new Object[]{tailorUsername}, new DressType.DressTypeRowMapper(true));
    } catch (Exception e) {
      System.err.println("Failed to get tailor dress types: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public TailorDressType getTailorDressPreference(String tailorUsername, int dressTypeId){
    String sql = "SELECT * FROM tailorDressTypes WHERE tailorUsername = ? AND dressTypeId = ?";
    try {
      List<TailorDressType> tailorDressTypes = jdbcTemplate.query(sql, new Object[]{tailorUsername, dressTypeId}, new TailorDressType.TailorDressTypeRowMapper());
      return tailorDressTypes.size() == 0 ? null : tailorDressTypes.get(0);
    } catch (Exception e) {
      System.err.println("Failed to get tailor dress type setting: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  public boolean setTailorDressTypePreference(String tailorUsername, int dressTypeId, boolean enabled) {
    TailorDressType existing = getTailorDressPreference(tailorUsername, dressTypeId);
    String sql = "INSERT INTO tailorDressTypes (enabled, dressTypeId, tailorUsername) VALUES (?, ?, ?)";
    if (existing != null) {
      if (existing.isEnabled() == enabled) return true;
      sql = "UPDATE tailorDressTypes SET enabled = ? WHERE dressTypeId = ? AND tailorUsername = ?";
    }
    try {
      jdbcTemplate.update(sql, enabled, dressTypeId, tailorUsername);
      return true;
    } catch (DataAccessException e) {
      System.err.println("Failed to update tailor dress type setting: " + e.getMessage());
      e.printStackTrace();
      return false;
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
