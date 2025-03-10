package com.backend.domain.post.repository.post

import com.backend.domain.category.domain.CategoryName
import com.backend.domain.comment.entity.QComment.comment
import com.backend.domain.post.dto.PostPageResponse
import com.backend.domain.post.dto.PostResponse
import com.backend.domain.post.dto.QPostPageResponse
import com.backend.domain.post.dto.QPostResponse
import com.backend.domain.post.entity.QPost.post
import com.backend.domain.post.util.PostSearchCondition
import com.backend.domain.recruitmentUser.entity.QRecruitmentUser.recruitmentUser
import com.backend.domain.recruitmentUser.entity.RecruitmentUserStatus
import com.backend.domain.voter.entity.QVoter.voter
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.ComparableExpressionBase
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class PostQueryRepository(private val queryFactory: JPAQueryFactory) {

    // 게시글 조회 리포지토리
    fun findAll(postSearchCondition: PostSearchCondition, pageable: Pageable): Page<PostPageResponse> {
        val content = queryFactory
            .selectDistinct(
                QPostPageResponse(
                    post.postId, post.subject, post.category.name,
                    post.author.name, post.author.profileImg,
                    comment.countDistinct(), voter.countDistinct(), post.createdAt
                )
            )
            .from(post)
            .leftJoin(post.category)
            .leftJoin(post.author)
            .leftJoin(post._postCommentList, comment)
            .leftJoin(post._postVoterList, voter)
            .groupBy(
                post.postId, post.subject, post.category.name,
                post.author.name, post.author.profileImg, post.createdAt
            )
            .where(
                getCategoryIdEq(postSearchCondition.categoryId),
                getSubjectContains(postSearchCondition.kw)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = queryFactory.select(post.count())
            .from(post)
            .where(
                getCategoryIdEq(postSearchCondition.categoryId),
                getSubjectContains(postSearchCondition.kw)
            )

        return PageableExecutionUtils.getPage(content, pageable) { countQuery.fetchOne()!! }
    }

    fun findRecruitmentAll(pageable: Pageable, userId: Long, status: RecruitmentUserStatus): Page<PostPageResponse> {
        val content = queryFactory
            .selectDistinct(
                QPostPageResponse(
                    post.postId, post.subject, post.category.name,
                    post.author.name, post.author.profileImg,
                    comment.countDistinct(), voter.countDistinct(), post.createdAt
                )
            )
            .from(post)
            .leftJoin(post.category)
            .leftJoin(post.author)
            .leftJoin(post._postCommentList, comment)
            .leftJoin(post._postVoterList, voter)
            .leftJoin(recruitmentUser)
            .on(recruitmentUser.siteUser.id.eq(userId).and(recruitmentUser.status.eq(status)))
            .groupBy(
                post.postId, post.subject, post.category.name,
                post.author.name, post.author.profileImg, post.createdAt
            )
            .where(
                post.category.name.eq(CategoryName.RECRUITMENT.value)
                    .and(recruitmentUser.siteUser.id.eq(userId))
                    .and(recruitmentUser.status.eq(status))
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = queryFactory.select(post.count())
            .from(post)
            .leftJoin(recruitmentUser)
            .on(recruitmentUser.siteUser.id.eq(userId).and(recruitmentUser.status.eq(status)))

        return PageableExecutionUtils.getPage(content, pageable) { countQuery.fetchOne()!! }
    }

    fun findPostResponseById(postId: Long, siteUserId: Long): Optional<PostResponse> {
        val postResponse = queryFactory.selectDistinct(
            QPostResponse(
                post.postId, post.subject, post.content, post.category.id,
                post.author.id.eq(siteUserId), post.author.name,
                post.author.profileImg,
                voter.countDistinct(), voter.siteUser.id.eq(siteUserId), post.createdAt
            )
        )
            .from(post)
            .leftJoin(post.category)
            .leftJoin(post.author)
            .leftJoin(post._postVoterList, voter)
            .leftJoin(recruitmentUser)
            .on(recruitmentUser.post.postId.eq(postId))
            .groupBy(
                post.postId, post.subject, post.content, post.category.id, post.author.id,
                post.author.name, post.author.profileImg, voter.siteUser.id, post.createdAt
            )
            .where(post.postId.eq(postId))
            .fetchOne()

        return Optional.ofNullable(postResponse)
    }

    /**
     * 정렬할 필드와 정렬 방식을 OrderSpecifier로 반환합니다.
     */
    private fun getOrderBy(postSearchCondition: PostSearchCondition): OrderSpecifier<*> {
        val queryOrder = if (Order.ASC.toString().equals(postSearchCondition.order, ignoreCase = true)) {
            Order.ASC
        } else {
            Order.DESC
        }

        val fieldMap: Map<String, ComparableExpressionBase<*>> = mapOf(
            "commentCount" to post._postCommentList.size(),
            "voter" to post._postVoterList.size(),
            "createdAt" to post.createdAt
        )

        val sortField = if (!postSearchCondition.sort.isNullOrBlank() && fieldMap.containsKey(postSearchCondition.sort)) {
            fieldMap[postSearchCondition.sort]
        } else {
            post.createdAt
        }

        return OrderSpecifier(queryOrder, sortField!!)
    }

    /**
     * 키워드 조건식을 반환합니다.
     */
    private fun getSubjectContains(kw: String?): BooleanExpression? {
        return kw?.let { post.subject.contains(it) }
    }

    /**
     * 카테고리 조건식을 반환합니다.
     */
    private fun getCategoryIdEq(categoryId: Long?): BooleanExpression? {
        return categoryId?.let { post.category.id.eq(it) }
    }
}