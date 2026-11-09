package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueDeviceClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 门店设施
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomVenueDeviceClientRespDTO.class)
public class StudyRoomVenueDeviceVO {

    /**
     * 图标地址
     */
    private String icon;

    /**
     * 设施名称
     */
    private String title;
}
