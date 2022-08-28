package com.gidalab.fortinet.reputation.avocado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvocadoResponseDto {

  private Double score;

}
