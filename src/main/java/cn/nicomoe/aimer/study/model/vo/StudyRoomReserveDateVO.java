package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveDateClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 可预约日期（/mp/reserve/v2/date/select）
 * <p>
 * 原始返回值形如 {@code ["2026-10-26 (周一)","2026-10-27 (周二)"]}， 后续接口的 reserveDateStr 只取空格前部分，如 2026-10-26。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomReserveDateClientRespDTO.class)
public class StudyRoomReserveDateVO {

    /**
     * 可预约日期，格式 yyyy-MM-dd (周X)
     */
    private List<String> reserveDateList;
}
