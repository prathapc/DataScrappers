package com.helpchat.scrapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class CsvDump {

	 public static void dumpDataToCsv(Set<Zomato> entities) {
		    int rownum = 0;

		    HSSFWorkbook workbook = new HSSFWorkbook();
		    HSSFSheet sheet = workbook.createSheet("DATA");

		    Row headerRow = sheet.createRow(rownum++);
		    // Set Column Widths
		    sheet.setColumnWidth(0, 8000);
		    sheet.setColumnWidth(1, 8000);
		    sheet.setColumnWidth(2, 8000);
		    sheet.setColumnWidth(3, 8000);
		    sheet.setColumnWidth(4, 8000);
		    sheet.setColumnWidth(5, 8000);
		    sheet.setColumnWidth(6, 8000);
		    sheet.setColumnWidth(7, 8000);
		    sheet.setColumnWidth(8, 8000);
		    sheet.setColumnWidth(9, 8000);
		    sheet.setColumnWidth(10, 8000);
		    sheet.setColumnWidth(11, 8000);
		    sheet.setColumnWidth(12, 8000);
		    sheet.setColumnWidth(13, 8000);
		    sheet.setColumnWidth(14, 8000);
		    sheet.setColumnWidth(15, 8000);
		    sheet.setColumnWidth(16, 8000);
		    sheet.setColumnWidth(17, 8000);
		    sheet.setColumnWidth(18, 8000);
		    sheet.setColumnWidth(19, 8000);

		    // Setup the Page margins - Left, Right, Top and Bottom
		    sheet.setMargin(Sheet.LeftMargin, 0.25);
		    sheet.setMargin(Sheet.RightMargin, 0.25);
		    sheet.setMargin(Sheet.TopMargin, 0.75);
		    sheet.setMargin(Sheet.BottomMargin, 0.75);

		    // Setup the Header and Footer Margins
		    sheet.setMargin(Sheet.HeaderMargin, 0.25);
		    sheet.setMargin(Sheet.FooterMargin, 0.25);

		    Cell cell0 = headerRow.createCell(0);
		    cell0.setCellValue("S.No");
		    
		    Cell cell1 = headerRow.createCell(1);
		    cell1.setCellValue("Name");

		    Cell cell2 = headerRow.createCell(2);
		    cell2.setCellValue("Href");
		    
		    Cell cell3 = headerRow.createCell(3);
		    cell3.setCellValue("Serve Nov-veg");
		    
		    Cell cell4 = headerRow.createCell(4);
		    cell4.setCellValue("Featured for");
		    
		    Cell cell5 = headerRow.createCell(5);
		    cell5.setCellValue("Known for");
		    
		    Cell cell6 = headerRow.createCell(6);
		    cell6.setCellValue("Location");
		    
		    Cell cell7 = headerRow.createCell(7);
		    cell7.setCellValue("Rating");
		    
		    Cell cell8 = headerRow.createCell(8);
		    cell8.setCellValue("Cost for 2");
		    
		    Cell cell9 = headerRow.createCell(9);
		    cell9.setCellValue("Cost range");
		    
		    Cell cell10 = headerRow.createCell(10);
		    cell10.setCellValue("Cuisine");
		    
		    Cell cell11 = headerRow.createCell(11);
		    cell11.setCellValue("Category");
		    
		    Cell cell12 = headerRow.createCell(12);
		    cell12.setCellValue("Address");
		    
		    Cell cell13 = headerRow.createCell(13);
		    cell13.setCellValue("Phone");
		    
		    Cell cell14 = headerRow.createCell(14);
		    cell14.setCellValue("Opening hours");
		    
		    Cell cell15 = headerRow.createCell(15);
		    cell15.setCellValue("Min order");
		    
		    Cell cell16 = headerRow.createCell(16);
		    cell16.setCellValue("Home Delivery");
		    
		    Cell cell17 = headerRow.createCell(17);
		    cell17.setCellValue("Latitude");

		    Cell cell18 = headerRow.createCell(18);
		    cell18.setCellValue("Longitude");

		    Cell cell19 = headerRow.createCell(19);
		    cell19.setCellValue("ResImage");
		    
		    sheet.createRow(rownum++);

		    int i=0;
		    for (Zomato entity : entities) {
		      Row row = sheet.createRow(rownum++);

		      Cell c0 = row.createCell(0);
		      c0.setCellValue(++i);
		      
		      Cell c1 = row.createCell(1);
		      c1.setCellValue(entity.getRestaurantName());
		      
		      Cell c2 = row.createCell(2);
		      c2.setCellValue(entity.getHref());
		      
		      Cell c3 = row.createCell(3);
		      if(entity.isServeNonVeg() == true)
		    	  c3.setCellValue("Yes");
		      else
		    	  c3.setCellValue("No");
		      
		      Cell c4 = row.createCell(4);
		      c4.setCellValue(entity.getFeaturedFor());
		      
		      Cell c5 = row.createCell(5);
		      c5.setCellValue(entity.getKnownFor());
		      
		      Cell c6 = row.createCell(6);
		      c6.setCellValue(entity.getLocation());
		      
		      Cell c7 = row.createCell(7);
		      c7.setCellValue(entity.getRating());
		      
		      Cell c8 = row.createCell(8);
		      c8.setCellValue(entity.getCostForTwo());
		      
		      Cell c9 = row.createCell(9);
		      c9.setCellValue(entity.getCostRange());

		      Cell c10 = row.createCell(10);
		      c10.setCellValue(entity.getCuisine());
		      
		      Cell c11 = row.createCell(11);
		      c11.setCellValue(entity.getCategory());
		      
		      Cell c12 = row.createCell(12);
		      c12.setCellValue(entity.getAddress());
		      
		      Cell c13 = row.createCell(13);
		      c13.setCellValue(entity.getPhone());
		      
		      Cell c14 = row.createCell(14);
		      c14.setCellValue(entity.getOpeningHours());
		      
		      Cell c15 = row.createCell(15);
		      c15.setCellValue(entity.getMinOrder());
		      
		      Cell c16 = row.createCell(16);
		      if(entity.isHomeDelivery() == true)
		    	  c16.setCellValue("Yes");
		      else
		    	  c16.setCellValue("No");
		      
		      Cell c17 = row.createCell(17);
		      c17.setCellValue(entity.getLatitude());
		      
		      Cell c18 = row.createCell(18);
		      c18.setCellValue(entity.getLongitude());
		      
		      Cell c19 = row.createCell(19);
		      c19.setCellValue(entity.getResImageUrl());

		    }
		    //String fileName = "/Users/prathap/Documents/BNG_Restaurants.xls";
		    String fileName = "/opt/BNG_Zomato_Restaurants.xls";
		    FileOutputStream fos;
		    try {
		      fos = new FileOutputStream(fileName);
		      workbook.write(fos);
		      fos.close();
		      System.out.println(fileName + " written successfully");
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
  public static void dumpZomatoDataToCsv(Set<Zomato> entities) {
    int rownum = 0;

    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("DATA");

    Row headerRow = sheet.createRow(rownum++);
    // Set Column Widths
    sheet.setColumnWidth(0, 8000);
    sheet.setColumnWidth(1, 8000);
    sheet.setColumnWidth(2, 8000);

    // Setup the Page margins - Left, Right, Top and Bottom
    sheet.setMargin(Sheet.LeftMargin, 0.25);
    sheet.setMargin(Sheet.RightMargin, 0.25);
    sheet.setMargin(Sheet.TopMargin, 0.75);
    sheet.setMargin(Sheet.BottomMargin, 0.75);

    // Setup the Header and Footer Margins
    sheet.setMargin(Sheet.HeaderMargin, 0.25);
    sheet.setMargin(Sheet.FooterMargin, 0.25);

    Cell cell1 = headerRow.createCell(0);
    cell1.setCellValue("Name");

    Cell cell2 = headerRow.createCell(1);
    cell2.setCellValue("Latitude");

    Cell cell3 = headerRow.createCell(2);
    cell3.setCellValue("Longitude");

    sheet.createRow(rownum++);

    for (Zomato entity : entities) {
      Row row = sheet.createRow(rownum++);

      Cell c1 = row.createCell(0);
      c1.setCellValue(entity.getRestaurantName());

      Cell c2 = row.createCell(1);
      c2.setCellValue(entity.getLatitude());
      Cell c3 = row.createCell(2);
      c3.setCellValue(entity.getLongitude());

    }
    String fileName = "/Users/paragkhanna/Documents/ncrRestaurants5.xls";
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(fileName);
      workbook.write(fos);
      fos.close();
      System.out.println(fileName + " written successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void dumpFoodPandaMenuDataToCsv(List<RestaurantDishRow> entities) {
    long entitiesSize = entities.size();
    int entitiesIteration = 0;
    if (entitiesSize > 65534) {
      entitiesIteration = (int) (entitiesSize / 65534);
    }

    if (entitiesIteration > 0) {
      entitiesIteration = entitiesIteration + 1;
      long total = 0;
      for (int k = 0; k < entitiesIteration; k++) {
        long diff = entitiesSize - total;
        List<RestaurantDishRow> subEntitiesList = new ArrayList<RestaurantDishRow>();
        if (diff < 65534) {
          subEntitiesList = entities.subList(65534 * k, entities.size());
        } else {
          subEntitiesList = entities.subList(65534 * k, 65534 * (k + 1));
        }
        total = total + subEntitiesList.size();
        printCsv(subEntitiesList, k);
      }
    } else {
      printCsv(entities, 0);
    }


  }

  public static void printCsv(List<RestaurantDishRow> entities, int k) {

    int rownum = 0;

    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("DATA");

    Row headerRow = sheet.createRow(rownum++);
    // Set Column Widths
    sheet.setColumnWidth(0, 8000);
    sheet.setColumnWidth(1, 8000);
    sheet.setColumnWidth(2, 8000);
    sheet.setColumnWidth(3, 15000);

    // Setup the Page margins - Left, Right, Top and Bottom
    sheet.setMargin(Sheet.LeftMargin, 0.25);
    sheet.setMargin(Sheet.RightMargin, 0.25);
    sheet.setMargin(Sheet.TopMargin, 0.75);
    sheet.setMargin(Sheet.BottomMargin, 0.75);

    // Setup the Header and Footer Margins
    sheet.setMargin(Sheet.HeaderMargin, 0.25);
    sheet.setMargin(Sheet.FooterMargin, 0.25);

    Cell cell1 = headerRow.createCell(0);
    cell1.setCellValue("Restaurant Name");

    Cell cell2 = headerRow.createCell(1);
    cell2.setCellValue("Category Name");

    Cell cell3 = headerRow.createCell(2);
    cell3.setCellValue("Dish Name");

    Cell cell4 = headerRow.createCell(3);
    cell4.setCellValue("Price Variation");
    sheet.createRow(rownum++);

    for (RestaurantDishRow dishRow : entities) {
      Row row = sheet.createRow(rownum++);

      Cell c1 = row.createCell(0);
      c1.setCellValue(dishRow.getRestaurantName());

      Cell c2 = row.createCell(1);
      c2.setCellValue(dishRow.getDishCategory());

      Cell c3 = row.createCell(2);
      c3.setCellValue(dishRow.getDishName());

      Cell c4 = row.createCell(3);
      c4.setCellValue(dishRow.getPriceVariation());
    }

    //String fileName = "/Users/prathap/Documents/foodpanda2/foodpandaDataBlrAll_" + k + ".xls";
    String fileName = "/opt/foodpandaDataBlrAll_" + k + ".xls";
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(fileName);
      workbook.write(fos);
      fos.close();
      System.out.println(fileName + " written successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void dumpFoodPandaDataToCsv(List<FoodPandaRestaurant> foodPandaRestaurantDetails) {
    int rownum = 0;

    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("DATA");

    Row headerRow = sheet.createRow(rownum++);
    // Set Column Widths
    sheet.setColumnWidth(0, 8000);
    sheet.setColumnWidth(1, 8000);
    sheet.setColumnWidth(2, 8000);
    sheet.setColumnWidth(3, 8000);

    // Setup the Page margins - Left, Right, Top and Bottom
    sheet.setMargin(Sheet.LeftMargin, 0.25);
    sheet.setMargin(Sheet.RightMargin, 0.25);
    sheet.setMargin(Sheet.TopMargin, 0.75);
    sheet.setMargin(Sheet.BottomMargin, 0.75);

    // Setup the Header and Footer Margins
    sheet.setMargin(Sheet.HeaderMargin, 0.25);
    sheet.setMargin(Sheet.FooterMargin, 0.25);

    Cell cell1 = headerRow.createCell(0);
    cell1.setCellValue("Restaurant Name");

    Cell cell2 = headerRow.createCell(1);
    cell2.setCellValue("Delivery Time");

    Cell cell3 = headerRow.createCell(2);
    cell3.setCellValue("Min Order");

    Cell cell4 = headerRow.createCell(3);
    cell4.setCellValue("Delivery Fee");

    sheet.createRow(rownum++);

    for (FoodPandaRestaurant res : foodPandaRestaurantDetails) {
      Row row = sheet.createRow(rownum++);

      Cell c1 = row.createCell(0);
      c1.setCellValue(res.getRestaurantName());

      Cell c2 = row.createCell(1);
      c2.setCellValue(res.getDeliveryTime());

      Cell c3 = row.createCell(2);
      c3.setCellValue(res.getMinOrderValue());

      Cell c4 = row.createCell(3);
      c4.setCellValue(res.getDeliveryFee());
    }

    //String fileName = "/Users/prathap/Documents/foodpanda2/foodpandaResBlrAll.xls";
    String fileName = "/opt/foodpandaResBlrAll.xls";
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(fileName);
      workbook.write(fos);
      fos.close();
      System.out.println(fileName + " written successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
