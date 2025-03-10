package com.backend.domain.post.conveter;

import com.backend.domain.category.entity.Category;
import com.backend.domain.jobposting.entity.JobPosting;
import com.backend.domain.jobposting.repository.JobPostingRepository;
import com.backend.domain.post.dto.FreePostRequest;
import com.backend.domain.post.dto.PostCreateResponse;
import com.backend.domain.post.dto.PostResponse;
import com.backend.domain.post.dto.RecruitmentPostRequest;
import com.backend.domain.post.dto.RecruitmentPostResponse;
import com.backend.domain.post.entity.Post;
import com.backend.domain.post.entity.RecruitmentPost;
import com.backend.domain.user.entity.SiteUser;

public class PostConverter {

    // 게시글 저장할 때
    public static Post createPost(FreePostRequest freePostRequest, SiteUser siteUser,
            Category category) {
        return new Post(freePostRequest.getSubject(), freePostRequest.getContent(), category,
                siteUser);
    }

    // 모집 게시글 저장할 때
    public static RecruitmentPost createPost(RecruitmentPostRequest recruitmentPostRequest,
            Category category, SiteUser author, JobPosting jobPosting) {
        return new RecruitmentPost(recruitmentPostRequest.getSubject(),
                recruitmentPostRequest.getContent(), category, author, jobPosting);
    }

    // 자유 게시글 응답 변환
    public static PostResponse toPostResponse(Post post, boolean isAuthor, Long currentUserId) {
        return new PostResponse(post.getPostId(), post.getSubject(), post.getContent(),
                post.getCategory().getId(), isAuthor, post.getAuthor().getName(),
                post.getAuthor().getProfileImg(), post.getPostVoterList().size(),
                post.getPostVoterList().stream()
                        .anyMatch(voter -> voter.getSiteUser().getId().equals(currentUserId)),
                post.getCreatedAt());
    }

    // 모집 게시글 응답 변환
    public static RecruitmentPostResponse toPostResponse(RecruitmentPost post, boolean isAuthor, int currentAcceptedCount,
            SiteUser currentUser) {
        return new RecruitmentPostResponse(
                post.getPostId(),
                post.getSubject(),
                post.getContent(),
                post.getCategory().getId(),
                isAuthor,
                post.getAuthor().getName(),
                post.getAuthor().getProfileImg(),
                post.getPostVoterList().size(), // voterCount 추가 (long 타입)
                post.getPostVoterList().contains(currentUser), // isVoter 추가 (boolean 타입)
                post.getCreatedAt(),
                post.getJobPosting().getId(), // jobPostingId 추가 (long 타입)
                post.getNumOfApplicants() != null ? post.getNumOfApplicants() : 0,
                // numOfApplicants 추가 (int 타입)
                post.getRecruitmentStatus(), // recruitmentStatus 추가 (RecruitmentStatus 타입)
                currentAcceptedCount); // currentAcceptedCount 추가 (int 타입)
    }

    public static PostCreateResponse toPostCreateResponse(Long postId, Long categoryId) {
        return new PostCreateResponse(postId, categoryId);
    }

}
