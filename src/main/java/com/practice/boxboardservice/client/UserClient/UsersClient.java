package com.practice.boxboardservice.client.UserClient;

/**
 * UserClient.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */

import com.practice.boxboardservice.client.UserClient.dto.RequestIsMyScriptDto;
import com.practice.boxboardservice.client.UserClient.dto.ResponseIsMyScriptDto;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
@Slf4j
public class UsersClient {

  private final DiscoveryClient discoveryClient;

  public ResponseIsMyScriptDto isMyScript(RequestIsMyScriptDto reqDto) {
    ResponseIsMyScriptDto resDto = new ResponseIsMyScriptDto();
    if (reqDto.getUserUuid().isEmpty()) {
      resDto.setSavedId(0);
      resDto.setScriptSaved(false);
      return resDto;
    }
    try {
      RestTemplate restTemplate = new RestTemplate();
      String userServiceUrl = getServiceUrl("user-service");
      String endpoint = userServiceUrl + "/private/users/me/scripts/is-my-scripts";
      HttpHeaders headers = new HttpHeaders();
      headers.set("uuid", reqDto.getUserUuid().get());
      headers.set("path", reqDto.getScriptPath());
      HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(headers);
      ResponseEntity<ResponseIsMyScriptDto> response = restTemplate.exchange(
          endpoint, HttpMethod.GET, requestEntity, ResponseIsMyScriptDto.class);
      return response.getBody();
    } catch (HttpClientErrorException ignored) {
    } catch (Exception e) {
      log.error("UserClient.isMyScript error", e);
      resDto.setScriptSaved(false);
      resDto.setSavedId(0);
    }
    return resDto;
  }

  private String getServiceUrl(String serviceName) {
    List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
    if (instances != null && !instances.isEmpty()) {
      return instances.get(0).getUri().toString();
    }
    throw new RuntimeException("No service instance available for " + serviceName);
  }
}
