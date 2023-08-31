package com.practice.boxboardservice.client.UserClient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResponseIsMyScriptDto.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseIsMyScriptDto {

  private long savedId;
  private boolean scriptSaved;
}
