package com.practice.boxboardservice.client.UserClient.dto;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RequestIsMyScriptDto.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestIsMyScriptDto {

  Optional<String> userUuid;
  private String scriptPath;
}
