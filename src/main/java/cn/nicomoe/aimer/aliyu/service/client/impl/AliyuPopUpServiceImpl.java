package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuPopUpClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpRecordReqDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuPopUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 阿里鱼弹窗营销域服务实现。
 */
@Service
@RequiredArgsConstructor
public class AliyuPopUpServiceImpl implements AliyuPopUpService {

    private final AliyuPopUpClient popUpClient;

    @Override
    public AliyuPopUpQueryRespDTO query(AliyuPopUpQueryReqDTO form) {
        AliyuClientResult<AliyuPopUpQueryRespDTO> result = popUpClient.query(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public void record(AliyuPopUpRecordReqDTO form) {
        popUpClient.record(form);
    }
}
