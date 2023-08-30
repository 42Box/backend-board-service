package com.practice.boxboardservice.service.dto;

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
public class DeleteBoardsDto {

  protected Long BoardId;
  protected String writerUuid;
}
