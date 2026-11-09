package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomConfigClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomNoticeClientRespDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomConfigVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomNoticeVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomConfigService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 自习室配置域接口
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/config")
public class StudyRoomConfigController {

    private final StudyRoomConfigService configService;

    /**
     * 小程序全局配置（含可提前预约天数、保留时长等）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/sys")
    public R<StudyRoomConfigVO> sysConfig() {
        StudyRoomConfigClientRespDTO configDTO = configService.sysConfig();
        StudyRoomConfigVO configVO = MapstructUtils.convert(configDTO, StudyRoomConfigVO.class);
        return R.ok(configVO);
    }

    /**
     * 公告栏
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/notice")
    public R<StudyRoomNoticeVO> noticeBoard(
            @RequestParam(value = "venueId") Integer venueId) {
        StudyRoomNoticeClientRespDTO noticeDTO = configService.noticeBoard(venueId);
        StudyRoomNoticeVO noticeVO = MapstructUtils.convert(noticeDTO, StudyRoomNoticeVO.class);
        return R.ok(noticeVO);
    }
}
