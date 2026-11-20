package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ItemBuyRespDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 商品购买（规格选择）（{@code GET /item/itemBuy}）。
 */
@Data
@AutoMapper(target = ItemBuyRespDTO.class)
public class ItemBuyVO {

    /**
     * 商品主图
     */
    private String imageUrl;

    /**
     * 规格组
     */
    private List<ModelVO> model;

    /**
     * 子规格 ID 到下单 sku 的映射（作为 {@code goodSku} 原样提交给 {@code /trade/addCartGood}）
     */
    private Map<String, JsonNode> modelSkuMap;

    /**
     * 规格组。
     */
    @Data
    public static class ModelVO {

        /**
         * 规格组名称
         */
        private String modelName;

        /**
         * 子规格列表
         */
        private List<SubModelVO> subModelList;
    }

    /**
     * 子规格。
     */
    @Data
    public static class SubModelVO {

        /**
         * 子规格 ID
         */
        private String subModelId;

        /**
         * 子规格名称
         */
        private String subModelName;

        /**
         * 子规格图片
         */
        private String imageUrl;

        /**
         * 库存数量
         */
        private Long inventory;

        /**
         * 价格（直接展示）
         */
        private String price;

        /**
         * 所需积分（直接展示）
         */
        private String integral;
    }
}
