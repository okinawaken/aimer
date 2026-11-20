package cn.nicomoe.aimer.study.tpl;

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
 * 向 Erupt 注册自习室自定义页面菜单，启动时与 {@code e_upms_menu} 增量对齐。 菜单 {@code value} 即模板路由，同时是权限键。
 */
@Component
public class StudyRoomTplModule implements EruptModule {

    static {
        EruptModuleInvoke.addEruptModule(StudyRoomTplModule.class);
    }

    @Override
    public ModuleInfo info() {
        return ModuleInfo.builder()
                .name("study-room")
                .group(ModuleGroup.BUSINESS)
                .description("自习室运营后台：自动预约、用户设置与业务页面")
                .build();
    }

    @Override
    public List<MetaMenu> initMenus() {
        List<MetaMenu> menus = new ArrayList<>();
        MetaMenu root = MetaMenu.createRootMenu("study-room", "自习室", "fa fa-book", 20);
        menus.add(root);
        menus.add(this.page(root, "study-room-dashboard", "运营看板", StudyRoomTplConstants.DASHBOARD, "fa fa-area-chart", 10, MenuStatus.OPEN));
        menus.add(this.page(root, "study-room-auto-reserve", "自动预约", StudyRoomTplConstants.AUTO_RESERVE, "fa fa-clock-o", 20, MenuStatus.OPEN));
        menus.add(this.page(root, "study-room-sign", "签到签退", StudyRoomTplConstants.SIGN, "fa fa-sign-in", 30, MenuStatus.OPEN));
        menus.add(this.page(root, "study-room-user-setting", "用户设置", StudyRoomTplConstants.USER_SETTING, "fa fa-sliders", 40, MenuStatus.OPEN));
        return menus;
    }

    private MetaMenu page(MetaMenu parent, String code, String name, String value, String icon, Integer sort,
                          MenuStatus menuStatus) {
        return MetaMenu.createSimpleMenu(code, name, value, parent, sort, icon, EruptTplService.TPL, menuStatus);
    }
}
