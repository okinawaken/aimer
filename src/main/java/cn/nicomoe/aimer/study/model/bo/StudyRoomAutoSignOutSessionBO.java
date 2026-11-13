package cn.nicomoe.aimer.study.model.bo;

import lombok.Data;

/**
 * 自动签退的单条在座会话执行计划
 * <p>
 * 由过滤器链解析完毕，执行器据此逐条发起签退请求，无需再做业务判断。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomAutoSignOutSessionBO {

    /**
     * 签到ID（签退请求的 {@code userSignId}）
     */
    private Long userSignId;

    /**
     * 签退扣费套餐ID（签退请求的 {@code settleUserPackageId}）
     */
    private Long settleUserPackageId;

    /**
     * 当前经度，取门店经度
     */
    private Double currentLng;

    /**
     * 当前纬度，取门店纬度
     */
    private Double currentLat;

    /**
     * 门店名称，仅用于日志
     */
    private String venueName;

    /**
     * 座位号，仅用于日志
     */
    private String seatNumber;
}
