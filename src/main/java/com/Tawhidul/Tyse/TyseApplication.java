package com.Tawhidul.Tyse;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

import com.Tawhidul.Tyse.model.Spider;
import com.Tawhidul.Tyse.util.Url;

// remove exclusions later
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class TyseApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(TyseApplication.class, args);
    Spider spider = new Spider("https://www.scrapethissite.com/pages/simple/");
    Url testUrl = new Url(1.1, "https://www.scrapethissite.com/pages/simple/");
    spider.run(testUrl);
  }
}
