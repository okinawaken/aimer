package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageOptClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 套餐筛选项（/mp/buy/v2/package/optList）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomBuyV2PackageOptClientRespDTO.class)
public class StudyRoomBuyV2PackageOptVO {

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
    private List<StudyRoomIdNameVO> packageTypeList;

    /**
     * 套餐标签
     */
    private List<StudyRoomIdNameVO> packageLabelList;

    /**
     * 套餐标签（类型2）
     */
    private List<StudyRoomIdNameVO> packageLabelListOfType2;

    /**
     * 门店
     */
    private List<StudyRoomIdNameVO> venueList;
}
