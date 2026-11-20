package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.ProjectEventsByDateRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 按日期查场次（{@code GET /pro/eventsByDate}）。
 */
@Data
@AutoMapper(target = ProjectEventsByDateRespDTO.class)
public class ProjectEventsByDateVO {

    /**
     * 倒计时状态：1 倒计时中
     */
    private Long countDownState;

    /**
     * 倒计时结束时间
     */
    private String countDownEndTime;

    /**
     * 当日场次列表
     */
    private List<ProjectEventVO> eventVoList;
}
