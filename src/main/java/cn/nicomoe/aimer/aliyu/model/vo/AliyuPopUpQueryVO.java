package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 弹窗查询响应（{@code POST /aliyuPopUp/query}）。
 */
@Data
@AutoMapper(target = AliyuPopUpQueryRespDTO.class)
public class AliyuPopUpQueryVO {

    /**
     * 弹窗 ID（曝光上报的 {@code popUpId}）
     */
    private String id;

    /**
     * 弹窗图片地址
     */
    private String imageUrl;

    /**
     * 热区配置，兼容数组与 JSON 字符串两种下发格式。 坐标为百分比，支持小数；解析失败或非数组时视为无热区。
     */
    private List<HotZoneVO> hotZone;

    /**
     * 弹窗热区。坐标为百分比；{@code actionType}：1 跳转（需 {@code jumpUrl}）、2 关闭。
     */
    @Data
    public static class HotZoneVO {

        /**
         * 热区 ID（渲染列表 key）
         */
        private String id;

        /**
         * 热区行为：1 跳转、2 关闭
         */
        private Integer actionType;

        /**
         * 跳转地址（{@code actionType=1} 时使用）
         */
        private String jumpUrl;

        /**
         * 左偏移百分比
         */
        private Double x;

        /**
         * 上偏移百分比
         */
        private Double y;

        /**
         * 宽度百分比
         */
        private Double width;

        /**
         * 高度百分比
         */
        private Double height;
    }

}
