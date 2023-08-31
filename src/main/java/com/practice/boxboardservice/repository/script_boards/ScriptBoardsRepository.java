package com.practice.boxboardservice.repository.script_boards;

import com.practice.boxboardservice.entity.script_boards.ScriptBoardsEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * ScriptBoardsRepository.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
public interface ScriptBoardsRepository extends JpaRepository<ScriptBoardsEntity, Long>,
    ScriptBoardsRepositoryCustom,
    QuerydslPredicateExecutor<ScriptBoardsEntity> {

  Optional<ScriptBoardsEntity> findByIdAndDeleted(Long boardId, boolean deleted);

}
