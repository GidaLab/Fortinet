package com.gidalab.fortinet.cache;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheProvider {

  @Bean
  public LRUCache scoreCache(@Value("${scoreCache.expiration:1}") int expiration,
      @Value("${scoreCache.timeUnit:HOURS}") TimeUnit timeUnit,
      @Value("${scoreCache.size:100}") int size) {
    return new LRUCache(expiration, timeUnit, size);
  }
}
