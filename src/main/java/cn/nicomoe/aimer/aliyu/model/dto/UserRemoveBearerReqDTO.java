package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 删除持票人请求（{@code POST /user/removeBearer}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRemoveBearerReqDTO {

    /** 持票人 ID */
    private String bearerId;
}
