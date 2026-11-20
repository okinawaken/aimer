package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 权益校验请求（{@code GET /pro/privilegeCheck}）。 小程序传 {@code privilegeId}（字符串化）与 {@code eventId}（场次 token）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectPrivilegeCheckReqDTO {

    /**
     * 权益 ID。
     */
    private String privilegeId;

    /**
     * 场次 token。
     */
    private String eventId;
}
