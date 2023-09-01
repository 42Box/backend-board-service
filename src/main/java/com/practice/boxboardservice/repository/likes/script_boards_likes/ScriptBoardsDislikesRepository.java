package com.practice.boxboardservice.repository.likes.script_boards_likes;

import com.practice.boxboardservice.entity.likes.ScriptBoardsDislikesEntity;
import com.practice.boxboardservice.repository.likes.LikesRepository;
import org.springframework.stereotype.Repository;

/**
 * ScriptBoardsLikesRepository.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@Repository
public interface ScriptBoardsDislikesRepository extends
    LikesRepository<ScriptBoardsDislikesEntity> {

}
