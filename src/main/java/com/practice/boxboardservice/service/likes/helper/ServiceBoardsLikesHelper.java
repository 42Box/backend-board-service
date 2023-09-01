package com.practice.boxboardservice.service.likes.helper;

import com.practice.boxboardservice.entity.boards.ServiceBoardsEntity;
import com.practice.boxboardservice.entity.likes.ServiceBoardsLikesEntity;
import org.springframework.stereotype.Component;

/**
 * ScriptBoardsLikesHelper.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
@Component
public class ServiceBoardsLikesHelper implements
    LikesHelper<ServiceBoardsLikesEntity, ServiceBoardsEntity> {

  @Override
  public void increaseFromBoard(ServiceBoardsEntity entity) {
    entity.increaseLikes();
  }

  @Override
  public void decreaseFromBoard(ServiceBoardsEntity entity) {
    entity.decreaseLikes();
  }
}
