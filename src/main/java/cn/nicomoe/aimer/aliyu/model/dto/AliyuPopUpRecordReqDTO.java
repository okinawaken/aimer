package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 弹窗曝光记录请求（{@code POST /aliyuPopUp/record}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuPopUpRecordReqDTO {

    /** 设备标识（与 {@link AliyuPopUpQueryReqDTO#getDeviceId()} 同源） */
    private String deviceId;

    /** 弹窗 ID（取查询响应中的 {@code id}） */
    private String popUpId;
}