package cn.nicomoe.aimer.study.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 签到首页（/mp/study/index）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomIndexClientReqDTO {

    /**
     * 当前经度（必填）
     */
    @NotNull(message = "当前经度不能为空")
    private Double currentLng;

    /**
     * 当前纬度（必填）
     */
    @NotNull(message = "当前纬度不能为空")
    private Double currentLat;

    /**
     * 门店ID
     */
    private Integer venueId;

    /**
     * BSSID
     */
    private String bssid;

    /**
     * SSID
     */
    private String ssid;
}
