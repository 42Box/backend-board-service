package com.practice.boxboardservice.controller.boards;

import com.practice.boxboardservice.service.boards.script_boards.ScriptBoardsService;
import com.practice.boxboardservice.service.boards.service_boards.ServiceBoardsService;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PrivateBoardsController.
 *
 * @author : middlefitting
 * @since : 2023/09/04
 */
@RestController
@RequestMapping("/private")
@AllArgsConstructor
public class PrivateBoardsController {

  private final ScriptBoardsService scriptBoardsService;
  private final ServiceBoardsService serviceBoardsService;
  ;

  @PostMapping("/script-boards/{boardId}/comments")
  public ResponseEntity<Void> newScriptBoardComment(@PathVariable long boardId) {
    try {
      scriptBoardsService.newScriptBoardComment(boardId);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/script-boards/{boardId}/comments")
  public ResponseEntity<Void> deleteScriptBoardComment(@PathVariable long boardId) {
    try {
      scriptBoardsService.deleteScriptBoardComment(boardId);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/service-boards/{boardId}/comments")
  public ResponseEntity<Void> newServiceBoardComment(@PathVariable long boardId) {
    try {
      serviceBoardsService.newServiceBoardComment(boardId);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/service-boards/{boardId}/comments")
  public ResponseEntity<Void> deleteServiceBoardComment(@PathVariable long boardId) {
    try {
      serviceBoardsService.deleteServiceBoardComment(boardId);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
