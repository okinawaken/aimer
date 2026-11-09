package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageConfirmClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 确认签退结算套餐（/mp/study/userPackage/confirm）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomUserPackageConfirmClientRespDTO.class)
public class StudyRoomUserPackageConfirmVO {

    /**
     * 欠费分钟数
     */
    private Integer unpaidMinute;

    /**
     * 结算使用的用户套餐ID
     */
    private Long userPackageId;

    /**
     * 签退类型
     */
    private Integer signOutType;

    /**
     * 提示文案
     */
    private String studyTipText;

    /**
     * 结算明细
     */
    private StudyRoomSettleDataVO settleData;

}
