package com.backend.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitmentPost is a Querydsl query type for RecruitmentPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitmentPost extends EntityPathBase<RecruitmentPost> {

    private static final long serialVersionUID = 1342274364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitmentPost recruitmentPost = new QRecruitmentPost("recruitmentPost");

    public final QPost _super;

    // inherited
    public final com.backend.domain.user.entity.QSiteUser author;

    // inherited
    public final com.backend.domain.category.entity.QCategory category;

    //inherited
    public final ListPath<com.backend.domain.comment.entity.Comment, com.backend.domain.comment.entity.QComment> commentList;

    //inherited
    public final StringPath content;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdAt;

    public final com.backend.domain.jobposting.entity.QJobPosting jobPosting;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> modifiedAt;

    public final NumberPath<Integer> numOfApplicants = createNumber("numOfApplicants", Integer.class);

    //inherited
    public final NumberPath<Long> postId;

    public final DateTimePath<java.time.ZonedDateTime> recruitmentClosingDate = createDateTime("recruitmentClosingDate", java.time.ZonedDateTime.class);

    public final EnumPath<RecruitmentStatus> recruitmentStatus = createEnum("recruitmentStatus", RecruitmentStatus.class);

    //inherited
    public final StringPath subject;

    //inherited
    public final ListPath<com.backend.domain.voter.entity.Voter, com.backend.domain.voter.entity.QVoter> voterList;

    public QRecruitmentPost(String variable) {
        this(RecruitmentPost.class, forVariable(variable), INITS);
    }

    public QRecruitmentPost(Path<? extends RecruitmentPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitmentPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitmentPost(PathMetadata metadata, PathInits inits) {
        this(RecruitmentPost.class, metadata, inits);
    }

    public QRecruitmentPost(Class<? extends RecruitmentPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPost(type, metadata, inits);
        this.author = _super.author;
        this.category = _super.category;
        this.commentList = _super.commentList;
        this.content = _super.content;
        this.createdAt = _super.createdAt;
        this.jobPosting = inits.isInitialized("jobPosting") ? new com.backend.domain.jobposting.entity.QJobPosting(forProperty("jobPosting"), inits.get("jobPosting")) : null;
        this.modifiedAt = _super.modifiedAt;
        this.postId = _super.postId;
        this.subject = _super.subject;
        this.voterList = _super.voterList;
    }

}

