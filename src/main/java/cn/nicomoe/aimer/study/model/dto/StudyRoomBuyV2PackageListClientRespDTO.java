package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 套餐列表（/mp/buy/v2/package/list） */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomBuyV2PackageListClientRespDTO {

    /** 套餐列表 */
    private List<StudyRoomPackageItemClientRespDTO> packageList;
}
