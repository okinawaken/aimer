package cn.nicomoe.aimer.study.filter.autosignout.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignOutFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosignout.StudyRoomAutoSignOutFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomVenueService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 门店坐标解析过滤器
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
        StudyRoomIndexClientRespDTO indexClientRespDTO = context.getIndexClientRespDTO();
        StudyRoomVenueClientRespDTO venueClientRespDTO = venueService.reserveVenueDetail(indexClientRespDTO.getUserSign().getVenueId(), context.getUserId());
        CheckUtils.throwIfNull(venueClientRespDTO, "门店信息返回为空");
        context.setVenueClientRespDTO(venueClientRespDTO);
        return FilterResultDTO.success(venueClientRespDTO);
    }
}
