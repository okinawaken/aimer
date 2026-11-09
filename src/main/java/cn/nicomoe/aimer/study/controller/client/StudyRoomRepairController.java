package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomRepairSubmitClientReqDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomRepairListVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomRepairVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomRepairService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 自习室报修域接口
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/repair")
public class StudyRoomRepairController {

    private final StudyRoomRepairService repairService;

    /**
     * 提交报修
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/submit")
    public R<Void> submitRepair(@RequestBody StudyRoomRepairSubmitClientReqDTO form) {
        repairService.submitRepair(form);
        return R.ok();
    }

    /**
     * 报修列表
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/list")
    public R<StudyRoomRepairListVO> repairList() {
        StudyRoomRepairListClientRespDTO repairListDTO = repairService.repairList();
        StudyRoomRepairListVO repairListVO = MapstructUtils.convert(repairListDTO, StudyRoomRepairListVO.class);
        return R.ok(repairListVO);
    }

    /**
     * 报修详情
     *
     * @param id ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/detail/{id}")
    public R<StudyRoomRepairVO> repairDetail(@PathVariable(value = "id") Long id) {
        StudyRoomRepairClientRespDTO repairDTO = repairService.repairDetail(id);
        StudyRoomRepairVO repairVO = MapstructUtils.convert(repairDTO, StudyRoomRepairVO.class);
        return R.ok(repairVO);
    }
}
