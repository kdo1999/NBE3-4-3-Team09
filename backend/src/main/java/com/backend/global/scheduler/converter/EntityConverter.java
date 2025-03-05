package com.backend.global.scheduler.converter;

import com.backend.domain.jobposting.entity.ExperienceLevel;
import com.backend.domain.jobposting.entity.JobPosting;
import com.backend.domain.jobposting.entity.RequireEducate;
import com.backend.domain.jobposting.entity.Salary;
import com.backend.domain.jobskill.entity.JobSkill;
import com.backend.global.scheduler.apiresponse.Job;
import com.backend.global.scheduler.apiresponse.position.ExperienceLevelDto;
import com.backend.global.scheduler.apiresponse.position.JobCodeDto;
import com.backend.global.scheduler.apiresponse.position.RequireEducateDto;
import com.backend.global.scheduler.apiresponse.salary.SalaryDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityConverter {

	public static JobPosting jobToJobPosting(Job job) {

		job.setJobPostingStatus();

		return new JobPosting(job.getPositionDto().getTitle(), job.getUrl(), job.getPostDate(), job.getOpenDate(),
			job.getCloseDate(), job.getCompanyDto().getCompanyDetailDto().getName(), job.getJobPostingStatus(),
			job.getSalaryDto().toEntity(), Long.parseLong(job.getApplyCnt()),
			job.getPositionDto().getExperienceLevel().toEntity(), job.getPositionDto().getRequireEducate().toEntity(),
			Long.parseLong(job.getId()), job.getCompanyDto().getCompanyDetailDto().getHref());
	}

	public static RequireEducate dtoToRequireEducate(RequireEducateDto dto) {
		return new RequireEducate(Integer.parseInt(dto.getCode()), dto.getName());
	}

	public static ExperienceLevel dtoToExperienceLevel(ExperienceLevelDto dto) {

		return new ExperienceLevel(dto.getCode(), dto.getMin(), dto.getMax(), dto.getName());
	}

	public static Salary dtoToSalary(SalaryDto dto) {

		return new Salary(Integer.parseInt(dto.getCode()), dto.getName());
	}

	public static JobSkill dtoToJobSkill(JobCodeDto dto) {
		return JobSkill.builder()
			.code(Integer.parseInt(dto.getCode()))
			.name(dto.getName())
			.build();
	}

}
