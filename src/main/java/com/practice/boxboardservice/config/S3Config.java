package com.practice.boxboardservice.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.practice.boxboardservice.global.env.EnvUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * S3Config.
 *
 * @author : middlefitting
 * @since : 2023/08/28
 */
@Configuration
@AllArgsConstructor
public class S3Config {

  private final EnvUtil envUtil;

  @Bean
  public AmazonS3 s3client() {
    String region = envUtil.getStringEnv("bucket.region");
    return AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .build();
  }
}

