package cn.nicomoe.aimer.study.filter.autosignout;

import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import org.springframework.core.Ordered;

/**
 * 自动签退执行过滤器
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomAutoSignOutFilter extends Ordered {

    /**
     * 执行当前过滤环节
     *
     * @param context 执行上下文
     * @return 响应数据
     */
    FilterResultDTO<?> doFilter(StudyRoomAutoSignOutContext context);
}
