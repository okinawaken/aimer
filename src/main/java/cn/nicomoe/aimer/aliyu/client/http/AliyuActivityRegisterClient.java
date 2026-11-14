package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.Map;
import cn.nicomoe.aimer.aliyu.model.dto.resp.DrawListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.RegisterActDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.RegisterActListRespDTO;

/**
 * 活动报名 / 抽奖域：{@code /act}、{@code /event}。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuActivityRegisterClient extends AliyuBaseClient {

    /**
     * 报名活动详情。
     *
     * @param actId 活动 ID
     * @return 响应数据
     */
    @Get(url = "/act/registerActDetail")
    AliyuClientResult<RegisterActDetailRespDTO> registerActDetail(@Query(value = "actId") String actId);

    /**
     * 报名活动列表。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/act/registerActList")
    AliyuClientResult<RegisterActListRespDTO> registerActList(@JSONBody Map<String, Object> body);

    /**
     * 活动报名。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/act/registerUser")
    AliyuClientResult<Object> registerUser(@JSONBody Map<String, Object> body);

    /**
     * 抽奖列表（入参 {@code pageIndex, pageSize}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/act/drawList")
    AliyuClientResult<DrawListRespDTO> drawList(@JSONBody Map<String, Object> body);

    /**
     * 活动注册。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/event/register/eventRegister")
    AliyuClientResult<Object> eventRegister(@JSONBody Map<String, Object> body);

    /**
     * 更新开售提醒。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/event/register/updateSaleRemind")
    AliyuClientResult<Object> updateSaleRemind(@JSONBody Map<String, Object> body);
}
