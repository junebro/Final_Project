package com.final_project.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@Getter
@Setter
public class JwtToken {
    private String grantType; // JWT에 대한 인증타입
    private String accessToken;
    private String refreshToken;
}
