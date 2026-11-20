package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 演出日历查询请求（{@code GET /pro/getShowCalendar}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectShowCalendarReqDTO {

    /**
     * 开始日期（yyyy-MM-dd）。
     */
    private String startDate;

    /**
     * 结束日期（yyyy-MM-dd）。
     */
    private String endDate;
}
