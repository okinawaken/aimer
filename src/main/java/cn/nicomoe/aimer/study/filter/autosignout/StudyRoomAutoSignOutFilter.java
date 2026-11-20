package cn.nicomoe.aimer.study.filter.autosignout;

import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import org.springframework.core.Ordered;

/**
 * 自动签退执行过滤器
 */
public interface StudyRoomAutoSignOutFilter extends Ordered {

    /**
     * 执行当前过滤环节
     */
    FilterResultDTO<?> doFilter(StudyRoomAutoSignOutContext context);
}
