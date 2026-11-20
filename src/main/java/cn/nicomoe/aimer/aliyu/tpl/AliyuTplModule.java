package cn.nicomoe.aimer.aliyu.tpl;

import cn.nicomoe.aimer.aliyu.constant.AliyuTplConstants;
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
 * 向 Erupt 注册阿里鱼自定义页面菜单，启动时与 {@code e_upms_menu} 增量对齐。 菜单 {@code value} 即模板路由，同时是权限键。
 */
@Component
public class AliyuTplModule implements EruptModule {

    static {
        EruptModuleInvoke.addEruptModule(AliyuTplModule.class);
    }

    @Override
    public ModuleInfo info() {
        return ModuleInfo.builder()
                .name("aliyu")
                .group(ModuleGroup.BUSINESS)
                .description("阿里鱼运营后台：演出浏览、购票、演出日历与账号资料")
                .build();
    }

    @Override
    public List<MetaMenu> initMenus() {
        List<MetaMenu> menus = new ArrayList<>();
        MetaMenu root = MetaMenu.createRootMenu("aliyu", "阿里鱼", "fa fa-ticket", 30);
        menus.add(root);
        menus.add(this.page(root, "aliyu-user-setting", "用户设置", AliyuTplConstants.USER_SETTING, "fa fa-sliders", 10, MenuStatus.OPEN));
        menus.add(this.page(root, "aliyu-profile", "个人资料", AliyuTplConstants.PROFILE, "fa fa-user", 20, MenuStatus.OPEN));
        menus.add(this.page(root, "aliyu-show-calendar", "演出日历", AliyuTplConstants.SHOW_CALENDAR, "fa fa-calendar", 30, MenuStatus.OPEN));
        menus.add(this.page(root, "aliyu-shows", "演出列表", AliyuTplConstants.SHOWS, "fa fa-ticket", 40, MenuStatus.OPEN));

        menus.add(this.page(root, "aliyu-show-detail", "演出详情", AliyuTplConstants.SHOW_DETAIL, "fa fa-file-text-o", 50, MenuStatus.HIDE));
        menus.add(this.page(root, "aliyu-purchase", "演出购票", AliyuTplConstants.PURCHASE, "fa fa-credit-card", 60, MenuStatus.HIDE));
        return menus;
    }

    private MetaMenu page(MetaMenu parent, String code, String name, String value, String icon, Integer sort,
                          MenuStatus status) {
        return MetaMenu.createSimpleMenu(code, name, value, parent, sort, icon, EruptTplService.TPL, status);
    }
}
