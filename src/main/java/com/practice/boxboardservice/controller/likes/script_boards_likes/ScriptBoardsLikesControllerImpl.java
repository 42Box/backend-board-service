package com.practice.boxboardservice.controller.likes.script_boards_likes;

import com.practice.boxboardservice.controller.likes.LikesController;
import com.practice.boxboardservice.controller.likes.dto.RequestLikesAndDislikesDto;
import com.practice.boxboardservice.controller.likes.dto.ResponseLikesAndDislikesDto;
import com.practice.boxboardservice.global.aop.validate_nickname_header.HeaderAuthCheck;
import com.practice.boxboardservice.service.likes.LikesService;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;
import com.practice.boxboardservice.service.likes.script_boards_likes.ScriptBoardsLikesService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ScriptBoardsLikesControllerImpl.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@RestController
@RequestMapping("/script-boards/likes")
public class ScriptBoardsLikesControllerImpl implements LikesController {

  private final LikesService scriptBoardsLikesService;
  private final ModelMapper modelMapper;

  @Autowired
  public ScriptBoardsLikesControllerImpl(ScriptBoardsLikesService scriptBoardsLikesService,
      ModelMapper modelMapper) {
    this.scriptBoardsLikesService = scriptBoardsLikesService;
    this.modelMapper = modelMapper;
  }

  @Override
  @HeaderAuthCheck
  @PostMapping("")
  public ResponseEntity<ResponseLikesAndDislikesDto> likesAndDislikes(HttpServletRequest request,
      @RequestBody @Valid RequestLikesAndDislikesDto requestDto) {
    String userUuid = request.getHeader("uuid");
    LikesAndDislikesDto dto = modelMapper.map(requestDto, LikesAndDislikesDto.class);
    dto.setUserUuid(userUuid);
    LikesAndDislikesResultDto resultDto = scriptBoardsLikesService.likesAndDislikes(dto);
    ResponseLikesAndDislikesDto responseDto = modelMapper.map(resultDto,
        ResponseLikesAndDislikesDto.class);
    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }
}
