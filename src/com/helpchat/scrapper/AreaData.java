package com.helpchat.scrapper;

import com.google.gson.annotations.SerializedName;

public class AreaData {


  @SerializedName("value")
  public String value;

  @SerializedName("fillSearchFormOnSelect")
  public fillSearchFormOnSelect fillSearchFormOnSelect;

  @Override
  public boolean equals(Object object) {
    boolean result = false;
    if (object == null || object.getClass() != getClass()) {
      result = false;
    } else {
      AreaData dishRow = (AreaData) object;
      if (this.value.equalsIgnoreCase(dishRow.getValue())) {
        result = true;
      }
    }
    return result;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public fillSearchFormOnSelect getFillSearchFormOnSelect() {
    return fillSearchFormOnSelect;
  }

  public void setFillSearchFormOnSelect(fillSearchFormOnSelect fillSearchFormOnSelect) {
    this.fillSearchFormOnSelect = fillSearchFormOnSelect;
  }

}
