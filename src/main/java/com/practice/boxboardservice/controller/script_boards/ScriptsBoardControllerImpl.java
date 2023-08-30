package com.practice.boxboardservice.controller.script_boards;

import com.practice.boxboardservice.controller.BoardsController;
import com.practice.boxboardservice.controller.dto.RequestPostBoardsDto;
import com.practice.boxboardservice.controller.script_boards.dto.RequestPostScriptBoardsDto;
import com.practice.boxboardservice.controller.script_boards.dto.ResponseGetScriptBoardsDto;
import com.practice.boxboardservice.global.aop.validate_nickname_header.HeaderAuthCheck;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.global.exception.DefaultServiceException;
import com.practice.boxboardservice.global.utils.JsonObjectConverter;
import com.practice.boxboardservice.global.utils.ObjectValidator;
import com.practice.boxboardservice.repository.script_boards.dto.ScriptBoardsPageConditionDto;
import com.practice.boxboardservice.repository.script_boards.dto.ScriptBoardsPageResultDto;
import com.practice.boxboardservice.repository.script_boards.type.ScriptSearchCondition;
import com.practice.boxboardservice.service.aws.s3.type.S3File;
import com.practice.boxboardservice.service.dto.DeleteBoardsDto;
import com.practice.boxboardservice.service.dto.UpdateBoardsDto;
import com.practice.boxboardservice.service.script_boards.ScriptBoardsService;
import com.practice.boxboardservice.service.script_boards.dto.GetScriptBoardsDto;
import com.practice.boxboardservice.service.script_boards.dto.PostScriptBoardsDto;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * ScriptsBoardControllerImpl.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@RestController
@RequestMapping("/script-boards")
@AllArgsConstructor
public class ScriptsBoardControllerImpl implements
    BoardsController<ResponseGetScriptBoardsDto, RequestPostBoardsDto, Page<ScriptBoardsPageResultDto>, ScriptSearchCondition> {

  private final ScriptBoardsService scriptBoardsService;
  private final ModelMapper modelMapper;
  private final EnvUtil envUtil;
  private final ObjectValidator objectValidator;
  private final JsonObjectConverter jsonObjectConverter;

  @Override
  @HeaderAuthCheck
  @PostMapping("")
  public ResponseEntity<Void> postBoards(HttpServletRequest request,
      @RequestParam(value = "script-file") MultipartFile scriptFile,
      @RequestParam(value = "body") String body) {

    RequestPostScriptBoardsDto requestPostBoardsDto = jsonObjectConverter.convertToObject(body,
        RequestPostScriptBoardsDto.class, "global.error.invalid-argument-request");
    objectValidator.validateBodyObject(requestPostBoardsDto);
    scriptTypeCheck(scriptFile);
    PostScriptBoardsDto dto = modelMapper.map(requestPostBoardsDto, PostScriptBoardsDto.class);
    dto.setWriterUuid(request.getHeader("uuid"));
    dto.setWriterNickname(request.getHeader("nickname"));
    dto.setWriterProfileImageUrl(request.getHeader("profileImageUrl"));
    dto.setWriterProfileImagePath(request.getHeader("profileImagePath"));
    dto.setScriptFile(scriptFile);
    scriptBoardsService.postBoards(dto);

    return ResponseEntity.status(201).build();
  }

  @Override
  @GetMapping("/{boardId}")
  public ResponseEntity<ResponseGetScriptBoardsDto> getBoards(@PathVariable Long boardId) {
    GetScriptBoardsDto dto = scriptBoardsService.findBoardsByBoardId(boardId);
    ResponseGetScriptBoardsDto responseGetScriptBoardsDto = modelMapper.map(dto,
        ResponseGetScriptBoardsDto.class);
    return ResponseEntity.status(HttpStatus.OK).body(responseGetScriptBoardsDto);
  }

  @Override
  @HeaderAuthCheck
  @PutMapping("/{boardId}")
  public ResponseEntity<Void> updateBoards(HttpServletRequest request, @PathVariable Long boardId,
      @RequestBody @Valid RequestPostBoardsDto requestDto) {
    UpdateBoardsDto dto = modelMapper.map(requestDto, UpdateBoardsDto.class);
    dto.setWriterUuid(request.getHeader("uuid"));
    dto.setBoardId(boardId);
    scriptBoardsService.updateBoards(dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Override
  @HeaderAuthCheck
  @DeleteMapping("/{boardId}")
  public ResponseEntity<Void> deleteBoards(HttpServletRequest request, @PathVariable Long boardId) {
    DeleteBoardsDto dto = new DeleteBoardsDto(boardId, request.getHeader("uuid"));
    scriptBoardsService.deleteBoards(dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Override
  @GetMapping("")
  public ResponseEntity<Page<ScriptBoardsPageResultDto>> getBoards(
      @PageableDefault(size = 15, sort = "regDate", direction = Direction.DESC) Pageable pageable,
      @RequestParam(required = false, defaultValue = "0") Long mainCursor,
      @RequestParam(required = false, defaultValue = "0") Long cursor,
      @RequestParam(required = false, defaultValue = "") String search,
      @RequestParam(required = false, defaultValue = "NONE") ScriptSearchCondition searchCondition,
      @RequestParam(required = false, defaultValue = "true") boolean isNext) {
    ScriptBoardsPageConditionDto conditionDto = ScriptBoardsPageConditionDto.builder()
        .mainCursor(mainCursor)
        .cursor(cursor)
        .search(search.trim())
        .searchCondition(searchCondition)
        .isNext(isNext)
        .build();
    Page<ScriptBoardsPageResultDto> page = scriptBoardsService.findBoards(pageable, conditionDto);

    return ResponseEntity.status(HttpStatus.OK).body(page);
  }

  private void scriptTypeCheck(MultipartFile scriptFile) {
    if (S3File.SCRIPT.getContentType().equals(scriptFile.getContentType())) {
      throw new DefaultServiceException("boards.error.invalid-script-format", envUtil);
    }
  }
}
