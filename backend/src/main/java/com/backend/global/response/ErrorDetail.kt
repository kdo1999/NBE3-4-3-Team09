package com.backend.global.response;

/**
 * ErrorDetail
 * <p>@NotNull등과 같은 필드 에러 발생시 처리할 클래스</p>
 *
 * @param field
 * @param reason
 * @author Kim Dong O
 */
class ErrorDetail(
	val field: String,
	val reason: String?
) {

	companion object {
		/**
		 * ErrorDetail 생성 팩토리 메서드
		 *
		 * @param field  예외가 발생한 필드 이름
		 * @param reason 예외가 발생한 이유
		 * @return {@link ErrorDetail}
		 */
		fun of(field: String, reason: String?): ErrorDetail
		{
			return ErrorDetail (field, reason)
		}
	}
}
