package cn.nicomoe.aimer.study.filter.autoreserve;

import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoReserveContext;
import org.springframework.core.Ordered;

/** 自动预约执行过滤器 */
public interface StudyRoomAutoReserveFilter extends Ordered {

    /** 执行当前过滤环节 */
    FilterResultDTO<?> doFilter(StudyRoomAutoReserveContext context);
}
