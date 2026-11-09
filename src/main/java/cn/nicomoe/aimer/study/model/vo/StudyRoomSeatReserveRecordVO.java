package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomSeatReserveRecordClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 座位在某天的预约占用记录（StudyRoomSeatVO.reserveRecordList 元素）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomSeatReserveRecordClientRespDTO.class)
public class StudyRoomSeatReserveRecordVO {

    /**
     * 预约记录ID
     */
    private Long id;

    /**
     * 预约日期 yyyy-MM-dd
     */
    private String reserveDateStr;

    /**
     * 占用时长，如 13小时
     */
    private String reserveMinuteStr;

    /**
     * 占用时段，如 10:00-23:00
     */
    private String reserveTimeStr;
}
