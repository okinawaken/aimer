package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomGuardListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomGuardOpenClientReqDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomGuardListVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomGuardService;
import cn.nicomoe.aimer.util.MapstructUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 自习室门禁域接口：门禁区域列表、开门
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/guard")
public class StudyRoomGuardController {

    private final StudyRoomGuardService guardService;

    /**
     * 门店门禁区域列表
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/select")
    public R<StudyRoomGuardListVO> guardSelect(
            @RequestParam(value = "venueId") Integer venueId) {
        StudyRoomGuardListClientRespDTO guardListDTO = guardService.guardSelect(venueId);
        StudyRoomGuardListVO guardListVO = MapstructUtils.convert(guardListDTO, StudyRoomGuardListVO.class);
        return R.ok(guardListVO);
    }

    /**
     * 门禁开门（服务端校验/记录）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/open")
    public R<Void> guardOpen(@Valid @RequestBody StudyRoomGuardOpenClientReqDTO form) {
        guardService.guardOpen(form);
        return R.ok();
    }

}
