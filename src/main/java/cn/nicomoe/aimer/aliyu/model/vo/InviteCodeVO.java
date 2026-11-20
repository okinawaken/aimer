package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.InviteCodeRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 邀请码 / 分享链接响应（{@code GET /aliyu/act/getInviteCode}）。
 */
@Data
@AutoMapper(target = InviteCodeRespDTO.class)
public class InviteCodeVO {

    /**
     * 用户专属分享链接（分享小程序卡片时作为 {@code path}）
     */
    private String userShareLink;
}
