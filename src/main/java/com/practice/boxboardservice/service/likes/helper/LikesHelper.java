package com.practice.boxboardservice.service.likes.helper;

import com.practice.boxboardservice.entity.boards.BoardsEntity;
import com.practice.boxboardservice.entity.likes.LikesEntity;

/**
 * LikesHelper.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
public interface LikesHelper<T extends LikesEntity, S extends BoardsEntity> {

  void increaseFromBoard(S entity);

  void decreaseFromBoard(S entity);
}
