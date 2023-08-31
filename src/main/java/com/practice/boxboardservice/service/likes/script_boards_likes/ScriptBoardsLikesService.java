package com.practice.boxboardservice.service.likes.script_boards_likes;

import com.practice.boxboardservice.entity.script_boards.ScriptBoardsEntity;
import com.practice.boxboardservice.entity.script_boards.ScriptBoardsLikesEntity;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.global.exception.DefaultServiceException;
import com.practice.boxboardservice.repository.likes.script_boards_likes.ScriptBoardsLikesRepository;
import com.practice.boxboardservice.repository.script_boards.ScriptBoardsRepository;
import com.practice.boxboardservice.service.likes.LikesService;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ScriptBoardsLikesService.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@Service
public class ScriptBoardsLikesService implements LikesService {

  private final EnvUtil envUtil;
  private final ScriptBoardsLikesRepository scriptBoardsLikesRepository;
  private final ScriptBoardsRepository scriptBoardsRepository;

  public ScriptBoardsLikesService(EnvUtil envUtil,
      ScriptBoardsLikesRepository scriptBoardsLikesRepository,
      ScriptBoardsRepository scriptBoardsRepository) {
    this.envUtil = envUtil;
    this.scriptBoardsLikesRepository = scriptBoardsLikesRepository;
    this.scriptBoardsRepository = scriptBoardsRepository;
  }

  @Override
  @Transactional(rollbackFor = IOException.class)
  public LikesAndDislikesResultDto likesAndDislikes(LikesAndDislikesDto dto) {
    LikesAndDislikesResultDto resultDto = new LikesAndDislikesResultDto();
    ScriptBoardsLikesEntity likesEntity;
    ScriptBoardsEntity boardsEntity = findBoard(dto.getBoardId());
    if (dto.getBoardLiked()) {
      likesEntity = findLikes(dto);
      deleteLikes(likesEntity);
      resultDto.setBoardLiked(false);
      boardsEntity.decreaseLikes();
    } else {
      likesEntity = buildEntity(dto);
      saveLikes(likesEntity);
      resultDto.setBoardLiked(true);
      boardsEntity.increaseLikes();
    }
    scriptBoardsRepository.save(boardsEntity);
    return resultDto;
  }

  private ScriptBoardsLikesEntity findLikes(LikesAndDislikesDto dto) {
    return scriptBoardsLikesRepository.getByBoardIdAndUserUuid(
            dto.getBoardId(), dto.getUserUuid())
        .orElseThrow(() -> new DefaultServiceException("likes.error.not-found", envUtil));
  }

  private void deleteLikes(ScriptBoardsLikesEntity entity) {
    try {
      scriptBoardsLikesRepository.deleteById(entity.getId());
    } catch (NoSuchElementException e) {
      throw new DefaultServiceException("likes.error.delete-not-found", envUtil);
    }
  }

  private void saveLikes(ScriptBoardsLikesEntity entity) {
    try {
      scriptBoardsLikesRepository.save(entity);
    } catch (DataIntegrityViolationException e) {
      throw new DefaultServiceException("likes.error.already-like", envUtil);
    }
  }

  private ScriptBoardsLikesEntity buildEntity(LikesAndDislikesDto dto) {
    return ScriptBoardsLikesEntity.builder()
        .boardId(dto.getBoardId())
        .userUuid(dto.getUserUuid())
        .build();
  }

  private ScriptBoardsEntity findBoard(Long boardId) {
    return scriptBoardsRepository.findById(boardId)
        .orElseThrow(() -> new DefaultServiceException("likes.error.board-not-found", envUtil));
  }
}
