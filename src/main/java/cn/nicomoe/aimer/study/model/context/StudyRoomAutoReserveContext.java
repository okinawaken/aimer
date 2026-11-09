package cn.nicomoe.aimer.study.model.context;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveConfirmClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * 自动预约执行上下文
 * <p>
 * 在过滤器链中传递任务、目标日期、座位与终态信息。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@RequiredArgsConstructor
public class StudyRoomAutoReserveContext {

    /**
     * 任务数据
     */
    private final StudyRoomAutoReserveTaskInfoDTO taskInfoDTO;

    /**
     * 触发方式
     */
    private final Integer triggerType;

    /**
     * 目标预约日期
     */
    private LocalDate targetDate;

    /**
     * 选中的门店
     */
    private StudyRoomVenueClientRespDTO venueClientRespDTO;

    /**
     * 选中的座位
     */
    private StudyRoomSeatClientRespDTO seatClientRespDTO;

    /**
     * 预约确认结果
     */
    private StudyRoomReserveConfirmClientRespDTO reserveConfirmClientRespDTO;
}
