package com.practice.boxboardservice.service.likes.service_boards_likes;

import com.practice.boxboardservice.entity.boards.ServiceBoardsEntity;
import com.practice.boxboardservice.entity.likes.LikesEntityFactory;
import com.practice.boxboardservice.entity.likes.ServiceBoardsLikesEntity;
import com.practice.boxboardservice.global.env.EnvUtil;
import com.practice.boxboardservice.repository.boards.service_boards.ServiceBoardsRepository;
import com.practice.boxboardservice.repository.likes.service_boards_likes.ServiceBoardsLikesRepository;
import com.practice.boxboardservice.service.likes.LikesService;
import com.practice.boxboardservice.service.likes.LikesServiceImplTemplate;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesDto;
import com.practice.boxboardservice.service.likes.dto.LikesAndDislikesResultDto;
import com.practice.boxboardservice.service.likes.helper.ServiceBoardsLikesHelper;
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
public class ServiceBoardsLikesService implements LikesService {

  private final LikesServiceImplTemplate<ServiceBoardsLikesEntity, ServiceBoardsEntity> template;

  @Autowired
  public ServiceBoardsLikesService(EnvUtil envUtil, ServiceBoardsLikesRepository likesRepository,
      LikesEntityFactory<ServiceBoardsLikesEntity> likesEntityFactory,
      ServiceBoardsRepository serviceBoardsRepository,
      ServiceBoardsLikesHelper helper) {
    this.template = new LikesServiceImplTemplate<ServiceBoardsLikesEntity, ServiceBoardsEntity>(
        envUtil, likesRepository, serviceBoardsRepository, likesEntityFactory, helper);
  }

  @Override
  @Transactional(rollbackFor = IOException.class)
  public LikesAndDislikesResultDto likesAndDislikes(LikesAndDislikesDto dto) {
    return template.likesAndDislikes(dto);
  }
}
