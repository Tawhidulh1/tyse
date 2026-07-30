package com.Tawhidul.Tyse.dto;

import java.util.List;

import com.Tawhidul.Tyse.model.IndexedPage;

public class SearchResult {

	private List<IndexedPage> pages;

	public SearchResult(List<IndexedPage> pages) {
		this.pages = pages;
	}

	public List<IndexedPage> getPages() {
		return this.pages;
	}

}
