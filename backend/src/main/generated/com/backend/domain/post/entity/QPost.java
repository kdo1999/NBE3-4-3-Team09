package com.backend.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 668423616L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.backend.global.baseentity.QBaseEntity _super = new com.backend.global.baseentity.QBaseEntity(this);

    public final com.backend.domain.user.entity.QSiteUser author;

    public final com.backend.domain.category.entity.QCategory category;

    public final ListPath<com.backend.domain.comment.entity.Comment, com.backend.domain.comment.entity.QComment> commentList = this.<com.backend.domain.comment.entity.Comment, com.backend.domain.comment.entity.QComment>createList("commentList", com.backend.domain.comment.entity.Comment.class, com.backend.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final StringPath subject = createString("subject");

    public final ListPath<com.backend.domain.voter.entity.Voter, com.backend.domain.voter.entity.QVoter> voterList = this.<com.backend.domain.voter.entity.Voter, com.backend.domain.voter.entity.QVoter>createList("voterList", com.backend.domain.voter.entity.Voter.class, com.backend.domain.voter.entity.QVoter.class, PathInits.DIRECT2);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.backend.domain.user.entity.QSiteUser(forProperty("author")) : null;
        this.category = inits.isInitialized("category") ? new com.backend.domain.category.entity.QCategory(forProperty("category")) : null;
    }

}

