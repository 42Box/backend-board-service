package com.practice.boxboardservice.service.boards.script_boards.dto;

import com.practice.boxboardservice.service.dto.PostBoardsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PostBoardsDto.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateScriptBoardsDto extends PostBoardsDto {

  protected Long BoardId;
  protected String scriptName;
}
