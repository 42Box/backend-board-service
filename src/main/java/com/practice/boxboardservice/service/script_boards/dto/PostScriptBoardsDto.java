package com.practice.boxboardservice.service.script_boards.dto;

import com.practice.boxboardservice.service.dto.PostBoardsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * PostScriptBoardsDto.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Getter
@Setter
@NoArgsConstructor
public class PostScriptBoardsDto extends PostBoardsDto {

  private String scriptName;
  private String scriptPath;
  private String scriptUrl;
  private String writerUuid;
  private String writerNickname;
  private String writerProfileImageUrl;
  private String writerProfileImagePath;
  private MultipartFile scriptFile;

  public PostScriptBoardsDto(String title, String content, String scriptName) {
    super(title, content);
    this.scriptName = scriptName;
  }
}
