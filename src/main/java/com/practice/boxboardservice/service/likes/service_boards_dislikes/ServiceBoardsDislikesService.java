package com.practice.boxboardservice.service.likes.service_boards_dislikes;

import com.practice.boxboardservice.entity.boards.ServiceBoardsEntity;
import com.practice.boxboardservice.entity.likes.LikesEntityFactory;
import com.practice.boxboardservice.entity.likes.ServiceBoardsDislikesEntity;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.repository.boards.service_boards.ServiceBoardsRepository;
import com.practice.boxboardservice.repository.likes.service_boards_likes.ServiceBoardsDislikesRepository;
import com.practice.boxboardservice.service.likes.LikesService;
import com.practice.boxboardservice.service.likes.LikesServiceImplTemplate;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;
import com.practice.boxboardservice.service.likes.helper.ServiceBoardsDislikesHelper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ServiceBoardsLikesService.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@Service
public class ServiceBoardsDislikesService implements LikesService {

  private final LikesServiceImplTemplate<ServiceBoardsDislikesEntity, ServiceBoardsEntity> template;

  @Autowired
  public ServiceBoardsDislikesService(EnvUtil envUtil,
      ServiceBoardsDislikesRepository likesRepository,
      LikesEntityFactory<ServiceBoardsDislikesEntity> likesEntityFactory,
      ServiceBoardsRepository serviceBoardsRepository,
      ServiceBoardsDislikesHelper helper) {
    this.template = new LikesServiceImplTemplate<ServiceBoardsDislikesEntity, ServiceBoardsEntity>(
        envUtil, likesRepository, serviceBoardsRepository, likesEntityFactory, helper);
  }

  @Override
  @Transactional(rollbackFor = IOException.class)
  public LikesAndDislikesResultDto likesAndDislikes(LikesAndDislikesDto dto) {
    return template.likesAndDislikes(dto);
  }
}
