package com.practice.boxboardservice.entity.boards;

import com.practice.boxboardservice.service.dto.UpdateBoardsDto;

/**
 * BoardsEntity.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
public interface BoardsEntity {

  void update(UpdateBoardsDto dto);

  void delete();

  void decreaseLikes();

  void increaseLikes();

  void decreaseDislikes();

  void increaseDislikes();
}
