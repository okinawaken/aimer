package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 费用/签到状态（/mp/user/info/fee）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomUserFeeClientRespDTO {

    /**
     * 是否有欠费：1 有
     */
    private Integer hasOwnFee;

    /**
     * 当前签到ID，0 表示未签到
     */
    private Long userSignId;
}
