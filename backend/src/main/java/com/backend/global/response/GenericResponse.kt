package com.backend.global.response;

import lombok.Getter
import org.springframework.http.HttpStatus
import java.time.ZonedDateTime

/**
 * GenericResponse
 * <p>공통 응답 객체 입니다.</p>
 *
 * @author Kim Dong O
 */
class GenericResponse<T> private constructor(
    val timestamp: ZonedDateTime,
    val success: Boolean,
    val code: Int,
    val data: T?,
    val message: String?
) {
	companion object {
		/**
		 * 요청이 성공하고 code, data, message 있을 때
		 *
		 * @param code    응답 코드 값
		 * @param data    반환 데이터
		 * @param message 반환 메세지
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> ok(code: Int, data: T?, message: String?): GenericResponse<T> =
			GenericResponse(ZonedDateTime.now(), true, code, data, message)

		/**
		 * 요청이 성공하고 code, data 있을 때
		 *
		 * @param code    응답 코드 값
		 * @param data    반환 데이터
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> ok(code: Int, data: T?): GenericResponse<T> = ok(code, data, null)

		/**
		 * 요청이 성공하고 code, message 있을 때
		 *
		 * @param code    응답 코드 값
		 * @param message 반환 메세지
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun  <T> ok(code: Int, message: String?): GenericResponse<T> = ok(code, null,  message)

		/**
		 * 요청이 성공하고 code, data, message 있을 때
		 *
		 * @param code    응답 코드 값
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> ok(code: Int): GenericResponse<T> = ok(code, null)

		/**
		 * 요청이 성공하고 data, message 있을 때
		 * <p>code 기본 값 : 200</p>
		 *
		 * @param data    반환 데이터
		 * @param message 반환 메세지
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> ok(data: T?, message: String?): GenericResponse<T> =
			ok(HttpStatus.OK.value(), data, message)

		/**
		 * 요청이 성공하고 message 있을 때
		 * <p>code 기본 값 : 200</p>
		 *
		 * @param message 반환 메세지
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> ok(message: String?): GenericResponse<T> = ok(null, message)

		/**
		 * 요청이 성공하고 data 있을 때
		 * <p>code 기본 값 : 200</p>
		 *
		 * @param data    반환 데이터
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> ok(data: T?): GenericResponse<T> = ok(HttpStatus.OK.value(), data)

		/**
		 * 요청이 성공했을 때
		 * <p>code 기본 값 : 200</p>
		 *
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> ok(): GenericResponse<T> = ok(null)

		/**
		 * 요청이 실패하고 code, data, message 있을 때
		 *
		 * @param code    응답 코드 값
		 * @param data    반환 데이터
		 * @param message 반환 메세지
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> fail(code: Int, data: T?, message: String?): GenericResponse<T> =
			GenericResponse(ZonedDateTime.now(), false, code, data, message)

		/**
		 * 요청이 실패하고 code, data 있을 때
		 *
		 * @param code    응답 코드 값
		 * @param data    반환 데이터
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> fail(code: Int, data: T?): GenericResponse<T> =
			fail(code, data, null)

		/**
		 * 요청이 실패하고 code, message 있을 때
		 *
		 * @param code    응답 코드 값
		 * @param message 반환 메세지
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> fail(code: Int, message: String?): GenericResponse<T> = fail(code, null, message)

		/**
		 * 요청이 실패하고 code 있을 때
		 *
		 * @param code    응답 코드 값
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> fail(code: Int): GenericResponse<T> = fail(code, null)

		/**
		 * 요청이 실패하고 data, message 있을 때
		 * <p>code 기본 값 : 400</p>
		 *
		 * @param data    반환 데이터
		 * @param message 반환 메세지
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> fail(data: T?, message: String?): GenericResponse<T> =
			fail(HttpStatus.BAD_REQUEST.value(), data, message)

		/**
		 * 요청이 실패하고 message 있을 때
		 * <p>code 기본 값 : 400</p>
		 *
		 * @param message 반환 메세지
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> fail(message: String): GenericResponse<T> = fail(HttpStatus.BAD_REQUEST.value(), message)

		/**
		 * 요청이 실패하고 data 있을 때
		 * <p>code 기본 값 : 400</p>
		 *
		 * @param data    반환 데이터
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> fail(data: T?): GenericResponse<T> = fail(HttpStatus.BAD_REQUEST.value(), data)

		/**
		 * 요청이 실패했을 때
		 * <p>code 기본 값 : 400</p>
		 *
		 * @return {@link GenericResponse<T>}
		 */
		@JvmStatic
		fun <T> fail(): GenericResponse<T> = fail(null)
	}
}
