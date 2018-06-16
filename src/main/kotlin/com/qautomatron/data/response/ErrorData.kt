package com.qautomatron.data.response

enum class ErrorData(message: ResponseMessage) {
    Unauthorized(ResponseMessage(message = "Unauthorized", type = "Error"))
}