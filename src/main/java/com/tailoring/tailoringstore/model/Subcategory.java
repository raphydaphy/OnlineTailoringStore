package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Subcategory {
  private int subcategoryId;
  private String subcategoryName;

  public int getSubcategoryId() {
    return subcategoryId;
  }

  public void setSubcategoryId(int subcategoryId) {
    this.subcategoryId = subcategoryId;
  }

  public String getSubcategoryName() {
    return subcategoryName;
  }

  public void setSubcategoryName(String subcategoryName) {
    this.subcategoryName = subcategoryName;
  }

  public static class SubcategoryRowMapper implements RowMapper<Subcategory> {
    @Override
    public Subcategory mapRow(ResultSet rs, int rowNum) throws SQLException {
      Subcategory category = new Subcategory();
      category.setSubcategoryId(rs.getInt("subcategoryId"));
      category.setSubcategoryName(rs.getString("subcategoryName"));
      return category;
    }
  }
}
