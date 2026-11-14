package cn.nicomoe.aimer.aliyu.controller.user;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserSettingReqDTO;
import cn.nicomoe.aimer.aliyu.model.vo.AliyuUserConfigVO;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;
import xyz.erupt.upms.service.EruptUserService;

/**
 * 阿里鱼用户设置 API：小程序凭证束绑定。
 * <p>
 * 以系统用户 ID 作为资源 ID，未落库的用户配置读取时返回默认值。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/user-setting")
public class AliyuUserSettingController {

    private final AliyuUserSettingService settingService;
    private final EruptUserService eruptUserService;

    /**
     * 查询当前登录用户的设置。
     *
     * @return 详情数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/me")
    public R<AliyuUserConfigVO> me() {
        Long currentUid = eruptUserService.getCurrentUid();
        AliyuUserConfigDTO userConfigDTO = settingService.get(currentUid);
        AliyuUserConfigVO userConfigVO = MapstructUtils.convert(userConfigDTO, AliyuUserConfigVO.class);
        return R.ok(userConfigVO);
    }

    /**
     * 修改当前登录用户的设置（绑定 / 换绑小程序凭证束）。
     *
     * @param req 请求参数
     * @return 无数据响应
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PutMapping(value = "/me")
    public R<Void> updateMe(@RequestBody AliyuUserSettingReqDTO req) {
        Long currentUid = eruptUserService.getCurrentUid();
        settingService.update(req, currentUid);
        return R.ok();
    }
}
