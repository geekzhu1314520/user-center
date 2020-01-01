package com.watermelon.medium.usercenter.domain.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLoginDTO {
    private String code;
    private String avatarUrl;
    private String wxNickname;
}
