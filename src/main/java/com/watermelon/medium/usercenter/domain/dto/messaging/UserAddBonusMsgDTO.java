package com.watermelon.medium.usercenter.domain.dto.messaging;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserAddBonusMsgDTO {
    private Integer userId;
    private Integer bonus;
    private String description;
    private String event;
}
