package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAuthorizeHeadImgClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUpdateUserInfoClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserDetailClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserFeeClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSignListClientRespDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserDetailVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserFeeVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserIndexVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserSignListVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomUserService;
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
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.core.view.R;

/**
 * 自习室用户域接口：资料、头像、签到记录
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/user")
public class StudyRoomUserController {

    private final StudyRoomUserService userService;

    /**
     * 用户概览：头像、手机号、累计签到次数、累计学习时长
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/index")
    public R<StudyRoomUserIndexVO> userIndex() {
        StudyRoomUserIndexClientRespDTO userIndexDTO = userService.userIndex();
        StudyRoomUserIndexVO userIndexVO = MapstructUtils.convert(userIndexDTO, StudyRoomUserIndexVO.class);
        return R.ok(userIndexVO);
    }

    /**
     * 用户详情
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/detail")
    public R<StudyRoomUserDetailVO> userDetail() {
        StudyRoomUserDetailClientRespDTO userDetailDTO = userService.userDetail();
        StudyRoomUserDetailVO userDetailVO = MapstructUtils.convert(userDetailDTO, StudyRoomUserDetailVO.class);
        return R.ok(userDetailVO);
    }

    /**
     * 欠费 / 当前签到状态
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/fee")
    public R<StudyRoomUserFeeVO> userFee() {
        StudyRoomUserFeeClientRespDTO userFeeDTO = userService.userFee();
        StudyRoomUserFeeVO userFeeVO = MapstructUtils.convert(userFeeDTO, StudyRoomUserFeeVO.class);
        return R.ok(userFeeVO);
    }

    /**
     * 修改资料
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/update-info")
    public R<Integer> updateUserInfo(@RequestBody StudyRoomUpdateUserInfoClientReqDTO form) {
        Integer result = userService.updateUserInfo(form);
        return R.ok(result);
    }

    /**
     * 更新授权头像（URL 形式）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/authorize/head-img")
    public R<Void> authorizeHeadImg(@RequestBody StudyRoomAuthorizeHeadImgClientReqDTO form) {
        userService.authorizeHeadImg(form);
        return R.ok();
    }

    /**
     * 上传授权头像（当前不支持）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/authorize/head-img/upload")
    public R<Void> uploadAuthorizeHeadImg() {
        throw new EruptWebApiRuntimeException("暂不支持文件上传");
    }

    /**
     * 解除授权（退出登录）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/authorize/cancel")
    public R<Void> cancelAuthorize() {
        userService.cancelAuthorize();
        return R.ok();
    }

    /**
     * 签到记录列表（最近 N 条），id 传 0 即可
     *
     * @param id ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/sign/list")
    public R<StudyRoomUserSignListVO> userSignList(@RequestParam(value = "id") Long id) {
        StudyRoomUserSignListClientRespDTO signListDTO = userService.userSignList(id);
        StudyRoomUserSignListVO signListVO = MapstructUtils.convert(signListDTO, StudyRoomUserSignListVO.class);
        return R.ok(signListVO);
    }

    /**
     * 更早的签到记录（分页续拉，无入参）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/sign/list/more")
    public R<StudyRoomUserSignListVO> userSignListMore() {
        StudyRoomUserSignListClientRespDTO signListDTO = userService.userSignListMore();
        StudyRoomUserSignListVO signListVO = MapstructUtils.convert(signListDTO, StudyRoomUserSignListVO.class);
        return R.ok(signListVO);
    }
}
