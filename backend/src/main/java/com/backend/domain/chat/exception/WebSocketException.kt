package com.backend.domain.chat.exception

import com.backend.global.exception.GlobalErrorCode

class WebSocketExceptionKt(
    override val message: String
) : RuntimeException(message) {

    companion object {
        val ERROR_CODE: GlobalErrorCode = GlobalErrorCode.EXCEPTION_IN_WEBSOCKET
        const val MESSAGE_KEY: String = "exception.websocket"
    }
}
