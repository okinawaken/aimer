package cn.nicomoe.aimer.study.controller.autoreserve;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveSeatOptionDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskReqDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskQuery;
import cn.nicomoe.aimer.study.model.vo.StudyRoomAutoReserveSeatOptionVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomAutoReserveTaskInfoVO;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskService;
import cn.nicomoe.aimer.util.MapstructUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;
import xyz.erupt.upms.service.EruptUserService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/auto-reserve/task")
public class StudyRoomAutoReserveTaskController {

    private final StudyRoomAutoReserveTaskService taskService;
    private final EruptUserService eruptUserService;

    /**
     * 查询任务列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping
    public R<List<StudyRoomAutoReserveTaskInfoVO>> list(StudyRoomAutoReserveTaskQuery query) {
        Long currentUid = eruptUserService.getCurrentUid();
        List<StudyRoomAutoReserveTaskInfoDTO> taskInfoListDTO = taskService.list(currentUid, query);
        List<StudyRoomAutoReserveTaskInfoVO> taskInfoListVO = MapstructUtils.convert(taskInfoListDTO, StudyRoomAutoReserveTaskInfoVO.class);
        return R.ok(taskInfoListVO);
    }

    /**
     * 查询任务详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/{id}")
    public R<StudyRoomAutoReserveTaskInfoVO> get(@PathVariable(value = "id") Long id) {
        Long currentUid = eruptUserService.getCurrentUid();
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = taskService.get(currentUid, id);
        StudyRoomAutoReserveTaskInfoVO taskInfoVO = MapstructUtils.convert(taskInfoDTO, StudyRoomAutoReserveTaskInfoVO.class);
        return R.ok(taskInfoVO);
    }

    /**
     * 创建任务。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping
    public R<Long> create(@Valid @RequestBody StudyRoomAutoReserveTaskReqDTO req) {
        Long currentUid = eruptUserService.getCurrentUid();
        return R.ok(taskService.create(currentUid, req));
    }

    /**
     * 修改任务。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PutMapping(value = "/{id}")
    public R<Void> update(@PathVariable(value = "id") Long id,
                          @Valid @RequestBody StudyRoomAutoReserveTaskReqDTO req) {
        Long currentUid = eruptUserService.getCurrentUid();
        taskService.update(currentUid, req, id);
        return R.ok();
    }

    /**
     * 删除任务。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @DeleteMapping
    public R<Void> delete(@RequestBody List<Long> ids) {
        Long currentUid = eruptUserService.getCurrentUid();
        taskService.delete(currentUid, ids);
        return R.ok();
    }

    /**
     * 暂停任务。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/{id}/pause")
    public R<Void> pause(@PathVariable(value = "id") Long id) {
        Long currentUid = eruptUserService.getCurrentUid();
        taskService.pauseTask(currentUid, id, "用户手动暂停");
        return R.ok();
    }

    /**
     * 恢复任务。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/{id}/resume")
    public R<Void> resume(@PathVariable(value = "id") Long id) {
        Long currentUid = eruptUserService.getCurrentUid();
        taskService.resumeTask(currentUid, id);
        return R.ok();
    }

    /**
     * 立即执行一次。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/{id}/execute")
    public R<Void> execute(@PathVariable(value = "id") Long id) {
        Long currentUid = eruptUserService.getCurrentUid();
        taskService.executeTask(currentUid, id);
        return R.ok();
    }

    /**
     * 查询指定门店的区域 / 座位下拉选项。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/seat/options")
    public R<List<StudyRoomAutoReserveSeatOptionVO>> seatOptions(@RequestParam(value = "venueId") Integer venueId) {
        List<StudyRoomAutoReserveSeatOptionDTO> seatOptionListDTO = taskService.listSeatOptions(venueId);
        List<StudyRoomAutoReserveSeatOptionVO> seatOptionListVO = MapstructUtils.convert(seatOptionListDTO, StudyRoomAutoReserveSeatOptionVO.class);
        return R.ok(seatOptionListVO);
    }
}
