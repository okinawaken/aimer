package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 新增持票人请求（{@code POST /user/addBearer}）。 {@code identification} 为 RSA 加密字段；错误码 {@code 16020129} 表示实名校验未通过。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAddBearerReqDTO {

    /**
     * 持票人姓名
     */
    private String bearerName;

    /**
     * 证件号（RSA 加密字段）
     */
    private String identification;

    /**
     * 证件类型
     */
    private String identificationType;
}
