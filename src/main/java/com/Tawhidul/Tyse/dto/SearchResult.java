package com.Tawhidul.Tyse.dto;

import java.util.Set;

public class SearchResult {

  private Set<String> urls;

  public SearchResult(Set<String> urls) {
    this.urls = urls;
  }

  public Set<String> getUrls() {
    return urls;
  }
}
