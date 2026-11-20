package cn.nicomoe.aimer.study.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 自定义页面路由常量：同时是 {@code @TplAction} 值、菜单 value 与权限键。 取值必须小写、无前导斜杠、不带查询串。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StudyRoomTplConstants {

    /**
     * 运营看板
     */
    public static final String DASHBOARD = "study/dashboard";

    /**
     * 自动预约任务管理
     */
    public static final String AUTO_RESERVE = "study/auto-reserve";

    /**
     * 用户设置
     */
    public static final String USER_SETTING = "study/user-setting";

    /**
     * 签到签退
     */
    public static final String SIGN = "study/sign";
}
