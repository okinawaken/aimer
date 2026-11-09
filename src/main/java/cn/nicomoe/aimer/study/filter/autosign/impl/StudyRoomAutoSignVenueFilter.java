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

/**
 * 签到门店校验过滤器
 * <p>
 * 按待签到预约的门店 ID 查询门店详情，校验门店存在并写入执行上下文，
 * 后续签到首页使用该门店的经纬度作为当前定位。
 *
 * @author bixiu
 * @since 4.2.0
 */
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
