package com.Tawhidul.Tyse;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import com.Tawhidul.Tyse.model.Spider;
import com.Tawhidul.Tyse.service.SearchService;

// remove exclusions later
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class TyseApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(TyseApplication.class, args);
		SearchService searchService = context.getBean(SearchService.class);
		// Spider examplecomSpider = new Spider("https://example.com", searchService);
		// Spider wikipediaSpider = new Spider("https://en.wikipedia.org",
		// searchService);

	}
}
