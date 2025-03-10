package com.backend.domain.category.repository

import com.backend.domain.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CategoryRepository : JpaRepository<Category, Long> {

    // 중복 검사
    fun existsByName(name: String): Boolean

    /*
    nullable 타입으로 인한 컴파일 에러가 생겨서 임시로 Optional 변경 후,
    Post 코틀린 변환 완료되면 nullable로 바꾸겠습니다.
     */
    fun findByName(categoryName: String): Optional<Category>
}
