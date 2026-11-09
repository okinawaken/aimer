package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 门店营业时间点
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomTimePointClientRespDTO {

    /**
     * 小时
     */
    private Integer hour;

    /**
     * 分钟
     */
    private Integer minute;
}
