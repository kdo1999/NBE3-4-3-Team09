package com.backend.domain.jobposting.controller;

import com.backend.domain.jobposting.dto.JobPostingDetailResponse
import com.backend.domain.jobposting.dto.JobPostingPageResponse
import com.backend.domain.jobposting.service.JobPostingService
import com.backend.domain.jobposting.util.JobPostingSearchCondition
import com.backend.global.response.GenericResponse
import com.backend.global.security.custom.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * ApiV1JobPostingController
 * <p>채용 공고 컨트롤러 입니다. <br>
 * 경로: /api/v1/job-posting</p>
 *
 * @author Kim Dong O
 */
@RestController
@RequestMapping("/api/v1/job-posting")
class ApiV1JobPostingController(private val jobPostingService: JobPostingService) {

	/**
	 * 전체 조회 메서드 입니다.
	 *
	 * @param jobPostingSearchCondition
	 * @return {@link GenericResponse<Page<JobPostingPageResponse>>}
	 */
	@GetMapping
	fun findAll(@Valid jobPostingSearchCondition: JobPostingSearchCondition): GenericResponse<Page<JobPostingPageResponse>> {

		val findAll = jobPostingService.findAll(jobPostingSearchCondition)

		return GenericResponse.ok(findAll)
	}

	/**
	 * 상세 조회 메서드 입니다.
	 * @param jobPostingId
	 * @param customUserDetails
	 * @return {@link GenericResponse<JobPostingDetailResponse>}
	 */
	@GetMapping("/{id}")
	fun findDetailId(
		@PathVariable("id") jobPostingId: Long,
		@AuthenticationPrincipal customUserDetails: CustomUserDetails)
	: GenericResponse<JobPostingDetailResponse>{

		val jobPostingDetailResponse = jobPostingService
			.findDetailById(jobPostingId, customUserDetails.siteUser.id!!)

		return GenericResponse.ok(jobPostingDetailResponse)
	}

	/**
	 * 추천 채용 공고 조회 메서드 입니다.
	 *
	 * @return {@link GenericResponse<Page<JobPostingPageResponse>>}
	 */
	@GetMapping("/voter")
	fun findAllVoter(
		@Valid jobPostingSearchCondition: JobPostingSearchCondition,
		@AuthenticationPrincipal customUserDetails: CustomUserDetails
	): GenericResponse<Page<JobPostingPageResponse>> {

		val findAllVoter = jobPostingService.findAllVoter(
			jobPostingSearchCondition, customUserDetails.siteUser
		)

		return GenericResponse.ok(findAllVoter)
	}
}
