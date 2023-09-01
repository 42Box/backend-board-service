package com.practice.boxboardservice.repository.boards.service_boards;


import static com.practice.boxboardservice.entity.boards.QScriptBoardsEntity.scriptBoardsEntity;
import static com.practice.boxboardservice.entity.boards.QServiceBoardsEntity.serviceBoardsEntity;

import com.practice.boxboardservice.entity.boards.ServiceBoardsEntity;
import com.practice.boxboardservice.repository.boards.service_boards.dto.QServiceBoardsPageResultDto;
import com.practice.boxboardservice.repository.boards.service_boards.dto.ServiceBoardsPageConditionDto;
import com.practice.boxboardservice.repository.boards.service_boards.dto.ServiceBoardsPageResultDto;
import com.practice.boxboardservice.repository.boards.service_boards.type.ServiceSearchCondition;
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
public class ServiceBoardsRepositoryCustomImpl implements ServiceBoardsRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ServiceBoardsRepositoryCustomImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }


  private OrderSpecifier<?> mainSort(Pageable pageable) {
    if (!pageable.getSort().isEmpty()) {
      for (Sort.Order order : pageable.getSort()) {
        Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
        switch (order.getProperty()) {
          case "commentCount":
            return new OrderSpecifier<>(direction, serviceBoardsEntity.commentCount);
          case "viewCount":
            return new OrderSpecifier<>(direction, serviceBoardsEntity.viewCount);
          case "likeCount":
            return new OrderSpecifier<>(direction, serviceBoardsEntity.likeCount);
          default:
            return new OrderSpecifier<>(direction, serviceBoardsEntity.regDate);
        }
      }
    }
    return new OrderSpecifier<>(Order.DESC, serviceBoardsEntity.regDate);
  }

  @Override
  public Page<ServiceBoardsPageResultDto> findBoardsPage(Pageable pageable,
      ServiceBoardsPageConditionDto condition) {
    List<ServiceBoardsPageResultDto> content = queryFactory
        .select(new QServiceBoardsPageResultDto(
                serviceBoardsEntity.id,
                serviceBoardsEntity.title,
                serviceBoardsEntity.content,
                serviceBoardsEntity.writerUuid,
                serviceBoardsEntity.writerNickname,
                serviceBoardsEntity.writerProfileImagePath,
                serviceBoardsEntity.viewCount,
                serviceBoardsEntity.likeCount,
                serviceBoardsEntity.dislikeCount,
                serviceBoardsEntity.commentCount,
                serviceBoardsEntity.regDate,
                serviceBoardsEntity.modDate
            )
        ).from(serviceBoardsEntity)
        .where(
            writerUuidCondition(condition),
            titleSearch(condition),
            contentSearch(condition),
            nicknameSearch(condition),
            serviceUrlSearch(condition),
            serviceBoardsEntity.deleted.eq(false)
        )
        .orderBy(
            mainSort(pageable)
        )
        .offset((long) pageable.getPageNumber() * pageable.getPageSize())
        .limit(pageable.getPageSize())
        .fetch();

    JPAQuery<ServiceBoardsEntity> countQuery = queryFactory
        .selectFrom(serviceBoardsEntity)
        .where(
            writerUuidCondition(condition),
            titleSearch(condition),
            contentSearch(condition),
            nicknameSearch(condition),
            serviceUrlSearch(condition),
            serviceBoardsEntity.deleted.eq(false)
        );
    return PageableExecutionUtils.getPage(content, pageable,
        countQuery.fetch()::size);
  }

  private BooleanExpression writerUuidCondition(ServiceBoardsPageConditionDto condition) {
    return condition.getWriterUuid().isEmpty() ? null
        : serviceBoardsEntity.writerUuid.eq(condition.getWriterUuid());
  }

  private BooleanExpression titleSearch(ServiceBoardsPageConditionDto condition) {
    if (!condition.getSearch().isEmpty() && (
        condition.getSearchCondition() == ServiceSearchCondition.ALL
            || condition.getSearchCondition() == ServiceSearchCondition.TITLE)) {
      return serviceBoardsEntity.title.contains(condition.getSearch());
    }
    return null;
  }

  private BooleanExpression contentSearch(ServiceBoardsPageConditionDto condition) {
    if (!condition.getSearch().isEmpty() && (
        condition.getSearchCondition() == ServiceSearchCondition.ALL
            || condition.getSearchCondition() == ServiceSearchCondition.CONTENT)) {
      return serviceBoardsEntity.content.contains(condition.getSearch());
    }
    return null;
  }

  private BooleanExpression nicknameSearch(ServiceBoardsPageConditionDto condition) {
    if (!condition.getSearch().isEmpty() && (
        condition.getSearchCondition() == ServiceSearchCondition.ALL
            || condition.getSearchCondition() == ServiceSearchCondition.WRITER_NICKNAME)) {
      return serviceBoardsEntity.writerNickname.contains(condition.getSearch());
    }
    return null;
  }

  private BooleanExpression serviceUrlSearch(ServiceBoardsPageConditionDto condition) {
    if (!condition.getSearch().isEmpty() && (
        condition.getSearchCondition() == ServiceSearchCondition.ALL
            || condition.getSearchCondition() == ServiceSearchCondition.SERVICE_URL)) {
      return serviceBoardsEntity.serviceUrl.contains(condition.getSearch());
    }
    return null;
  }
}
