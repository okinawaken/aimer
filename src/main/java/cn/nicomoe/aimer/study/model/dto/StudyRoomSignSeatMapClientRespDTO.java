package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 签到选座座位图（{@code /mp/study/seat/list}、{@code /mp/study/change/seat/list}）， 比 {@link StudyRoomSeatMapClientRespDTO} 多了门店与当前签到信息。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomSignSeatMapClientRespDTO extends StudyRoomSeatMapClientRespDTO {

    /**
     * 当前签到ID
     */
    private Long userSignId;

    /**
     * 签到扣费套餐
     */
    private StudyRoomUserPackageClientRespDTO signInUserPackage;

    /**
     * 是否再签一个座位：1 是
     */
    private Integer signAnotherFlag;
}
