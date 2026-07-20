package com.Tawhidul.Tyse.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Tawhidul.Tyse.dto.SearchResult;
import com.Tawhidul.Tyse.model.Spider;

@RestController
public class SearchApiController {
  @GetMapping("/search")
  public ResponseEntity<SearchResult> getSearch(@RequestParam String q) {
    StringTokenizer st = new StringTokenizer(q);
    Set<String> results = new HashSet<>();
    while (st.hasMoreTokens()) {
      String word = st.nextToken();
      Set<String> indexedList = Spider.getUrlsFromIndex(word);
      results.addAll(indexedList);
    }
    SearchResult searchResults = new SearchResult(results);
    return ResponseEntity.ok(searchResults);
  }
}
