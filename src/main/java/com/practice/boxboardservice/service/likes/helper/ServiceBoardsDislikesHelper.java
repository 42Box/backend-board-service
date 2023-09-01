package com.practice.boxboardservice.service.likes.helper;

import com.practice.boxboardservice.entity.boards.ServiceBoardsEntity;
import com.practice.boxboardservice.entity.likes.ServiceBoardsDislikesEntity;
import org.springframework.stereotype.Component;

/**
 * ScriptsBoardsDislikesHelper.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
@Component
public class ServiceBoardsDislikesHelper implements
    LikesHelper<ServiceBoardsDislikesEntity, ServiceBoardsEntity> {

  @Override
  public void increaseFromBoard(ServiceBoardsEntity entity) {
    entity.increaseDislikes();
  }

  @Override
  public void decreaseFromBoard(ServiceBoardsEntity entity) {
    entity.decreaseDislikes();
  }
}
