package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.MemberCouponRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 会员券（{@code GET /aliyuMember/memberCoupon/detail}、{@code POST /aliyuMember/memberCoupon/list} 列表项）。
 */
@Data
@AutoMapper(target = MemberCouponRespDTO.class)
public class MemberCouponVO {

    /**
     * 会员券 ID（列表 key 与详情查询入参）
     */
    private String memberCouponId;

    /**
     * 券模板 ID（mock 中与 memberCouponId 同值）
     */
    private String couponId;

    /**
     * 券名称
     */
    private String couponName;

    /**
     * 券图片（mock 为 null，卡片按 {@code couponType} 使用内置占位图）
     */
    private String couponPic;

    /**
     * 券类型（字符串；{@code "2"} 为代金券，其余按兑换券展示）
     */
    private String couponType;

    /**
     * 券类型描述（如「代金券」）
     */
    private String couponTypeDesc;

    /**
     * 生效开始时间（字符串，如 {@code 2026-05-05 10:00}）
     */
    private String validStartTime;

    /**
     * 生效结束时间（字符串）
     */
    private String validEndTime;

    /**
     * 有效期文案（如 {@code 2026-05-05 10:00至2026-08-05 10:00}，直接展示）
     */
    private String validityText;

    /**
     * 适用门店文案（如「吉伊卡哇杭州旗舰店」）
     */
    private String storeText;

    /**
     * 券状态（列表 tab 筛选用；历史券 tab 传 {@code 3} 已使用 / {@code 4} 已过期）
     */
    private String couponStatus;

    /**
     * 券状态描述（如「未使用」「可使用」）
     */
    private String couponStatusDesc;

    /**
     * 是否展示状态角标
     */
    private Boolean showStatusCornerMark;

    /**
     * 使用说明（多行文案，卡片可展开）
     */
    private String usageInstruction;

    /**
     * 是否适用全部门店（false 时才透传 {@code shopIds}）
     */
    private Boolean supportAllShops;

    /**
     * 指定适用门店 ID 列表（前端 {@code join(",")} 后作为路由参数传递）
     */
    private java.util.List<Long> shopIds;
}
