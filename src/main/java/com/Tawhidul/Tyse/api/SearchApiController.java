package com.Tawhidul.Tyse.api;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Tawhidul.Tyse.model.Spider;

@RestController
public class SearchApiController {
  @GetMapping("/search")
  public String getSearch(@RequestParam String q) {
    StringTokenizer st = new StringTokenizer(q);
    List<String> results = new ArrayList<>();
    while (st.hasMoreTokens()) {
      String word = st.nextToken();
      List<String> indexedList = Spider.getUrlsFromIndex(word);
      results.addAll(indexedList);
    }
    return results.toString();
  }
}
