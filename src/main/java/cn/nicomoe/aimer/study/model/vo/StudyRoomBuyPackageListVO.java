package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomBuyPackageListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 套餐列表（/mp/buy/package/list，旧版）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomBuyPackageListClientRespDTO.class)
public class StudyRoomBuyPackageListVO {

    /**
     * 按区域分组的套餐
     */
    private List<StudyRoomVenueAreaPackageVO> venueAreaList;
}
