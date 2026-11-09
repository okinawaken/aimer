package cn.nicomoe.aimer.study.model.query;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 预约首页查询条件
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomReserveIndexQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前经度
     */
    private Double currentLng;

    /**
     * 当前纬度
     */
    private Double currentLat;

    /**
     * 门店名称
     */
    private String venueName;

    /**
     * 渠道标识
     */
    private Integer channelBili;

    /**
     * 区域 ID
     */
    private Long areaId;
}
