package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ShowCalendarRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 演出日历（{@code GET /pro/getShowCalendar}）。
 */
@Data
@AutoMapper(target = ShowCalendarRespDTO.class)
public class ShowCalendarVO {

    /**
     * 演出日期
     */
    private String date;

    /**
     * 当日是否售罄
     */
    private Boolean sellOut;
}
