package com.practice.boxboardservice.repository.likes.script_boards_likes;

import com.practice.boxboardservice.entity.likes.ScriptBoardsLikesEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * ScriptBoardsLikesRepository.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@Repository
public interface ScriptBoardsLikesRepository extends LikesRepository<ScriptBoardsLikesEntity> {

}
