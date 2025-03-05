package com.backend.domain.user.service

import com.backend.domain.jobskill.repository.JobSkillRepository
import com.backend.domain.user.dto.request.UserModifyProfileRequest
import com.backend.domain.user.entity.SiteUser
import com.backend.domain.user.repository.UserRepository
import com.backend.global.exception.GlobalErrorCode
import com.backend.global.exception.GlobalException
import com.backend.global.security.custom.CustomUserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val jobSkillRepository: JobSkillRepository
) {

    /**
     * 유저 id를 찾기 위한 메서드 입니다.
     *
     * @param id
     * @return [SiteUser]
     */
    fun getUserById(id: Long): SiteUser {
        return userRepository.findById(id).orElseThrow { GlobalException(GlobalErrorCode.USER_NOT_FOUND) }
    }

    /**
     * 유저 검증을 위한 메서드 입니다.
     *
     * @param id
     * @param customUserDetails
     */
    fun isValidUser(id: Long, customUserDetails: CustomUserDetails) {
        if (id != customUserDetails.id) {
            throw GlobalException(GlobalErrorCode.UNAUTHORIZATION_USER)
        }
    }

    /**
     * 유저 정보를 가져 오기 위한 메서드 입니다.
     *
     * @param id
     * @param customUserDetails
     * @return [SiteUser]
     */
    fun getUser(id: Long, customUserDetails: CustomUserDetails): SiteUser {
        isValidUser(id, customUserDetails)

        val user = getUserById(id)

        return user
    }

    /**
     * 유저 정보를 수정 하기 위한 메서드 입니다.
     *
     * @param id
     * @param customUserDetails
     * @param req
     */
    @Transactional
    fun modifyUser(id: Long, customUserDetails: CustomUserDetails, req: UserModifyProfileRequest) {
        isValidUser(id, customUserDetails)

        val user = getUserById(id)

        // TODO
        // 현재는 jobskill의  name이 private로 설정되어 있어서 접근이 불가능함
        // 다음 작업에서 처리 예정
//        if (req.jobSkills != null) {
//            user.jobSkills.clear()
//
//            req.jobSkills!!.forEach(Consumer { jobSkillReq: JobSkillRequest ->
//                val jobSkill = jobSkillRepository!!.findByName(jobSkillReq.name)
//                    .orElseThrow { GlobalException(GlobalErrorCode.INVALID_JOB_SKILL) }
//                user.jobSkills.add(jobSkill)
//            })
//        }

        user.modifyProfile(req.introduction, req.job)
    }
}
