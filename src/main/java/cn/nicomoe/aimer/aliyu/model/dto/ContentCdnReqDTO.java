package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** CDN 配置查询请求（{@code GET /cms/getCdn}）。 {@code type=1} 查项目静态资源，{@code type=2} 查场次日期静态资源（需 {@code eventDate}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentCdnReqDTO {

    /** 资源类型：1=项目，2=场次。 */
    private Integer type;

    /** 项目 token（{@code type=1}/{@code 2} 均传）。 */
    private String projectToken;

    /** 场次日期，仅 {@code type=2} 传。 */
    private String eventDate;
}