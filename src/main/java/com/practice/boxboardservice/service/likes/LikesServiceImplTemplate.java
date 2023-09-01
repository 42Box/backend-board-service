package com.practice.boxboardservice.service.likes;

import com.practice.boxboardservice.entity.boards.BoardsEntity;
import com.practice.boxboardservice.entity.likes.LikesEntity;
import com.practice.boxboardservice.entity.likes.LikesEntityFactory;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.global.exception.DefaultServiceException;
import com.practice.boxboardservice.repository.boards.BoardsRepository;
import com.practice.boxboardservice.repository.likes.LikesRepository;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;
import com.practice.boxboardservice.service.likes.helper.LikesHelper;
import java.util.NoSuchElementException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * ScriptBoardsLikesService.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
public class LikesServiceImplTemplate<T extends LikesEntity, S extends BoardsEntity> implements
    LikesService {

  private final EnvUtil envUtil;
  private final LikesRepository<T> likesRepository;
  private final LikesEntityFactory<T> likesEntityFactory;
  private final BoardsRepository<S> boardsRepository;
  private final LikesHelper<T, S> likesHelper;


  public LikesServiceImplTemplate(EnvUtil envUtil,
      LikesRepository<T> likesRepository,
      BoardsRepository<S> boardsRepository, LikesEntityFactory<T> likesEntityFactory,
      LikesHelper<T, S> likesHelper) {
    this.envUtil = envUtil;
    this.likesRepository = likesRepository;
    this.likesEntityFactory = likesEntityFactory;
    this.boardsRepository = boardsRepository;
    this.likesHelper = likesHelper;
  }

  public LikesAndDislikesResultDto likesAndDislikes(LikesAndDislikesDto dto) {
    LikesAndDislikesResultDto resultDto = new LikesAndDislikesResultDto();
    T likesEntity;
    S boardsEntity = findBoard(dto.getBoardId());
    if (dto.getLikeDislikeStatus()) {
      likesEntity = findLikes(dto);
      deleteLikes(likesEntity);
      resultDto.setLikeDislikeStatus(false);
      likesHelper.decreaseFromBoard(boardsEntity);
    } else {
      likesEntity = likesEntityFactory.create(dto);
      saveLikes(likesEntity);
      resultDto.setLikeDislikeStatus(true);
      likesHelper.increaseFromBoard(boardsEntity);
    }
    boardsRepository.save(boardsEntity);
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

  private S findBoard(Long boardId) {
    return boardsRepository.findById(boardId)
        .orElseThrow(() -> new DefaultServiceException("likes.error.board-not-found", envUtil));
  }
}
