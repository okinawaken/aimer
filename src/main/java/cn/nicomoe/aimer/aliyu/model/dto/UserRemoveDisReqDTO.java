package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 删除联系人请求（{@code POST /user/removeDis}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRemoveDisReqDTO {

    /** 联系人 ID */
    private String disId;
}
