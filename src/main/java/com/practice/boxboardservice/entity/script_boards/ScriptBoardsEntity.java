package com.practice.boxboardservice.entity.script_boards;

import com.practice.boxboardservice.entity.BaseEntity;
import com.practice.boxboardservice.service.dto.UpdateBoardsDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ScriptBoards.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Entity
@Table(name = "script_boards", indexes = {
    @Index(name = "idx_script_boards_writer_uuid", columnList = "script_board_writer_uuid", unique = false),
    @Index(name = "idx_script_boards_script_path", columnList = "script_board_script_path", unique = true),
    @Index(name = "idx_script_boards_deleted", columnList = "script_board_deleted", unique = false),
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ScriptBoardsEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "script_board_id", updatable = false, unique = true)
  private Long id;

  @Column(name = "script_board_title", nullable = false, columnDefinition = "VARCHAR(50)")
  private String title;

  @Column(name = "script_board_content", nullable = false, columnDefinition = "VARCHAR(2000)")
  private String content;

  @Column(name = "script_board_script_name", nullable = false, columnDefinition = "VARCHAR(50)")
  private String scriptName;

  @Column(name = "script_board_script_path", nullable = false, unique = true, updatable = false, columnDefinition = "VARCHAR(255)")
  private String scriptPath;

  @Column(name = "script_board_script_url", nullable = false, unique = true, updatable = false, columnDefinition = "VARCHAR(255)")
  private String scriptUrl;

  @Column(name = "script_board_writer_uuid", nullable = false, columnDefinition = "VARCHAR(255)")
  private String writerUuid;

  @Column(name = "script_board_writer_nickname", nullable = false, columnDefinition = "VARCHAR(50)")
  private String writerNickname;

  @Column(name = "script_board_writer_profile_image_url", nullable = false, columnDefinition = "VARCHAR(255)")
  private String writerProfileImageUrl;

  @Column(name = "script_board_writer_profile_image_path", nullable = false, columnDefinition = "VARCHAR(255)")
  private String writerProfileImagePath;

  @Column(name = "script_board_view_count")
  private int viewCount;

  @Column(name = "script_board_like_count")
  private int likeCount;

  @Column(name = "script_board_report_count")
  private short reportCount;

  @Column(name = "script_board_comment_count")
  private int commentCount;

  @Column(name = "script_board_deleted")
  private boolean deleted;

  @Builder
  public ScriptBoardsEntity(String title, String content, String scriptName, String scriptPath,
      String scriptUrl, String writerUuid,
      String writerNickname, String writerProfileImageUrl, String writerProfileImagePath) {
    this.title = title;
    this.content = content;
    this.scriptName = scriptName;
    this.scriptPath = scriptPath;
    this.scriptUrl = scriptUrl;
    this.writerUuid = writerUuid;
    this.writerNickname = writerNickname;
    this.writerProfileImageUrl = writerProfileImageUrl;
    this.writerProfileImagePath = writerProfileImagePath;
    this.viewCount = 0;
    this.likeCount = 0;
    this.reportCount = 0;
    this.commentCount = 0;
    this.deleted = false;
  }

  public void update(UpdateBoardsDto dto) {
    this.title = dto.getTitle();
    this.content = dto.getContent();
  }

  public void delete() {
    this.deleted = true;
  }
}
