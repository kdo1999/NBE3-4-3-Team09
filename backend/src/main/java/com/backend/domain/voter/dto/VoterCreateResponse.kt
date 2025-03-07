package com.backend.domain.voter.dto;

import com.backend.domain.voter.domain.VoterType

/**
 * VoterCreateResponse
 *
 * Voter 생성시 응답 객체 입니다.
 *
 * @param targetId 추천 타겟 ID
 * @param voterType [VoterType] 추천 타입
 */
data class VoterCreateResponse(
    val targetId: Long,
    val voterType: VoterType)