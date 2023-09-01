package com.practice.boxboardservice.repository.boards.script_boards;


import static com.practice.boxboardservice.entity.boards.QScriptBoardsEntity.scriptBoardsEntity;

import com.practice.boxboardservice.entity.boards.ScriptBoardsEntity;
import com.practice.boxboardservice.repository.boards.script_boards.dto.QScriptBoardsPageResultDto;
import com.practice.boxboardservice.repository.boards.script_boards.dto.ScriptBoardsPageConditionDto;
import com.practice.boxboardservice.repository.boards.script_boards.dto.ScriptBoardsPageResultDto;
import com.practice.boxboardservice.repository.boards.script_boards.type.ScriptSearchCondition;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;


/**
 * ScriptBoardsRepositoryCustomImpl.
 *
 * @author : middlefitting
 * @since : 2023/08/30
 */
public class ScriptBoardsRepositoryCustomImpl implements ScriptBoardsRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ScriptBoardsRepositoryCustomImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }


  private OrderSpecifier<?> mainSort(Pageable pageable) {
    if (!pageable.getSort().isEmpty()) {
      for (Sort.Order order : pageable.getSort()) {
        Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
        switch (order.getProperty()) {
          case "commentCount":
            return new OrderSpecifier<>(direction, scriptBoardsEntity.commentCount);
          case "viewCount":
            return new OrderSpecifier<>(direction, scriptBoardsEntity.viewCount);
          case "likeCount":
            return new OrderSpecifier<>(direction, scriptBoardsEntity.likeCount);
          default:
            return new OrderSpecifier<>(direction, scriptBoardsEntity.regDate);
        }
      }
    }
    return new OrderSpecifier<>(Order.DESC, scriptBoardsEntity.regDate);
  }

  @Override
  public Page<ScriptBoardsPageResultDto> findScriptBoardsPage(Pageable pageable,
      ScriptBoardsPageConditionDto condition) {
    List<ScriptBoardsPageResultDto> content = queryFactory
        .select(new QScriptBoardsPageResultDto(
                scriptBoardsEntity.id,
                scriptBoardsEntity.title,
                scriptBoardsEntity.content,
                scriptBoardsEntity.writerUuid,
                scriptBoardsEntity.writerNickname,
                scriptBoardsEntity.writerProfileImageUrl,
                scriptBoardsEntity.writerProfileImagePath,
                scriptBoardsEntity.viewCount,
                scriptBoardsEntity.likeCount,
                scriptBoardsEntity.dislikeCount,
                scriptBoardsEntity.commentCount,
                scriptBoardsEntity.regDate,
                scriptBoardsEntity.modDate
            )
        ).from(scriptBoardsEntity)
        .where(
            writerUuidCondition(condition),
            titleSearch(condition),
            contentSearch(condition),
            nicknameSearch(condition),
            scriptSearch(condition)
        )
        .orderBy(
            mainSort(pageable)
        )
        .offset((long) pageable.getPageNumber() * pageable.getPageSize())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<ScriptBoardsEntity> countQuery = queryFactory
        .selectFrom(scriptBoardsEntity)
        .where(
            writerUuidCondition(condition),
            titleSearch(condition),
            contentSearch(condition),
            nicknameSearch(condition),
            scriptSearch(condition)
        );
    return PageableExecutionUtils.getPage(content, pageable,
        countQuery.fetch()::size);
  }

  private BooleanExpression writerUuidCondition(ScriptBoardsPageConditionDto condition) {
    return condition.getWriterUuid().isEmpty() ? null
        : scriptBoardsEntity.writerUuid.eq(condition.getWriterUuid());
  }

  private BooleanExpression titleSearch(ScriptBoardsPageConditionDto condition) {
    if (!condition.getSearch().isEmpty() && (
        condition.getSearchCondition() == ScriptSearchCondition.ALL
            || condition.getSearchCondition() == ScriptSearchCondition.TITLE)) {
      return scriptBoardsEntity.title.contains(condition.getSearch());
    }
    return null;
  }

  private BooleanExpression contentSearch(ScriptBoardsPageConditionDto condition) {
    if (!condition.getSearch().isEmpty() && (
        condition.getSearchCondition() == ScriptSearchCondition.ALL
            || condition.getSearchCondition() == ScriptSearchCondition.CONTENT)) {
      return scriptBoardsEntity.content.contains(condition.getSearch());
    }
    return null;
  }

  private BooleanExpression nicknameSearch(ScriptBoardsPageConditionDto condition) {
    if (!condition.getSearch().isEmpty() && (
        condition.getSearchCondition() == ScriptSearchCondition.ALL
            || condition.getSearchCondition() == ScriptSearchCondition.WRITER_NICKNAME)) {
      return scriptBoardsEntity.writerNickname.contains(condition.getSearch());
    }
    return null;
  }

  private BooleanExpression scriptSearch(ScriptBoardsPageConditionDto condition) {
    if (!condition.getSearch().isEmpty() && (
        condition.getSearchCondition() == ScriptSearchCondition.ALL
            || condition.getSearchCondition() == ScriptSearchCondition.SCRIPT_NAME)) {
      return scriptBoardsEntity.scriptName.contains(condition.getSearch());
    }
    return null;
  }
}
