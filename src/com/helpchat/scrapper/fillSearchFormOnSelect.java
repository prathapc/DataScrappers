package com.helpchat.scrapper;

import com.google.gson.annotations.SerializedName;

public class fillSearchFormOnSelect {

  @SerializedName("area_id")
  private String areaId;

  public String getAreaId() {
    return areaId;
  }

  public void setAreaId(String areaId) {
    this.areaId = areaId;
  }


}
