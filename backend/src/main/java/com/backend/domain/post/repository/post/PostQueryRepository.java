//import com.backend.domain.category.domain.CategoryName;
//import com.backend.domain.post.dto.PostPageResponse;
//import com.backend.domain.post.dto.PostResponse;
//import com.backend.domain.post.dto.QPostPageResponse;
//import com.backend.domain.post.dto.QPostResponse;
//import com.backend.domain.post.util.PostSearchCondition;
//import com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus;
//import com.querydsl.jpa.impl.JPAQuery;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.data.domain.Page;
//import org.springframework.data.support.PageableExecutionUtils;
//
//public Page<PostPageResponse> findAll(PostSearchCondition postSearchCondition,
//        Pageable pageable) {
//    List<PostPageResponse> content = queryFactory
//            .selectDistinct(new QPostPageResponse(
//                    post.postId, post.subject, post.category.name,
//                    post.author.name, post.author.profileImg,
//                    comment.countDistinct(), voter.countDistinct(), post.createdAt)
//            )
//            .from(post)
//            .leftJoin(post.category)
//            .leftJoin(post.author)
//            .leftJoin(post._postCommentList, comment)
//            .leftJoin(post._postVoterList, voter)
//            .groupBy(post.postId, post.subject, post.category.name,
//                    post.author.name, post.author.profileImg, post.createdAt)
//            .where(
//                    getCategoryIdEq(postSearchCondition.categoryId()),
//                    getSubjectContains(postSearchCondition.kw())
//            )
//            .offset(pageable.getOffset())
//            .limit(pageable.getPageSize())
//            .fetch();
//
//    JPAQuery<Long> countQuery = queryFactory.select(post.count())
//            .from(post)
//            .where(
//                    getCategoryIdEq(postSearchCondition.categoryId()),
//                    getSubjectContains(postSearchCondition.kw())
//            );
//
//    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
//}
//
//public Page<PostPageResponse> findRecruitmentAll(Pageable pageable, Long userId,
//        RecruitmentUserStatus status) {
//    List<PostPageResponse> content = queryFactory
//            .selectDistinct(new QPostPageResponse(
//                    post.postId, post.subject, post.category.name,
//                    post.author.name, post.author.profileImg,
//                    comment.countDistinct(), voter.countDistinct(), post.createdAt)
//            )
//            .from(post)
//            .leftJoin(post.category)
//            .leftJoin(post.author)
//            .leftJoin(post._postCommentList, comment)
//            .leftJoin(post._postVoterList, voter)
//            .leftJoin(recruitmentUser)
//            .on(recruitmentUser.siteUser.id.eq(userId).and(recruitmentUser.status.eq(status)))
//            .groupBy(post.postId, post.subject, post.category.name,
//                    post.author.name, post.author.profileImg, post.createdAt)
//            .where(
//                    post.category.name.eq(CategoryName.RECRUITMENT.getValue())
//                            .and(recruitmentUser.siteUser.id.eq(userId))
//                            .and(recruitmentUser.status.eq(status)))
//            .offset(pageable.getOffset())
//            .limit(pageable.getPageSize())
//            .fetch();
//
//    JPAQuery<Long> countQuery = queryFactory.select(post.count())
//            .from(post)
//            .leftJoin(recruitmentUser)
//            .on(recruitmentUser.siteUser.id.eq(userId).and(recruitmentUser.status.eq(status)));
//
//    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
//}
//
//public Optional<PostResponse> findPostResponseById(Long postId, Long siteUserId) {
//    PostResponse postResponse = queryFactory.selectDistinct(
//                    new QPostResponse(post.postId, post.subject, post.content, post.category.id,
//                            post.author.id.eq(siteUserId), post.author.name,
//                            post.author.profileImg,
//                            voter.countDistinct(), voter.siteUser.id.eq(siteUserId), post.createdAt))
//            .from(post)
//            .leftJoin(post.category)
//            .leftJoin(post.author)
//            .leftJoin(post._postVoterList, voter)
//            .leftJoin(recruitmentUser)
//            .on(recruitmentUser.post.postId.eq(postId))
//            .groupBy(post.postId, post.subject, post.content, post.category.id, post.author.id,
//                    post.author.name, post.author.profileImg, voter.siteUser.id, post.createdAt)
//            .where(post.postId.eq(postId))
//            .fetchOne();
//
//    return Optional.ofNullable(postResponse);
//}
//
//	*/
///**
// * 정렬할 필드와 정렬 방식을 OrderSpecifier로 반환합니다.
// *
// * @param postSearchCondition
// * @return {@link OrderSpecifier <?>}
// *//*
//
//	private OrderSpecifier<?> getOrderBy(PostSearchCondition postSearchCondition) {
//		// 기본 정렬 방식 설정
//		Order queryOrder =
//			Order.ASC.toString().equalsIgnoreCase(postSearchCondition.order()) ?
//				Order.ASC : Order.DESC;
//
//		// 정렬 필드를 매핑
//		Map<String, ComparableExpressionBase<?>> fieldMap = Map.of(
//			"commentCount", post._postCommentList.size(),
//			"voter", post._postVoterList.size(),
//			"createdAt", post.createdAt
//		);
//
//		ComparableExpressionBase<?> sortField =
//			StringUtils.hasText(postSearchCondition.sort()) && fieldMap.containsKey(
//				postSearchCondition.sort()) ?
//				fieldMap.get(postSearchCondition.sort()) : post.createdAt;
//
//		return new OrderSpecifier<>(queryOrder, sortField);
//	}
//
//	*/
///**
// * 키워드 조건식을 반환합니다.
// *
// * @param kw
// * @return {@link BooleanExpression}
// *//*
//
//	private BooleanExpression getSubjectContains(String kw) {
//		return StringUtils.hasText(kw) ? post.subject.contains(kw) : null;
//	}
//
//	*/
///**
// * 카테고리 조건식을 반환합니다.
// *
// * @param categoryId
// * @return {@link BooleanExpression}
// *//*
//
//	private BooleanExpression getCategoryIdEq(Long categoryId) {
//		return categoryId != null ? post.category.id.eq(categoryId) : null;
//	}
//
//}
//*/
