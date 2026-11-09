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

import java.util.List;

/**
 * 自动预约执行记录 API。
 *
 * @author bixiu
 * @since 4.2.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/auto-reserve/task/record")
public class StudyRoomAutoReserveTaskRecordController {

    private final StudyRoomAutoReserveTaskRecordService recordService;

    /**
     * 查询执行记录列表。
     *
     * @param query 查询条件
     * @return 列表数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping
    public R<List<StudyRoomAutoReserveTaskRecordInfoVO>> list(StudyRoomAutoReserveTaskRecordQuery query) {
        List<StudyRoomAutoReserveTaskRecordInfoDTO> recordInfoListDTO = recordService.list(query);
        List<StudyRoomAutoReserveTaskRecordInfoVO> recordInfoListVO = MapstructUtils.convert(recordInfoListDTO, StudyRoomAutoReserveTaskRecordInfoVO.class);
        return R.ok(recordInfoListVO);
    }
}
