package com.Tawhidul.Tyse.model;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
    getUrls(seedUrl, 1);
    System.out.println("Got urls " + urls);
  }

  public void getUrls(String url, int runs) {
    if (runs == 0 || !validateUrl(url)) {
      return;
    }

    try {
      URI uri = new URI(url);
      String host = uri.getScheme() + "://" + uri.getHost();

      String robotsUrl = host + "/robots.txt";
      if (!robotsCache.containsKey(robotsUrl)) {
        System.out.println("New Robots.txt found: " + robotsUrl);

        robotsCache.put(robotsUrl, new ArrayList<String>());
        List<String> robotsList = robotsCache.get(robotsUrl);

        Document robotsDoc = Jsoup.connect(robotsUrl).get();
        BufferedReader reader = new BufferedReader(new StringReader(robotsDoc.wholeText()));
        String line = reader.readLine();
        while (line != null) {
          robotsList.add(line);
          line = reader.readLine();
        }

        parseRobots(robotsList);
      }
      String fullUrl = uri.normalize().toASCIIString();
      if (!urlsCache.contains(fullUrl)) {

      }

      Document d = Jsoup.connect(url).get();
      Elements links = d.select("a[href]");
      for (Element link : links) {
        String linkAsString = link.attr("href");
        getUrls(linkAsString, runs - 1);
      }
    } catch (Exception e) {
      System.out.println("Error with URL:" + url + " " + e);
    }
  }

  public void parseRobots(List<String> list) {
    System.out.println("Parsed Robots.txt " + list);
  }

  public boolean validateUrl(String url) {
    return true;
  }

}
