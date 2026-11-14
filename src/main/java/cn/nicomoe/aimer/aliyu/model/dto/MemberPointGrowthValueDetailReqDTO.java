package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 成长值明细查询请求（{@code GET /aliyupoint/getGrowthValueDetail}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointGrowthValueDetailReqDTO {

    /** 页码，从 1 开始。 */
    private Integer page;

    /** 每页数量，小程序传 10。 */
    private Integer pageSize;
}