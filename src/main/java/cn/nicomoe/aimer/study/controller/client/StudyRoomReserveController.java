package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveAnotherCheckClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveCheckClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveConfirmClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveConfirmClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveConfirmV1ClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveDateClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatMapClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatQueryClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatQueryV1ClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveCancelClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveCancelTipClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveListClientRespDTO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomReserveAnotherCheckVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomReserveCheckVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomReserveConfirmVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomReserveDateVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomSeatMapVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserReserveCancelTipVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomUserReserveListVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomReserveService;
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
 * 自习室预约域接口：座位图、下单、我的预约、取消
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/reserve")
public class StudyRoomReserveController {

    private final StudyRoomReserveService reserveService;

    /**
     * 预约资格校验（是否有可预约套餐）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/check")
    public R<StudyRoomReserveCheckVO> reserveCheck() {
        StudyRoomReserveCheckClientRespDTO reserveCheckDTO = reserveService.reserveCheck();
        StudyRoomReserveCheckVO reserveCheckVO = MapstructUtils.convert(reserveCheckDTO, StudyRoomReserveCheckVO.class);
        return R.ok(reserveCheckVO);
    }

    /**
     * 可预约日期列表
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/v2/date/select")
    public R<StudyRoomReserveDateVO> reserveDateList(@RequestParam(value = "venueId") Integer venueId) {
        StudyRoomReserveDateClientRespDTO reserveDateDTO = reserveService.reserveDateList(venueId);
        StudyRoomReserveDateVO reserveDateVO = MapstructUtils.convert(reserveDateDTO, StudyRoomReserveDateVO.class);
        return R.ok(reserveDateVO);
    }

    /**
     * 座位图（v2，推荐）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/v2/seat/list")
    public R<StudyRoomSeatMapVO> reserveV2SeatList(@RequestBody StudyRoomSeatQueryClientReqDTO form) {
        StudyRoomSeatMapClientRespDTO seatMapDTO = reserveService.reserveV2SeatList(form);
        StudyRoomSeatMapVO seatMapVO = MapstructUtils.convert(seatMapDTO, StudyRoomSeatMapVO.class);
        return R.ok(seatMapVO);
    }

    /**
     * 提交预约（v2，推荐）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/v2/seat/confirm")
    public R<StudyRoomReserveConfirmVO> reserveV2SeatConfirm(@RequestBody StudyRoomReserveConfirmClientReqDTO form) {
        StudyRoomReserveConfirmClientRespDTO reserveConfirmDTO = reserveService.reserveV2SeatConfirm(form);
        StudyRoomReserveConfirmVO reserveConfirmVO = MapstructUtils.convert(reserveConfirmDTO, StudyRoomReserveConfirmVO.class);
        return R.ok(reserveConfirmVO);
    }

    /**
     * 座位图（v1，旧版，时间格式 {@code yyyy-MM-dd HH:mm}）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/seat/list")
    public R<StudyRoomSeatMapVO> reserveSeatList(@RequestBody StudyRoomSeatQueryV1ClientReqDTO form) {
        StudyRoomSeatMapClientRespDTO seatMapDTO = reserveService.reserveSeatList(form);
        StudyRoomSeatMapVO seatMapVO = MapstructUtils.convert(seatMapDTO, StudyRoomSeatMapVO.class);
        return R.ok(seatMapVO);
    }

    /**
     * 提交预约（v1，旧版，时间格式 {@code yyyy-MM-dd HH:mm}）
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/seat/confirm")
    public R<StudyRoomReserveConfirmVO> reserveSeatConfirm(@RequestBody StudyRoomReserveConfirmV1ClientReqDTO form) {
        StudyRoomReserveConfirmClientRespDTO reserveConfirmDTO = reserveService.reserveSeatConfirm(form);
        StudyRoomReserveConfirmVO reserveConfirmVO = MapstructUtils.convert(reserveConfirmDTO, StudyRoomReserveConfirmVO.class);
        return R.ok(reserveConfirmVO);
    }

    /**
     * 是否还能再预约一个座位
     *
     * @param userReserveId 用户预约 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/another/check")
    public R<StudyRoomReserveAnotherCheckVO> reserveAnotherCheck(@RequestParam(value = "userReserveId") Long userReserveId) {
        StudyRoomReserveAnotherCheckClientRespDTO anotherCheckDTO = reserveService.reserveAnotherCheck(userReserveId);
        StudyRoomReserveAnotherCheckVO anotherCheckVO = MapstructUtils.convert(anotherCheckDTO, StudyRoomReserveAnotherCheckVO.class);
        return R.ok(anotherCheckVO);
    }

    /**
     * 我的预约列表（一次性返回全部，无分页）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/user/list")
    public R<StudyRoomUserReserveListVO> userReserveList() {
        StudyRoomUserReserveListClientRespDTO reserveListDTO = reserveService.userReserveList();
        StudyRoomUserReserveListVO reserveListVO = MapstructUtils.convert(reserveListDTO, StudyRoomUserReserveListVO.class);
        return R.ok(reserveListVO);
    }

    /**
     * 取消预约前的提示
     *
     * @param userReserveId 用户预约 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/user/cancel/tip")
    public R<StudyRoomUserReserveCancelTipVO> userReserveCancelTip(@RequestParam(value = "userReserveId") Long userReserveId) {
        StudyRoomUserReserveCancelTipClientRespDTO cancelTipDTO = reserveService.userReserveCancelTip(userReserveId);
        StudyRoomUserReserveCancelTipVO cancelTipVO = MapstructUtils.convert(cancelTipDTO, StudyRoomUserReserveCancelTipVO.class);
        return R.ok(cancelTipVO);
    }

    /**
     * 取消预约
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/user/cancel")
    public R<Integer> userReserveCancel(@RequestBody StudyRoomUserReserveCancelClientReqDTO form) {
        Integer result = reserveService.userReserveCancel(form);
        return R.ok(result);
    }
}
