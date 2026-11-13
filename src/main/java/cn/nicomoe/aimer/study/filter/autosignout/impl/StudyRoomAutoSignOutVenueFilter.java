package cn.nicomoe.aimer.study.filter.autosignout.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignOutFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosignout.StudyRoomAutoSignOutFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomVenueService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 门店坐标解析过滤器
 * <p>
 * 按已到期预约的门店 ID 查询门店详情，其经纬度将作为签到首页的当前定位。
 * 定时任务无定位能力，故沿用门店自身坐标，与自动签到保持一致。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignOutVenueFilter implements StudyRoomAutoSignOutFilter {

    private final StudyRoomVenueService venueService;

    @Override
    public int getOrder() {
        return StudyRoomAutoSignOutFilterOrderEnum.VENUE.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoSignOutContext context) {
        StudyRoomUserReserveClientRespDTO reserveClientRespDTO = context.getExpiredReserveClientRespDTO();
        StudyRoomVenueClientRespDTO venueClientRespDTO = venueService.reserveVenueDetail(reserveClientRespDTO.getVenueId(), context.getUserId());
        CheckUtils.throwIfNull(venueClientRespDTO, "门店信息返回为空");
        CheckUtils.throwIfNull(venueClientRespDTO.getVenueLng(), "门店经度为空，无法自动签退");
        CheckUtils.throwIfNull(venueClientRespDTO.getVenueLat(), "门店纬度为空，无法自动签退");
        context.setVenueClientRespDTO(venueClientRespDTO);
        return FilterResultDTO.success(venueClientRespDTO);
    }
}
