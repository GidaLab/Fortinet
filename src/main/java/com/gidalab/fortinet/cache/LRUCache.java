package com.gidalab.fortinet.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

public class LRUCache {

  private final Cache<String, Double> cache;

  public LRUCache(int expiration, TimeUnit timeUnit, int size) {
    cache = CacheBuilder.newBuilder()
        .expireAfterWrite(expiration, timeUnit)
        .maximumSize(size)
        .concurrencyLevel(Runtime.getRuntime().availableProcessors())
        .build();
  }

  public Double get(String fileHash) {
    return cache.getIfPresent(fileHash);
  }

  public void add(String fileHash, double value) {
    if (fileHash != null) {
      cache.put(fileHash, value);
    }
  }

}
