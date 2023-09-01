package com.practice.boxboardservice.repository.likes.service_boards_likes;

import com.practice.boxboardservice.entity.likes.ServiceBoardsDislikesEntity;
import com.practice.boxboardservice.repository.likes.LikesRepository;
import org.springframework.stereotype.Repository;

/**
 * ScriptBoardsLikesRepository.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@Repository
public interface ServiceBoardsDislikesRepository extends
    LikesRepository<ServiceBoardsDislikesEntity> {

}
