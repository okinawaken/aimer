package cn.nicomoe.aimer.study.controller.user;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSettingReqDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserConfigVO;
import cn.nicomoe.aimer.study.service.user.StudyRoomUserSettingService;
import cn.nicomoe.aimer.util.MapstructUtils;
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

/**
 * 用户设置 API：小程序 token、放号时刻与自动签到开关。
 * <p>
 * 以系统用户 ID 作为资源 ID，未落库的用户配置读取时返回默认值。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/user-setting")
public class StudyRoomUserSettingController {

    private final StudyRoomUserSettingService settingService;
    private final EruptUserService eruptUserService;

    /**
     * 查询当前登录用户的设置。
     * <p>
     * tpl 页面无法从浏览器侧取得当前用户 ID，故提供服务端解析登录态的便捷入口。
     * 字面量 {@code /me} 优先于 {@code /{id}} 匹配。
     * </p>
     *
     * @return 详情数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/me")
    public R<StudyRoomUserConfigVO> me() {
        Long currentUid = eruptUserService.getCurrentUid();
        StudyRoomUserConfigDTO userConfigDTO = settingService.get(currentUid);
        StudyRoomUserConfigVO userConfigVO = MapstructUtils.convert(userConfigDTO, StudyRoomUserConfigVO.class);
        return R.ok(userConfigVO);
    }

    /**
     * 修改当前登录用户的设置。
     *
     * @param req 请求参数
     * @return 无数据响应
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PutMapping(value = "/me")
    public R<Void> updateMe(@RequestBody StudyRoomUserSettingReqDTO req) {
        Long currentUid = eruptUserService.getCurrentUid();
        settingService.update(req, currentUid);
        return R.ok();
    }
}
