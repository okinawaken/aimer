package cn.nicomoe.aimer.study.module;

import cn.nicomoe.aimer.study.constant.StudyRoomTplConstants;
import org.springframework.stereotype.Component;
import xyz.erupt.core.constant.MenuStatus;
import xyz.erupt.core.module.EruptModule;
import xyz.erupt.core.module.EruptModuleInvoke;
import xyz.erupt.core.module.MetaMenu;
import xyz.erupt.core.module.ModuleGroup;
import xyz.erupt.core.module.ModuleInfo;
import xyz.erupt.tpl.service.EruptTplService;

import java.util.ArrayList;
import java.util.List;

/**
 * 自习室后台模块：向 Erupt 注册自定义页面菜单。
 * <p>
 * 启动时 {@code EruptMenuSeeder} 会调用 {@link #initMenus()} 并与 {@code e_upms_menu} 增量对齐；
 * 页面类型为 {@code tpl}，菜单 {@code value} 即模板路由（同时是权限键）。
 * </p>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
public class StudyRoomTplModule implements EruptModule {

    static {
        EruptModuleInvoke.addEruptModule(StudyRoomTplModule.class);
    }

    /**
     * 根菜单编码。
     */
    private static final String ROOT_CODE = "study-room";

    @Override
    public ModuleInfo info() {
        return ModuleInfo.builder()
                .name("study-room")
                .group(ModuleGroup.BUSINESS)
                .description("自习室运营后台：自动预约、用户设置与业务页面")
                .build();
    }

    /**
     * 声明页面菜单。每次调用都返回新对象，避免框架 reconcile 时被 mutate 影响。
     *
     * @return 菜单定义
     */
    @Override
    public List<MetaMenu> initMenus() {
        List<MetaMenu> menus = new ArrayList<>();
        MetaMenu root = MetaMenu.createRootMenu(ROOT_CODE, "自习室", "fa fa-book", 20);
        menus.add(root);
        menus.add(page(root, "study-room-dashboard", "运营看板", StudyRoomTplConstants.DASHBOARD, "fa fa-area-chart", 10));
        menus.add(page(root, "study-room-auto-reserve", "自动预约", StudyRoomTplConstants.AUTO_RESERVE, "fa fa-clock-o", 20));
        menus.add(page(root, "study-room-sign", "签到签退", StudyRoomTplConstants.SIGN, "fa fa-sign-in", 30));
        menus.add(page(root, "study-room-user-setting", "用户设置", StudyRoomTplConstants.USER_SETTING, "fa fa-sliders", 40));
        return menus;
    }

    /**
     * 构造一个 tpl 类型子菜单。
     *
     * @param parent 父菜单
     * @param code   菜单编码
     * @param name   菜单名称
     * @param value  模板路由（= 权限键）
     * @param icon   图标
     * @param sort   排序
     * @return 子菜单
     */
    private MetaMenu page(MetaMenu parent, String code, String name, String value, String icon, Integer sort) {
        return MetaMenu.createSimpleMenu(code, name, value, parent, sort, icon, EruptTplService.TPL, MenuStatus.OPEN);
    }
}
