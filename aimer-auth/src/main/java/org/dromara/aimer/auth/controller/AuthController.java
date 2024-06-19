package org.dromara.aimer.auth.controller;

import org.dromara.aimer.auth.dto.RegisterBodyDTO;
import org.dromara.aimer.auth.service.IAuthService;
import org.dromara.aimer.auth.vo.RegisterBodyVO;
import org.dromara.aimer.common.response.BaseResponse;
import org.dromara.aimer.common.utils.MapstructUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private IAuthService authService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<Void> register(@RequestBody RegisterBodyVO registerBodyVO) {
        RegisterBodyDTO registerBodyDTO = MapstructUtils.convert(registerBodyVO, RegisterBodyDTO.class);
        authService.register(registerBodyDTO);
        return BaseResponse.ok();
    }
}
