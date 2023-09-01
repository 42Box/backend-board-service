package com.practice.boxboardservice.repository.boards.service_boards;

import com.practice.boxboardservice.repository.boards.service_boards.dto.ServiceBoardsPageConditionDto;
import com.practice.boxboardservice.repository.boards.service_boards.dto.ServiceBoardsPageResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ScriptBoardsRepositoryCustom.
 *
 * @author : middlefitting
 * @since : 2023/08/30
 */
public interface ServiceBoardsRepositoryCustom {

  Page<ServiceBoardsPageResultDto> findBoardsPage(Pageable pageable,
      ServiceBoardsPageConditionDto condition);
}
