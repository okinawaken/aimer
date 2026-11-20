package cn.nicomoe.aimer.aliyu.controller.user;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserSettingReqDTO;
import cn.nicomoe.aimer.aliyu.model.vo.AliyuUserConfigVO;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import cn.nicomoe.aimer.util.MapstructUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;
import xyz.erupt.upms.service.EruptUserService;

/** 阿里鱼用户设置 API（凭证绑定），以系统用户 ID 作为资源 ID。 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/user-setting")
public class AliyuUserSettingController {

    private final AliyuUserSettingService settingService;
    private final EruptUserService eruptUserService;

    /** 查询当前登录用户的配置，原样返回配置数据。 */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/me")
    public R<AliyuUserConfigVO> me() {
        Long currentUid = eruptUserService.getCurrentUid();
        AliyuUserConfigDTO configDTO = settingService.get(currentUid);
        AliyuUserConfigVO configVO = MapstructUtils.convert(configDTO, AliyuUserConfigVO.class);
        return R.ok(configVO);
    }

    /** 绑定 / 换绑当前登录用户的凭证，保存前会调用上游校验。 */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PutMapping(value = "/me")
    public R<Void> updateMe(@Valid @RequestBody AliyuUserSettingReqDTO req) {
        Long currentUid = eruptUserService.getCurrentUid();
        settingService.update(currentUid, req);
        return R.ok();
    }
}
