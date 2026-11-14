package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 小鱼丸（积分）明细查询请求（{@code GET /aliyupoint/getPointsDetail}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointPointsDetailReqDTO {

    /** 会员卡类型。 */
    private String cardType;

    /** 页码，从 1 开始。 */
    private Integer page;

    /** 每页数量，小程序传 10。 */
    private Integer pageSize;
}