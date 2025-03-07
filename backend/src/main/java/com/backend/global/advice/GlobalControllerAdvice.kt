package com.backend.global.advice;

import com.backend.global.exception.GlobalErrorCode;
import com.backend.global.exception.GlobalException;
import com.backend.global.response.ErrorDetail;
import com.backend.global.response.GenericResponse;
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalControllerAdvice
 * <p>공통 예외 처리를 담당하는 클래스 입니다.</p>
 *
 * @author Kim Dong O
 */
private val log = KotlinLogging.logger {}

@RestControllerAdvice
@Slf4j
class GlobalControllerAdvice {

	/**
	 * GlobalException 처리 핸들러 입니다.
	 *
	 * @param globalException {@link GlobalException}
	 * @return {@link ResponseEntity<GenericResponse>}
	 */
	@ExceptionHandler(GlobalException::class)
	fun handlerGlobalException(globalException: GlobalException)
	: ResponseEntity<GenericResponse<Void>> {
		log.error("handlerGlobalException: ", globalException);

		val genericResponse: GenericResponse<Void> = GenericResponse.fail(
			globalException.getGlobalErrorCode().getCode(),
			globalException.message
		)

		return ResponseEntity.status(globalException.getStatus().value())
			.body(genericResponse);
	}

	/**
	 * Validation 예외 처리 핸들러 입니다.
	 *
	 * @param ex      Exception
	 * @param request HttpServletRequest
	 * @return {@link ResponseEntity<GenericResponse<List< ErrorDetail>}
	 */
	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun handlerMethodArgumentNotValidException(
		ex: MethodArgumentNotValidException,
		request: HttpServletRequest
	): ResponseEntity<GenericResponse<List<ErrorDetail>>> {
		log.error("handlerMethodArgumentNotValidException: ", ex);

		val bindingResult = ex.bindingResult
		val errors: MutableList<ErrorDetail> = mutableListOf()
		val globalErrorCode = GlobalErrorCode.NOT_VALID

		//Field 에러 처리
		for (error in bindingResult.getFieldErrors()) {
			val customError = ErrorDetail.of(error.getField(), error.getDefaultMessage());

			errors.add(customError);
		}

		//Object 에러 처리
		for (globalError in bindingResult.getGlobalErrors()) {
			val customError = ErrorDetail.of(
				globalError.getObjectName(),
				globalError.getDefaultMessage()
			);

			errors.add(customError);
		}

		val genericResponse: GenericResponse<List<ErrorDetail>> = GenericResponse.fail(
			globalErrorCode.getCode(),
			errors,
			globalErrorCode.getMessage()
		);

		return ResponseEntity.status(globalErrorCode.getHttpStatus().value())
			.body(genericResponse);
	}
}
