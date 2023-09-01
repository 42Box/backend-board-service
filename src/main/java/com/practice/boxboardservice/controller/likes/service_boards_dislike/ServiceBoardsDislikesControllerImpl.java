package com.practice.boxboardservice.controller.likes.service_boards_dislike;

import com.practice.boxboardservice.controller.likes.LikesController;
import com.practice.boxboardservice.controller.likes.LikesControllerImplTemplate;
import com.practice.boxboardservice.controller.likes.dto.RequestLikesAndDislikesDto;
import com.practice.boxboardservice.controller.likes.dto.ResponseLikesAndDislikesDto;
import com.practice.boxboardservice.global.aop.validate_nickname_header.HeaderAuthCheck;
import com.practice.boxboardservice.service.likes.service_boards_dislikes.ServiceBoardsDislikesService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * serviceBoardsLikesControllerImpl.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@RestController
@RequestMapping("/service-boards/dislikes")
public class ServiceBoardsDislikesControllerImpl implements LikesController {

  private final LikesControllerImplTemplate template;

  @Autowired
  public ServiceBoardsDislikesControllerImpl(ServiceBoardsDislikesService likeService,
      ModelMapper modelMapper) {
    this.template = new LikesControllerImplTemplate(likeService, modelMapper);
  }

  @Override
  @HeaderAuthCheck
  @PostMapping("")
  public ResponseEntity<ResponseLikesAndDislikesDto> likesAndDislikes(HttpServletRequest request,
      @RequestBody @Valid RequestLikesAndDislikesDto requestDto) {
    return template.likesAndDislikes(request, requestDto);
  }
}
