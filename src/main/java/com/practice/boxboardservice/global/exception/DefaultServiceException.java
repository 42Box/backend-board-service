package com.practice.boxboardservice.global.exception;

import com.practice.boxboardservice.global.env.EnvUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ServiceIllegalArgumentException.
 *
 * @author : middlefitting
 * @description:
 * @since : 2023/08/24
 */
@Getter
public class DefaultServiceException extends RuntimeException implements ServiceException {

  private final String msg;
  private final int code;
  private final HttpStatus status;

  public DefaultServiceException(String msg, int code, HttpStatus status) {
    super();
    this.msg = msg;
    this.code = code;
    this.status = status;
  }

  public DefaultServiceException(String envKey, EnvUtil envUtil) {
    super();
    this.msg = envUtil.getStringEnv(envKey + ".msg");
    this.code = envUtil.getIntegerEnv(envKey + ".code");
    this.status = envUtil.getHttpStatusEnv(envKey + ".status");
  }
}
