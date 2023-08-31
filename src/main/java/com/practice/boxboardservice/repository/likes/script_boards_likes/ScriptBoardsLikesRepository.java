package com.practice.boxboardservice.repository.likes.script_boards_likes;

import com.practice.boxboardservice.entity.script_boards.ScriptBoardsLikesEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ScriptBoardsLikesRepository.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
public interface ScriptBoardsLikesRepository extends JpaRepository<ScriptBoardsLikesEntity, Long> {

  Optional<ScriptBoardsLikesEntity> getByBoardIdAndUserUuid(Long boardId, String userUuid);
}
