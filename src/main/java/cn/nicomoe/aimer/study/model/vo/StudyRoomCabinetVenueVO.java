package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetVenueClientRespDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 储物柜（柜体）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomCabinetVenueClientRespDTO.class)
public class StudyRoomCabinetVenueVO {

    /**
     * 柜子ID（上游字段名为 cabinetId）
     */
    @JsonProperty(value = "cabinetId")
    private Integer cabinetId;

    /**
     * 柜子名称
     */
    private String cabinetName;

    /**
     * 门店ID
     */
    private Integer venueId;

    /**
     * 在售格口数
     */
    private Integer saleCount;

}
