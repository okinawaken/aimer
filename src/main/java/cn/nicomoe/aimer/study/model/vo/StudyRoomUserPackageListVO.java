package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 我的套餐（/mp/user/userPackage/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomUserPackageListClientRespDTO.class)
public class StudyRoomUserPackageListVO {

    /**
     * 已购套餐
     */
    private List<StudyRoomUserPackageItemVO> list;

    /**
     * 未购套餐推荐
     */
    private List<StudyRoomNotBuyPackageVO> notBuyList;
}
