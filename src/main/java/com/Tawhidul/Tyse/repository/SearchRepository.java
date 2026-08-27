package com.Tawhidul.Tyse.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.Tawhidul.Tyse.model.IndexedPage;

@ConditionalOnProperty(name = "elasticsearch.enabled", havingValue = "true")
public interface SearchRepository extends ElasticsearchRepository<IndexedPage, String> {
	List<IndexedPage> findByTitleContainingOrBodyContaining(String title, String body);
}
