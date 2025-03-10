package com.backend.domain.voter.repository;

import com.backend.domain.voter.domain.VoterType;
import com.backend.domain.voter.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

interface VoterJpaRepository: JpaRepository<Voter, Long> {

	fun existsBySiteUserIdAndJobPostingIdAndVoterType(
		siteUserId: Long,
		jobPostingId: Long,
		voterType: VoterType
	): Boolean

	fun existsBySiteUserIdAndPostPostIdAndVoterType(
		siteUserId: Long,
		postId: Long,
		voterType: VoterType
	): Boolean

	fun deleteByJobPostingId(jobPostingId: Long)

	fun deleteByPostPostId(postId: Long)
}