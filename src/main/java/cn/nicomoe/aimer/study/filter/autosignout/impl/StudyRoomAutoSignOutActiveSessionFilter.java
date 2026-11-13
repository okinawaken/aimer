package cn.nicomoe.aimer.study.filter.autosignout.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignOutFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosignout.StudyRoomAutoSignOutFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSignClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomSignService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 在座签到记录查询过滤器
 * <p>
 * 以已到期预约所在门店的经纬度作为当前定位查询签到首页，校验存在在座记录。
 * 同一账号可能同时签多个座位，故使用 {@code userSignInfoList} 而非单个 {@code userSign}。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignOutActiveSessionFilter implements StudyRoomAutoSignOutFilter {

    private final StudyRoomSignService signService;

    @Override
    public int getOrder() {
        return StudyRoomAutoSignOutFilterOrderEnum.SESSION.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoSignOutContext context) {
        StudyRoomUserReserveClientRespDTO reserveClientRespDTO = context.getExpiredReserveClientRespDTO();
        StudyRoomVenueClientRespDTO venueClientRespDTO = context.getVenueClientRespDTO();

        StudyRoomIndexClientReqDTO form = new StudyRoomIndexClientReqDTO();
        form.setCurrentLng(venueClientRespDTO.getVenueLng());
        form.setCurrentLat(venueClientRespDTO.getVenueLat());
        form.setVenueId(reserveClientRespDTO.getVenueId());
        StudyRoomIndexClientRespDTO indexClientRespDTO = signService.studyIndex(form, context.getUserId());
        CheckUtils.throwIfNull(indexClientRespDTO, "签到首页返回为空");

        List<StudyRoomUserSignClientRespDTO> userSignInfoList = indexClientRespDTO.getUserSignInfoList();
        if (!Objects.equals(indexClientRespDTO.getHasSeat(), 1) || Objects.isNull(userSignInfoList) || userSignInfoList.isEmpty()) {
            return FilterResultDTO.fail("当前无在座签到记录");
        }
        context.setIndexClientRespDTO(indexClientRespDTO);
        return FilterResultDTO.success(indexClientRespDTO);
    }
}
