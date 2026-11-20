package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatMapClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 签到选座座位图（{@code /mp/study/seat/list}、{@code /mp/study/change/seat/list}）， 比 {@link StudyRoomSeatMapVO} 多了门店与当前签到信息。
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
