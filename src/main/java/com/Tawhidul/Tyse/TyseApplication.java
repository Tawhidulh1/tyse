package com.Tawhidul.Tyse;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

import com.Tawhidul.Tyse.model.Spider;

// remove exclusions later
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class TyseApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(TyseApplication.class, args);
    Spider spider = new Spider("htt://google.com");
  }
}
