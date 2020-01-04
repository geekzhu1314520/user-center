package com.watermelon.medium.usercenter.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.google.common.collect.Maps;
import com.watermelon.medium.usercenter.domain.dto.user.JwtTokenRespDTO;
import com.watermelon.medium.usercenter.domain.dto.user.LoginRespDTO;
import com.watermelon.medium.usercenter.domain.dto.user.UserLoginDTO;
import com.watermelon.medium.usercenter.domain.dto.user.UserRespDTO;
import com.watermelon.medium.usercenter.domain.entity.user.User;
import com.watermelon.medium.usercenter.service.UserService;
import com.watermelon.medium.usercenter.util.JwtOperator;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtOperator jwtOperator;

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {

        //获取openId
        WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(loginDTO.getCode());
        String openId = result.getOpenid();

        //登录并注册
        User user = userService.login(loginDTO, openId);

        //获取token
        Map claims = Maps.newHashMap();
        claims.put("id", user.getId());
        claims.put("wxNickname", user.getWxNickname());
        claims.put("roles", user.getRoles());
        String token = jwtOperator.generateToken(claims);

        log.info("用户：{}登录，生成token={}, 有效期到：{}"
                , loginDTO.getWxNickname()
                , token
                , jwtOperator.getExpirationTime());

        //组装返回
        return LoginRespDTO.builder()
                .user(
                        UserRespDTO.builder()
                                .id(user.getId())
                                .wxNickname(user.getWxNickname())
                                .avatarUrl(user.getAvatarUrl())
                                .bonus(user.getBonus()).build()
                )
                .token(
                        JwtTokenRespDTO.builder()
                                .token(token)
                                .expirationTime(jwtOperator.getExpirationTime().getTime())
                                .build()
                )
                .build();
    }

}
