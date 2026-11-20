package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.CountryAreaRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 国家地区响应（{@code GET /countryArea/queryForUserRegister}，入参 {@code all: true|false}）。 结构为分组名到国家数组的映射；此处建模已知的 {@code hot/normal} 分组。
 */
@Data
@AutoMapper(target = CountryAreaRespDTO.class)
public class CountryAreaVO {

    /**
     * 热门国家/地区分组
     */
    private List<NormalVO> hot;

    /**
     * 普通国家/地区分组
     */
    private List<NormalVO> normal;

    /**
     * 国家/地区项。
     */
    @Data
    public static class NormalVO {

        /**
         * 国家二位字母代码
         */
        private String countryAbbrCode;

        /**
         * 电话国际区号（选择器展示 {@code +区号}）
         */
        private String countryAreaCode;

        /**
         * 国家中文名（{@code countryNameKey} 取不到文案时的兜底展示）
         */
        private String countryCnName;

        /**
         * 国家英文名
         */
        private String countryEnName;

        /**
         * 国家/地区 ID（选中后作为 {@code countryId} 回传）
         */
        private String countryId;

        /**
         * 国家名称的 i18n key
         */
        private String countryNameKey;

        /**
         * 名称首字母
         */
        private String firstPinyin;

        /**
         * 注册类型
         */
        private Long registerType;
    }
}
