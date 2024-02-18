package com.example.jounu.common.dto

import com.example.jounu.common.status.ResultCode

data class BaseResponse<T>(
    val resultCode: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message: String = ResultCode.SUCCESS.msg,
) {
}