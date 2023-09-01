package com.practice.boxboardservice.service.boards.service_boards.dto;

import com.practice.boxboardservice.service.dto.PostBoardsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * PostImageBoardsDto.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Getter
@Setter
@NoArgsConstructor
public class PostServiceBoardsDto extends PostBoardsDto {

  private String serviceUrl;
  private String imagePath;
  private String writerUuid;
  private String writerNickname;
  private String writerProfileImagePath;
  private MultipartFile ImageFile;

  public PostServiceBoardsDto(String title, String content, String serviceUrl) {
    super(title, content);
    this.serviceUrl = serviceUrl;
  }
}
