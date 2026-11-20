package cn.nicomoe.aimer.aliyu.tpl;

import cn.nicomoe.aimer.aliyu.constant.AliyuTplConstants;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.sub_erupt.Tpl;
import xyz.erupt.tpl.annotation.EruptTpl;
import xyz.erupt.tpl.annotation.TplAction;

import java.util.HashMap;
import java.util.Map;

/**
 * 阿里鱼自定义页面处理器（Native 引擎）。 引擎只输出静态 HTML 并替换 {@code ${base}}，页面数据由前端 fetch {@code /erupt-api/aliyu/**} 获取； 方法上的 {@code @TplAction} 只为登记路由与权限键，返回值不被消费。
 */
@Component
@EruptTpl(engine = Tpl.Engine.Native)
public class AliyuTplHandler {

    /**
     * 用户设置。 返回：空模型（Native 不使用）
     */
    @TplAction(value = AliyuTplConstants.USER_SETTING, path = "/tpl/aliyu/user-setting.html")
    public Map<String, Object> userSetting() {
        return new HashMap<>();
    }

    /**
     * 个人资料。 返回：空模型（Native 不使用）
     */
    @TplAction(value = AliyuTplConstants.PROFILE, path = "/tpl/aliyu/profile.html")
    public Map<String, Object> profile() {
        return new HashMap<>();
    }

    /**
     * 演出列表。 返回：空模型（Native 不使用）
     */
    @TplAction(value = AliyuTplConstants.SHOWS, path = "/tpl/aliyu/shows.html")
    public Map<String, Object> shows() {
        return new HashMap<>();
    }

    /**
     * 演出详情（由列表带 projectToken 进入）。 返回：空模型（Native 不使用）
     */
    @TplAction(value = AliyuTplConstants.SHOW_DETAIL, path = "/tpl/aliyu/show-detail.html")
    public Map<String, Object> showDetail() {
        return new HashMap<>();
    }

    /**
     * 演出日历。 返回：空模型（Native 不使用）
     */
    @TplAction(value = AliyuTplConstants.SHOW_CALENDAR, path = "/tpl/aliyu/show-calendar.html")
    public Map<String, Object> showCalendar() {
        return new HashMap<>();
    }

    /**
     * 演出购票（由详情页带 projectToken、eventToken 进入）。 返回：空模型（Native 不使用）
     */
    @TplAction(value = AliyuTplConstants.PURCHASE, path = "/tpl/aliyu/purchase.html")
    public Map<String, Object> purchase() {
        return new HashMap<>();
    }
}
