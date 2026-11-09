package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageLabelClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 套餐标签 / 座位区域筛选项（/mp/buy/v2/package/label/selectList）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomBuyV2PackageLabelClientRespDTO.class)
public class StudyRoomBuyV2PackageLabelVO {

    /**
     * 套餐标签
     */
    private List<StudyRoomIdNameVO> packageLabelList;

    /**
     * 座位区域
     */
    private List<StudyRoomIdNameVO> seatAreaList;
}
