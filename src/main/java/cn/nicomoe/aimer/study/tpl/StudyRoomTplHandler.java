package cn.nicomoe.aimer.study.tpl;

import cn.nicomoe.aimer.study.constant.StudyRoomTplConstants;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.sub_erupt.Tpl;
import xyz.erupt.tpl.annotation.EruptTpl;
import xyz.erupt.tpl.annotation.TplAction;

import java.util.HashMap;
import java.util.Map;

/** 自习室自定义页面处理器（Native 引擎）。 引擎只输出静态 HTML 并替换 {@code ${base}}，页面数据由前端 fetch {@code /erupt-api/study/**} 获取； 方法上的 {@code @TplAction} 只为登记路由与权限键，返回值不被消费。 */
@Component
@EruptTpl(engine = Tpl.Engine.Native)
public class StudyRoomTplHandler {

    /** 运营看板。 返回：空模型（Native 不使用） */
    @TplAction(value = StudyRoomTplConstants.DASHBOARD, path = "/tpl/study/dashboard.html")
    public Map<String, Object> dashboard() {
        return new HashMap<>();
    }

    /** 自动预约任务管理。 返回：空模型（Native 不使用） */
    @TplAction(value = StudyRoomTplConstants.AUTO_RESERVE, path = "/tpl/study/auto-reserve.html")
    public Map<String, Object> autoReserve() {
        return new HashMap<>();
    }

    /** 用户设置。 返回：空模型（Native 不使用） */
    @TplAction(value = StudyRoomTplConstants.USER_SETTING, path = "/tpl/study/user-setting.html")
    public Map<String, Object> userSetting() {
        return new HashMap<>();
    }

    /** 签到签退。 返回：空模型（Native 不使用） */
    @TplAction(value = StudyRoomTplConstants.SIGN, path = "/tpl/study/sign.html")
    public Map<String, Object> sign() {
        return new HashMap<>();
    }
}
