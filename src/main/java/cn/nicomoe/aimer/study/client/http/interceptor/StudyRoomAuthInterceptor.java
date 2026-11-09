package cn.nicomoe.aimer.study.client.http.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.nicomoe.aimer.study.constant.StudyRoomApiConstants;
import cn.nicomoe.aimer.study.model.bo.StudyRoomUserConfigBO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserConfigDTO;
import cn.nicomoe.aimer.study.service.user.StudyRoomUserSettingService;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.erupt.upms.service.EruptUserService;

import java.util.Objects;

/**
 * 小程序鉴权拦截器
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAuthInterceptor implements ForestInterceptor {

    private final EruptUserService userService;
    private final StudyRoomUserSettingService settingService;

    @Override
    public boolean beforeExecute(ForestRequest request) {
        request.addHeader(StudyRoomApiConstants.Header.HEADER_APPID, StudyRoomApiConstants.Gateway.APPID);

        String token = request.getHeaderValue(StudyRoomApiConstants.Header.HEADER_TOKEN);
        if (StrUtil.isEmpty(token)) {
            Long userId = this.resolveUserId(request);
            StudyRoomUserConfigDTO configDTO = settingService.get(userId);
            if (Objects.nonNull(configDTO)) {
                StudyRoomUserConfigBO configBO = configDTO.getConfig();
                if (Objects.nonNull(configBO)) {
                    request.addHeader(StudyRoomApiConstants.Header.HEADER_TOKEN, configBO.getToken());
                }
            }
        }
        return true;
    }

    private Long resolveUserId(ForestRequest<?> request) {
        Long requestUserId = Convert.toLong(request.getVariableValue(StudyRoomApiConstants.Variable.USER_ID));
        if (Objects.nonNull(requestUserId)) {
            return requestUserId;
        }
        return userService.getCurrentUid();
    }
}
