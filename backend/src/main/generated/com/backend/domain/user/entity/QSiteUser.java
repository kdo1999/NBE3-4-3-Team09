package com.backend.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSiteUser is a Querydsl query type for SiteUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSiteUser extends EntityPathBase<SiteUser> {

    private static final long serialVersionUID = 1937968637L;

    public static final QSiteUser siteUser = new QSiteUser("siteUser");

    public final com.backend.global.baseentity.QBaseEntity _super = new com.backend.global.baseentity.QBaseEntity(this);

    public final ListPath<com.backend.domain.comment.entity.Comment, com.backend.domain.comment.entity.QComment> comments = this.<com.backend.domain.comment.entity.Comment, com.backend.domain.comment.entity.QComment>createList("comments", com.backend.domain.comment.entity.Comment.class, com.backend.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final StringPath job = createString("job");

    public final ListPath<com.backend.domain.jobskill.entity.JobSkill, com.backend.domain.jobskill.entity.QJobSkill> jobSkills = this.<com.backend.domain.jobskill.entity.JobSkill, com.backend.domain.jobskill.entity.QJobSkill>createList("jobSkills", com.backend.domain.jobskill.entity.JobSkill.class, com.backend.domain.jobskill.entity.QJobSkill.class, PathInits.DIRECT2);

    public final StringPath kakaoId = createString("kakaoId");

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final ListPath<com.backend.domain.post.entity.Post, com.backend.domain.post.entity.QPost> posts = this.<com.backend.domain.post.entity.Post, com.backend.domain.post.entity.QPost>createList("posts", com.backend.domain.post.entity.Post.class, com.backend.domain.post.entity.QPost.class, PathInits.DIRECT2);

    public final StringPath profileImg = createString("profileImg");

    public final StringPath userRole = createString("userRole");

    public final ListPath<com.backend.domain.voter.entity.Voter, com.backend.domain.voter.entity.QVoter> voters = this.<com.backend.domain.voter.entity.Voter, com.backend.domain.voter.entity.QVoter>createList("voters", com.backend.domain.voter.entity.Voter.class, com.backend.domain.voter.entity.QVoter.class, PathInits.DIRECT2);

    public QSiteUser(String variable) {
        super(SiteUser.class, forVariable(variable));
    }

    public QSiteUser(Path<? extends SiteUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSiteUser(PathMetadata metadata) {
        super(SiteUser.class, metadata);
    }

}

