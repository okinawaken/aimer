package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 我的预约列表（/mp/user/userReserve/list）
 * <p>
 * 注意：该接口不支持分页参数，一次返回全部预约记录。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomUserReserveListClientRespDTO.class)
public class StudyRoomUserReserveListVO {

    /**
     * 预约记录
     */
    private List<StudyRoomUserReserveVO> list;
}
