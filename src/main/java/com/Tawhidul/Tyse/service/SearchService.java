package com.Tawhidul.Tyse.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.Tawhidul.Tyse.constants.ApplicationConstants;
import com.Tawhidul.Tyse.model.IndexedPage;
import com.Tawhidul.Tyse.repository.SearchRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchService {

	@Autowired(required = false)
	private SearchRepository searchRepository;

	@Value("${searxng.host.uri:http://localhost:8080}")
	private String searxngHostUri;

	public List<IndexedPage> search(String query) {
		if (query == null)
			return null;
		List<IndexedPage> pages = new ArrayList<>();
		if (ApplicationConstants.elasticsearchEnabled)
			pages = searchRepository.findByTitleContainingOrBodyContaining(query, query);

		if (pages.isEmpty()) {
			try {
				pages = new ArrayList<>();

				HttpClient client = HttpClient.newHttpClient();

				// URI requestUri = new URI("http://localhost:8888/search?q=" + query +
				// "&format=json");
				URI requestUri = UriComponentsBuilder
						.fromUriString(searxngHostUri + "/search?q={q}&format=json")
						.build(query);
				HttpRequest request = HttpRequest.newBuilder()
						.uri(requestUri)
						.GET()
						.build();
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

				ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.readTree(response.body());
				JsonNode results = root.get("results");
				for (JsonNode result : results) {
					JsonNode url = result.get("url");
					JsonNode title = result.get("title");
					JsonNode content = result.get("content");

					IndexedPage page = new IndexedPage(url.asText(), title.asText(), content.asText());
					pages.add(page);
				}
				if (ApplicationConstants.elasticsearchEnabled)
					searchRepository.saveAll(pages);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return pages;
	}

	public void put(IndexedPage page) {
		searchRepository.save(page);
	}
}
