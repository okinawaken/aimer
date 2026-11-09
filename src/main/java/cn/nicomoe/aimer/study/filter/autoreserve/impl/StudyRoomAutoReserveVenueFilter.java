package cn.nicomoe.aimer.study.filter.autoreserve.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoReserveFilterOrderEnum;
import cn.nicomoe.aimer.study.enums.StudyRoomReserveStatusEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autoreserve.StudyRoomAutoReserveFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoReserveContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAutoReserveTaskInfoDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomVenueService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 预约门店校验过滤器
 * <p>
 * 查询门店详情并校验门店是否可预约，结果缓存到执行上下文。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoReserveVenueFilter implements StudyRoomAutoReserveFilter {

    private final StudyRoomVenueService venueService;

    @Override
    public int getOrder() {
        return StudyRoomAutoReserveFilterOrderEnum.VENUE.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoReserveContext context) {
        StudyRoomAutoReserveTaskInfoDTO taskInfoDTO = context.getTaskInfoDTO();
        StudyRoomVenueClientRespDTO venueClientRespDTO = venueService.reserveVenueDetail(taskInfoDTO.getVenueId(), taskInfoDTO.getUserId());
        CheckUtils.throwIfNull(venueClientRespDTO, "门店数据返回为空");
        context.setVenueClientRespDTO(venueClientRespDTO);
        if (!StudyRoomReserveStatusEnum.isCanReserve(venueClientRespDTO.getVenueReserveState())) {
            return FilterResultDTO.fail("门店当前不可预约");
        }
        return FilterResultDTO.success(venueClientRespDTO);
    }
}
