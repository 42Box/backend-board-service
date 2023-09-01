package com.practice.boxboardservice.controller.boards.service_boards;

import com.practice.boxboardservice.controller.boards.service_boards.dto.RequestPostServiceBoardsDto;
import com.practice.boxboardservice.controller.boards.service_boards.dto.RequestUpdateServiceBoardsDto;
import com.practice.boxboardservice.controller.boards.service_boards.dto.ResponseGetServiceBoardsDto;
import com.practice.boxboardservice.global.aop.validate_nickname_header.HeaderAuthCheck;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.global.exception.DefaultServiceException;
import com.practice.boxboardservice.global.utils.JsonObjectConverter;
import com.practice.boxboardservice.global.utils.ObjectValidator;
import com.practice.boxboardservice.repository.boards.service_boards.dto.ServiceBoardsPageConditionDto;
import com.practice.boxboardservice.repository.boards.service_boards.dto.ServiceBoardsPageResultDto;
import com.practice.boxboardservice.repository.boards.service_boards.type.ServiceSearchCondition;
import com.practice.boxboardservice.service.boards.service_boards.ServiceBoardsService;
import com.practice.boxboardservice.service.boards.service_boards.dto.GetServiceBoardsDto;
import com.practice.boxboardservice.service.boards.service_boards.dto.PostServiceBoardsDto;
import com.practice.boxboardservice.service.boards.service_boards.dto.UpdateServiceBoardsDto;
import com.practice.boxboardservice.service.dto.DeleteBoardsDto;
import com.practice.boxboardservice.service.dto.UpdateBoardsDto;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * imagesBoardControllerImpl.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@RestController
@RequestMapping("/service-boards")
@AllArgsConstructor
public class ServiceBoardControllerImpl {

  private final ServiceBoardsService serviceBoardsService;
  private final ModelMapper modelMapper;
  private final EnvUtil envUtil;
  private final ObjectValidator objectValidator;
  private final JsonObjectConverter jsonObjectConverter;

  @HeaderAuthCheck
  @PostMapping("")
  public ResponseEntity<Void> postBoards(HttpServletRequest request,
      @RequestParam(value = "image-file", required = false) MultipartFile imageFile,
      @RequestParam(value = "body") String body) {
    RequestPostServiceBoardsDto requestPostBoardsDto = jsonObjectConverter.convertToObject(body,
        RequestPostServiceBoardsDto.class, "global.error.invalid-argument-request");
    objectValidator.validateBodyObject(requestPostBoardsDto);
    PostServiceBoardsDto dto = modelMapper.map(requestPostBoardsDto, PostServiceBoardsDto.class);
    dto.setWriterUuid(request.getHeader("uuid"));
    dto.setWriterNickname(request.getHeader("nickname"));
    dto.setWriterProfileImagePath(request.getHeader("profileImagePath"));
    dto.setImageFile(imageFile);
    imageTypeCheck(imageFile);
    serviceBoardsService.postBoards(dto);

    return ResponseEntity.status(201).build();
  }

  @GetMapping("/{boardId}")
  public ResponseEntity<ResponseGetServiceBoardsDto> getBoards(HttpServletRequest request,
      @PathVariable Long boardId) {
    Optional<String> userUuid = Optional.ofNullable(request.getHeader("uuid"));
    GetServiceBoardsDto dto = serviceBoardsService.findBoardsByBoardId(boardId, userUuid);
    ResponseGetServiceBoardsDto responseGetServiceBoardsDto = modelMapper.map(dto,
        ResponseGetServiceBoardsDto.class);
    return ResponseEntity.status(HttpStatus.OK).body(responseGetServiceBoardsDto);
  }

  @HeaderAuthCheck
  @PutMapping("/{boardId}")
  public ResponseEntity<Void> updateBoards(HttpServletRequest request, @PathVariable Long boardId,
      @RequestParam(value = "image-file", required = false) MultipartFile imageFile,
      @RequestParam(value = "body") String body) {
    RequestUpdateServiceBoardsDto requestDto = jsonObjectConverter.convertToObject(body,
        RequestUpdateServiceBoardsDto.class, "global.error.invalid-argument-request");
    objectValidator.validateBodyObject(requestDto);
    imageTypeCheck(imageFile);
    UpdateServiceBoardsDto dto = modelMapper.map(requestDto, UpdateServiceBoardsDto.class);
    dto.setWriterUuid(request.getHeader("uuid"));
    dto.setBoardId(boardId);
    serviceBoardsService.updateBoards(dto, imageFile);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @HeaderAuthCheck
  @DeleteMapping("/{boardId}")
  public ResponseEntity<Void> deleteBoards(HttpServletRequest request, @PathVariable Long boardId) {
    DeleteBoardsDto dto = new DeleteBoardsDto(boardId, request.getHeader("uuid"));
    serviceBoardsService.deleteBoards(dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("")
  public ResponseEntity<Page<ServiceBoardsPageResultDto>> getBoards(
      @PageableDefault(page = 0, size = 5, sort = "regDate", direction = Direction.DESC) Pageable pageable,
      @RequestParam(required = false, defaultValue = "") String search,
      @RequestParam(required = false, defaultValue = "") String writerUuid,
      @RequestParam(required = false, defaultValue = "NONE") ServiceSearchCondition searchCondition) {
    ServiceBoardsPageConditionDto conditionDto = ServiceBoardsPageConditionDto.builder()
        .search(search.trim())
        .writerUuid(writerUuid)
        .searchCondition(searchCondition)
        .build();
    Page<ServiceBoardsPageResultDto> page = serviceBoardsService.findBoards(pageable, conditionDto);
    System.out.println(pageable);
    return ResponseEntity.status(HttpStatus.OK).body(page);
  }

  private void imageTypeCheck(MultipartFile imageFile) {
//    if (!S3File.image.getContentType().equals(imageFile.getContentType())) {
//      throw new DefaultServiceException("boards.error.invalid-image-format", envUtil);
//    }
    if (imageFile == null) {
      return;
    }
    try {
      if (imageFile.getBytes().length > 2000000) {
        throw new DefaultServiceException("boards.error.invalid-image-size", envUtil);
      }
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
