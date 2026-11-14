package cn.nicomoe.aimer.study.controller.autoreserve;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskRecordInfoDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskRecordQuery;
import cn.nicomoe.aimer.study.model.vo.StudyRoomAutoReserveTaskRecordInfoVO;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskRecordService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;
import xyz.erupt.upms.service.EruptUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/auto-reserve/task/record")
public class StudyRoomAutoReserveTaskRecordController {

    private final StudyRoomAutoReserveTaskRecordService recordService;
    private final EruptUserService eruptUserService;

    /** 查询执行记录列表。 */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping
    public R<List<StudyRoomAutoReserveTaskRecordInfoVO>> list(StudyRoomAutoReserveTaskRecordQuery query) {
        Long currentUid = eruptUserService.getCurrentUid();
        List<StudyRoomAutoReserveTaskRecordInfoDTO> recordInfoListDTO = recordService.list(currentUid, query);
        List<StudyRoomAutoReserveTaskRecordInfoVO> recordInfoListVO = MapstructUtils.convert(recordInfoListDTO, StudyRoomAutoReserveTaskRecordInfoVO.class);
        return R.ok(recordInfoListVO);
    }
}
