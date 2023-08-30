package com.practice.boxboardservice.service.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.global.exception.DefaultServiceException;
import com.practice.boxboardservice.service.aws.s3.dto.S3UploadDto;
import com.practice.boxboardservice.service.aws.s3.dto.S3UploadResultDto;
import java.io.InputStream;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * S3Service.
 *
 * @author : middlefitting
 * @since : 2023/08/28
 */
@Service
@AllArgsConstructor
public class S3Service {

  private final AmazonS3 s3client;
  private final EnvUtil envUtil;

  public S3UploadResultDto uploadFile(S3UploadDto dto) {
    try {
      String fileName = UUID.randomUUID() + "." + dto.getS3File().getFormat();
      String bucketName = envUtil.getStringEnv("bucket.name");
      String s3FilePath = dto.getS3File().getFolder() + fileName;
      InputStream is = dto.getFile().getInputStream();
      ObjectMetadata metadata = getMetadata(dto);
      s3client.putObject(
          new PutObjectRequest(bucketName, s3FilePath, is, metadata));
      return new S3UploadResultDto(s3FilePath, s3client.getUrl(bucketName, s3FilePath).toString());
    } catch (Exception e) {
      throw new DefaultServiceException(dto.getS3File().getErrMsg(), envUtil);
    }
  }

  public void deleteFile(String filePath) {
    try {
      String bucketName = envUtil.getStringEnv("bucket.name");
      s3client.deleteObject(new DeleteObjectRequest(bucketName, filePath));
    } catch (Exception e) {
      throw new DefaultServiceException("boards.error.s3-script-delete-fail", envUtil);
    }
  }

  private ObjectMetadata getMetadata(S3UploadDto dto) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(dto.getS3File().getContentType());
    metadata.setContentLength(dto.getFile().getSize());
    return metadata;
  }
}
