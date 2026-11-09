package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomSettleDataClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 签退结算明细（settlement / userPackage/confirm 的 settleData）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomSettleDataClientRespDTO.class)
public class StudyRoomSettleDataVO {

    /**
     * 结算时长文案
     */
    private String settleTimeStr;

    /**
     * 学习时长描述
     */
    private String studyTimeDesc;

    /**
     * 学习分钟描述
     */
    private String studyMinuteDesc;

    /**
     * 已使用时长
     */
    private String usedTimeStr;

}
