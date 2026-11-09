package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueSimpleClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 门店简要信息（/mp/reserve/venue/select、/mp/venue/select、签到记录里的 venue）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomVenueSimpleClientRespDTO.class)
public class StudyRoomVenueSimpleVO {

    /**
     * 门店ID
     */
    private Integer id;

    /**
     * 门店名称
     */
    private String venueName;

    /**
     * 美团门店编码
     */
    private String meituanShopCode;

    /**
     * 美团门店名称
     */
    private String meituanShopName;

    /**
     * 抖音门店 UUID
     */
    private String openShopUuid;
}
