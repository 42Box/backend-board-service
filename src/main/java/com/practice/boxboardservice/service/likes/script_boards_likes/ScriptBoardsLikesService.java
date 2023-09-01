package com.practice.boxboardservice.service.likes.script_boards_likes;

import com.practice.boxboardservice.entity.boards.ScriptBoardsEntity;
import com.practice.boxboardservice.entity.likes.LikesEntityFactory;
import com.practice.boxboardservice.entity.likes.ScriptBoardsLikesEntity;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.repository.likes.script_boards_likes.ScriptBoardsLikesRepository;
import com.practice.boxboardservice.repository.boards.script_boards.ScriptBoardsRepository;
import com.practice.boxboardservice.service.likes.LikesService;
import com.practice.boxboardservice.service.likes.LikesServiceImplTemplate;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;
import com.practice.boxboardservice.service.likes.helper.ScriptBoardsLikesHelper;
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
public class ScriptBoardsLikesService implements LikesService {

  private final LikesServiceImplTemplate<ScriptBoardsLikesEntity, ScriptBoardsEntity> template;

  @Autowired
  public ScriptBoardsLikesService(EnvUtil envUtil, ScriptBoardsLikesRepository likesRepository,
      LikesEntityFactory<ScriptBoardsLikesEntity> likesEntityFactory,
      ScriptBoardsRepository scriptBoardsRepository,
      ScriptBoardsLikesHelper helper) {
    this.template = new LikesServiceImplTemplate<ScriptBoardsLikesEntity, ScriptBoardsEntity>(
        envUtil, likesRepository, scriptBoardsRepository, likesEntityFactory, helper);
  }

  @Override
  @Transactional(rollbackFor = IOException.class)
  public LikesAndDislikesResultDto likesAndDislikes(LikesAndDislikesDto dto) {
    return template.likesAndDislikes(dto);
  }
}
