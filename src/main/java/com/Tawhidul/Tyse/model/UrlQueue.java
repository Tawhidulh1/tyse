package com.Tawhidul.Tyse.model;

import java.util.PriorityQueue;

import com.Tawhidul.Tyse.util.Url;

public final class UrlQueue {
  private UrlQueue() {
  }

  private static PriorityQueue<Url> urlQueue;
  {
    urlQueue = new PriorityQueue<>((a, b) -> (int) (b.getRating() - a.getRating()));
  }

  // incomplete
  public static boolean add(Url url) {
    if (isSubUrl(url)) {

    } else {
      urlQueue.add(url);
    }
    return true;
  }

  // incomplete
  public static boolean isSubUrl(Url url) {
    return false;
  }

  public static Url getUrlAndRemove() {
    return urlQueue.remove();
  }

}
