package com.practice.boxboardservice.repository.boards.service_boards.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * ScriptBoardPageResultDto.
 *
 * @author : middlefitting
 * @since : 2023/08/30
 */
@Getter
public class ServiceBoardsPageResultDto {

  private final Long boardId;
  private final String title;
  private final String content;
  private final String serviceName;
  private final String writerUuid;
  private final String writerName;
  private final String writerProfileImagePath;
  private final int viewCount;
  private final int likeCount;
  private final int dislikeCount;
  private final int commentCount;
  private final LocalDateTime regDate;
  private final LocalDateTime modDate;

  @QueryProjection
  public ServiceBoardsPageResultDto(Long boardId, String title, String content, String serviceName,
      String writerUuid, String writerName, String writerProfileImagePath, int viewCount,
      int likeCount,
      int dislikeCount, int commentCount, LocalDateTime regDate,
      LocalDateTime modDate) {
    this.boardId = boardId;
    this.title = title;
    this.content = content;
    this.serviceName = serviceName;
    this.writerUuid = writerUuid;
    this.writerName = writerName;
    this.writerProfileImagePath = writerProfileImagePath;
    this.viewCount = viewCount;
    this.likeCount = likeCount;
    this.dislikeCount = dislikeCount;
    this.commentCount = commentCount;
    this.regDate = regDate;
    this.modDate = modDate;
  }
}
