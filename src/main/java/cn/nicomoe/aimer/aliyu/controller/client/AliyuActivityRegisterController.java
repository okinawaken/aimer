package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.ActivityDrawListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterActListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ActivityRegisterUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DrawListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.EventRegisterEventRegisterReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.EventRegisterUpdateSaleRemindReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegisterActDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegisterActListRespDTO;
import cn.nicomoe.aimer.aliyu.model.vo.DrawListVO;
import cn.nicomoe.aimer.aliyu.model.vo.RegisterActDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.RegisterActListVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuActivityRegisterService;
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
 * 阿里鱼活动报名域接口：报名详情 / 列表、活动报名、抽奖与开售提醒。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/activity-register")
public class AliyuActivityRegisterController {

    private final AliyuActivityRegisterService activityRegisterService;

    /**
     * 报名活动详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/detail")
    public R<RegisterActDetailVO> registerActDetail(@RequestParam(value = "actId") String actId) {
        RegisterActDetailRespDTO detailDTO = activityRegisterService.registerActDetail(actId);
        RegisterActDetailVO detailVO = MapstructUtils.convert(detailDTO, RegisterActDetailVO.class);
        return R.ok(detailVO);
    }

    /**
     * 报名活动列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/list")
    public R<RegisterActListVO> registerActList(@RequestBody ActivityRegisterActListReqDTO form) {
        RegisterActListRespDTO listDTO = activityRegisterService.registerActList(form);
        RegisterActListVO listVO = MapstructUtils.convert(listDTO, RegisterActListVO.class);
        return R.ok(listVO);
    }

    /**
     * 活动报名。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/register-user")
    public R<Boolean> registerUser(@RequestBody ActivityRegisterUserReqDTO form) {
        return R.ok(activityRegisterService.registerUser(form));
    }

    /**
     * 抽奖列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/draw-list")
    public R<DrawListVO> drawList(@RequestBody ActivityDrawListReqDTO form) {
        DrawListRespDTO drawListDTO = activityRegisterService.drawList(form);
        DrawListVO drawListVO = MapstructUtils.convert(drawListDTO, DrawListVO.class);
        return R.ok(drawListVO);
    }

    /**
     * 活动注册（开售提醒登记）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/event-register")
    public R<Boolean> eventRegister(@RequestBody EventRegisterEventRegisterReqDTO form) {
        return R.ok(activityRegisterService.eventRegister(form));
    }

    /**
     * 更新开售提醒。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/update-sale-remind")
    public R<Boolean> updateSaleRemind(@RequestBody EventRegisterUpdateSaleRemindReqDTO form) {
        return R.ok(activityRegisterService.updateSaleRemind(form));
    }
}
