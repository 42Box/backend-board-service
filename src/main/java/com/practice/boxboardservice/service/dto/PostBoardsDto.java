package com.practice.boxboardservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PostBoardsDto.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Data
@NoArgsConstructor
public class PostBoardsDto {

  protected String title;
  protected String content;
  protected String writerUuid;

  public PostBoardsDto(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
