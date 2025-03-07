package com.backend.global.exception;

import org.springframework.http.HttpStatus

/**
 * GlobalException
 * <p>공통으로 사용할 예외 클래스 입니다. <br><br>
 * 사용 예시: </p>
 * {@code
 * throw new GlobalException(GlobalErrorCode.NOT_VALID);
 * }
 * @author Kim Dong O
 */
class GlobalException(val globalErrorCode: GlobalErrorCode)
	:RuntimeException(globalErrorCode.message) {

	/**
     * 응답 HttpStatus를 반환하는 메서드 입니다.
     * @return {@link HttpStatus}
     */
    fun getStatus(): HttpStatus = globalErrorCode.httpStatus
}
