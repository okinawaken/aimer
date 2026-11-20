package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ShareImgRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 分享图响应（{@code GET /aliyu/act/getShareImg}）。
 */
@Data
@AutoMapper(target = ShareImgRespDTO.class)
public class ShareImgVO {

    /**
     * 用户专属分享图地址（返回 http 时客户端会改写为 https）
     */
    private String userShareImageUrl;
}
