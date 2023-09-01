package com.practice.boxboardservice.repository.likes;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * ScriptBoardsLikesRepository.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@NoRepositoryBean
public interface LikesRepository<T> extends JpaRepository<T, Long> {
  Optional<T> getByBoardIdAndUserUuid(Long boardId, String userUuid);
}
