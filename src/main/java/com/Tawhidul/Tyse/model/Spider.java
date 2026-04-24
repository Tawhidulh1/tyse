package com.Tawhidul.Tyse.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;

public class Spider {

  public Spider(String seedUrl) {
    System.out.println("New Spider Class Created");
    System.out.println("New Spider Class Created");
    System.out.println("New Spider Class Created");
  }

  private Document fetch(String url) {
    if (!(url.toLowerCase().substring(0, 7).equals("http://")
        || url.toLowerCase().subSequence(0, 8).equals("https://"))) {
      return null;
    }
    try {
      Connection urlConnection = Jsoup.connect(url).ignoreContentType(true);
      Document urlDocument = urlConnection.get();
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
}
