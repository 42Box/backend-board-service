package com.practice.boxboardservice.controller.boards.service_boards.dto;

import com.practice.boxboardservice.controller.boards.dto.RequestPostBoardsDto;
import javax.validation.constraints.NotEmpty;
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
public class RequestUpdateServiceBoardsDto extends RequestPostBoardsDto {

  @NotEmpty
  @Length(min = 1, max = 255)
  private String serviceUrl;

  public RequestUpdateServiceBoardsDto(String title, String content, String serviceUrl) {
    super(title, content);
    this.serviceUrl = serviceUrl;

  }
}
