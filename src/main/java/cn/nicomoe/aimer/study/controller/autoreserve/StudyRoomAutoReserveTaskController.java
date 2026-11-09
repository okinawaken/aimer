package cn.nicomoe.aimer.study.controller.autoreserve;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskReqDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomAutoReserveTaskQuery;
import cn.nicomoe.aimer.study.model.vo.StudyRoomAutoReserveTaskInfoVO;
import cn.nicomoe.aimer.study.service.autoreserve.StudyRoomAutoReserveTaskService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

import java.util.List;

/**
 * 自动预约任务 API。
 *
 * @author bixiu
 * @since 4.2.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/auto-reserve/task")
public class StudyRoomAutoReserveTaskController {

    private final StudyRoomAutoReserveTaskService taskService;

    /**
     * 查询任务列表。
     *
     * @param query 查询条件
     * @return 列表数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping
    public R<List<StudyRoomAutoReserveTaskInfoVO>> list(StudyRoomAutoReserveTaskQuery query) {
        List<StudyRoomAutoReserveTaskInfoDTO> taskInfoListDTO = taskService.list(query);
        List<StudyRoomAutoReserveTaskInfoVO> taskInfoListVO = MapstructUtils.convert(taskInfoListDTO, StudyRoomAutoReserveTaskInfoVO.class);
        return R.ok(taskInfoListVO);
    }

    /**
     * 查询任务详情。
     *
     * @param id 任务 ID
     * @return 详情数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/{id}")
    public R<StudyRoomAutoReserveTaskInfoVO> get(@PathVariable(value = "id") Long id) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = taskService.get(id);
        StudyRoomAutoReserveTaskInfoVO taskInfoVO = MapstructUtils.convert(taskInfoDTO, StudyRoomAutoReserveTaskInfoVO.class);
        return R.ok(taskInfoVO);
    }

    /**
     * 创建任务。
     *
     * @param req 请求参数
     * @return 新任务 ID
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping
    public R<Long> create(@RequestBody StudyRoomAutoReserveTaskReqDTO req) {
        return R.ok(taskService.create(req));
    }

    /**
     * 修改任务。
     *
     * @param id  任务 ID
     * @param req 请求参数
     * @return 无数据响应
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PutMapping(value = "/{id}")
    public R<Void> update(@PathVariable(value = "id") Long id,
                          @RequestBody StudyRoomAutoReserveTaskReqDTO req) {
        taskService.update(req, id);
        return R.ok();
    }

    /**
     * 删除任务。
     *
     * @param ids 任务 ID 集合
     * @return 无数据响应
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @DeleteMapping
    public R<Void> delete(@RequestBody List<Long> ids) {
        taskService.delete(ids);
        return R.ok();
    }

    /**
     * 暂停任务。
     *
     * @param id 任务 ID
     * @return 无数据响应
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/{id}/pause")
    public R<Void> pause(@PathVariable(value = "id") Long id) {
        taskService.pauseTask(id, "用户手动暂停");
        return R.ok();
    }

    /**
     * 恢复任务。
     *
     * @param id 任务 ID
     * @return 无数据响应
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/{id}/resume")
    public R<Void> resume(@PathVariable(value = "id") Long id) {
        taskService.resumeTask(id);
        return R.ok();
    }

    /**
     * 立即执行一次。
     *
     * @param id 任务 ID
     * @return 无数据响应
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/{id}/execute")
    public R<Void> execute(@PathVariable(value = "id") Long id) {
        taskService.executeTask(id);
        return R.ok();
    }
}
