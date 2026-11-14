package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 邀请码 / 分享链接响应（{@code GET /aliyu/act/getInviteCode}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InviteCodeRespDTO {

    /** 用户专属分享链接（分享小程序卡片时作为 {@code path}） */
    private String userShareLink;
}