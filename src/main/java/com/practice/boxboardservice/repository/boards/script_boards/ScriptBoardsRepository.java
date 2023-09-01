package com.practice.boxboardservice.repository.boards.script_boards;

import com.practice.boxboardservice.entity.boards.ScriptBoardsEntity;
import com.practice.boxboardservice.repository.boards.BoardsRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * ScriptBoardsRepository.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Repository
public interface ScriptBoardsRepository extends BoardsRepository<ScriptBoardsEntity>,
    ScriptBoardsRepositoryCustom, QuerydslPredicateExecutor<ScriptBoardsEntity> {

}
