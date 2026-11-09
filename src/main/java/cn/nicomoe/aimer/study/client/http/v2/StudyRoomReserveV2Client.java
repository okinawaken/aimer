package cn.nicomoe.aimer.study.client.http.v2;

import cn.nicomoe.aimer.study.client.http.StudyRoomBaseClient;
import cn.nicomoe.aimer.study.constant.StudyRoomApiConstants;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
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
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Var;

/**
 * 预约域（显式用户上下文）：座位图、下单、我的预约、取消
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomReserveV2Client extends StudyRoomBaseClient {

    /**
     * 预约资格校验（是否有可预约套餐）
     *
     * @param userId 用户 ID
     * @return 响应数据
     */
    @Get(url = "/mp/reserve/check")
    StudyRoomClientResult<StudyRoomReserveCheckClientRespDTO> reserveCheck(@Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 可预约日期列表（默认返回今天起 N 天，N 由 sysConfig.reserveMaxDays 决定）
     *
     * @param venueId 门店 ID
     * @param userId  用户 ID
     * @return 响应数据
     */
    @Get(url = "/mp/reserve/v2/date/select")
    StudyRoomClientResult<StudyRoomReserveDateClientRespDTO> reserveDateList(@Query(value = "venueId") Integer venueId, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 座位图（v2，推荐）：按日期 + 时间段查询门店全部座位
     * <p>
     * 时间段传空串表示查全天
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 响应数据
     */
    @Post(url = "/mp/reserve/v2/seat/list")
    StudyRoomClientResult<StudyRoomSeatMapClientRespDTO> reserveV2SeatList(@JSONBody StudyRoomSeatQueryClientReqDTO form, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 提交预约（v2，推荐）
     * <p>
     * 实测返回 {@code data.id} 即 userReserveId，取消预约必须用它
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 响应数据
     */
    @Post(url = "/mp/reserve/v2/seat/confirm")
    StudyRoomClientResult<StudyRoomReserveConfirmClientRespDTO> reserveV2SeatConfirm(@JSONBody StudyRoomReserveConfirmClientReqDTO form, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 座位图（v1，旧版）
     * <p>
     * 注意：v1 的时间参数格式是 {@code yyyy-MM-dd HH:mm}，不是 {@code HH:mm}
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 响应数据
     */
    @Post(url = "/mp/reserve/seat/list")
    StudyRoomClientResult<StudyRoomSeatMapClientRespDTO> reserveSeatList(@JSONBody StudyRoomSeatQueryV1ClientReqDTO form, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 提交预约（v1，旧版）
     * <p>
     * 注意：v1 的时间参数格式是 {@code yyyy-MM-dd HH:mm}
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 响应数据
     */
    @Post(url = "/mp/reserve/seat/confirm")
    StudyRoomClientResult<StudyRoomReserveConfirmClientRespDTO> reserveSeatConfirm(@JSONBody StudyRoomReserveConfirmV1ClientReqDTO form, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 是否还能再预约一个座位
     *
     * @param userReserveId 用户预约 ID
     * @param userId        用户 ID
     * @return 响应数据
     */
    @Get(url = "/mp/reserve/another/check")
    StudyRoomClientResult<StudyRoomReserveAnotherCheckClientRespDTO> reserveAnotherCheck(@Query(value = "userReserveId") Long userReserveId, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 我的预约列表（一次性返回全部，无分页）
     *
     * @param userId 用户 ID
     * @return 响应数据
     */
    @Get(url = "/mp/user/userReserve/list")
    StudyRoomClientResult<StudyRoomUserReserveListClientRespDTO> userReserveList(@Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 取消预约前的提示（实测返回 {@code {tipDesc, userReserveId}}）
     *
     * @param userReserveId 用户预约 ID
     * @param userId        用户 ID
     * @return 响应数据
     */
    @Get(url = "/mp/user/userReserve/cancel/tip")
    StudyRoomClientResult<StudyRoomUserReserveCancelTipClientRespDTO> userReserveCancelTip(@Query(value = "userReserveId") Long userReserveId, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

    /**
     * 取消预约
     * <p>
     * 实测返回 {@code data=1}（整数），不是对象
     *
     * @param form   请求参数
     * @param userId 用户 ID
     * @return 响应数据
     */
    @Post(url = "/mp/user/userReserve/cancel")
    StudyRoomClientResult<Integer> userReserveCancel(@JSONBody StudyRoomUserReserveCancelClientReqDTO form, @Var(value = StudyRoomApiConstants.Variable.USER_ID) Long userId);

}
