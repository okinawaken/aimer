package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 门店设施
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomVenueDeviceClientRespDTO {

    /**
     * 图标地址
     */
    private String icon;

    /**
     * 设施名称
     */
    private String title;
}
