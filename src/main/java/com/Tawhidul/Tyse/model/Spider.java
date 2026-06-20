package com.Tawhidul.Tyse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider {

  private static final ConcurrentHashMap<String, Set<String>> robotsCache = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap.KeySetView<String, Boolean> urlsCache = ConcurrentHashMap.newKeySet();

  // TEMPORARY!!
  private List<String> urls;

  private String userAgent;

  public Spider(String seedUrl) {
    userAgent = "TyseBot";
    urls = new ArrayList<String>();
    urls.add(seedUrl);
    crawl(2);
    System.out.println("Got urls " + urls);
  }

  private void crawl(int runs) {
    if (runs <= 0) {
      return;
    }
    String url = getUrlFromQueue();
    if (url == null) {
      return;
    }

    crawlUrls(url);
    crawlDocuments(url);

    crawl(runs - 1);
  }

  private void crawlUrls(String url) {
    if (!isValidUrl(url)) {
      System.out.println("Ended url search");
      return;
    }
    try {

      URI uri = new URI(url);
      String host = uri.getScheme() + "://" + uri.getHost();
      System.out.println("Found host: " + host);
      appendRobotsUrl(host);

      Connection connection = Jsoup.connect(url)
          .ignoreHttpErrors(true)
          .userAgent(userAgent)
          .timeout(5000);
      connection.execute();

      if (connection.response().statusCode() != 200) {
        System.out.println("not 200 code: " + url);
        return;
      }

      Document d = connection.response().parse();

      Elements elements = d.select("a[href]");
      Set<String> links = new HashSet<>();
      elements.stream()
          .map(element -> element.attr("abs:href"))
          .forEach(link -> links.add(link));
      links.iterator().forEachRemaining((a) -> appendUrlToQueue(a));

    } catch (Exception e) {
      System.err.println("Error with url: " + url);
      e.printStackTrace();
    }
  }

  private void crawlDocuments(String url) {
    try {
      URI uri = new URI(url);

      // Ex. https://google.com
      // TODO ensure url is not disallowed in robots.txt
      String host = uri.getScheme() + "://" + uri.getHost();
      if (robotsCache.containsKey(host)) {
        Set<String> robots = robotsCache.get(host);

      }

    } catch (Exception e) {

    }
  }

  public void appendRobotsUrl(String host) throws IOException {
    if (!robotsCache.containsKey(host)) {

      String robotsUrl = host + "/robots.txt";
      Connection connection = Jsoup.connect(robotsUrl)
          .ignoreHttpErrors(true)
          .userAgent(userAgent)
          .timeout(5000);
      connection.execute();

      if (connection.response().statusCode() != 200) {
        return;
      }
      Set<String> robotsSet = new HashSet<>();

      Document robotsDoc = connection.response().parse();

      BufferedReader br = new BufferedReader(new StringReader(robotsDoc.wholeText()));
      String currentLine = br.readLine();

      boolean appropriateAgent = false;
      while (currentLine != null) {
        String trimmedLine = currentLine.trim();

        if (trimmedLine.startsWith("#") || trimmedLine.isEmpty()) {
          continue;
        }

        String lowerCaseTrimmedLine = trimmedLine.toLowerCase();
        if (lowerCaseTrimmedLine.startsWith("user-agent:")) {
          String agent = trimmedLine.substring(11);
          appropriateAgent = (agent.equals("*") || agent.equals(userAgent));
        }
        if (appropriateAgent && lowerCaseTrimmedLine.startsWith("disallow:")) {
          String path = trimmedLine.substring(9);
          if (!path.isEmpty()) {
            robotsSet.add(path);
          }
        }

        currentLine = br.readLine();
      }
      robotsCache.put(host, robotsSet);
    }
  }

  // TODO: implement an actual queue system

  public void appendUrlToQueue(String url) {
    if (!urlsCache.contains(url)) {
      urlsCache.add(url);
      urls.add(url);
    }
  }

  public String getUrlFromQueue() {
    if (urls.isEmpty()) {
      return null;
    }
    return urls.remove(0);
  }

  // TODO implement robots path check from robotsCache
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
