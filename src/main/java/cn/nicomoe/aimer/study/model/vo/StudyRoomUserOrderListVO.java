package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomUserOrderListClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/** 我的订单（/mp/user/userOrder/list） */
@Data
@AutoMapper(target = StudyRoomUserOrderListClientRespDTO.class)
public class StudyRoomUserOrderListVO {

    /** 订单列表 */
    private List<StudyRoomUserOrderVO> list;
}
