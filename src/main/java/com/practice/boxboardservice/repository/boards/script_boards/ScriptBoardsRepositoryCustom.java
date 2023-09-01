package com.practice.boxboardservice.repository.boards.script_boards;

import com.practice.boxboardservice.repository.boards.script_boards.dto.ScriptBoardsPageConditionDto;
import com.practice.boxboardservice.repository.boards.script_boards.dto.ScriptBoardsPageResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ScriptBoardsRepositoryCustom.
 *
 * @author : middlefitting
 * @since : 2023/08/30
 */
public interface ScriptBoardsRepositoryCustom {

  Page<ScriptBoardsPageResultDto> findScriptBoardsPage(Pageable pageable,
      ScriptBoardsPageConditionDto condition);
}
