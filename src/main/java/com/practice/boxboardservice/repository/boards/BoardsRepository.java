package com.practice.boxboardservice.repository.boards;

import com.practice.boxboardservice.entity.boards.ScriptBoardsEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * BoardsRepository.
 *
 * @author : middlefitting
 * @since : 2023/09/01
 */
@NoRepositoryBean
public interface BoardsRepository<T> extends JpaRepository<T, Long> {

  Optional<T> findByIdAndDeleted(Long boardId, boolean deleted);
}
