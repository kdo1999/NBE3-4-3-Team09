package com.backend.domain.voter.repository;

import com.backend.domain.voter.domain.VoterType;
import com.backend.domain.voter.entity.Voter;

/**
 * VoterRepository
 * <p>Voter 리포지토리 입니다.</p>
 *
 * @author Kim Dong O
 */
interface VoterRepository {

	/**
	 * @param voter [Voter] 객체
	 * @return [Voter]
	 * @implSpec [Voter] 저장 메서드 입니다.
	 */
	fun save(voter: Voter): Voter

	/**
	 * @param siteUserId   siteUserId
	 * @param jobPostingId jobPostingId
	 * @param voterType    voterType {@link VoterType}
	 * @return [Boolean] 데이터 존재할 시 true, 존재하지 않을 때 false
	 * @implSpec [Voter] exists 메서드 입니다.
	 */
	fun existsByJobPostingId(siteUserId: Long, jobPostingId: Long, voterType: VoterType): Boolean


/**
	 * @param siteUserId siteUserId
	 * @param postId     jobPostingId
	 * @param voterType  voterType [VoterType]
	 * @return [Boolean] 데이터 존재할 시 true, 존재하지 않을 때 false
	 * @implSpec [Voter] exists 메서드 입니다.
	 */

	fun existsByPostId(siteUserId: Long, postId: Long, voterType: VoterType): Boolean

	fun deleteByJobPostingId(jobPostingId: Long)

	fun deleteByPostId(postId: Long)
}
