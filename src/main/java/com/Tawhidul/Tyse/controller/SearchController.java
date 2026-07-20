package com.Tawhidul.Tyse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
  @GetMapping("/")
  public String getSearch() {
    return "search";
  }
}
