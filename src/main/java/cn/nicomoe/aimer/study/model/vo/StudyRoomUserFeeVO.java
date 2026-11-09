package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserFeeClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 费用/签到状态（/mp/user/info/fee）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomUserFeeClientRespDTO.class)
public class StudyRoomUserFeeVO {

    /**
     * 是否有欠费：1 有
     */
    private Integer hasOwnFee;

    /**
     * 当前签到ID，0 表示未签到
     */
    private Integer userSignId;
}
