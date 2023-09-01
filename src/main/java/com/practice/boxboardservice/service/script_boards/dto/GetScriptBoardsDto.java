package com.practice.boxboardservice.service.script_boards.dto;

import java.time.LocalDateTime;
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
public class GetScriptBoardsDto {

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
  private LocalDateTime regDate;
  private LocalDateTime modDate;
  private int viewCount;
  private int likeCount;
  private int dislikeCount;
  private int commentCount;
  private boolean boardLiked;
  private boolean boardDisliked;
  private boolean scriptSaved;
  private long savedId;
}
