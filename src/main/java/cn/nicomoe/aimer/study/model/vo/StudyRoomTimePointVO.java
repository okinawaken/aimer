package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomTimePointClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 门店营业时间点
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomTimePointClientRespDTO.class)
public class StudyRoomTimePointVO {

    /**
     * 小时
     */
    private Integer hour;

    /**
     * 分钟
     */
    private Integer minute;
}
