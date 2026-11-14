package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.Map;
import cn.nicomoe.aimer.aliyu.model.dto.resp.HotProjectListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.ProjectCityListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.ProjectListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.ProjectTypeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.ReceiveBasicInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.ShowCalendarRespDTO;

/**
 * 项目 / 演出域：{@code /pro}、{@code /proType}、{@code /region}、{@code /item}。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuProjectClient extends AliyuBaseClient {

    /**
     * 项目列表。
     *
     * @return 响应数据
     */
    @Get(url = "/pro/projects")
    AliyuClientResult<ProjectListRespDTO> projects();

    /**
     * 项目详情。
     *
     * @param projectId 项目 ID
     * @return 响应数据
     */
    @Get(url = "/pro/project")
    AliyuClientResult<Object> project(@Query(value = "projectId") String projectId);

    /**
     * 场次详情。
     *
     * @param eventId 场次 ID
     * @return 响应数据
     */
    @Get(url = "/pro/event")
    AliyuClientResult<Object> event(@Query(value = "eventId") String eventId);

    /**
     * 按日期查场次。
     *
     * @param date 日期
     * @return 响应数据
     */
    @Get(url = "/pro/eventsByDate")
    AliyuClientResult<Object> eventsByDate(@Query(value = "date") String date);

    /**
     * 演出日历。
     *
     * @return 响应数据
     */
    @Get(url = "/pro/getShowCalendar")
    AliyuClientResult<java.util.List<ShowCalendarRespDTO>> getShowCalendar();

    /**
     * 栏目信息。
     *
     * @return 响应数据
     */
    @Get(url = "/pro/getColumnInfo")
    AliyuClientResult<Object> getColumnInfo();

    /**
     * 热门项目。
     *
     * @return 响应数据
     */
    @Get(url = "/pro/hotProjectVos")
    AliyuClientResult<HotProjectListRespDTO> hotProjectVos();

    /**
     * 权益校验。
     *
     * @return 响应数据
     */
    @Get(url = "/pro/privilegeCheck")
    AliyuClientResult<Object> privilegeCheck();

    /**
     * 设置开售提醒。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/pro/hasSaleRemind")
    AliyuClientResult<Object> hasSaleRemind(@JSONBody Map<String, Object> body);

    /**
     * 项目分类。
     *
     * @return 响应数据
     */
    @Get(url = "/proType/getProjectTypeList")
    AliyuClientResult<java.util.List<ProjectTypeListRespDTO>> getProjectTypeList();

    /**
     * 项目所在城市。
     *
     * @return 响应数据
     */
    @Get(url = "/region/getProjectCityList")
    AliyuClientResult<java.util.List<ProjectCityListRespDTO>> getProjectCityList();

    /**
     * 地区城市列表。
     *
     * @return 响应数据
     */
    @Get(url = "/region/getAreaCityList")
    AliyuClientResult<java.util.List<Object>> getAreaCityList();

    /**
     * 按国家查地区。
     *
     * @param countryId 国家 ID
     * @return 响应数据
     */
    @Get(url = "/region/getRegionByCountryId")
    AliyuClientResult<Object> getRegionByCountryId(@Query(value = "countryId") String countryId);

    /**
     * 按省查地区。
     *
     * @param provinceId 省 ID
     * @return 响应数据
     */
    @Get(url = "/region/getRegionByProvinceId")
    AliyuClientResult<Object> getRegionByProvinceId(@Query(value = "provinceId") String provinceId);

    /**
     * 收件基础信息（快递票）。
     *
     * @return 响应数据
     */
    @Get(url = "/region/loadReceiveBasicInfo")
    AliyuClientResult<ReceiveBasicInfoRespDTO> loadReceiveBasicInfo();

    /**
     * 商品详情。
     *
     * @param itemId 商品 ID
     * @return 响应数据
     */
    @Get(url = "/item/itemDetail")
    AliyuClientResult<Object> itemDetail(@Query(value = "itemId") String itemId);

    /**
     * 商品购买。
     *
     * @param itemId 商品 ID
     * @return 响应数据
     */
    @Get(url = "/item/itemBuy")
    AliyuClientResult<Object> itemBuy(@Query(value = "itemId") String itemId);
}
