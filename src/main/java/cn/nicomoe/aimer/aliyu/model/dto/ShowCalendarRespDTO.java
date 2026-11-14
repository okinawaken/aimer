package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 演出日历（{@code GET /pro/getShowCalendar}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowCalendarRespDTO {

    /** 演出日期 */
    private String date;

    /** 当日是否售罄 */
    private Boolean sellOut;
}