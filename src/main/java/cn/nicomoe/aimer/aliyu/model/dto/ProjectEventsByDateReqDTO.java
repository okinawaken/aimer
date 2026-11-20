package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 按日期查场次请求（{@code GET /pro/eventsByDate}）。 小程序在项目详情页按所选日期拉取场次时传入项目 token、日期与请求类型。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectEventsByDateReqDTO {

    /**
     * 项目 token
     */
    private String projectToken;

    /**
     * 查询日期（{@code yyyy-MM-dd}）
     */
    private String eventDate;

    /**
     * 请求类型，小程序固定传 1
     */
    private Long reqType;
}
