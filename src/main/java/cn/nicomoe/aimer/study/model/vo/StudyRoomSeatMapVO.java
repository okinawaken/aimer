package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatMapClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 座位图（/mp/reserve/v2/seat/list、/mp/reserve/seat/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomSeatMapClientRespDTO.class)
public class StudyRoomSeatMapVO {

    /**
     * 预约后保留座位分钟数（字符串，如 30）
     */
    private String reserveHoldMinutes;

    /**
     * 预约可取消次数上限（字符串，如 100）
     */
    private String reserveCancelLimit;

    /**
     * 预约取消提前分钟数（字符串）
     */
    private String reserveCancelMinutes;

    /**
     * 门店信息
     */
    private StudyRoomVenueVO venue;

    /**
     * 区域及座位
     */
    private List<StudyRoomSeatAreaVO> venueAreaList;
}
