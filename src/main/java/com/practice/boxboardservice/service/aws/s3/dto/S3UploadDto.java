package com.practice.boxboardservice.service.aws.s3.dto;

import com.practice.boxboardservice.service.aws.s3.type.S3File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * S3Dto.
 *
 * @author : middlefitting
 * @since : 2023/08/28
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class S3UploadDto {

  MultipartFile file;
  S3File s3File;
}
