package cn.nicomoe.aimer.study.tpl;

import cn.nicomoe.aimer.study.constant.StudyRoomTplConstants;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.sub_erupt.Tpl;
import xyz.erupt.tpl.annotation.EruptTpl;
import xyz.erupt.tpl.annotation.TplAction;

import java.util.HashMap;
import java.util.Map;

/**
 * 自习室自定义页面处理器（Native 引擎）。
 * <p>
 * Native 引擎只输出静态 HTML 并替换 {@code ${base}}，不消费方法返回值；
 * 页面数据由前端 {@code fetch} 调用 {@code /erupt-api/study/**} 获取。
 * 方法声明 {@code @TplAction} 的意义是登记页面路由（同时作为菜单权限键）。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@EruptTpl(engine = Tpl.Engine.Native)
public class StudyRoomTplHandler {

    /**
     * 运营看板。
     *
     * @return 空模型（Native 不使用）
     */
    @TplAction(value = StudyRoomTplConstants.DASHBOARD, path = "/tpl/study/dashboard.html")
    public Map<String, Object> dashboard() {
        return new HashMap<>();
    }

    /**
     * 自动预约任务管理。
     *
     * @return 空模型（Native 不使用）
     */
    @TplAction(value = StudyRoomTplConstants.AUTO_RESERVE, path = "/tpl/study/auto-reserve.html")
    public Map<String, Object> autoReserve() {
        return new HashMap<>();
    }

    /**
     * 用户设置。
     *
     * @return 空模型（Native 不使用）
     */
    @TplAction(value = StudyRoomTplConstants.USER_SETTING, path = "/tpl/study/user-setting.html")
    public Map<String, Object> userSetting() {
        return new HashMap<>();
    }

    /**
     * 签到签退。
     *
     * @return 空模型（Native 不使用）
     */
    @TplAction(value = StudyRoomTplConstants.SIGN, path = "/tpl/study/sign.html")
    public Map<String, Object> sign() {
        return new HashMap<>();
    }
}
