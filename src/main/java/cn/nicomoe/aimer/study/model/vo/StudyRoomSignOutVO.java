package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomSignOutClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 签退结算结果（/mp/study/signOut）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomSignOutClientRespDTO.class)
public class StudyRoomSignOutVO {

    /**
     * 签到ID
     */
    private Long userSignId;

    /**
     * 扣费套餐名称
     */
    private String packageName;

    /**
     * 结算时长
     */
    private String settleHourStr;

    /**
     * 预计签退时间
     */
    private String signOutExpectTimeStr;

    /**
     * 学习小时数
     */
    private Integer studyHours;

    /**
     * 学习分钟数
     */
    private Integer studyMinutes;

    /**
     * 本次扣费（元）
     */
    private BigDecimal payPriceYuan;

    /**
     * 实际签退时间
     */
    private String signOutTimeStr;

    /**
     * 签退提示文案（部分门店/套餐会返回自定义提示）
     */
    private String studyTipText;
}
