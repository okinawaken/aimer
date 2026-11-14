package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 联系人 / 收件人列表查询请求（{@code GET /user/getDis}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGetDisReqDTO {

    /** 页码，从 1 开始。 */
    private Integer page;

    /** 每页数量，小程序传 20。 */
    private Integer pageSize;
}