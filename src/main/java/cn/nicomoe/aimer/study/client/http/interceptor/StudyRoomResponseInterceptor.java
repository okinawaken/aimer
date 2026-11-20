package cn.nicomoe.aimer.study.client.http.interceptor;

import cn.nicomoe.aimer.study.constant.StudyRoomApiConstants;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.ForestInterceptor;
import com.dtflys.forest.utils.ForestDataType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import java.util.Objects;

/**
 * 自习室响应拦截器
 */
@Component
@RequiredArgsConstructor
public class StudyRoomResponseInterceptor implements ForestInterceptor {

    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
        if (response.isError() || !response.status_2xx()) {
            throw new EruptWebApiRuntimeException("自习室接口调用失败(" + response.getStatusCode() + ")");
        }
        if (!Objects.equals(response.getContentType().bodyType(), ForestDataType.JSON)) {
            throw new EruptWebApiRuntimeException("自习室接口返回非 JSON 响应");
        }
        Object resultData = response.getResult();
        if (Objects.isNull(resultData)) {
            throw new EruptWebApiRuntimeException("自习室小程序接口返回为空");
        }
        if (resultData instanceof StudyRoomClientResult<?> result) {
            String code = result.getCode();
            if (!Objects.equals(code, StudyRoomApiConstants.ApiCode.SUCCESS_CODE)
                    && !Objects.equals(code, StudyRoomApiConstants.ApiCode.BUSINESS_CODE)) {
                throw new EruptWebApiRuntimeException(result.getContent());
            }
        }
    }
}
