package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** 壁纸列表查询请求（{@code GET /aliyuMember/pointsMall/wallpaper/list}）。 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsMallWallpaperListReqDTO {

    /** 页码，从 1 开始。 */
    private Integer pageNo;

    /** 每页数量。 */
    private Integer pageSize;

    /** IP 编码，存在时才传。 */
    private String ipCode;
}