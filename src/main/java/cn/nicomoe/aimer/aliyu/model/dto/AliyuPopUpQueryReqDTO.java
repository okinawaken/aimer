package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 弹窗查询请求（{@code POST /aliyuPopUp/query}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuPopUpQueryReqDTO {

    /** 设备标识（小程序侧持久化的随机 UUID） */
    private String deviceId;
}