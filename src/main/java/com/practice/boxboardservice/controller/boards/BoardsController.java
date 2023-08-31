package com.practice.boxboardservice.controller.boards;

import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * BoardsController.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
public interface BoardsController<RES_GET, RES_PUT, RES_GET_PAGE, SEARCH_CONDITION> {

  ResponseEntity<Void> postBoards(HttpServletRequest request, MultipartFile file, String bodyJson);

  ResponseEntity<RES_GET> getBoards(HttpServletRequest request, Long boardId);

  ResponseEntity<Void> updateBoards(HttpServletRequest request, Long boardId, RES_PUT body);

  ResponseEntity<Void> deleteBoards(HttpServletRequest request, Long boardId);

  ResponseEntity<RES_GET_PAGE> getBoards(Pageable pageable, String search, String writerUuid,
      SEARCH_CONDITION searchCondition);

}
