package cn.nicomoe.aimer.study.controller.user;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSettingReqDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserConfigVO;
import cn.nicomoe.aimer.study.service.user.StudyRoomUserSettingService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

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

    /**
     * 查询用户设置。
     *
     * @param id 用户 ID
     * @return 详情数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/{id}")
    public R<StudyRoomUserConfigVO> get(@PathVariable(value = "id") Long id) {
        StudyRoomUserConfigDTO userConfigDTO = settingService.get(id);
        StudyRoomUserConfigVO userConfigVO = MapstructUtils.convert(userConfigDTO, StudyRoomUserConfigVO.class);
        return R.ok(userConfigVO);
    }

    /**
     * 修改用户设置。
     *
     * @param id  用户 ID
     * @param req 请求参数
     * @return 无数据响应
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PutMapping(value = "/{id}")
    public R<Void> update(@PathVariable(value = "id") Long id, @RequestBody StudyRoomUserSettingReqDTO req) {
        settingService.update(req, id);
        return R.ok();
    }
}
