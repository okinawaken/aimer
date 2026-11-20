package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.HotProjectListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ItemBuyRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ItemDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ItemQueryReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectCityListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectColumnInfoReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectColumnInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectEventRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectEventsByDateReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectEventsByDateRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectHasSaleRemindReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectHotReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectPrivilegeCheckReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectPrivilegeCheckRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectShowCalendarReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectTypeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ReceiveBasicInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegionCountryRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegionProvinceRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShowCalendarRespDTO;
import cn.nicomoe.aimer.aliyu.model.vo.HotProjectListVO;
import cn.nicomoe.aimer.aliyu.model.vo.ItemBuyVO;
import cn.nicomoe.aimer.aliyu.model.vo.ItemDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.ProjectCityListVO;
import cn.nicomoe.aimer.aliyu.model.vo.ProjectColumnInfoVO;
import cn.nicomoe.aimer.aliyu.model.vo.ProjectDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.ProjectEventVO;
import cn.nicomoe.aimer.aliyu.model.vo.ProjectEventsByDateVO;
import cn.nicomoe.aimer.aliyu.model.vo.ProjectListVO;
import cn.nicomoe.aimer.aliyu.model.vo.ProjectPrivilegeCheckVO;
import cn.nicomoe.aimer.aliyu.model.vo.ProjectTypeListVO;
import cn.nicomoe.aimer.aliyu.model.vo.ReceiveBasicInfoVO;
import cn.nicomoe.aimer.aliyu.model.vo.RegionCountryVO;
import cn.nicomoe.aimer.aliyu.model.vo.RegionProvinceVO;
import cn.nicomoe.aimer.aliyu.model.vo.ShowCalendarVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuProjectService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

import java.util.List;

/**
 * 阿里鱼项目 / 演出域接口：项目、场次、地区、商品。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/project")
public class AliyuProjectController {

    private final AliyuProjectService projectService;

    /**
     * 项目列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/list")
    public R<ProjectListVO> projects(ProjectListReqDTO query) {
        ProjectListRespDTO projectListDTO = projectService.projects(query);
        ProjectListVO projectListVO = MapstructUtils.convert(projectListDTO, ProjectListVO.class);
        return R.ok(projectListVO);
    }

    /**
     * 分类项目列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/customizable/list")
    public R<ProjectListVO> customizableProjects(ProjectListReqDTO query) {
        ProjectListRespDTO projectListDTO = projectService.customizableProjects(query);
        ProjectListVO projectListVO = MapstructUtils.convert(projectListDTO, ProjectListVO.class);
        return R.ok(projectListVO);
    }

    /**
     * 按项目名称检索。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/language/list")
    public R<ProjectListVO> languageProjects(ProjectListReqDTO query) {
        ProjectListRespDTO projectListDTO = projectService.languageProjects(query);
        ProjectListVO projectListVO = MapstructUtils.convert(projectListDTO, ProjectListVO.class);
        return R.ok(projectListVO);
    }

    /**
     * 项目详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/detail")
    public R<ProjectDetailVO> project(
            @RequestParam(value = "projectToken") String projectToken,
            @RequestParam(value = "reqType", required = false) Long reqType,
            @RequestParam(value = "eventDate", required = false) String eventDate) {
        ProjectDetailRespDTO projectDetailDTO = projectService.project(projectToken, reqType, eventDate);
        ProjectDetailVO projectDetailVO = MapstructUtils.convert(projectDetailDTO, ProjectDetailVO.class);
        return R.ok(projectDetailVO);
    }

    /**
     * 场次详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/event")
    public R<ProjectEventVO> event(@RequestParam(value = "eventToken") String eventToken) {
        ProjectEventRespDTO eventDTO = projectService.event(eventToken);
        ProjectEventVO eventVO = MapstructUtils.convert(eventDTO, ProjectEventVO.class);
        return R.ok(eventVO);
    }

    /**
     * 按日期查场次。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/events/by-date")
    public R<ProjectEventsByDateVO> eventsByDate(ProjectEventsByDateReqDTO query) {
        ProjectEventsByDateRespDTO eventsByDateDTO = projectService.eventsByDate(query);
        ProjectEventsByDateVO eventsByDateVO = MapstructUtils.convert(eventsByDateDTO, ProjectEventsByDateVO.class);
        return R.ok(eventsByDateVO);
    }

    /**
     * 演出日历。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/show-calendar")
    public R<List<ShowCalendarVO>> getShowCalendar(ProjectShowCalendarReqDTO query) {
        List<ShowCalendarRespDTO> showCalendarListDTO = projectService.getShowCalendar(query);
        List<ShowCalendarVO> showCalendarListVO = MapstructUtils.convert(showCalendarListDTO, ShowCalendarVO.class);
        return R.ok(showCalendarListVO);
    }

    /**
     * 栏目信息。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/column/info")
    public R<ProjectColumnInfoVO> getColumnInfo(ProjectColumnInfoReqDTO query) {
        ProjectColumnInfoRespDTO columnInfoDTO = projectService.getColumnInfo(query);
        ProjectColumnInfoVO columnInfoVO = MapstructUtils.convert(columnInfoDTO, ProjectColumnInfoVO.class);
        return R.ok(columnInfoVO);
    }

    /**
     * 热门项目。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/hot")
    public R<HotProjectListVO> hotProjectVos(ProjectHotReqDTO query) {
        HotProjectListRespDTO hotProjectListDTO = projectService.hotProjectVos(query);
        HotProjectListVO hotProjectListVO = MapstructUtils.convert(hotProjectListDTO, HotProjectListVO.class);
        return R.ok(hotProjectListVO);
    }

    /**
     * 权益校验。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/privilege/check")
    public R<ProjectPrivilegeCheckVO> privilegeCheck(ProjectPrivilegeCheckReqDTO query) {
        ProjectPrivilegeCheckRespDTO privilegeCheckDTO = projectService.privilegeCheck(query);
        ProjectPrivilegeCheckVO privilegeCheckVO = MapstructUtils.convert(privilegeCheckDTO, ProjectPrivilegeCheckVO.class);
        return R.ok(privilegeCheckVO);
    }

    /**
     * 查询项目 / 场次是否已设置开售提醒。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/sale-remind")
    public R<Boolean> hasSaleRemind(@RequestBody ProjectHasSaleRemindReqDTO form) {
        return R.ok(projectService.hasSaleRemind(form));
    }

    /**
     * 项目分类。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/type/list")
    public R<List<ProjectTypeListVO>> getProjectTypeList() {
        List<ProjectTypeListRespDTO> projectTypeListDTO = projectService.getProjectTypeList();
        List<ProjectTypeListVO> projectTypeListVO = MapstructUtils.convert(projectTypeListDTO, ProjectTypeListVO.class);
        return R.ok(projectTypeListVO);
    }

    /**
     * 项目所在城市。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/city/list")
    public R<List<ProjectCityListVO>> getProjectCityList() {
        List<ProjectCityListRespDTO> projectCityListDTO = projectService.getProjectCityList();
        List<ProjectCityListVO> projectCityListVO = MapstructUtils.convert(projectCityListDTO, ProjectCityListVO.class);
        return R.ok(projectCityListVO);
    }

    /**
     * 地区城市列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/area/city/list")
    public R<List<ProjectCityListVO>> getAreaCityList() {
        List<ProjectCityListRespDTO> areaCityListDTO = projectService.getAreaCityList();
        List<ProjectCityListVO> areaCityListVO = MapstructUtils.convert(areaCityListDTO, ProjectCityListVO.class);
        return R.ok(areaCityListVO);
    }

    /**
     * 按国家查地区。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/region/country")
    public R<RegionCountryVO> getRegionByCountryId(
            @RequestParam(value = "countryId") String countryId,
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "ptnrId", required = false) String ptnrId) {
        RegionCountryRespDTO regionCountryDTO = projectService.getRegionByCountryId(countryId, tenantId, ptnrId);
        RegionCountryVO regionCountryVO = MapstructUtils.convert(regionCountryDTO, RegionCountryVO.class);
        return R.ok(regionCountryVO);
    }

    /**
     * 按省查地区。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/region/province")
    public R<RegionProvinceVO> getRegionByProvinceId(
            @RequestParam(value = "parentId") String parentId,
            @RequestParam(value = "countryId", required = false) String countryId) {
        RegionProvinceRespDTO regionProvinceDTO = projectService.getRegionByProvinceId(parentId, countryId);
        RegionProvinceVO regionProvinceVO = MapstructUtils.convert(regionProvinceDTO, RegionProvinceVO.class);
        return R.ok(regionProvinceVO);
    }

    /**
     * 收件基础信息（快递票）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/receive/basic-info")
    public R<ReceiveBasicInfoVO> loadReceiveBasicInfo() {
        ReceiveBasicInfoRespDTO receiveBasicInfoDTO = projectService.loadReceiveBasicInfo();
        ReceiveBasicInfoVO receiveBasicInfoVO = MapstructUtils.convert(receiveBasicInfoDTO, ReceiveBasicInfoVO.class);
        return R.ok(receiveBasicInfoVO);
    }

    /**
     * 商品详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/item/detail")
    public R<ItemDetailVO> itemDetail(ItemQueryReqDTO query) {
        ItemDetailRespDTO itemDetailDTO = projectService.itemDetail(query);
        ItemDetailVO itemDetailVO = MapstructUtils.convert(itemDetailDTO, ItemDetailVO.class);
        return R.ok(itemDetailVO);
    }

    /**
     * 商品购买。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/item/buy")
    public R<ItemBuyVO> itemBuy(ItemQueryReqDTO query) {
        ItemBuyRespDTO itemBuyDTO = projectService.itemBuy(query);
        ItemBuyVO itemBuyVO = MapstructUtils.convert(itemBuyDTO, ItemBuyVO.class);
        return R.ok(itemBuyVO);
    }
}
