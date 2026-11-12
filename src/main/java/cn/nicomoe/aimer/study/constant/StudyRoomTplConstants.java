package cn.nicomoe.aimer.study.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 自习室自定义页面（erupt-tpl）路由常量。
 * <p>
 * 页面路由同时作为 {@code @TplAction.value}、菜单 {@code value} 与权限键，
 * 统一在此维护，避免三者漂移。所有取值必须为小写、无前导斜杠、不带查询串。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
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
