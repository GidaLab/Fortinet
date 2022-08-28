package com.gidalab.fortinet.reputation.bamboo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BambooResponseDto {

  private double score;

}
