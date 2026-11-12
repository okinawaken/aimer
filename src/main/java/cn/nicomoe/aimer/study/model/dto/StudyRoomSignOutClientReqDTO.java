package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/**
 * 签退（/mp/study/signOut）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomSignOutClientReqDTO {

    /**
     * 签退二维码内容：场馆 {@code qrCodeNeedSignOut=true} 时为座位扫码结果（必填），
     * 否则传空串 {@code ""}
     */
    private String qrCode;

    /**
     * 当前经度
     */
    private Double currentLng;

    /**
     * 当前纬度
     */
    private Double currentLat;

    /**
     * 结算套餐ID（用户套餐ID）（必填）
     */
    private Long settleUserPackageId;

    /**
     * 当前签到ID（必填）
     */
    private Long userSignId;
}
