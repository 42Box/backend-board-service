package com.practice.boxboardservice.controller.likes.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RequestLikesAndDislikesDto.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestLikesAndDislikesDto {

  @NotNull
  private Long boardId;
  @NotNull
  private Boolean boardLiked;
}
