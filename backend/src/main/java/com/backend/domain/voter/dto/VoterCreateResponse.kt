package com.backend.domain.voter.dto;

import com.backend.domain.voter.domain.VoterType

/**
 * VoterCreateResponse
 * <p>Voter 생성시 응답 객체 입니다.</p></p>
 * @param targetId 추천 타겟 ID
 * @param voterType {@link VoterType} 추천 타입
 */
data class VoterCreateResponse(
    val targetId: Long,
    val voterType: VoterType)