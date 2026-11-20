package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.DynamicQrCodeRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 动态会员码响应（{@code GET /aliyupoint/getDynamicQrCode}）。
 */
@Data
@AutoMapper(target = DynamicQrCodeRespDTO.class)
public class DynamicQrCodeVO {

    /**
     * 动态码内容（用于生成二维码）
     */
    private String code;

    /**
     * 动态码失效时间（毫秒时间戳）
     */
    private Long validTime;
}
