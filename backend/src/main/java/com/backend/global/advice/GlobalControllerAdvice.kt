package com.backend.global.advice;

import com.backend.global.exception.GlobalErrorCode
import com.backend.global.exception.GlobalException
import com.backend.global.response.ErrorDetail
import com.backend.global.response.GenericResponse
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

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
     * @param globalException [GlobalException]
     * @return [ResponseEntity]
     */
    @ExceptionHandler(GlobalException::class)
    fun handlerGlobalException(globalException: GlobalException)
            : ResponseEntity<GenericResponse<Void>> {
        log.error("handlerGlobalException: ", globalException);

        val genericResponse: GenericResponse<Void> = GenericResponse.fail(
            globalException.globalErrorCode.code,
            globalException.message
        )

        return ResponseEntity.status(globalException.getStatus().value())
            .body(genericResponse);
    }

    /**
     * Validation 예외 처리 핸들러 입니다.
     *
     * @param ex      [MethodArgumentNotValidException]
     * @param request [HttpServletRequest]
     * @return [ResponseEntity]
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
                globalError.objectName,
                globalError.defaultMessage
            );

            errors.add(customError);
        }

        val genericResponse: GenericResponse<List<ErrorDetail>> = GenericResponse.fail(
            globalErrorCode.code,
            errors,
            globalErrorCode.message
        );

        return ResponseEntity.status(globalErrorCode.httpStatus.value())
            .body(genericResponse);
    }

    /**
     * Kotlin null을 허용하지 않는 객체에 null이 들어올 경우 예외를 처리합니다.
     *
     * @param ex      [HttpMessageNotReadableException]
     * @param request HttpServletRequest
     * @return [ResponseEntity]
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handlerHttpMessageNotReadableException(ex: HttpMessageNotReadableException)
            : ResponseEntity<GenericResponse<List<ErrorDetail>>> {
        log.error("[HttpMessageNotReadableException] ${ex.message}")

        val errors: List<ErrorDetail> = (ex.cause as? MismatchedInputException)
            ?.path
            ?.mapNotNull { field ->
                field.fieldName?.let { ErrorDetail.of(it, "유효하지 않은 값 입니다.") }
            }
            ?: listOf(ErrorDetail.of("unknown", "유효하지 않은 요청입니다."))

        val genericResponse = GenericResponse.fail(
            GlobalErrorCode.NOT_VALID.code,
            errors,
            GlobalErrorCode.NOT_VALID.message
        )

        return ResponseEntity.status(GlobalErrorCode.NOT_VALID.httpStatus.value())
            .body(genericResponse)
    }

}
