package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuAuthLoginClientReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.LoginAuthLoginRespDTO;
import cn.nicomoe.aimer.aliyu.model.vo.LoginAuthLoginVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuLoginService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 阿里鱼登录鉴权域接口。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/login")
public class AliyuLoginController {

    private final AliyuLoginService loginService;

    /**
     * 小程序授权登录。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/auth-login-for-applet")
    public R<LoginAuthLoginVO> authLoginForApplet(@RequestBody AliyuAuthLoginClientReqDTO form) {
        LoginAuthLoginRespDTO loginDTO = loginService.authLoginForApplet(form);
        LoginAuthLoginVO loginVO = MapstructUtils.convert(loginDTO, LoginAuthLoginVO.class);
        return R.ok(loginVO);
    }
}
