package cn.nicomoe.aimer.aliyu.client.http.v2;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
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
import com.dtflys.forest.annotation.Var;

/** 项目 / 演出域（显式用户上下文）。 */
public interface AliyuProjectV2Client extends AliyuBaseClient {

    /** 项目列表。 参数 query：分页与筛选条件（小程序透传 data 并追加 siteId） 参数 userId：系统用户 ID */
    @Get(url = "/pro/projects")
    AliyuClientResult<ProjectListRespDTO> projects(@Query ProjectListReqDTO query,
                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 分类项目列表。 <p> 小程序分类页按筛选条件请求该接口，返回结构与 {@link #projects(ProjectListReqDTO, Long)} 一致； 当前包中的分支开关恒为真，因此分类页实际走该路径，否则回落到 {@code /pro/projects}。 参数 query：分页与筛选条件（不追加 siteId） 参数 userId：系统用户 ID */
    @Get(url = "/pro/customizableProjects")
    AliyuClientResult<ProjectListRespDTO> customizableProjects(@Query ProjectListReqDTO query,
                                                               @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 按项目名称检索。 <p> 小程序在 {@code projectName} 非空时改走该路径，其余筛选字段原样透传。 参数 query：分页、筛选条件与 {@code projectName} 参数 userId：系统用户 ID */
    @Get(url = "/pro/language/projects")
    AliyuClientResult<ProjectListRespDTO> languageProjects(@Query ProjectListReqDTO query,
                                                           @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 项目详情。 参数 projectToken：项目 token 参数 reqType：请求类型，小程序传 1 参数 eventDate：场次日期（按日期查单场时传） 参数 userId：系统用户 ID */
    @Get(url = "/pro/project")
    AliyuClientResult<ProjectDetailRespDTO> project(@Query(value = "projectToken") String projectToken,
                                                    @Query(value = "reqType") Long reqType,
                                                    @Query(value = "eventDate") String eventDate,
                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 场次详情。 */
    @Get(url = "/pro/event")
    AliyuClientResult<ProjectEventRespDTO> event(@Query(value = "eventToken") String eventToken,
                                                 @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 按日期查场次。 */
    @Get(url = "/pro/eventsByDate")
    AliyuClientResult<ProjectEventsByDateRespDTO> eventsByDate(@Query(value = "date") String date,
                                                               @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 演出日历。 参数 query：起止日期参数 userId：系统用户 ID */
    @Get(url = "/pro/getShowCalendar")
    AliyuClientResult<java.util.List<ShowCalendarRespDTO>> getShowCalendar(@Query ProjectShowCalendarReqDTO query,
                                                                           @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 栏目信息。 参数 query：数据类型与栏目 ID 参数 userId：系统用户 ID */
    @Get(url = "/pro/getColumnInfo")
    AliyuClientResult<ProjectColumnInfoRespDTO> getColumnInfo(@Query ProjectColumnInfoReqDTO query,
                                                              @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 热门项目。 参数 query：页面类型与站点解析标志参数 userId：系统用户 ID */
    @Get(url = "/pro/hotProjectVos")
    AliyuClientResult<HotProjectListRespDTO> hotProjectVos(@Query ProjectHotReqDTO query,
                                                           @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 权益校验。 参数 query：权益 ID 与场次 token 参数 userId：系统用户 ID */
    @Get(url = "/pro/privilegeCheck")
    AliyuClientResult<ProjectPrivilegeCheckRespDTO> privilegeCheck(@Query ProjectPrivilegeCheckReqDTO query,
                                                                   @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 设置开售提醒（返回是否已提醒）。 */
    @Post(url = "/pro/hasSaleRemind")
    AliyuClientResult<Boolean> hasSaleRemind(@JSONBody ProjectHasSaleRemindReqDTO body,
                                             @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 项目分类。 */
    @Get(url = "/proType/getProjectTypeList")
    AliyuClientResult<java.util.List<ProjectTypeListRespDTO>> getProjectTypeList(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 项目所在城市。 */
    @Get(url = "/region/getProjectCityList")
    AliyuClientResult<java.util.List<ProjectCityListRespDTO>> getProjectCityList(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 地区城市列表（体育类站点使用，元素结构与 {@link ProjectCityListRespDTO} 一致， 大区项额外带 {@code cityList} 子城市列表） */
    @Get(url = "/region/getAreaCityList")
    AliyuClientResult<java.util.List<ProjectCityListRespDTO>> getAreaCityList(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 按国家查地区。 */
    @Get(url = "/region/getRegionByCountryId")
    AliyuClientResult<RegionCountryRespDTO> getRegionByCountryId(@Query(value = "countryId") String countryId,
                                                                 @Query(value = "tenantId") String tenantId,
                                                                 @Query(value = "ptnrId") String ptnrId,
                                                                 @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 按省查地区。 */
    @Get(url = "/region/getRegionByProvinceId")
    AliyuClientResult<RegionProvinceRespDTO> getRegionByProvinceId(@Query(value = "parentId") String parentId,
                                                                   @Query(value = "countryId") String countryId,
                                                                   @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 收件基础信息（快递票）。 */
    @Get(url = "/region/loadReceiveBasicInfo")
    AliyuClientResult<ReceiveBasicInfoRespDTO> loadReceiveBasicInfo(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 商品详情。 参数 query：商品 ID、活动 ID、业务类型（{@code bizType=2} 时 {@code activityId} 必传） 参数 userId：系统用户 ID */
    @Get(url = "/item/itemDetail")
    AliyuClientResult<ItemDetailRespDTO> itemDetail(@Query ItemQueryReqDTO query,
                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 商品购买。 参数 query：商品 ID、活动 ID、业务类型参数 userId：系统用户 ID */
    @Get(url = "/item/itemBuy")
    AliyuClientResult<ItemBuyRespDTO> itemBuy(@Query ItemQueryReqDTO query,
                                              @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);
}
