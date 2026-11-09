package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/**
 * 抖音券核销（/mp/douYin/certificate/prepareAndVerify）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomDouYinVerifyClientReqDTO {

    /**
     * 门店ID（必填）
     */
    private Integer venueId;

    /**
     * 券码（必填）
     */
    private String receiptCode;
}
