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
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;

public class Spider {

  private static final ConcurrentHashMap<String, List<String>> robotsCache = new ConcurrentHashMap<>();

  public Spider(String seedUrl) {
    fetch(seedUrl);
  }

  private Document fetch(String url) {
    if (!(url.toLowerCase().substring(0, 7).equals("http://")
        || url.toLowerCase().subSequence(0, 8).equals("https://"))) {
      return null;
    }
    try {
      Connection urlConnection = Jsoup.connect(url).ignoreContentType(true);
      Document urlDocument = urlConnection.get();
      parseRobotsTxt(url);
      return urlDocument;

    } catch (IOException ioException) {
      return null;
    }
  }

  private List<WebPage> extractUrls(Document document) {
    List<WebPage> webPages = new ArrayList<>();
    if (document == null) {
      return webPages;
    }

    return null;
  }

  private List<String> parseRobotsTxt(String url) throws IOException {
    List<String> parsedRobots = new ArrayList<>();
    if (url.substring(url.length() - 1).equals("/")) {
      url = url + "robots.txt";
    } else {
      url = url + "/robots.txt";
    }
    Connection connection = Jsoup.connect(url).ignoreContentType(true);
    Document document = connection.get();
    String robotsBody = document.text();
    System.out.println(robotsBody);

    return parsedRobots;
  }
}
