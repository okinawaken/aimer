package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 设置开售提醒（{@code POST /pro/hasSaleRemind}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectHasSaleRemindReqDTO {

    /**
     * 项目 ID
     */
    private String projectId;

    /**
     * 场次 ID
     */
    private Long eventId;
}
