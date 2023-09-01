package com.practice.boxboardservice.repository.boards.service_boards.dto;

import com.practice.boxboardservice.repository.boards.service_boards.type.ServiceSearchCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * ScriptBoardsPageConditionDto.
 *
 * @author : middlefitting
 * @since : 2023/08/30
 */
@Data
@AllArgsConstructor
@Builder
public class ServiceBoardsPageConditionDto {

  private String search;
  private String writerUuid;
  private ServiceSearchCondition searchCondition;
}
