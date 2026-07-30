package com.Tawhidul.Tyse.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.Tawhidul.Tyse.model.IndexedPage;

public interface SearchRepository extends ElasticsearchRepository<IndexedPage, String> {
	List<IndexedPage> findByTitleContainingOrBodyContaining(String title, String body);
}
