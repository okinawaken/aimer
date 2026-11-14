package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/** 我的套餐（/mp/user/userPackage/list） */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomUserPackageListClientRespDTO {

    /** 已购套餐 */
    private List<StudyRoomUserPackageItemClientRespDTO> list;

    /** 未购套餐推荐 */
    private List<StudyRoomNotBuyPackageClientRespDTO> notBuyList;
}
