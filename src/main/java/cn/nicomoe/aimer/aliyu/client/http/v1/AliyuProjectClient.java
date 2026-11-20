package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
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
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

/**
 * 项目 / 演出域。
 */
public interface AliyuProjectClient extends AliyuBaseClient {

    /**
     * 项目列表。
     */
    @Get(url = "/pro/projects")
    AliyuClientResult<ProjectListRespDTO> projects(@Query ProjectListReqDTO query);

    /**
     * 分类项目列表。
     */
    @Get(url = "/pro/customizableProjects")
    AliyuClientResult<ProjectListRespDTO> customizableProjects(@Query ProjectListReqDTO query);

    /**
     * 按项目名称检索。
     */
    @Get(url = "/pro/language/projects")
    AliyuClientResult<ProjectListRespDTO> languageProjects(@Query ProjectListReqDTO query);

    /**
     * 项目详情。
     */
    @Get(url = "/pro/project")
    AliyuClientResult<ProjectDetailRespDTO> project(@Query(value = "projectToken") String projectToken,
                                                    @Query(value = "reqType") Long reqType,
                                                    @Query(value = "eventDate") String eventDate);

    /**
     * 场次详情。
     */
    @Get(url = "/pro/event")
    AliyuClientResult<ProjectEventRespDTO> event(@Query(value = "eventToken") String eventToken);

    /**
     * 按日期查场次。
     */
    @Get(url = "/pro/eventsByDate")
    AliyuClientResult<ProjectEventsByDateRespDTO> eventsByDate(@Query ProjectEventsByDateReqDTO query);

    /**
     * 演出日历。
     */
    @Get(url = "/pro/getShowCalendar")
    AliyuClientResult<java.util.List<ShowCalendarRespDTO>> getShowCalendar(@Query ProjectShowCalendarReqDTO query);

    /**
     * 栏目信息。
     */
    @Get(url = "/pro/getColumnInfo")
    AliyuClientResult<ProjectColumnInfoRespDTO> getColumnInfo(@Query ProjectColumnInfoReqDTO query);

    /**
     * 热门项目。
     */
    @Get(url = "/pro/hotProjectVos")
    AliyuClientResult<HotProjectListRespDTO> hotProjectVos(@Query ProjectHotReqDTO query);

    /**
     * 权益校验。
     */
    @Get(url = "/pro/privilegeCheck")
    AliyuClientResult<ProjectPrivilegeCheckRespDTO> privilegeCheck(@Query ProjectPrivilegeCheckReqDTO query);

    /**
     * 查询是否已设置开售提醒。
     */
    @Post(url = "/pro/hasSaleRemind")
    AliyuClientResult<Boolean> hasSaleRemind(@JSONBody ProjectHasSaleRemindReqDTO body);

    /**
     * 项目分类。
     */
    @Get(url = "/proType/getProjectTypeList")
    AliyuClientResult<java.util.List<ProjectTypeListRespDTO>> getProjectTypeList();

    /**
     * 项目所在城市。
     */
    @Get(url = "/region/getProjectCityList")
    AliyuClientResult<java.util.List<ProjectCityListRespDTO>> getProjectCityList();

    /**
     * 地区城市列表。
     */
    @Get(url = "/region/getAreaCityList")
    AliyuClientResult<java.util.List<ProjectCityListRespDTO>> getAreaCityList();

    /**
     * 按国家查地区。
     */
    @Get(url = "/region/getRegionByCountryId")
    AliyuClientResult<RegionCountryRespDTO> getRegionByCountryId(@Query(value = "countryId") String countryId,
                                                                 @Query(value = "tenantId") String tenantId,
                                                                 @Query(value = "ptnrId") String ptnrId);

    /**
     * 按省查地区。
     */
    @Get(url = "/region/getRegionByProvinceId")
    AliyuClientResult<RegionProvinceRespDTO> getRegionByProvinceId(@Query(value = "parentId") String parentId,
                                                                   @Query(value = "countryId") String countryId);

    /**
     * 收件基础信息（快递票）。
     */
    @Get(url = "/region/loadReceiveBasicInfo")
    AliyuClientResult<ReceiveBasicInfoRespDTO> loadReceiveBasicInfo();

    /**
     * 商品详情。
     */
    @Get(url = "/item/itemDetail")
    AliyuClientResult<ItemDetailRespDTO> itemDetail(@Query ItemQueryReqDTO query);

    /**
     * 商品购买。
     */
    @Get(url = "/item/itemBuy")
    AliyuClientResult<ItemBuyRespDTO> itemBuy(@Query ItemQueryReqDTO query);
}
