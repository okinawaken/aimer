package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;

import java.util.Map;

/**
 * 弹窗营销域：{@code /aliyuPopUp}。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuPopUpClient extends AliyuBaseClient {

    /**
     * 查询弹窗配置（入参 {@code deviceId}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyuPopUp/query")
    AliyuClientResult<Object> query(@JSONBody Map<String, Object> body);

    /**
     * 弹窗曝光记录（入参 {@code deviceId, popUpId}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyuPopUp/record")
    AliyuClientResult<Object> record(@JSONBody Map<String, Object> body);
}
