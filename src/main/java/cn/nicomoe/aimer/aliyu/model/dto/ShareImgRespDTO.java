package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 分享图响应（{@code GET /aliyu/act/getShareImg}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareImgRespDTO {

    /** 用户专属分享图地址（返回 http 时客户端会改写为 https） */
    private String userShareImageUrl;
}