package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 注销会员响应（{@code POST /aliyupoint/deregisterUser}）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPointDeregisterUserRespDTO {

    /**
     * 注销申请是否提交成功
     */
    private Boolean success;
}
