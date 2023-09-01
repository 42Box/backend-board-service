package com.practice.boxboardservice.service.likes.helper;

import com.practice.boxboardservice.entity.boards.ScriptBoardsEntity;
import com.practice.boxboardservice.entity.likes.ScriptBoardsDislikesEntity;
import org.springframework.stereotype.Component;

/**
 * ScriptsBoardsDislikesHelper.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
@Component
public class ScriptBoardsDislikesHelper implements
    LikesHelper<ScriptBoardsDislikesEntity, ScriptBoardsEntity> {

  @Override
  public void increaseFromBoard(ScriptBoardsEntity entity) {
    entity.increaseDislikes();
  }

  @Override
  public void decreaseFromBoard(ScriptBoardsEntity entity) {
    entity.decreaseDislikes();
  }
}
