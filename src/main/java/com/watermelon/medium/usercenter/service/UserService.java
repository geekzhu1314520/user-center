package com.watermelon.medium.usercenter.service;

import com.watermelon.medium.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.watermelon.medium.usercenter.domain.dto.user.UserLoginDTO;
import com.watermelon.medium.usercenter.domain.entity.user.User;

public interface UserService {

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 用户注册&登录
     *
     * @param loginDTO
     * @param openId
     * @return
     */
    User login(UserLoginDTO loginDTO, String openId);

    /**
     * 增加积分
     *
     * @param msgDTO
     */
    void addBonus(UserAddBonusMsgDTO msgDTO);

}
