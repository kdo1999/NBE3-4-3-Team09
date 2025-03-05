package com.backend.domain.category.entity;

import com.backend.domain.category.entity.QCategory.category
import com.backend.domain.post.entity.Post;
import com.backend.global.baseentity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable_.id

@Entity
open class Category protected constructor() : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(nullable = false, length = 25, unique = true)
    open var name: String = ""

    @OneToMany(mappedBy = "category", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    open var posts: MutableList<Post> = mutableListOf() // 게시글 리스트

    constructor(name: String) : this() {
        this.name = name
    }

    // equals() 재정의: id와 name을 기준으로 비교
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Category

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    // hashcode() 재정의
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        return result
    }

    // 카테고리 더티 체킹
    open fun updateName(name: String) {
        this.name = name
    }
}
