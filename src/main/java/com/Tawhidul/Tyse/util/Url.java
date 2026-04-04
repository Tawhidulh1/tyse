package com.Tawhidul.Tyse.util;

import java.util.PriorityQueue;

public class Url {
  private double rating;
  private String name;
  private PriorityQueue<Pair> pairQueue;

  // Left = always rating RATING | NAME
  Url(double rating, String name) {
    this.rating = rating;
    this.name = name;
    pairQueue = new PriorityQueue<>((a, b) -> (int) (a.getLeft()) - (int) (b.getLeft()));
  }

  public double getRating() {
    return rating;
  }

  public String getName() {
    return name;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public void setName(String name) {
    this.name = name;
  }

  void add(double rating, String name) {
    Pair pair = new Pair<Double, String>(rating, name);
    pairQueue.add(pair);
  }

  void remove(Pair pair) {
    pairQueue.remove(pair);
  }
}
