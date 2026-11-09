package cn.nicomoe.aimer.study.service.client.impl;

import cn.nicomoe.aimer.study.client.http.v1.StudyRoomConfigClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomConfigClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomNoticeClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自习室配置域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomConfigServiceImpl implements StudyRoomConfigService {

    private final StudyRoomConfigClient configClient;

    @Override
    public StudyRoomConfigClientRespDTO sysConfig() {
        StudyRoomClientResult<StudyRoomConfigClientRespDTO> result = configClient.sysConfig();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomNoticeClientRespDTO noticeBoard(Integer venueId) {
        StudyRoomClientResult<StudyRoomNoticeClientRespDTO> result = configClient.noticeBoard(venueId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
