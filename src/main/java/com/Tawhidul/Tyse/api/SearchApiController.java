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
import com.Tawhidul.Tyse.model.IndexedPage;
import com.Tawhidul.Tyse.model.Spider;
import com.Tawhidul.Tyse.service.SearchService;

@RestController
public class SearchApiController {

	private SearchService searchService;

	public SearchApiController(SearchService searchService) {
		this.searchService = searchService;
	}

	@GetMapping("/search")
	public ResponseEntity<SearchResult> getSearch(@RequestParam String q) {
		StringTokenizer st = new StringTokenizer(q);
		List<IndexedPage> results = new ArrayList<>();
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			List<IndexedPage> page = searchService.search(word);
			results.addAll(page);
		}
		SearchResult searchResults = new SearchResult(results);
		return ResponseEntity.ok(searchResults);
	}
}
