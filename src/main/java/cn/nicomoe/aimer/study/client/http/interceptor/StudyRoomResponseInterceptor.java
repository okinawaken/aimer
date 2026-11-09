package cn.nicomoe.aimer.study.client.http.interceptor;

import cn.nicomoe.aimer.study.constant.StudyRoomApiConstants;
import cn.nicomoe.aimer.study.enums.StudyRoomApiErrorCodeEnum;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;

import java.util.Objects;

/**
 * 自习室响应拦截器
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomResponseInterceptor implements ForestInterceptor {

    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
        if (response.isError()) {
            throw new EruptWebApiRuntimeException("自习室接口调用失败(" + response.getStatusCode() + ")");
        }
        Object resultData = response.getResult();
        if (Objects.isNull(resultData)) {
            throw new EruptWebApiRuntimeException(StudyRoomApiErrorCodeEnum.EMPTY_RESPONSE.getDescription());
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
