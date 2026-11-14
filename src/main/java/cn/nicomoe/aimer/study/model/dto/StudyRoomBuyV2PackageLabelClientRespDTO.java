package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 套餐标签 / 座位区域筛选项（/mp/buy/v2/package/label/selectList） */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomBuyV2PackageLabelClientRespDTO {

    /** 套餐标签 */
    private List<StudyRoomIdNameClientRespDTO> packageLabelList;

    /** 座位区域 */
    private List<StudyRoomIdNameClientRespDTO> seatAreaList;
}
