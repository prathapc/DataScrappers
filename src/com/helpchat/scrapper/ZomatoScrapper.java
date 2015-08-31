package com.helpchat.scrapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ZomatoScrapper {

	public static void main(String k[]) {

		//String searchUrl = "https://www.zomato.com/ncr/restaurants?page=";
		String searchUrl = "https://www.zomato.com/bangalore/restaurants?page=";
		//String searchUrl = "https://www.zomato.com/bangalore/kumaraswamy-layout-restaurants?page=";
		Set<Zomato> zomatoDataList = new HashSet<Zomato>();
		try {
			Connection connection = Jsoup.connect(searchUrl).timeout(0);
			Document doc = connection.get();
			Map<String, String> cookie = connection.response().cookies();
			for (String cookee : cookie.keySet()) {
				connection.request().cookie(cookee, cookie.get(cookee));
			}

			int totalPages = Integer.parseInt(doc.select("div.pagination-number").text().replaceAll("Page 1 of", "").trim());

			System.out.println(totalPages);

			int page = 1;
			do {
				connection = Jsoup.connect(searchUrl + page).timeout(0);
				doc = connection.get();
				Elements pageList = doc.select("section#search-results-container").select("ol#orig-search-list").select("li");
				for (Element res : pageList) {
					try {
						Zomato zomato = new Zomato();

						String resId = res.attr("data-res_id").trim();
						String resName = res.select("div.search-name > h3 > a").text().trim();
						zomato.setRestaurantName(resName);
						String href = res.select("div.search-name > h3 > a").attr("href").trim();
						zomato.setHref(href);
						String rating = res.select("div.rating-for-" + resId).text().trim();
						zomato.setRating(rating);
						String category = res.select("div.res-snippet-small-cuisine .cblack").text().trim();
						zomato.setCategory(category);
						// bangalore exclude
						if (href.contains("é") || href.contains("ö")
								|| href.equals("0°") || href.contains("ü")) {
							continue;
						}
						if (href.equals("https://www.zomato.com/bangalore/café-mezzuna-st-marks-road")
								|| href.equals("https://www.zomato.com/bangalore/sai-café-nagarbhavi")) {
							continue;
						}

						// delhi exclude
						if (href.equals("https://www.zomato.com/ncr/ampm-café-bar-rajouri-garden-new-delhi")
								|| href.equals("https://www.zomato.com/ncr/azoté-sda-new-delhi")
								|| href.equals("https://www.zomato.com/ncr/bohème-vasant-vihar-new-delhi")
								|| href.equals("https://www.zomato.com/ncr/döner-grill-greater-kailash-gk-1-new-delhi")
								|| href.equals("https://www.zomato.com/ncr/the-village-café-vasant-kunj-new-delhi")
								|| href.equals("https://www.zomato.com/ncr/its-9-bakery-café-chanakyapuri-new-delhi")
								|| href.equals("https://www.zomato.com/ncr/café-tafri-sector-14-gurgaon")
								|| href.equals("https://www.zomato.com/ncr/cottage-café-by-smoothie-factory-janpath-new-delhi")
								|| href.equals("https://www.zomato.com/ncr/caffé-la-poya-sector-30-noida")
								|| href.equals("https://www.zomato.com/ncr/white-walkers-rajouri-garden-delhi")
								|| href.equals("https://www.zomato.com/ncr/longitude-0°8-bar-le-meridien-mg-road-gurgaon")
								|| href.equals("https://www.zomato.com/ncr/uttaranchal-dhaba-and-restaurant-mahipalpur-new-delhi")
								|| href.equals("https://www.zomato.com/ncr/kathi-rolls-vasant-kunj-new-delhi")
								|| href.equals("https://www.zomato.com/ncr/om-bhojnalaya-greater-noida-noida")) {
							continue;
						}

						// details call
						connection = Jsoup.connect(href).timeout(0);
						Document resDoc = connection.get();

						String latlong = resDoc.select("div#res-map-canvas div.resmap-img").attr("style").trim();
						String latitude = "";
						String longitude = "";
						if (!latlong.isEmpty()) {
							latlong = latlong.replaceAll("&.*", "");
							latlong = latlong.replaceAll(".*center=", "");

							latitude = latlong.split(",")[0];
							zomato.setLatitude(latitude);
							longitude = latlong.split(",")[1];
							zomato.setLongitude(longitude);
						}

						String resPhone = resDoc.select("span.res-tel").text().trim();
						zomato.setPhone(resPhone);
						String resAddress = resDoc.select("div.res-main-address-text").text().trim();
						zomato.setAddress(resAddress);
						String resKnownFor = resDoc.select("div.res-info-known-for-text").text().trim();
						zomato.setKnownFor(resKnownFor);
						String resFeaturedFor = resDoc.select("div.res-info-dishes-text").text().trim();
						zomato.setFeaturedFor(resFeaturedFor);
						String location = resDoc.select("div.res-main-subzone-links > a").text().trim();
						zomato.setLocation(location);
						String costForTwoText = resDoc.select("div.res-info-detail > span[itemprop = priceRange]").text().trim();
						int costForTwo;
						String costRange = "";
						if(costForTwoText != null && costForTwoText != "") {
							String costForTwoStrings[] = costForTwoText.split(" ");
							if(costForTwoStrings != null && costForTwoStrings.length > 1) {
								costForTwo = Integer.parseInt(costForTwoStrings[1].replace(",", "").trim());
								zomato.setCostForTwo(costForTwo);
								if(costForTwo < 400)
									costRange = "Low";
								else if(costForTwo > 400 && costForTwo <= 999)
									costRange = "Medium";
								else if(costForTwo > 999)
									costRange = "High";
								zomato.setCostRange(costRange);
							}
						}
						Element resImageDiv = resDoc.select("div.res-photo-thumbnails").first();
						String resImageUrl = null;
						if(resImageDiv != null) {
							Elements resImages = resImageDiv.children();
							for(Element resImage : resImages) {
								Element image = resImage.select("a").first().select("img.res-photo-thumbnail").first();
								if(image != null)
									resImageUrl = image.absUrl("src");
								break;
							}
						}
						if(resImageUrl != null)
							zomato.setResImageUrl(resImageUrl);
						
						Element cuisineDiv = resDoc.select("div.res-info-cuisines").first();
						if(cuisineDiv != null) {
							Elements cuisineElements = cuisineDiv.children();
							//List<String> cuisines = new ArrayList<String>();
							String resCuisines = "";
							for (Element cuisineElement : cuisineElements) {
								String cuisine = cuisineElement.select("a").text();
								//cuisines.add(cuisine);
								resCuisines += cuisine+" ,";
							}
							//zomato.setCuisine(cuisines.toString());
							if(resCuisines != "") {
								resCuisines = resCuisines.trim().replaceAll(",$", "").trim();
								zomato.setCuisine(resCuisines);
							}
						}
						
						//Map<String, String> resOpenHours = new HashMap<String, String>();
						String openingHours = "";
						Element openingHoursElements = resDoc.select("#res-week-timetable").first();
						if(openingHoursElements != null) {
							Elements openingHoursChildren = openingHoursElements.children();  //null pointer
							Elements childDivs = null;
							for (Element openingHourDiv : openingHoursChildren) {
								childDivs = openingHourDiv.children();
								//resOpenHours.put(childDivs.get(0).text(), childDivs.get(1).text());
								openingHours += childDivs.get(0).text() +"="+ childDivs.get(1).text() +"^";
							}
						}
						if(openingHours.length() > 0 && openingHours.endsWith("^"))
							openingHours = openingHours.substring(0, openingHours.length()-1);
						zomato.setOpeningHours(openingHours);
						
						String minOrder = resDoc.select("div.min-order-section .cblack").text().trim();
						zomato.setMinOrder(minOrder);
						
						//highlights div
						boolean homeDelivery = false;
						boolean servesNonVeg = false;
						Element highlightsElements = resDoc.select("div.res-info-highlights").first();
						if(highlightsElements != null) {
							Elements highlightsChildren = highlightsElements.children();
							Elements resInfoFeatureDivs = null;
							for (Element resInfoFeatureDiv : highlightsChildren) {
								resInfoFeatureDivs = resInfoFeatureDiv.children();
								if(resInfoFeatureDivs.size() > 1 && (resInfoFeatureDivs.get(1).text().trim()).equals("Home Delivery"))
									homeDelivery = true;
								if(resInfoFeatureDivs.size() > 1 && (resInfoFeatureDivs.get(1).text().trim()).equals("Serves Non Veg"))
									servesNonVeg = true;
							}
							zomato.setServeNonVeg(servesNonVeg);
							zomato.setHomeDelivery(homeDelivery);
						}

						System.out.println("\n Restaurant Name : " + resName+ " ||||  Latitude-Longitude : " + latlong);

						zomatoDataList.add(zomato);

					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}


				System.out.println("Current Page : " + page);
				page++;
			} while (page < totalPages);
			CsvDump.dumpDataToCsv(zomatoDataList);

		} catch (Exception e) {
			CsvDump.dumpDataToCsv(zomatoDataList);
			System.out.println("Exception Caused : " + e.getMessage());
			e.printStackTrace();
		}

	}

}
