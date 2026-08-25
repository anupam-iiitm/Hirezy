package com.zosh.job.mapper;

import com.zosh.job.dto.JobTagResponse;
import com.zosh.job.modal.JobTag;

public class JobTagMapper {

    public static JobTagResponse toTagResponse(JobTag jobTag) {

        return JobTagResponse.builder()
                .id(jobTag.getId())
                .name(jobTag.getName())
                .slug(jobTag.getSlug())
                .build();
    }
}
