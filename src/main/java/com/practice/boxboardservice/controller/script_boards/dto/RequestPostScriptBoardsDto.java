package com.practice.boxboardservice.controller.script_boards.dto;

import com.practice.boxboardservice.controller.dto.RequestPostBoardsDto;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * RequestPostScriptBoardsDto.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Getter
@Setter
@NoArgsConstructor
public class RequestPostScriptBoardsDto extends RequestPostBoardsDto {

  @NotEmpty
  @Length(min = 1, max = 50)
  private String scriptName;

  public RequestPostScriptBoardsDto(String title, String content, String scriptName) {
    super(title, content);
    this.scriptName = scriptName;
  }
}
