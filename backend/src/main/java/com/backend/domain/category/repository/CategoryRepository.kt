package com.backend.domain.category.repository

import com.backend.domain.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CategoryRepository : JpaRepository<Category, Long> {

    // 중복 검사
    fun existsByName(name: String): Boolean

    /*
    nullable 타입으로 인한 컴파일 에러가 생겨서 임시로 Optional 변경 후,
    Post 코틀린 변환 완료되면 nullable로 바꾸겠습니다.
     */
    @Query("SELECT c FROM Category c WHERE c.name = :categoryName")
    fun findByName(@Param("categoryName")categoryName: String): Category?
}
