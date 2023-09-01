package com.practice.boxboardservice.service.likes.helper;

import com.practice.boxboardservice.entity.boards.ScriptBoardsEntity;
import com.practice.boxboardservice.entity.likes.ScriptBoardsLikesEntity;
import org.springframework.stereotype.Component;

/**
 * ScriptBoardsLikesHelper.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
@Component
public class ScriptBoardsLikesHelper implements
    LikesHelper<ScriptBoardsLikesEntity, ScriptBoardsEntity> {

  @Override
  public void increaseFromBoard(ScriptBoardsEntity entity) {
    entity.increaseLikes();
  }

  @Override
  public void decreaseFromBoard(ScriptBoardsEntity entity) {
    entity.decreaseLikes();
  }
}
