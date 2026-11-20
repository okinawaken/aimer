package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.HotProjectListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ItemBuyRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ItemDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ItemQueryReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectCityListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectColumnInfoReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectColumnInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectEventRespDTO;
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

import java.util.List;

/** 阿里鱼项目 / 演出域服务，返回已解包的业务数据。 */
public interface AliyuProjectService {

    /** 项目列表。 */
    ProjectListRespDTO projects(ProjectListReqDTO query);

    /** 分类项目列表。 */
    ProjectListRespDTO customizableProjects(ProjectListReqDTO query);

    /** 按项目名称检索。 */
    ProjectListRespDTO languageProjects(ProjectListReqDTO query);

    /** 项目详情。 */
    ProjectDetailRespDTO project(String projectToken, Long reqType, String eventDate);

    /** 场次详情。 */
    ProjectEventRespDTO event(String eventToken);

    /** 按日期查场次。 */
    ProjectEventsByDateRespDTO eventsByDate(String date);

    /** 演出日历。 */
    List<ShowCalendarRespDTO> getShowCalendar(ProjectShowCalendarReqDTO query);

    /** 栏目信息。 */
    ProjectColumnInfoRespDTO getColumnInfo(ProjectColumnInfoReqDTO query);

    /** 热门项目。 */
    HotProjectListRespDTO hotProjectVos(ProjectHotReqDTO query);

    /** 权益校验。 */
    ProjectPrivilegeCheckRespDTO privilegeCheck(ProjectPrivilegeCheckReqDTO query);

    /** 设置开售提醒（返回是否已提醒）。 */
    Boolean hasSaleRemind(ProjectHasSaleRemindReqDTO form);

    /** 项目分类。 */
    List<ProjectTypeListRespDTO> getProjectTypeList();

    /** 项目所在城市。 */
    List<ProjectCityListRespDTO> getProjectCityList();

    /** 地区城市列表。 */
    List<ProjectCityListRespDTO> getAreaCityList();

    /** 按国家查地区。 */
    RegionCountryRespDTO getRegionByCountryId(String countryId, String tenantId, String ptnrId);

    /** 按省查地区。 */
    RegionProvinceRespDTO getRegionByProvinceId(String parentId, String countryId);

    /** 收件基础信息（快递票）。 */
    ReceiveBasicInfoRespDTO loadReceiveBasicInfo();

    /** 商品详情。 */
    ItemDetailRespDTO itemDetail(ItemQueryReqDTO query);

    /** 商品购买。 */
    ItemBuyRespDTO itemBuy(ItemQueryReqDTO query);
}
