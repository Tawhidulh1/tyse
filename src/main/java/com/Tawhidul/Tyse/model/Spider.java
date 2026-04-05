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

  private Cleaner cleaner;
  private Safelist basicSafelist;

  private Connection currentConnection;
  private Document currentDocument;

  // incomplete
  public Spider(String seedUrl) {
    Connection root = Jsoup.connect(seedUrl);
    currentDocument = new Document(seedUrl);
    currentConnection = root;
    basicSafelist = Safelist.basic();
    cleaner = new Cleaner(basicSafelist);
  }

  public void run(Url url) throws IOException {
    run(url, 0);
  }

  // incomplete
  public void run(Url url, int runCtr) throws IOException {
    String urlName = url.getName();
    currentConnection = Jsoup.connect(urlName);
    currentDocument = getDocument();
    System.out.println(currentDocument.body());

    if (runCtr == 0) {
      return;
    }

    Url nextUrl = UrlQueue.getUrlAndRemove();
    run(nextUrl, runCtr - 1);
  }

  // Note: improve exception handling when possible

  // incomplete
  public Document getDocument() throws IOException {
    Document document = currentConnection.get();
    document = cleaner.clean(document);
    return document;
  }

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
