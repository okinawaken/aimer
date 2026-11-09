package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveCheckClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 可预约校验结果（/mp/reserve/check）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomReserveCheckClientRespDTO.class)
public class StudyRoomReserveCheckVO {

    /**
     * 当前用户可用于预约的套餐列表
     */
    private List<StudyRoomUserPackageVO> canReservePackageList;

    /**
     * 预约状态：0 可预约 1 不可预约
     */
    private Integer reserveState;

    /**
     * 预约状态说明
     */
    private String reserveStateMsg;
}
