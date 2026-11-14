package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 项目列表查询请求（{@code GET /pro/projects}）。 小程序在 data 上透传筛选条件并追加 {@code siteId}，字段均为可选。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectListReqDTO {

    /** 页码，从 1 开始。 */
    private Integer page;

    /** 每页数量。 */
    private Integer pageSize;

    /** 是否重置列表（切换筛选条件时传 true）。 */
    private Boolean reset;

    /** 站点 ID。 */
    private String siteId;

    /** 项目分类 / 赛事 ID。 */
    private String projectClass;

    /** 城市 ID。 */
    private String city;

    /** 大区 ID。 */
    private String areaId;

    /** 开始时间（yyyy/mm/dd）。 */
    private String startTime;

    /** 结束时间（yyyy/mm/dd）。 */
    private String endTime;

    /** 项目名称，用于 {@code /pro/language/projects} 搜索。 */
    private String projectName;
}
