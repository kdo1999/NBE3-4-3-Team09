package com.backend.domain.voter.repository;

import com.backend.domain.voter.domain.VoterType
import com.backend.domain.voter.entity.Voter
import org.springframework.stereotype.Repository

@Repository
class VoterRepositoryImpl(private val voterJpaRepository: VoterJpaRepository): VoterRepository {

	override fun save(voter: Voter): Voter = voterJpaRepository.save(voter)

	override fun existsByJobPostingId(
		siteUserId: Long,
		jobPostingId: Long,
		voterType: VoterType
	): Boolean = voterJpaRepository.existsBySiteUserIdAndJobPostingIdAndVoterType(
		siteUserId,
		jobPostingId,
		voterType
	)

	override fun existsByPostId(
		siteUserId: Long,
		postId: Long,
		voterType: VoterType
	): Boolean = voterJpaRepository.existsBySiteUserIdAndPostPostIdAndVoterType(
		siteUserId,
		postId,
		voterType
	)

	override fun deleteByJobPostingId(jobPostingId: Long) =
		voterJpaRepository.deleteByJobPostingId(jobPostingId)

	override fun deleteByPostId(postId: Long) =
		voterJpaRepository.deleteByPostPostId(postId)
}
