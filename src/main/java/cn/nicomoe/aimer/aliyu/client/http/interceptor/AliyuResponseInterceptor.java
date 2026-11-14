package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.hutool.core.convert.Convert;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.enums.AliyuApiErrorCodeEnum;
import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserConfigBO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserConfigDTO;
import cn.nicomoe.aimer.aliyu.service.user.AliyuUserSettingService;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.erupt.core.exception.EruptWebApiRuntimeException;
import xyz.erupt.upms.service.EruptUserService;

import java.util.Objects;

/**
 * 阿里鱼响应拦截器。
 * <p>
 * HTTP 层失败直接抛出；业务层仅在 {@code code} 非空且不等于 {@code "200"} 时抛出上游文案
 * （上游文案为空时回落到 {@link AliyuApiErrorCodeEnum#UPSTREAM_ERROR}），
 * 对 {@code code} 缺失（如 {@code success} 布尔位响应）放行，交由调用方按 {@code data} 自行判断。
 * 另外负责把响应头回写的 {@code X-MZ-SESSION}（TTL 360s）写入凭证缓存，供后续请求复用。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AliyuResponseInterceptor implements ForestInterceptor {

    private final EruptUserService userService;
    private final AliyuUserSettingService settingService;
    private final AliyuCredentialCache credentialCache;

    @Override
    public void afterExecute(ForestRequest request, ForestResponse response) {
        this.refreshSession(request, response);
        if (response.isError()) {
            throw new EruptWebApiRuntimeException("阿里鱼接口调用失败(" + response.getStatusCode() + ")");
        }
        Object resultData = response.getResult();
        if (Objects.isNull(resultData)) {
            throw new EruptWebApiRuntimeException(AliyuApiErrorCodeEnum.EMPTY_RESPONSE.getDescription());
        }
        if (resultData instanceof AliyuClientResult<?> result) {
            String code = result.getCode();
            if (Objects.nonNull(code) && !Objects.equals(code, AliyuApiConstants.ApiCode.SUCCESS_CODE)) {
                String msg = result.getMsg();
                if (Objects.isNull(msg) || msg.isBlank()) {
                    msg = AliyuApiErrorCodeEnum.UPSTREAM_ERROR.getDescription();
                }
                throw new EruptWebApiRuntimeException(msg);
            }
        }
    }

    /**
     * 回写响应头中的 maitix 会话标识。
     *
     * @param request  Forest 请求
     * @param response Forest 响应
     */
    private void refreshSession(ForestRequest<?> request, ForestResponse<?> response) {
        String newSession = response.getHeaderValue(AliyuApiConstants.Header.X_MZ_SESSION);
        if (Objects.isNull(newSession) || newSession.isEmpty()) {
            return;
        }
        AliyuUserConfigBO config = this.resolveConfig(request);
        if (Objects.nonNull(config)) {
            this.credentialCache.updateSession(config, newSession);
        }
    }

    private AliyuUserConfigBO resolveConfig(ForestRequest<?> request) {
        try {
            Long requestUserId = Convert.toLong(request.getVariableValue(AliyuApiConstants.Variable.USER_ID));
            Long userId = Objects.nonNull(requestUserId) ? requestUserId : userService.getCurrentUid();
            if (Objects.isNull(userId)) {
                return null;
            }
            AliyuUserConfigDTO configDTO = settingService.get(userId);
            return Objects.nonNull(configDTO) ? configDTO.getConfig() : null;
        } catch (RuntimeException e) {
            log.debug("阿里鱼会话回写跳过：{}", e.getMessage());
            return null;
        }
    }
}
