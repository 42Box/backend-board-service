package com.practice.boxboardservice.entity.likes;

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
    name = "script_boards_dislikes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"dislike_board_id", "dislike_user_uuid"}),
    indexes = {
        @Index(name = "idx_dislike_board_id", columnList = "dislike_board_id", unique = false),
        @Index(name = "idx_dislike_user_uuid", columnList = "dislike_user_uuid", unique = false)
    })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScriptBoardsDislikesEntity extends BaseEntity implements LikesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "dislike_id", updatable = false)
  private long id;
  @Column(name = "dislike_board_id", nullable = false, updatable = false, unique = false)
  private Long boardId;
  @Column(name = "dislike_user_uuid", columnDefinition = "VARCHAR(255)", nullable = false, updatable = false, unique = false)
  private String userUuid;

  @Builder
  public ScriptBoardsDislikesEntity(Long boardId, String userUuid) {
    this.boardId = boardId;
    this.userUuid = userUuid;
  }
}
