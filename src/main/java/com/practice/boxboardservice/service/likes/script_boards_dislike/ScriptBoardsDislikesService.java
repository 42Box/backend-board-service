package com.practice.boxboardservice.service.likes.script_boards_dislike;

import com.practice.boxboardservice.entity.boards.ScriptBoardsEntity;
import com.practice.boxboardservice.entity.likes.LikesEntityFactory;
import com.practice.boxboardservice.entity.likes.ScriptBoardsDislikesEntity;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.repository.likes.script_boards_likes.ScriptBoardsDislikesRepository;
import com.practice.boxboardservice.repository.boards.script_boards.ScriptBoardsRepository;
import com.practice.boxboardservice.service.likes.LikesService;
import com.practice.boxboardservice.service.likes.LikesServiceImplTemplate;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;
import com.practice.boxboardservice.service.likes.helper.ScriptBoardsDislikesHelper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ScriptBoardsLikesService.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@Service
public class ScriptBoardsDislikesService implements LikesService {

  private final LikesServiceImplTemplate<ScriptBoardsDislikesEntity, ScriptBoardsEntity> template;

  @Autowired
  public ScriptBoardsDislikesService(EnvUtil envUtil,
      ScriptBoardsDislikesRepository likesRepository,
      LikesEntityFactory<ScriptBoardsDislikesEntity> likesEntityFactory,
      ScriptBoardsRepository scriptBoardsRepository,
      ScriptBoardsDislikesHelper helper) {
    this.template = new LikesServiceImplTemplate<ScriptBoardsDislikesEntity, ScriptBoardsEntity>(
        envUtil, likesRepository, scriptBoardsRepository, likesEntityFactory, helper);
  }

  @Override
  @Transactional(rollbackFor = IOException.class)
  public LikesAndDislikesResultDto likesAndDislikes(LikesAndDislikesDto dto) {
    return template.likesAndDislikes(dto);
  }
}
