package com.Tawhidul.Tyse.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;

import com.Tawhidul.Tyse.util.Url;

public class Spider {

  Cleaner cleaner;
  Safelist basicSafelist;

  // incomplete
  public Spider(String seedUrl) {
    Connection root = Jsoup.connect(seedUrl);
    basicSafelist = Safelist.basic();
    cleaner = new Cleaner(basicSafelist);

  }

  // incomplete
  public void run(Url url, int runCtr) {
    if (runCtr == 0) {
      return;
    }

    Url nextUrl = UrlQueue.getUrlAndRemove();
    run(nextUrl, runCtr - 1);
  }

  // Note: improve exception handling when possible

  // incomplete
  public Document getDocument(Connection connection) throws IOException {
    Document document = connection.get();
    document = cleaner.clean(document);
    return document;
  }

  // incomplete
  public List<Url> getUrls(Document document) {
    return new ArrayList<>();
  }

}
