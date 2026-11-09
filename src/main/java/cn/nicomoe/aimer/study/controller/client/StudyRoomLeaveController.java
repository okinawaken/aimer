package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveCancelClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserLeaveEnterClientRespDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserLeaveEnterVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomLeaveService;
import cn.nicomoe.aimer.util.MapstructUtils;
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
 * 自习室请假域接口
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/leave")
public class StudyRoomLeaveController {

    private final StudyRoomLeaveService leaveService;

    /**
     * 进入请假页（校验可请假套餐）
     *
     * @param userPackageId 参数 userPackageId
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/enter")
    public R<StudyRoomUserLeaveEnterVO> userLeaveEnter(@RequestParam(value = "userPackageId") Long userPackageId) {
        StudyRoomUserLeaveEnterClientRespDTO leaveEnterDTO = leaveService.userLeaveEnter(userPackageId);
        StudyRoomUserLeaveEnterVO leaveEnterVO = MapstructUtils.convert(leaveEnterDTO, StudyRoomUserLeaveEnterVO.class);
        return R.ok(leaveEnterVO);
    }

    /**
     * 确认请假
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/confirm")
    public R<Integer> userLeaveConfirm(@RequestBody StudyRoomUserLeaveConfirmClientReqDTO form) {
        Integer result = leaveService.userLeaveConfirm(form);
        return R.ok(result);
    }

    /**
     * 取消请假
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/cancel")
    public R<Integer> userLeaveCancel(@RequestBody StudyRoomUserLeaveCancelClientReqDTO form) {
        Integer result = leaveService.userLeaveCancel(form);
        return R.ok(result);
    }
}
