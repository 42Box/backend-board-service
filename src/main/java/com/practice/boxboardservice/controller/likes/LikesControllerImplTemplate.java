package com.practice.boxboardservice.controller.likes;

import com.practice.boxboardservice.controller.likes.dto.RequestLikesAndDislikesDto;
import com.practice.boxboardservice.controller.likes.dto.ResponseLikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.LikesService;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * LikesControllerImpl.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
public class LikesControllerImplTemplate implements LikesController {

  private final LikesService scriptBoardsLikesService;
  private final ModelMapper modelMapper;

  public LikesControllerImplTemplate(LikesService scriptBoardsLikesService,
      ModelMapper modelMapper) {
    this.scriptBoardsLikesService = scriptBoardsLikesService;
    this.modelMapper = modelMapper;
  }

  public ResponseEntity<ResponseLikesAndDislikesDto> likesAndDislikes(HttpServletRequest request,
      RequestLikesAndDislikesDto requestDto) {
    String userUuid = request.getHeader("uuid");
    LikesAndDislikesDto dto = modelMapper.map(requestDto, LikesAndDislikesDto.class);
    dto.setUserUuid(userUuid);
    LikesAndDislikesResultDto resultDto = scriptBoardsLikesService.likesAndDislikes(dto);
    ResponseLikesAndDislikesDto responseDto = modelMapper.map(resultDto,
        ResponseLikesAndDislikesDto.class);
    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }
}
