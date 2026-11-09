package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatMapClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 签到选座座位图（/mp/study/seat/list、/mp/study/change/seat/list）
 * <p>
 * 与 {@link StudyRoomSeatMapVO} 结构一致，额外带上门店与当前签到信息。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomSignSeatMapClientRespDTO.class)
public class StudyRoomSignSeatMapVO extends StudyRoomSeatMapVO {

    /**
     * 当前签到ID
     */
    private Long userSignId;

    /**
     * 签到扣费套餐
     */
    private StudyRoomUserPackageVO signInUserPackage;

    /**
     * 是否再签一个座位：1 是
     */
    private Integer signAnotherFlag;
}
