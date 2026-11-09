package cn.nicomoe.aimer.study.filter.autosign;

import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自动签到过滤器链，按顺序执行过滤器，遇到失败立即返回。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignFilterChain {

    private final List<StudyRoomAutoSignFilter> filters;

    /**
     * 按顺序执行过滤器链，遇到失败立即返回
     *
     * @param context 执行上下文
     * @return 执行结果
     */
    public FilterResultDTO<?> doFilter(StudyRoomAutoSignContext context) {
        for (StudyRoomAutoSignFilter filter : this.filters) {
            FilterResultDTO<?> result = filter.doFilter(context);
            if (!result.isSuccess()) {
                return result;
            }
        }
        return FilterResultDTO.success();
    }
}
