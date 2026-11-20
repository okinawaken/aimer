package cn.nicomoe.aimer.notify.service.impl;

import cn.nicomoe.aimer.notify.client.http.BarkClient;
import cn.nicomoe.aimer.notify.model.dto.BarkPushReqDTO;
import cn.nicomoe.aimer.notify.service.BarkService;
import cn.nicomoe.aimer.util.GsonUtils;
import com.dtflys.forest.http.ForestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarkServiceImpl implements BarkService {

    private final BarkClient barkClient;

    @Override
    public boolean push(String barkUrl, BarkPushReqDTO payload) {
        try {
            ForestResponse<?> response = barkClient.push(barkUrl, payload);
            if (Objects.isNull(response) || !response.isSuccess()) {
                log.warn("Bark 推送失败，barkUrl={}，payload={}", barkUrl, GsonUtils.toJson(payload));
                return false;
            }
            return true;
        } catch (Exception e) {
            log.warn("Bark 推送异常，barkUrl={}，payload={}，原因={}", barkUrl, GsonUtils.toJson(payload), e.getMessage());
            return false;
        }
    }
}