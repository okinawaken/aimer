package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 团购券核销结果（/mp/receipt/consume、/mp/douYin/certificate/prepareAndVerify）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomConsumeResultClientRespDTO {

    /**
     * 核销状态
     */
    private Integer consumeState;

    /**
     * 核销说明
     */
    private String consumeMsg;

    /**
     * 核销套餐名称
     */
    private String packageName;

    /**
     * 券码
     */
    private String orderNumber;
}
