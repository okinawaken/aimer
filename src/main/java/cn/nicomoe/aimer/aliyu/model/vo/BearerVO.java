package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.BearerRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 持票人 / 参与人列表元素（{@code GET /user/getBearers} 的 {@code dataList} 项）。
 */
@Data
@AutoMapper(target = BearerRespDTO.class)
public class BearerVO {

    /**
     * 持票人 ID（勾选、删除时回传服务端）
     */
    private String bearerId;

    /**
     * 持票人姓名
     */
    private String bearerName;

    /**
     * 证件号（页面直接展示）
     */
    private String identification;

    /**
     * 证件类型（页面以 {@code String(identificationType)} 查字典，取值如 "1".."12"）
     */
    private String identificationType;

    /**
     * 证件类型是否可用（{@code false} 时该参与人在当前订单不可勾选）
     */
    private Boolean identificationEnable;
}
