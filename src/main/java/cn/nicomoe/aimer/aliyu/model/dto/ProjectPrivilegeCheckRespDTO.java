package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 权益校验（{@code GET /pro/privilegeCheck}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectPrivilegeCheckRespDTO {

    /**
     * 是否拥有该购买特权
     */
    private Boolean hasPrivilege;
}
