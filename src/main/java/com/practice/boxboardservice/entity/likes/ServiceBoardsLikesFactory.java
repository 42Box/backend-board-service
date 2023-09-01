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
public class ServiceBoardsLikesFactory implements LikesEntityFactory<ServiceBoardsLikesEntity> {

  public ServiceBoardsLikesEntity create(LikesAndDislikesDto dto) {
    return ServiceBoardsLikesEntity.builder()
        .boardId(dto.getBoardId())
        .userUuid(dto.getUserUuid())
        .build();
  }
}
