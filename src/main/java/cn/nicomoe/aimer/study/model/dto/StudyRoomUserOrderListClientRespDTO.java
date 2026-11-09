package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 我的订单（/mp/user/userOrder/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomUserOrderListClientRespDTO {

    /**
     * 订单列表
     */
    private List<StudyRoomUserOrderClientRespDTO> list;
}
