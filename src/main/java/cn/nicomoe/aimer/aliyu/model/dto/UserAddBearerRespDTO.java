package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 新增持票人响应（{@code POST /user/addBearer}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAddBearerRespDTO {

    /**
     * 持票人 ID（实名校验通过时返回）
     */
    private String bearerId;
}
