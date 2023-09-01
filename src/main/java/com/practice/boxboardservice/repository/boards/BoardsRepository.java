package com.practice.boxboardservice.repository.boards;

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

}
