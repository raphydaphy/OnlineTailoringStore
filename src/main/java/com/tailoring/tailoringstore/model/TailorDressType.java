package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TailorDressType {
  private int dressTypeId;
  private String tailorUsername;
  private boolean enabled;

  public int getDressTypeId() {
    return dressTypeId;
  }

  public void setDressTypeId(int dressTypeId) {
    this.dressTypeId = dressTypeId;
  }

  public String getTailorUsername() {
    return tailorUsername;
  }

  public void setTailorUsername(String tailorUsername) {
    this.tailorUsername = tailorUsername;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public static class TailorDressTypeRowMapper implements RowMapper<TailorDressType> {
    @Override
    public TailorDressType mapRow(ResultSet rs, int rowNum) throws SQLException {
      TailorDressType tailorDressType = new TailorDressType();
      tailorDressType.setDressTypeId(rs.getInt("dressTypeId"));
      tailorDressType.setTailorUsername(rs.getString("tailorUsername"));
      tailorDressType.setEnabled(rs.getBoolean("enabled"));
      return tailorDressType;
    }
  }
}
