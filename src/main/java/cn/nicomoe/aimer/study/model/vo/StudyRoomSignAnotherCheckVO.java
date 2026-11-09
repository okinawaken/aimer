package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomSignAnotherCheckClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 是否可再签一座（/mp/study/signAnother/check）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomSignAnotherCheckClientRespDTO.class)
public class StudyRoomSignAnotherCheckVO {

    /**
     * 是否可再签：1 可以
     */
    private Integer canSignAnother;

    /**
     * 是否有可用的加签套餐：1 有
     */
    private Integer hasSignAnotherPackage;

    /**
     * 不可再签原因
     */
    private String descNoSignAnother;

    /**
     * 可选结算套餐
     */
    private List<StudyRoomSettlePackageVO> settlePackageList;
}
