package com.helpchat.scrapper;

import com.google.gson.annotations.SerializedName;


public class PriceVariation {

  @SerializedName("variation")
  private String variation;

  @SerializedName("price")
  private String price;

  public String getVariation() {
    return variation;
  }

  public void setVariation(String variation) {
    this.variation = variation;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

}
