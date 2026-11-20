package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ProjectPrivilegeCheckRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 权益校验（{@code GET /pro/privilegeCheck}）。
 */
@Data
@AutoMapper(target = ProjectPrivilegeCheckRespDTO.class)
public class ProjectPrivilegeCheckVO {

    /**
     * 是否拥有该购买特权
     */
    private Boolean hasPrivilege;
}
