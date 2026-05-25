package com.Tawhidul.Tyse.model;

import java.io.IOException;
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

  // TEMPORARY!!
  private static List<String> urls;

  public Spider(String seedUrl) {
    urls = new ArrayList<String>();
    getUrls(seedUrl, 5);
    System.out.println(urls);
  }

  public void getUrls(String url, int runs) {
    if (runs == 0) {
      return;
    }
    try {
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

  public void urlHandler(String url) {
    urls.add(url);
  }

}
