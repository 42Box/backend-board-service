package com.practice.boxboardservice.entity.boards;

/**
 * BoardsEntity.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
public interface BoardsEntity {

  void decreaseLikes();

  void increaseLikes();

  void decreaseDislikes();

  void increaseDislikes();
}
