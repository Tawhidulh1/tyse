package com.Tawhidul.Tyse.model;

// Might not use this
public class WebPage {
  private String url;
  private String htmlPage;

  public WebPage(String url) {
    this.url = url;
    htmlPage = null;
  }

  public WebPage(String url, String htmlPage) {
    this.url = url;
    this.htmlPage = htmlPage;
  }

  public String getUrl() {
    return url;
  }

  public String getHtmlPage() {
    return htmlPage;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setHtmlPage(String htmlPage) {
    this.htmlPage = htmlPage;
  }
}
