package com.Tawhidul.Tyse.model;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
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
      String host = uri.getHost();

      if (!urlsCache.contains(host)) {
        System.out.println("New host url found: " + host);
        String robotsUrl = host + "/robots.txt";
        if (!robotsCache.containsKey(robotsUrl)) {
          System.out.println("New Robots.txt found: " + robotsUrl);
          robotsCache.put(robotsUrl, new ArrayList<String>());
          List<String> robotsList = robotsCache.get(robotsUrl);
          parseRobots(robotsList);
        }
      }

      Document d = Jsoup.connect(url).get();
      Elements links = d.select("a[href]");
      for (Element link : links) {
        String linkAsString = link.attr("href");
        urlHandler(linkAsString);
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

  public void urlHandler(String url) {
    urls.add(url);
  }

}
