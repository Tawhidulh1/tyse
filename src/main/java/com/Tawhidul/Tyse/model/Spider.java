package com.Tawhidul.Tyse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider {

  private static final ConcurrentHashMap<String, List<String>> robotsCache = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap.KeySetView<String, Boolean> urlsCache = ConcurrentHashMap.newKeySet();

  // TEMPORARY!!
  private static List<String> urls;

  public Spider(String seedUrl) {
    urls = new ArrayList<String>();
    getUrls(seedUrl, 2);
    System.out.println("Got urls " + urls);
  }

  public void getUrls(String url, int runs) {
    if (runs == 0 || !isValidUrl(url)) {
      return;
    }

    try {

      URI uri = new URI(url);
      String host = uri.getScheme() + "://" + uri.getHost();
      System.out.println("Found host: " + host);
      appendRobotsUrl(host);

      Connection connection = Jsoup.connect(url)
          .ignoreHttpErrors(true);

      connection.execute();
      if (connection.response().statusCode() != 200) {
        return;
      }

      Document d = connection.get();

      appendUrlToQueue(url);
      Elements links = d.select("a[href]");
      for (Element link : links) {
        String linkAsString = link.attr("href");
        getUrls(linkAsString, runs - 1);
      }

    } catch (Exception e) {
      System.err.println("Error with url: " + url);
      e.printStackTrace();
    }
  }

  public void appendRobotsUrl(String host) throws IOException {
    if (!robotsCache.containsKey(host)) {
      robotsCache.put(host, new ArrayList<String>());
      List<String> robotsList = robotsCache.get(host);

      String robotsUrl = host + "/robots.txt";
      Connection connection = Jsoup.connect(robotsUrl)
          .ignoreHttpErrors(true);
      connection.execute();

      if (connection.response().statusCode() != 200) {
        return;
      }
      Document robotsDoc = connection.get();

      BufferedReader br = new BufferedReader(new StringReader(robotsDoc.wholeText()));
      String currentLine = br.readLine();
      while (currentLine != null) {
        robotsList.add(currentLine);
        currentLine = br.readLine();
      }
    }
  }

  public void appendUrlToQueue(String url) {
    if (!urlsCache.contains(url)) { // TODO implement an actual queue system
      urls.add(url);
    }
  }

  public boolean isValidUrl(String url) {
    try {
      URI uri = new URI(url);
      if (uri.getScheme() == null) {
        return false;
      }
    } catch (Exception e) {
      System.err.println("Unable to validateUrl: " + url);
      e.printStackTrace();
    }
    return true;
  }

}
