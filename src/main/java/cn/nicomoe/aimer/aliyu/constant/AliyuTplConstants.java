package cn.nicomoe.aimer.aliyu.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 阿里鱼自定义页面路由常量：同时是 {@code @TplAction} 值、菜单 value 与权限键。 取值必须小写、无前导斜杠、不带查询串。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliyuTplConstants {

    /**
     * 用户设置（凭证绑定）
     */
    public static final String USER_SETTING = "aliyu/user-setting";

    /**
     * 个人资料（会员信息与基本资料）
     */
    public static final String PROFILE = "aliyu/profile";

    /**
     * 演出列表
     */
    public static final String SHOWS = "aliyu/shows";

    /**
     * 演出详情
     */
    public static final String SHOW_DETAIL = "aliyu/show-detail";

    /**
     * 演出日历
     */
    public static final String SHOW_CALENDAR = "aliyu/show-calendar";

    /**
     * 演出购票（选票档、确认订单、锁单）
     */
    public static final String PURCHASE = "aliyu/purchase";
}
