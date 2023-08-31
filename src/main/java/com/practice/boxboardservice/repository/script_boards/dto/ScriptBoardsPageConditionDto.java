package com.practice.boxboardservice.repository.script_boards.dto;

import com.practice.boxboardservice.repository.script_boards.type.ScriptSearchCondition;
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
public class ScriptBoardsPageConditionDto {
  private String search;
  private String writerUuid;
  private ScriptSearchCondition searchCondition;
}
