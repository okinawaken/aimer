package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import java.util.Objects;

/** 校验 HTTP 状态与业务码。业务码缺失（如裸 DTO 响应）时放行；非成功码优先使用上游错误文案。 */
@Component
@RequiredArgsConstructor
public class AliyuResponseInterceptor implements ForestInterceptor {

    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
        if (response.isError()) {
            throw new EruptWebApiRuntimeException("阿里鱼接口调用失败(" + response.getStatusCode() + ")");
        }
        Object resultData = response.getResult();
        if (Objects.isNull(resultData)) {
            throw new EruptWebApiRuntimeException(AliyuApiErrorCodeEnum.EMPTY_RESPONSE.getDescription());
        }
        if (resultData instanceof AliyuClientResult<?> result) {
            String code = result.getCode();
            if (!Objects.equals(code, AliyuApiConstants.ApiCode.SUCCESS_CODE)) {
                throw new EruptWebApiRuntimeException(result.getMsg());
            }
        }
    }
}
