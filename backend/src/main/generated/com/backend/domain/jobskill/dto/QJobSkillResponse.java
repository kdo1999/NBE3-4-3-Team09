package com.backend.domain.jobskill.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.backend.domain.jobskill.dto.QJobSkillResponse is a Querydsl Projection type for JobSkillResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QJobSkillResponse extends ConstructorExpression<JobSkillResponse> {

    private static final long serialVersionUID = -1991149317L;

    public QJobSkillResponse(com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> code) {
        super(JobSkillResponse.class, new Class<?>[]{String.class, int.class}, name, code);
    }

}

