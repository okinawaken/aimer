package cn.nicomoe.aimer.aliyu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 壁纸列表响应（{@code GET /aliyuMember/pointsMall/wallpaper/list}）。 接口可返回纯数组或含 {@code dataList} 的分页对象；本 DTO 仅描述对象形态，数组需由调用方处理。 页码字段为 {@code pageNo}，不是 {@code page}。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WallpaperListRespDTO {

    /**
     * 壁纸系列列表
     */
    private java.util.List<DataObj> dataList;

    /**
     * 当前页码（服务端字段名为 {@code pageNo}）
     */
    private Long pageNo;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 总页数（缺省时前端按是否满页推算）
     */
    private Long totalPage;

    /**
     * 总记录数
     */
    private Long totalRow;

    /**
     * 壁纸系列。
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataObj {

        /**
         * 系列 ID
         */
        private Long seriesId;

        /**
         * 系列名称
         */
        private String seriesName;

        /**
         * 排序优先级
         */
        private Long priority;

        /**
         * 系列内最低兑换积分
         */
        private Long minPoints;

        /**
         * 系列封面图
         */
        private String coverUrl;
    }
}