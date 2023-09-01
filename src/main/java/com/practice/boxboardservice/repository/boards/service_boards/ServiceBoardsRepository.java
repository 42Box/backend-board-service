package com.practice.boxboardservice.repository.boards.service_boards;

import com.practice.boxboardservice.entity.boards.ServiceBoardsEntity;
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
public interface ServiceBoardsRepository extends BoardsRepository<ServiceBoardsEntity>,
    ServiceBoardsRepositoryCustom, QuerydslPredicateExecutor<ServiceBoardsEntity> {

}
