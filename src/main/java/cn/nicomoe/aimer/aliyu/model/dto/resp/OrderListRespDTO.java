package cn.nicomoe.aimer.aliyu.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 由真实响应自动反推生成（2026-11-15）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderListRespDTO {
    private java.util.List<Object> dataList;
    private Long endRow;
    private Boolean nextPage;
    private Long page;
    private Long pageSize;
    private Long startRow;
    private Long totalPage;
    private Long totalRow;


}
