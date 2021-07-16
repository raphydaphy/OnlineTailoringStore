package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Category {
  private int categoryId;
  private String name;

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static class CategoryRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
      Category category = new Category();
      category.setCategoryId(rs.getInt("categoryId"));
      category.setName(rs.getString("categoryName"));
      return category;
    }
  }
}
