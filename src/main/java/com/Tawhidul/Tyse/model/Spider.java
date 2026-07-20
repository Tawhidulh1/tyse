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
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Spider {

  private static final ConcurrentHashMap<String, Set<String>> robotsCache = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<String, Set<String>> index = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap.KeySetView<String, Boolean> urlsCache = ConcurrentHashMap.newKeySet();

  // TEMPORARY!!
  private List<String> urls;

  private String userAgent;

  public Spider(String seedUrl) {
    userAgent = "TyseBot";
    urls = new ArrayList<String>();
    urls.add(seedUrl);
    crawl(10);
    System.out.println("Got urls " + urls);
  }

  private void crawl(int runs) {
    System.out.println("Crawl(" + runs + ")");
    if (runs <= 0) {
      return;
    }
    String url = getUrl();
    if (url == null) {
      return;
    }
    crawlUrls(url);
    crawlDocuments(url);

    crawl(runs - 1);
  }

  private void crawlUrls(String url) {
    System.out.println("fetching urls: " + url);
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
      links.iterator().forEachRemaining((a) -> appendUrl(a));

    } catch (Exception e) {
      System.err.println("Error with url: " + url);
      e.printStackTrace();
    }
  }

  private void crawlDocuments(String url) {
    System.out.println("fetching documents: " + url);

    try {
      if (!isValidUrl(url)) {
        return;
      }

      Connection connection = Jsoup.connect(url)
          .ignoreHttpErrors(true)
          .timeout(5000)
          .userAgent(userAgent);
      connection.execute();

      if (connection.response().statusCode() != 200) {
        System.out.println("not 200 code: " + url);
        return;
      }

      Document document = connection.response().parse();
      StringTokenizer st = new StringTokenizer(document.title());

      while (st.hasMoreTokens()) {
        String cur = st.nextToken().toLowerCase();
        index.computeIfAbsent(cur, list -> new HashSet<>()).add(url);
      }
      st = new StringTokenizer(document.body().text());
      while (st.hasMoreTokens()) {
        String cur = st.nextToken().toLowerCase();
        index.computeIfAbsent(cur, list -> new HashSet<>()).add(url);
      }

    } catch (IOException e) {
      System.err.println("Error(document): " + url);
      e.printStackTrace();
    }
  }

  public void appendRobotsUrl(String host) throws IOException {
    System.out.println("attempting to cache robots.txt: " + host + "/robots.txt");
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
      int lineCtr = 0;
      while (currentLine != null) {
        String trimmedLine = currentLine.trim();

        if (trimmedLine.startsWith("#") || trimmedLine.isEmpty()) {
          currentLine = br.readLine();
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
        if (lineCtr % 100 == 0) {
          System.out.println(lineCtr);
        }
        currentLine = br.readLine();
        lineCtr++;
      }

      System.out.println("caching robots success: " + robotsUrl);
      robotsCache.put(host, robotsSet);
    }
  }

  public void appendUrl(String url) {
    System.out.println("attempting to add url to collection: " + url);
    if (!urlsCache.contains(url)) {
      urlsCache.add(url);
      urls.add(url);
    }
  }

  public String getUrl() {
    if (urls.isEmpty()) {
      return null;
    }
    return urls.remove(0);
  }

  public boolean isValidUrl(String url) {
    boolean validity = true;
    try {
      URI uri = new URI(url);
      String host = uri.getScheme() + "://" + uri.getHost();
      if (uri.getScheme() == null) {
        return false;
      }
      if (robotsCache.containsKey(host)) {
        Set<String> disallowedPaths = robotsCache.get(host);
        String path = uri.getRawPath();
        for (String disallowed : disallowedPaths) {
          validity = !path.startsWith(disallowed);
          if (!validity) {
            break;
          }
          int firstAsterisk = disallowed.indexOf("*");
          if (firstAsterisk != -1) {
            boolean startsWithDisallowed = path.startsWith(disallowed.substring(0, firstAsterisk));
            boolean endsWithDisallowed = path.endsWith(disallowed.substring(firstAsterisk + 1));
            if (endsWithDisallowed && startsWithDisallowed) {
              validity = false;
              break;
            }
          }
        }
      }

    } catch (Exception e) {
      System.err.println("Unable to validateUrl: " + url);
      e.printStackTrace();
    }
    return validity;
  }

  public static Set<String> getUrlsFromIndex(String word) {
    Set<String> indexedList = index.get(word.toLowerCase());
    return indexedList != null ? indexedList : new HashSet<>();
  }
}
