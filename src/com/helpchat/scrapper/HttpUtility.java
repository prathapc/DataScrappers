package com.helpchat.scrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtility {

  public static String sendGet(String url) throws Exception {
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    // optional default is GET
    con.setRequestMethod("GET");
    con.setRequestProperty(
        "User-Agent",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36");

    int responseCode = con.getResponseCode();
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    // print result
    // LOGGER.info("Response : " + response.toString());
    return response.toString();

  }

}
