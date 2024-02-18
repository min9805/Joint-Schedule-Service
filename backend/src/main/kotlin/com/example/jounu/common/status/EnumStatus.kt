package com.example.jounu.common.status

enum class Gender(val desc: String) {
    MAN("남"),
    WOMAN("여"),
}

enum class ResultCode(val msg: String) {
    SUCCESS("정상 처리 되었습ㄴ디ㅏ."),
    ERROR("에러가 발생했습니다."),
}

enum class ROLE {
    MEMBER
}