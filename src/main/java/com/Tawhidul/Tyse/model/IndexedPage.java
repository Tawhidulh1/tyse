package com.Tawhidul.Tyse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "pages")
public class IndexedPage {

	@Id
	private String url;
	private String title;
	private String body;

	public IndexedPage(String url, String title, String body) {
		this.url = url;
		this.title = title;
		this.body = body;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
