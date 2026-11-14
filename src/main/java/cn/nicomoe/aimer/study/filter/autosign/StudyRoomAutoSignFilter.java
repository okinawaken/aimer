package cn.nicomoe.aimer.study.filter.autosign;

import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignContext;
import org.springframework.core.Ordered;

/** 自动签到执行过滤器 */
public interface StudyRoomAutoSignFilter extends Ordered {

    /** 执行当前过滤环节 */
    FilterResultDTO<?> doFilter(StudyRoomAutoSignContext context);
}
