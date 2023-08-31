package com.practice.boxboardservice.controller.likes;

import com.practice.boxboardservice.controller.likes.dto.RequestLikesAndDislikesDto;
import com.practice.boxboardservice.controller.likes.dto.ResponseLikesAndDislikesDto;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 * LikesController.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
public interface LikesController {

  ResponseEntity<ResponseLikesAndDislikesDto> likesAndDislikes(
      HttpServletRequest request, RequestLikesAndDislikesDto requestDto);
}
