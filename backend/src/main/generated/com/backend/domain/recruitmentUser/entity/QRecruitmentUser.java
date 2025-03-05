package com.backend.domain.recruitmentUser.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitmentUser is a Querydsl query type for RecruitmentUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitmentUser extends EntityPathBase<RecruitmentUser> {

    private static final long serialVersionUID = 1837066118L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitmentUser recruitmentUser = new QRecruitmentUser("recruitmentUser");

    public final com.backend.global.baseentity.QBaseEntity _super = new com.backend.global.baseentity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> modifiedAt = _super.modifiedAt;

    public final com.backend.domain.post.entity.QRecruitmentPost post;

    public final com.backend.domain.user.entity.QSiteUser siteUser;

    public final EnumPath<RecruitmentUserStatus> status = createEnum("status", RecruitmentUserStatus.class);

    public QRecruitmentUser(String variable) {
        this(RecruitmentUser.class, forVariable(variable), INITS);
    }

    public QRecruitmentUser(Path<? extends RecruitmentUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitmentUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitmentUser(PathMetadata metadata, PathInits inits) {
        this(RecruitmentUser.class, metadata, inits);
    }

    public QRecruitmentUser(Class<? extends RecruitmentUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.backend.domain.post.entity.QRecruitmentPost(forProperty("post"), inits.get("post")) : null;
        this.siteUser = inits.isInitialized("siteUser") ? new com.backend.domain.user.entity.QSiteUser(forProperty("siteUser")) : null;
    }

}

