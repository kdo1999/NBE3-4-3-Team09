package com.backend.domain.voter.domain

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.stream.Stream

enum class VoterType {
    JOB_POSTING, POST;

    companion object {
        @JvmStatic
		@JsonCreator
        fun from(param: String?): VoterType? {
            return Stream.of(*entries.toTypedArray())
                .filter { voterType: VoterType? -> voterType.toString().equals(param, ignoreCase = true) }
                .findFirst()
                .orElse(null)
        }
    }
}
