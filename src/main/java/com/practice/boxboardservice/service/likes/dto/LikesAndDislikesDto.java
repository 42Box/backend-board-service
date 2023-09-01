package com.practice.boxboardservice.service.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LikesAndDislikesDto.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesAndDislikesDto {

  private Long boardId;
  private String userUuid;
  private Boolean likeDislikeStatus;
}
