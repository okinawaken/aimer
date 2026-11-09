package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 套餐筛选项（/mp/buy/v2/package/optList）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomBuyV2PackageOptClientRespDTO {

    /**
     * 购买页说明 HTML
     */
    private String packageBuyHtml;

    /**
     * 购买页图片
     */
    private String packageBuyImg;

    /**
     * 支付按钮文案
     */
    private String packagePayBtn;

    /**
     * 套餐类型
     */
    private List<StudyRoomIdNameClientRespDTO> packageTypeList;

    /**
     * 套餐标签
     */
    private List<StudyRoomIdNameClientRespDTO> packageLabelList;

    /**
     * 套餐标签（类型2）
     */
    private List<StudyRoomIdNameClientRespDTO> packageLabelListOfType2;

    /**
     * 门店
     */
    private List<StudyRoomIdNameClientRespDTO> venueList;
}
