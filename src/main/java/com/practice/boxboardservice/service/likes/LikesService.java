package com.practice.boxboardservice.service.likes;

import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;

/**
 * LikesService.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
public interface LikesService {

  LikesAndDislikesResultDto likesAndDislikes(LikesAndDislikesDto dto);
}
