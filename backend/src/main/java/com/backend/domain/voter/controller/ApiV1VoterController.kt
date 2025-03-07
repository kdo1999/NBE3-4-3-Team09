package com.backend.domain.voter.controller;

import com.backend.domain.voter.domain.VoterType
import com.backend.domain.voter.dto.VoterCreateRequest
import com.backend.domain.voter.dto.VoterCreateResponse
import com.backend.domain.voter.service.VoterService
import com.backend.global.response.GenericResponse
import com.backend.global.security.custom.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * ApiV1VoterController
 * <p>추천 컨트롤러 입니다.</p>
 *
 * @author Kim Dong O
 */
@RestController
@RequestMapping("/api/v1/voter")
class ApiV1VoterController(private val voterService: VoterService) {

	@PostMapping
	fun create(
		@RequestBody @Validated voterCreateRequest: VoterCreateRequest,
		@AuthenticationPrincipal customUserDetails: CustomUserDetails
	): GenericResponse<VoterCreateResponse> {

		val voterCreateResponse = voterService.save(
			customUserDetails.getSiteUser(),
			voterCreateRequest.targetId,
			voterCreateRequest.voterType
		)

		return GenericResponse.ok(HttpStatus.CREATED.value(), voterCreateResponse);
	}

	@DeleteMapping("/{targetId}")
	fun delete(
		@PathVariable("targetId") targetId: Long,
		@RequestParam("voterType") voterType: String,
		@AuthenticationPrincipal customUserDetails: CustomUserDetails
	): GenericResponse<Void> {

		val voterTypeEnum = VoterType.from(voterType)

		voterService.delete(voterTypeEnum, targetId, customUserDetails.getSiteUser())

		return GenericResponse.ok()
	}
}
