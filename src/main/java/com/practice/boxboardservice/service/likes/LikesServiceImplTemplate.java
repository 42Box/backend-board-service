package com.practice.boxboardservice.service.likes;

import com.practice.boxboardservice.entity.boards.ScriptBoardsEntity;
import com.practice.boxboardservice.entity.likes.LikesEntity;
import com.practice.boxboardservice.entity.likes.LikesEntityFactory;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.global.exception.DefaultServiceException;
import com.practice.boxboardservice.repository.likes.script_boards_likes.LikesRepository;
import com.practice.boxboardservice.repository.script_boards.ScriptBoardsRepository;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;
import java.util.NoSuchElementException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * ScriptBoardsLikesService.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
public class LikesServiceImplTemplate<T extends LikesEntity> implements LikesService {

  private final EnvUtil envUtil;
  private final LikesRepository<T> likesRepository;
  private final LikesEntityFactory<T> likesEntityFactory;
  private final ScriptBoardsRepository scriptBoardsRepository;

  public LikesServiceImplTemplate(EnvUtil envUtil,
      LikesRepository<T> likesRepository,
      ScriptBoardsRepository scriptBoardsRepository, LikesEntityFactory<T> likesEntityFactory) {
    this.envUtil = envUtil;
    this.likesRepository = likesRepository;
    this.likesEntityFactory = likesEntityFactory;
    this.scriptBoardsRepository = scriptBoardsRepository;
  }

  public LikesAndDislikesResultDto likesAndDislikes(LikesAndDislikesDto dto) {
    LikesAndDislikesResultDto resultDto = new LikesAndDislikesResultDto();
    T likesEntity;
    ScriptBoardsEntity boardsEntity = findBoard(dto.getBoardId());
    if (dto.getLikeDislikeStatus()) {
      likesEntity = findLikes(dto);
      deleteLikes(likesEntity);
      resultDto.setLikeDislikeStatus(false);
      boardsEntity.decreaseLikes();
    } else {
      likesEntity = likesEntityFactory.create(dto);
      saveLikes(likesEntity);
      resultDto.setLikeDislikeStatus(true);
      boardsEntity.increaseLikes();
    }
    scriptBoardsRepository.save(boardsEntity);
    return resultDto;
  }

  private T findLikes(LikesAndDislikesDto dto) {
    return likesRepository.getByBoardIdAndUserUuid(
            dto.getBoardId(), dto.getUserUuid())
        .orElseThrow(() -> new DefaultServiceException("likes.error.not-found", envUtil));
  }

  private void deleteLikes(T entity) {
    try {
      likesRepository.deleteById(entity.getId());
    } catch (NoSuchElementException e) {
      throw new DefaultServiceException("likes.error.delete-not-found", envUtil);
    }
  }

  private void saveLikes(T entity) {
    try {
      likesRepository.save(entity);
    } catch (DataIntegrityViolationException e) {
      throw new DefaultServiceException("likes.error.already-like", envUtil);
    }
  }

  private ScriptBoardsEntity findBoard(Long boardId) {
    return scriptBoardsRepository.findById(boardId)
        .orElseThrow(() -> new DefaultServiceException("likes.error.board-not-found", envUtil));
  }
}
