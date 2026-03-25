package com.Tawhidul.Tyse.model;

import java.net.URL;
import java.util.PriorityQueue;

import com.Tawhidul.Tyse.util.Pair;

public final class UrlQueue {
  private UrlQueue() {
  }

  private static PriorityQueue<Url> urlQueue;
  {
    urlQueue = new PriorityQueue<>((a, b) -> (int) (b.rating - a.rating));
  }

  public boolean add(Url url) {
    if (isSubUrl(url)) {

    } else {
      urlQueue.add(url);
    }
    return true;
  }

  public boolean isSubUrl(Url url) {
    return false;
  }

  private class Url {
    double rating;
    String name;
    PriorityQueue<Pair> pairQueue;

    // Left = always rating RATING | NAME
    Url(double rating, String name) {
      this.rating = rating;
      this.name = name;
      pairQueue = new PriorityQueue<>((a, b) -> (int) (a.getLeft()) - (int) (b.getLeft()));
    }

    void add(double rating, String name) {
      Pair pair = new Pair<Double, String>(rating, name);
      pairQueue.add(pair);
    }

    void remove(Pair pair) {
      pairQueue.remove(pair);
    }
  }
}
