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
public class ScriptBoardsLikesFactory implements LikesEntityFactory<ScriptBoardsLikesEntity> {

  public ScriptBoardsLikesEntity create(LikesAndDislikesDto dto) {
    return ScriptBoardsLikesEntity.builder()
        .boardId(dto.getBoardId())
        .userUuid(dto.getUserUuid())
        .build();
  }
}
