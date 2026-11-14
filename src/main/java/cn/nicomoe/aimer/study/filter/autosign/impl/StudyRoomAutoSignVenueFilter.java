package cn.nicomoe.aimer.study.filter.autosign.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosign.StudyRoomAutoSignFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomVenueService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 按待签到预约的门店查详情并写入上下文；后续签到首页用该门店经纬度当定位。 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignVenueFilter implements StudyRoomAutoSignFilter {

    private final StudyRoomVenueService venueService;

    @Override
    public int getOrder() {
        return StudyRoomAutoSignFilterOrderEnum.VENUE.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoSignContext context) {
        StudyRoomUserReserveClientRespDTO userReserveClientRespDTO = context.getReserveClientRespDTO();
        StudyRoomVenueClientRespDTO venueClientRespDTO = venueService.reserveVenueDetail(userReserveClientRespDTO.getVenueId(), context.getUserId());
        CheckUtils.throwIfNull(venueClientRespDTO, "门店信息返回为空");
        context.setVenueClientRespDTO(venueClientRespDTO);
        return FilterResultDTO.success(venueClientRespDTO);
    }
}
