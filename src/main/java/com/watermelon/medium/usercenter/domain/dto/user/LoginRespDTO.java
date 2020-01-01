package com.watermelon.medium.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginRespDTO {
    private JwtTokenRespDTO token;
    private UserRespDTO user;
}
