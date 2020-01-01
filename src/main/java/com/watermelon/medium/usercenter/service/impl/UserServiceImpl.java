package com.watermelon.medium.usercenter.service.impl;

import com.watermelon.medium.usercenter.dao.bonus.BonusEventLogMapper;
import com.watermelon.medium.usercenter.dao.user.UserMapper;
import com.watermelon.medium.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.watermelon.medium.usercenter.domain.dto.user.UserLoginDTO;
import com.watermelon.medium.usercenter.domain.entity.bonus.BonusEventLog;
import com.watermelon.medium.usercenter.domain.entity.user.User;
import com.watermelon.medium.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BonusEventLogMapper bonusEventLogMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User login(UserLoginDTO loginDTO, String openId) {

        //1.查询已有用户
        User user = userMapper.selectOne(User.builder().wxId(openId).build());

        //2.生成新用户
        if (user == null) {
            Date now = new Date();
            user = User.builder()
                    .wxId(openId)
                    .wxNickname(loginDTO.getWxNickname())
                    .roles("user")
                    .avatarUrl(loginDTO.getAvatarUrl())
                    .bonus(0)
                    .createTime(now)
                    .updateTime(now)
                    .build();
            userMapper.insertSelective(user);
        }
        return user;
    }

    @Override
    @Transactional
    public void addBonus(UserAddBonusMsgDTO msgDTO) {

        //1.为用户增加积分
        Integer userId = msgDTO.getUserId();
        Integer bonus = msgDTO.getBonus();
        User user = userMapper.selectByPrimaryKey(userId);

        user.setBonus(user.getBonus() + bonus);
        userMapper.updateByPrimaryKeySelective(user);

        //2.记录日志到bonus_event_log表
        bonusEventLogMapper.insert(BonusEventLog.builder()
                .userId(userId)
                .value(bonus)
                .event(msgDTO.getEvent())
                .createTime(new Date())
                .description(msgDTO.getDescription())
                .build());

        log.info("积分添加完毕...");
    }
}
