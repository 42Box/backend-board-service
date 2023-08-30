package com.practice.boxboardservice.controller.script_boards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResponseGetBoards.
 *
 * @author : middlefitting
 * @since : 2023/08/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGetScriptBoardsDto {

  private Long boardId;
  private String title;
  private String content;
  private String scriptName;
  private String scriptPath;
  private String scriptUrl;
  private String writerUuid;
  private String writerNickname;
  private String writerProfileImageUrl;
  private String writerProfileImagePath;
  private String createdAt;
  private String updatedAt;
  private int viewCount;
  private int likeCount;
  private short reportCount;
  private int commentCount;
}
