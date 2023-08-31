package com.practice.boxboardservice.controller.likes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResponsePostLike.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseLikesAndDislikesDto {

  private boolean boardLiked;
}
