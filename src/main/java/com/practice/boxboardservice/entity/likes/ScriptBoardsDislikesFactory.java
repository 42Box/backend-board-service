package com.practice.boxboardservice.entity.likes;

import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import org.springframework.stereotype.Component;

/**
 * ScriptBoardsLikesFactory.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
@Component
public class ScriptBoardsDislikesFactory implements LikesEntityFactory<ScriptBoardsDislikesEntity> {

  public ScriptBoardsDislikesEntity create(LikesAndDislikesDto dto) {
    return ScriptBoardsDislikesEntity.builder()
        .boardId(dto.getBoardId())
        .userUuid(dto.getUserUuid())
        .build();
  }
}
