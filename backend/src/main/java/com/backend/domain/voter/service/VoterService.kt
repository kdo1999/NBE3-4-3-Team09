package com.backend.domain.voter.service;

import com.backend.domain.jobposting.entity.JobPosting
import com.backend.domain.post.entity.Post
import com.backend.domain.user.entity.SiteUser
import com.backend.domain.voter.domain.VoterType
import com.backend.domain.voter.dto.VoterCreateResponse
import com.backend.domain.voter.entity.Voter
import com.backend.domain.voter.repository.VoterRepository
import com.backend.global.exception.GlobalErrorCode
import com.backend.global.exception.GlobalException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * VoterService
 * <p>추천 서비스 입니다.</p>
 *
 * @author Kim Dong O
 */
@Service
class VoterService(
	private val voterRepository: VoterRepository,
) {


	/**
	 * 추천 저장 메서드 입니다.
	 *
	 * @param siteUser  로그인 유저
	 * @param targetId  추천 타겟 ID
	 * @param voterType [VoterType] 추천 타입
	 * @return [VoterCreateResponse]
	 * @throws [GlobalException] 이미 추천 저장이 되어 있을 때 또는 지원하지 않는 VoterType 일 때 발생
	 */
	@Transactional
	fun save(siteUser: SiteUser, targetId: Long, voterType: VoterType): VoterCreateResponse {
		val result = existsCheck(siteUser.id!!, targetId, voterType)

		if (result) throw GlobalException(GlobalErrorCode.VOTER_ALREADY)

		when (voterType) {
			VoterType.JOB_POSTING -> {

				val jobPosting = JobPosting(targetId)

				val saveVoter = Voter(jobPosting, siteUser, voterType)

				voterRepository.save(saveVoter)
			}
			VoterType.POST -> {
				val post = Post(targetId)

				val saveVoter = Voter(post, siteUser, voterType);

				voterRepository.save(saveVoter);
			}
		}

		return VoterCreateResponse(targetId, voterType);
	}

	/**
	 * 추천 삭제 메서드 입니다.
	 *
	 * @param voterType 추천 타입
	 * @param targetId  타겟 ID
	 * @param siteUser  로그인한 회원
	 */
	@Transactional
	fun delete(voterType: VoterType?, targetId: Long, siteUser: SiteUser) {
		voterType ?: throw  GlobalException(GlobalErrorCode.NOT_SUPPORT_TYPE)

		val voterExists = existsCheck(siteUser.id!!, targetId, voterType);

		//데이터가 없을 때
		if (!voterExists) throw GlobalException(GlobalErrorCode.VOTER_NOT_FOUND)

		when (voterType) {
			VoterType.JOB_POSTING -> voterRepository.deleteByJobPostingId(targetId);
			VoterType.POST -> voterRepository.deleteByPostId(targetId);
		}
	}

	/**
	 * 특정 targetId에 추천을 눌렀는지 체크합니다.
	 * <p>
	 * 주어진 targetId에 추천을 눌렀는지 체크합니다.
	 * <br> 존재하지 않을 경우 예외를 발생시킵니다.
	 * </p>
	 *
	 * @param siteUserId siteUserId
	 * @param targetId   targetId
	 * @param voterType  검사할 타입
	 */
	private fun existsCheck(siteUserId: Long, targetId: Long, voterType: VoterType): Boolean {
		var result = false;

		when (voterType) {
			VoterType.JOB_POSTING -> result = voterRepository.
				existsByJobPostingId(siteUserId, targetId, voterType);
			VoterType.POST -> result = voterRepository
				.existsByPostId(siteUserId, targetId, voterType);
		}

		return result;
	}
}
