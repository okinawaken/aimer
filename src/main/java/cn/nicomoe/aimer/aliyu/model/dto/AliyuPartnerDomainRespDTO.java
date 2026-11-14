package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 租户域名和商户信息。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AliyuPartnerDomainRespDTO {

    private String ptnrDomainMFrontend;
    private String ptnrId;
    private String ptnrName;
}
