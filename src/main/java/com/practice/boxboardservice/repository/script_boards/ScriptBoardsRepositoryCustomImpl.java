package com.practice.boxboardservice.repository.script_boards;


import static com.practice.boxboardservice.entity.script_boards.QScriptBoardsEntity.scriptBoardsEntity;

import com.practice.boxboardservice.entity.script_boards.ScriptBoardsEntity;
import com.practice.boxboardservice.repository.script_boards.dto.QScriptBoardsPageResultDto;
import com.practice.boxboardservice.repository.script_boards.dto.ScriptBoardsPageConditionDto;
import com.practice.boxboardservice.repository.script_boards.dto.ScriptBoardsPageResultDto;
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
            return new OrderSpecifier<>(direction, scriptBoardsEntity.id);
        }
      }
    }
    return new OrderSpecifier<>(Order.DESC, scriptBoardsEntity.id);
  }

  private OrderSpecifier<?> idSort(Pageable pageable) {
    if (!pageable.getSort().isEmpty()) {
      for (Sort.Order order : pageable.getSort()) {
        Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
        return new OrderSpecifier<>(direction, scriptBoardsEntity.id);
      }
    }
    return new OrderSpecifier<>(Order.DESC, scriptBoardsEntity.id);
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
                scriptBoardsEntity.commentCount,
                scriptBoardsEntity.regDate,
                scriptBoardsEntity.modDate
            )
        ).from(scriptBoardsEntity)
        .where(
            mainCursorCondition(pageable, condition),
            cursorCondition(pageable, condition)
        )
        .orderBy(
            mainSort(pageable),
            idSort(pageable)
        )
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<ScriptBoardsEntity> countQuery = queryFactory
        .selectFrom(scriptBoardsEntity)
        .where(
            mainCursorCondition(pageable, condition),
            cursorCondition(pageable, condition)
        );
    Page<ScriptBoardsPageResultDto> page = PageableExecutionUtils.getPage(content, pageable,
        countQuery.fetch()::size);
    return page;
  }

  private BooleanExpression IdSortCondition(Pageable pageable,
      ScriptBoardsPageConditionDto condition) {
    if (condition.getIsNext()) {
      return scriptBoardsEntity.id.lt(condition.getCursor());
    }
    return scriptBoardsEntity.id.gt(condition.getCursor());
  }

  private BooleanExpression cursorCondition(Pageable pageable,
      ScriptBoardsPageConditionDto condition) {
    if (condition.getCursor() == 0) {
      return null;
    }
    Order direction = Order.DESC;
    for (Sort.Order order : pageable.getSort()) {
      direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
    }
    if (direction == Order.DESC) {
      return condition.getIsNext() ? scriptBoardsEntity.id.lt(condition.getCursor())
          : scriptBoardsEntity.id.gt(condition.getCursor());
    }
    return condition.getIsNext() ? scriptBoardsEntity.id.gt(condition.getCursor())
        : scriptBoardsEntity.id.lt(condition.getCursor());
  }

  private BooleanExpression mainCursorCondition(Pageable pageable,
      ScriptBoardsPageConditionDto condition) {
    if (condition.getMainCursor() == 0) {
      return null;
    }
    Order direction = Order.DESC;
    for (Sort.Order order : pageable.getSort()) {
      direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
      switch (order.getProperty()) {
        case "commentCount": {
          if (direction == Order.DESC) {
            return condition.getIsNext() ? scriptBoardsEntity.commentCount.lt(
                condition.getMainCursor())
                : scriptBoardsEntity.commentCount.gt(condition.getMainCursor());
          }
          return condition.getIsNext() ? scriptBoardsEntity.commentCount.gt(
              condition.getMainCursor())
              : scriptBoardsEntity.commentCount.lt(condition.getMainCursor());
        }
        case "viewCount": {
          if (direction == Order.DESC) {
            return condition.getIsNext() ? scriptBoardsEntity.viewCount.lt(
                condition.getMainCursor())
                : scriptBoardsEntity.viewCount.gt(condition.getMainCursor());
          }
          return condition.getIsNext() ? scriptBoardsEntity.viewCount.gt(condition.getMainCursor())
              : scriptBoardsEntity.viewCount.lt(condition.getMainCursor());
        }
        case "likeCount": {
          if (direction == Order.DESC) {
            return condition.getIsNext() ? scriptBoardsEntity.likeCount.lt(
                condition.getMainCursor())
                : scriptBoardsEntity.likeCount.gt(condition.getMainCursor());
          }
          return condition.getIsNext() ? scriptBoardsEntity.likeCount.gt(condition.getMainCursor())
              : scriptBoardsEntity.likeCount.lt(condition.getMainCursor());
        }
        default: {
          if (direction == Order.DESC) {
            return condition.getIsNext() ? scriptBoardsEntity.id.lt(condition.getMainCursor())
                : scriptBoardsEntity.id.gt(condition.getMainCursor());
          }
          return condition.getIsNext() ? scriptBoardsEntity.id.gt(condition.getMainCursor())
              : scriptBoardsEntity.id.lt(condition.getMainCursor());
        }
      }
    }
    return condition.getIsNext() ? scriptBoardsEntity.id.lt(condition.getMainCursor())
        : scriptBoardsEntity.id.gt(condition.getMainCursor());
  }
}
