package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.MemberPointDeregisterUserRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 注销会员响应（{@code POST /aliyupoint/deregisterUser}）。
 */
@Data
@AutoMapper(target = MemberPointDeregisterUserRespDTO.class)
public class MemberPointDeregisterUserVO {

    /**
     * 注销申请是否提交成功
     */
    private Boolean success;
}
