package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpRecordReqDTO;
import cn.nicomoe.aimer.aliyu.model.vo.AliyuPopUpQueryVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuPopUpService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 阿里鱼弹窗营销域接口。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/pop-up")
public class AliyuPopUpController {

    private final AliyuPopUpService popUpService;

    /**
     * 查询弹窗配置。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/query")
    public R<AliyuPopUpQueryVO> query(@RequestBody AliyuPopUpQueryReqDTO form) {
        AliyuPopUpQueryRespDTO popUpDTO = popUpService.query(form);
        AliyuPopUpQueryVO popUpVO = MapstructUtils.convert(popUpDTO, AliyuPopUpQueryVO.class);
        return R.ok(popUpVO);
    }

    /**
     * 弹窗曝光记录。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/record")
    public R<Void> record(@RequestBody AliyuPopUpRecordReqDTO form) {
        popUpService.record(form);
        return R.ok();
    }
}
