package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.ForestInterceptor;
import com.dtflys.forest.utils.ForestDataType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import java.util.Objects;

/**
 * 响应拦截器：校验 HTTP 状态与业务码。
 */
@Component
@RequiredArgsConstructor
public class AliyuValidateResponseInterceptor implements ForestInterceptor {

    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
        if (response.isError() || !response.status_2xx()) {
            throw new EruptWebApiRuntimeException("阿里鱼接口调用失败(" + response.getStatusCode() + ")");
        }
        if (!Objects.equals(response.getContentType().bodyType(), ForestDataType.JSON)) {
            throw new EruptWebApiRuntimeException("阿里鱼小程序接口返回非 JSON 响应");
        }
        Object resultData = response.getResult();
        if (Objects.isNull(resultData)) {
            throw new EruptWebApiRuntimeException("阿里鱼小程序接口返回为空");
        }
        if (resultData instanceof AliyuClientResult<?> result) {
            String code = result.getCode();
            if (!Objects.equals(code, AliyuApiConstants.ApiCode.SUCCESS_CODE)) {
                throw new EruptWebApiRuntimeException(result.getMsg());
            }
        }
    }
}
