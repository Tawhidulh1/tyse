package com.Tawhidul.Tyse.model;

import org.jsoup.nodes.Document;

public class WebPage {
  private String Url;
  private String HtmlPage;

  public WebPage(String url) {
    this.Url = url;
    HtmlPage = null;
  }

  public WebPage(String Url, String HtmlPage) {
    this.Url = Url;
    this.HtmlPage = HtmlPage;
  }

  public String getUrl() {
    return Url;
  }

  public String getHtmlPage() {
    return HtmlPage;
  }

  public void setUrl(String Url) {
    this.Url = Url;
  }

  public void setHtmlPage(String HtmlPage) {
    this.HtmlPage = HtmlPage;
  }
}
