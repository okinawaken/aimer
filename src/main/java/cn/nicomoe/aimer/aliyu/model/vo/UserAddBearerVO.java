package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.UserAddBearerRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 新增持票人响应（{@code POST /user/addBearer}）。
 */
@Data
@AutoMapper(target = UserAddBearerRespDTO.class)
public class UserAddBearerVO {

    /**
     * 持票人 ID（实名校验通过时返回）
     */
    private String bearerId;
}
