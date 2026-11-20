package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.BearerListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DisListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.LoginUserRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserAddBearerReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserAddBearerRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserAddDisReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserContactRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserGetDisInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserGetDisReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserInfoReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserLoginOffReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserModifyDisReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserModifyUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserRemoveBearerReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.UserRemoveDisReqDTO;
import cn.nicomoe.aimer.aliyu.model.vo.BearerListVO;
import cn.nicomoe.aimer.aliyu.model.vo.DisListVO;
import cn.nicomoe.aimer.aliyu.model.vo.LoginUserVO;
import cn.nicomoe.aimer.aliyu.model.vo.UserAddBearerVO;
import cn.nicomoe.aimer.aliyu.model.vo.UserContactVO;
import cn.nicomoe.aimer.aliyu.model.vo.UserGetDisInfoVO;
import cn.nicomoe.aimer.aliyu.model.vo.UserInfoVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuUserService;
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
 * 阿里鱼用户域接口：资料、联系方式、持票人与联系人。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/user")
public class AliyuUserController {

    private final AliyuUserService userService;

    /**
     * 获取登录用户信息。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/login-user")
    public R<LoginUserVO> loginUser() {
        LoginUserRespDTO loginUserDTO = userService.loginUser();
        LoginUserVO loginUserVO = MapstructUtils.convert(loginUserDTO, LoginUserVO.class);
        return R.ok(loginUserVO);
    }

    /**
     * 用户信息。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/info")
    public R<UserInfoVO> userInfo(UserInfoReqDTO query) {
        UserInfoRespDTO userInfoDTO = userService.userInfo(query);
        UserInfoVO userInfoVO = MapstructUtils.convert(userInfoDTO, UserInfoVO.class);
        return R.ok(userInfoVO);
    }

    /**
     * 用户联系方式。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/contact")
    public R<UserContactVO> userContact() {
        UserContactRespDTO userContactDTO = userService.userContact();
        UserContactVO userContactVO = MapstructUtils.convert(userContactDTO, UserContactVO.class);
        return R.ok(userContactVO);
    }

    /**
     * 获取持票人列表。{@code page/pageSize} 与 {@code eventIds/exitTicketCert} 均以字符串传递。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/bearer/list")
    public R<BearerListVO> getBearers(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "pageSize", required = false) String pageSize,
            @RequestParam(value = "eventIds", required = false) String eventIds,
            @RequestParam(value = "exitTicketCert", required = false) String exitTicketCert) {
        BearerListRespDTO bearerListDTO = userService.getBearers(page, pageSize, eventIds, exitTicketCert);
        BearerListVO bearerListVO = MapstructUtils.convert(bearerListDTO, BearerListVO.class);
        return R.ok(bearerListVO);
    }

    /**
     * 获取联系人 / 收件人列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/dis/list")
    public R<DisListVO> getDis(UserGetDisReqDTO query) {
        DisListRespDTO disListDTO = userService.getDis(query);
        DisListVO disListVO = MapstructUtils.convert(disListDTO, DisListVO.class);
        return R.ok(disListVO);
    }

    /**
     * 联系人详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/dis/info")
    public R<UserGetDisInfoVO> getDisInfo(@RequestParam(value = "disId") String disId) {
        UserGetDisInfoRespDTO disInfoDTO = userService.getDisInfo(disId);
        UserGetDisInfoVO disInfoVO = MapstructUtils.convert(disInfoDTO, UserGetDisInfoVO.class);
        return R.ok(disInfoVO);
    }

    /**
     * 修改用户信息。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/modify")
    public R<Boolean> modifyUser(@RequestBody UserModifyUserReqDTO form) {
        return R.ok(userService.modifyUser(form));
    }

    /**
     * 修改联系人。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/dis/modify")
    public R<Boolean> modifyDis(@RequestBody UserModifyDisReqDTO form) {
        return R.ok(userService.modifyDis(form));
    }

    /**
     * 新增持票人。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/bearer/add")
    public R<UserAddBearerVO> addBearer(@RequestBody UserAddBearerReqDTO form) {
        UserAddBearerRespDTO addBearerDTO = userService.addBearer(form);
        UserAddBearerVO addBearerVO = MapstructUtils.convert(addBearerDTO, UserAddBearerVO.class);
        return R.ok(addBearerVO);
    }

    /**
     * 删除持票人。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/bearer/remove")
    public R<Boolean> removeBearer(@RequestBody UserRemoveBearerReqDTO form) {
        return R.ok(userService.removeBearer(form));
    }

    /**
     * 新增联系人。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/dis/add")
    public R<Boolean> addDis(@RequestBody UserAddDisReqDTO form) {
        return R.ok(userService.addDis(form));
    }

    /**
     * 删除联系人。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/dis/remove")
    public R<Boolean> removeDis(@RequestBody UserRemoveDisReqDTO form) {
        return R.ok(userService.removeDis(form));
    }

    /**
     * 退出登录。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/login-off")
    public R<Boolean> loginOff(@RequestBody UserLoginOffReqDTO form) {
        return R.ok(userService.loginOff(form));
    }
}
