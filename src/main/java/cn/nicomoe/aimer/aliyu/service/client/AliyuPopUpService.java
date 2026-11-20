package cn.nicomoe.aimer.aliyu.service.client;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpRecordReqDTO;

/**
 * 阿里鱼弹窗营销域服务，返回已解包的业务数据。
 */
public interface AliyuPopUpService {

    /**
     * 查询弹窗配置。
     */
    AliyuPopUpQueryRespDTO query(AliyuPopUpQueryReqDTO form);

    /**
     * 弹窗曝光记录。
     */
    void record(AliyuPopUpRecordReqDTO form);
}
