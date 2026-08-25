package com.zosh.job.dto;

import com.zosh.job.domain.SocialPlatform;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLinkResponse {


    private SocialPlatform platform;
    private String url;
}
