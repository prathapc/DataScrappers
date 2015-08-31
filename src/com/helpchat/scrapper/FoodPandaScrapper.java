package com.helpchat.scrapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FoodPandaScrapper {

  public static String BASE_URL = "https://www.foodpanda.in";
  public static int BENGALURU_CITY_ID = 11;

  public static void main(String k[]) {
    // 65535
    int totalCount = 0;
    Set<RestaurantDishRow> restaurantDishRowsSet = new HashSet<RestaurantDishRow>();
    Set<FoodPandaRestaurant> foodPandaRestaurantDetails = new HashSet<FoodPandaRestaurant>();
    List<RestaurantDishRow> restaurantDishRows = new ArrayList<RestaurantDishRow>();
    Set<String> restaurantResNamesSet = new HashSet<String>();
    String areaName = new String();
    try {

      int cityId = BENGALURU_CITY_ID;
      String cityUrl = BASE_URL + "/city-suggestions-ajax/" + cityId;
      String cityAreaData = HttpUtility.sendGet(cityUrl);

      if (null == cityAreaData) {
        System.out.println("cityAreaData is NULL");
        return;
      }

      Gson gson = new Gson();

      Type collectionType = new TypeToken<List<AreaData>>() {}.getType();
      List<AreaData> areaDataList = gson.fromJson(cityAreaData, collectionType);
      System.out.println("AreaData size : " + areaDataList.size());
      List<AreaData> subAreaDataList = areaDataList.subList(0, areaDataList.size());

      int areaDataIndex = 0;
      for (AreaData areaData : subAreaDataList) {
        System.out.println("Area Data Index : " + areaDataIndex);
        int count = 0;

        areaName = areaData.getValue();
        System.out.println("****Getting Restaurants for " + areaData.getValue());
        String areaId = areaData.getFillSearchFormOnSelect().getAreaId();

        int page = 0;
        while (true) {
          page++;
          String searchUrl =
              BASE_URL
                  + "/restaurants?currentUrl=https%3A%2F%2Fwww.foodpanda.in/location-suggestions?cityId="
                  + cityId + "&area=" + areaName + "&button=&area_id=" + areaId
                  + "&user_search=&sort=&sort=&page=" + page;

          Connection connection =
              Jsoup
                  .connect(searchUrl)
                  .timeout(0)
                  .userAgent(
                      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36");

          Document doc = connection.get();
          Map<String, String> cookie = connection.response().cookies();
          for (String cookee : cookie.keySet()) {
            connection.request().cookie(cookee, cookie.get(cookee));
          }

          Elements restaurantElements = doc.select("article.js-vendor-list-vendor-panel");
          if (!restaurantElements.isEmpty()) {
            for (Element restData : restaurantElements) {
              String resName = restData.select("a.js-fire-click-tracking-event").text();
              if (resName.contains("Go to menu")) {
                resName = resName.replace("Go to menu", "");
              }
              if (resName.contains("Preorder now")) {
                resName = resName.replace("Preorder now", "");
              }

              String resDetailUrl = restData.select("a.js-fire-click-tracking-event").attr("href");

              if (restaurantResNamesSet.contains(resName)) {
                continue;
              }
              Map<String, Map<String, List<Dish>>> resToMenuData =
                  menuData(resName, resDetailUrl, restaurantDishRowsSet, foodPandaRestaurantDetails);
              // System.out.println("\n\n Restaurant Data : " + resToMenuData);
              RestaurantDishRow restaurantDishRow = new RestaurantDishRow();
              restaurantDishRow.setRestaurantName("");
              restaurantDishRow.setDishCategory("");
              restaurantDishRow.setDishName("");
              restaurantDishRow.setPriceVariation("");
              restaurantDishRowsSet.add(restaurantDishRow);
              restaurantResNamesSet.add(resName);
              count++;
              totalCount++;
            }
          } else {
            break;
          }
        }
        System.out.println("Count : " + count);
        System.out.println("********** SET for Area " + areaName + " done !!!!");
        areaDataIndex++;
      }
      restaurantDishRows = new ArrayList<RestaurantDishRow>(restaurantDishRowsSet);
      Collections.sort(restaurantDishRows, new RestaurantDishRowComparator());
    } catch (Exception e) {
      CsvDump.dumpFoodPandaMenuDataToCsv(restaurantDishRows);
      CsvDump
          .dumpFoodPandaDataToCsv(new ArrayList<FoodPandaRestaurant>(foodPandaRestaurantDetails));

    }
    CsvDump.dumpFoodPandaMenuDataToCsv(restaurantDishRows);
    CsvDump.dumpFoodPandaDataToCsv(new ArrayList<FoodPandaRestaurant>(foodPandaRestaurantDetails));
    System.out.println("Total Count : " + totalCount);
  }

  public static Map<String, Map<String, List<Dish>>> menuData(String resName, String resDetailUrl,
      Set<RestaurantDishRow> restaurantDishRows, Set<FoodPandaRestaurant> foodPandaRestaurantDetails) {
	  if(resName.trim().equals("Mast Kalandar (Marathahalli)"))
		  System.out.println("got it");
    String searchUrl = BASE_URL + resDetailUrl;
    Map<String, Map<String, List<Dish>>> resToMenuMap =
        new HashMap<String, Map<String, List<Dish>>>();
    try {
      Connection connection =
          Jsoup
              .connect(searchUrl)
              .timeout(0)
              .userAgent(
                  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36");
      Document doc = connection.get();
      Map<String, String> cookie = connection.response().cookies();
      for (String cookee : cookie.keySet()) {
        connection.request().cookie(cookee, cookie.get(cookee));
      }

      List<String> dgNames = new ArrayList<String>();
      Map<String, List<Dish>> dgToDishList = new HashMap<String, List<Dish>>();
      Elements dgList = doc.select("ul.categories__list").select("li");
      for (Element dgElement : dgList) {
        String dgName = dgElement.select("a").html();
        dgNames.add(dgName);
      }

      Elements menuArticleList = doc.select("article.menu__category");
      for (Element menuArticle : menuArticleList) {
        String dgName = menuArticle.select("div.menu__category__title").text();
        Elements menuItems = menuArticle.select("article.menu-item");
        List<Dish> dishes = new ArrayList<Dish>();
        for (Element menuItem : menuItems) {
          String dishName = menuItem.select("div.menu-item__title").text();
          Elements dishVariations = menuItem.select("article.menu-item__variation");

          List<PriceVariation> priceVariations = new ArrayList<PriceVariation>();
          for (Element dishVariation : dishVariations) {
            String variation = dishVariation.select("div.menu-item__variation__title").text();
            String variationPrice = dishVariation.select("div.menu-item__variation__price").text();
            String[] priceVar;
            if(variationPrice != null && variationPrice != "") {
            	variationPrice = variationPrice.trim();
            	 if(variationPrice.contains(" ")) {
                 	priceVar = variationPrice.split(" ");
                 	variationPrice = priceVar[0].trim();
                 }
            }
           
            
            variation = variation.trim();
            if (variation.equals(" ") || variation.length() == 1) {
              variation = "NA";
            }

            //PriceVariation priceVariation = new PriceVariation();
            //priceVariation.setVariation(variation);
            //priceVariation.setPrice(variationPrice);
            //priceVariations.add(priceVariation);

          

          //Gson gson = new Gson();
          //String priceVariationJson = gson.toJson(priceVariations);

            Dish dish = new Dish();
            if(variation != "NA")
            	dish.setName(dishName+" ("+variation+")");
            else
            	dish.setName(dishName);
            dish.setPrice(variationPrice);
            dishes.add(dish);

            RestaurantDishRow restaurantDishRow = new RestaurantDishRow();
            restaurantDishRow.setRestaurantName(resName);
            restaurantDishRow.setDishCategory(dgName);
            if(variation != "NA")
            	restaurantDishRow.setDishName(dishName+" ("+variation+")");
            else
            	restaurantDishRow.setDishName(dishName);
           
            restaurantDishRow.setPriceVariation(variationPrice);
            restaurantDishRows.add(restaurantDishRow);
          }
        }
        dgToDishList.put(dgName, dishes);
      }

      String deliveryTime = doc.select("li.cart__empty__element--minimum-delivery-time").text();
      String minOrderValue = doc.select("li.cart__empty__element--minimum-order-amount").text();
      String deliveryFee = doc.select("li.cart__empty__element--minimum-delivery-fee").text();
      String[] delTime, minOrder, delFee;
      if(deliveryTime != null && deliveryTime.contains(":")) {
    	  delTime = deliveryTime.split(":");
    	  deliveryTime = delTime[1].trim();
      }
      if(minOrderValue != null && minOrderValue.contains(":")) {
    	  minOrder = minOrderValue.split(":");
    	  minOrderValue = minOrder[1].trim();
      }
      if(deliveryFee != null && deliveryFee.contains(":")) {
    	  delFee = deliveryFee.split(":");
    	  deliveryFee = delFee[1].trim();
      }
    	  
      FoodPandaRestaurant foodPandaRestaurant = new FoodPandaRestaurant();
      foodPandaRestaurant.setRestaurantName(resName);
      foodPandaRestaurant.setDeliveryTime(deliveryTime);
      foodPandaRestaurant.setMinOrderValue(minOrderValue);
      foodPandaRestaurant.setDeliveryFee(deliveryFee);
      foodPandaRestaurantDetails.add(foodPandaRestaurant);

      resToMenuMap.put(resName, dgToDishList);
    } catch (Exception e) {
      CsvDump.dumpFoodPandaMenuDataToCsv(new ArrayList<RestaurantDishRow>(restaurantDishRows));
      CsvDump
          .dumpFoodPandaDataToCsv(new ArrayList<FoodPandaRestaurant>(foodPandaRestaurantDetails));
      System.out.println("Exception caused : " + e.getMessage());
      e.printStackTrace();
    }
    return resToMenuMap;
  }
}
