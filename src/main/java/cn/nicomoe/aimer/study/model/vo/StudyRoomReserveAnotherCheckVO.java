package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveAnotherCheckClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 是否还能再约一座（/mp/reserve/another/check）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomReserveAnotherCheckClientRespDTO.class)
public class StudyRoomReserveAnotherCheckVO {

    /**
     * 是否可再约：1 可以
     */
    private Integer canReserveAnother;

    /**
     * 是否有可用的加座套餐：1 有
     */
    private Integer hasReserveAnotherPackage;

    /**
     * 不可再约原因
     */
    private String descNoReserveAnother;
}
