package com.practice.boxboardservice.service.aws.s3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * S3UploadResultDto.
 *
 * @author : middlefitting
 * @since : 2023/08/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class S3UploadResultDto {

  private String path;
  private String url;
}
