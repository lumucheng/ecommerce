package com.ecommerce.model

data class Response(val isSuccess: Boolean,
                    val message: String? = null) {
    companion object {
        fun ok(message: String): Response {
            return Response(isSuccess = true, message = message)
        }

        fun fail(message: String): Response {
            return Response(isSuccess = false, message = message)
        }
    }
}