package com.practice.boxboardservice.entity.boards;

import com.practice.boxboardservice.entity.BaseEntity;
import com.practice.boxboardservice.service.boards.service_boards.dto.UpdateServiceBoardsDto;
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
 * imageBoards.
 *
 * @author : middlefitting
 * @since : 2023/08/27
 */
@Entity
@Table(name = "service_boards", indexes = {
    @Index(name = "idx_service_boards_writer_uuid", columnList = "service_board_writer_uuid", unique = false),
    @Index(name = "idx_service_boards_image_path", columnList = "service_board_image_path", unique = false),
    @Index(name = "idx_service_boards_deleted", columnList = "service_board_deleted", unique = false),
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ServiceBoardsEntity extends BaseEntity implements BoardsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "service_board_id", updatable = false, unique = true)
  private Long id;

  @Column(name = "service_board_title", nullable = false, columnDefinition = "VARCHAR(40)")
  private String title;

  @Column(name = "service_board_content", nullable = false, columnDefinition = "VARCHAR(2000)")
  private String content;

  @Column(name = "service_board_service_url", nullable = false, columnDefinition = "VARCHAR(255)")
  private String serviceUrl;

  @Column(name = "service_board_image_path", nullable = false, columnDefinition = "VARCHAR(255)")
  private String imagePath;

  @Column(name = "service_board_writer_uuid", nullable = false, columnDefinition = "VARCHAR(255)")
  private String writerUuid;

  @Column(name = "service_board_writer_nickname", nullable = false, columnDefinition = "VARCHAR(50)")
  private String writerNickname;

  @Column(name = "service_board_writer_profile_image_path", nullable = false, columnDefinition = "VARCHAR(255)")
  private String writerProfileImagePath;

  @Column(name = "service_board_view_count")
  private int viewCount;

  @Column(name = "service_board_like_count")
  private int likeCount;

  @Column(name = "service_board_dislike_count")
  private int dislikeCount;

  @Column(name = "service_board_comment_count")
  private int commentCount;

  @Column(name = "service_board_deleted")
  private boolean deleted;

  @Builder
  public ServiceBoardsEntity(String title, String content, String serviceUrl, String imagePath,
      String writerUuid, String writerNickname, String writerProfileImagePath) {
    this.title = title;
    this.content = content;
    this.serviceUrl = serviceUrl;
    this.imagePath = imagePath;
    this.writerUuid = writerUuid;
    this.writerNickname = writerNickname;
    this.writerProfileImagePath = writerProfileImagePath;
    this.viewCount = 0;
    this.likeCount = 0;
    this.dislikeCount = 0;
    this.commentCount = 0;
    this.deleted = false;
  }

  public void update(UpdateServiceBoardsDto dto, String imagePath) {
    this.title = dto.getTitle();
    this.content = dto.getContent();
    this.serviceUrl = dto.getServiceUrl();
    this.imagePath = imagePath;
  }

  public void delete() {
    this.deleted = true;
  }

  public void decreaseLikes() {
    this.likeCount--;
  }

  public void increaseLikes() {
    this.likeCount++;
  }

  public void decreaseDislikes() {
    this.dislikeCount--;
  }

  public void increaseDislikes() {
    this.dislikeCount++;
  }

  public void deletePath() {
    this.imagePath = "";
  }
}
