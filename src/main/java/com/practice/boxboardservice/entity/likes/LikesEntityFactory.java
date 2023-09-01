package com.practice.boxboardservice.entity.likes;

import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;

/**
 * LikesEntityFactory.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
public interface LikesEntityFactory<T> {

  T create(LikesAndDislikesDto dto);
}
