package com.backend.domain.user.dto.response

data class KakaoLoginResponse(
    val id: Long,
    val email: String,
    val name: String,
    val profileImg: String
) {
    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    class Builder {
        private var id: Long = 0
        private var email: String = ""
        private var name: String = ""
        private var profileImg: String = ""

        fun id(id: Long) = apply { this.id = id }
        fun email(email: String) = apply { this.email = email }
        fun name(name: String) = apply { this.name = name }
        fun profileImg(profileImg: String) = apply { this.profileImg = profileImg }
        fun build() = KakaoLoginResponse(id, email, name, profileImg)
    }
}
