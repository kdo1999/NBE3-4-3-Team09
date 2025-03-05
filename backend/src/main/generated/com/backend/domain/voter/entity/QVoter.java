package com.backend.domain.voter.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoter is a Querydsl query type for Voter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoter extends EntityPathBase<Voter> {

    private static final long serialVersionUID = 595639206L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVoter voter = new QVoter("voter");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.backend.domain.jobposting.entity.QJobPosting jobPosting;

    public final com.backend.domain.post.entity.QPost post;

    public final com.backend.domain.user.entity.QSiteUser siteUser;

    public final EnumPath<com.backend.domain.voter.domain.VoterType> voterType = createEnum("voterType", com.backend.domain.voter.domain.VoterType.class);

    public QVoter(String variable) {
        this(Voter.class, forVariable(variable), INITS);
    }

    public QVoter(Path<? extends Voter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVoter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVoter(PathMetadata metadata, PathInits inits) {
        this(Voter.class, metadata, inits);
    }

    public QVoter(Class<? extends Voter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.jobPosting = inits.isInitialized("jobPosting") ? new com.backend.domain.jobposting.entity.QJobPosting(forProperty("jobPosting"), inits.get("jobPosting")) : null;
        this.post = inits.isInitialized("post") ? new com.backend.domain.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
        this.siteUser = inits.isInitialized("siteUser") ? new com.backend.domain.user.entity.QSiteUser(forProperty("siteUser")) : null;
    }

}

