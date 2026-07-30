package com.Tawhidul.Tyse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tawhidul.Tyse.model.IndexedPage;
import com.Tawhidul.Tyse.repository.SearchRepository;

@Service
public class SearchService {

	private SearchRepository searchRepository;

	public SearchService(SearchRepository searchRepository) {
		this.searchRepository = searchRepository;
	}

	public List<IndexedPage> search(String word) {
		if (word == null)
			return null;
		List<IndexedPage> pages = searchRepository.findByTitleContainingOrBodyContaining(word, word);
		return pages;
	}

	public void put(IndexedPage page) {
		searchRepository.save(page);
	}
}
