package com.gidalab.fortinet.reputation.avocado;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AvocadoClient {

  private static final String BAMBOO_BASE_URL = "http://34.134.220.86:8080/query_file/{hash}";

  private final WebClient client = WebClient.create(BAMBOO_BASE_URL);

  public AvocadoResponseDto call(String fileHash) {
    final Mono<AvocadoResponseDto> bambooResponse = client.get()
        .uri(uriBuilder ->
            uriBuilder.
                build(fileHash))
        .exchangeToMono(response -> {
          if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(AvocadoResponseDto.class);
          } else {
            return response.createException().flatMap(Mono::error);
          }
        });
    return bambooResponse.block();
  }

}
