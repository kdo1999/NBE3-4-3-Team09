package com.backend.domain.jobposting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJobPostingJobSkill is a Querydsl query type for JobPostingJobSkill
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJobPostingJobSkill extends EntityPathBase<JobPostingJobSkill> {

    private static final long serialVersionUID = 947449694L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJobPostingJobSkill jobPostingJobSkill = new QJobPostingJobSkill("jobPostingJobSkill");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QJobPosting jobPosting;

    public final com.backend.domain.jobskill.entity.QJobSkill jobSkill;

    public QJobPostingJobSkill(String variable) {
        this(JobPostingJobSkill.class, forVariable(variable), INITS);
    }

    public QJobPostingJobSkill(Path<? extends JobPostingJobSkill> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJobPostingJobSkill(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJobPostingJobSkill(PathMetadata metadata, PathInits inits) {
        this(JobPostingJobSkill.class, metadata, inits);
    }

    public QJobPostingJobSkill(Class<? extends JobPostingJobSkill> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.jobPosting = inits.isInitialized("jobPosting") ? new QJobPosting(forProperty("jobPosting"), inits.get("jobPosting")) : null;
        this.jobSkill = inits.isInitialized("jobSkill") ? new com.backend.domain.jobskill.entity.QJobSkill(forProperty("jobSkill")) : null;
    }

}

