package cn.nicomoe.aimer.study.filter.autosignout;

import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自动签退过滤器链，按顺序执行过滤器，遇到失败立即返回。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignOutFilterChain {

    private final List<StudyRoomAutoSignOutFilter> filters;

    /**
     * 按顺序执行过滤器链，遇到失败立即返回
     *
     * @param context 执行上下文
     * @return 执行结果
     */
    public FilterResultDTO<?> doFilter(StudyRoomAutoSignOutContext context) {
        for (StudyRoomAutoSignOutFilter filter : this.filters) {
            FilterResultDTO<?> result = filter.doFilter(context);
            if (!result.isSuccess()) {
                return result;
            }
        }
        return FilterResultDTO.success();
    }
}
