package com.practice.boxboardservice.controller.boards.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * RequestPostBoardsDto.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostBoardsDto {

  @NotEmpty
  @Length(min = 10, max = 40)
  protected String title;
  @NotEmpty
  @Length(min = 10, max = 2000)
  protected String content;
}


