package com.backend.domain.voter.dto;

import com.backend.domain.voter.domain.VoterType
import jakarta.validation.constraints.Min

/**
 * VoterCreateRequest
 * <p>추천 등록시 사용할 요청 객체 입니다.</p>
 *
 * @param targetId 타겟 ID
 * @param voterType 추천 타입 [VoterType]
 * @author Kim Dong O
 */
data class VoterCreateRequest(
	@field:Min(1, message = "타겟 ID는 필수 입니다.")
	val targetId: Long,
	val voterType: VoterType)