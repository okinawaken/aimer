package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyV2PackageListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 套餐列表（/mp/buy/v2/package/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomBuyV2PackageListClientRespDTO.class)
public class StudyRoomBuyV2PackageListVO {

    /**
     * 套餐列表
     */
    private List<StudyRoomPackageItemVO> packageList;
}
