package com.practice.boxboardservice.entity.script_boards;

import com.practice.boxboardservice.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ScriptBoardsLikes.
 *
 * @author : middlefitting
 * @since : 2023/08/31
 */
@Entity
@Table(
    name = "script_boards_likes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"like_board_id", "like_user_uuid"}),
    indexes = {
        @Index(name = "idx_like_board_id", columnList = "like_board_id", unique = false),
        @Index(name = "idx_like_user_uuid", columnList = "like_user_uuid", unique = false)
    })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScriptBoardsLikesEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "like_id", updatable = false)
  private long id;
  @Column(name = "like_board_id", nullable = false, updatable = false, unique = false)
  private Long boardId;
  @Column(name = "like_user_uuid", columnDefinition = "VARCHAR(255)", nullable = false, updatable = false, unique = false)
  private String userUuid;

  @Builder
  public ScriptBoardsLikesEntity(Long boardId, String userUuid) {
    this.boardId = boardId;
    this.userUuid = userUuid;
  }
}
