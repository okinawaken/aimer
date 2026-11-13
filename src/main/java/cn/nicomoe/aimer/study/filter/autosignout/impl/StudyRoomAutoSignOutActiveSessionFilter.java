package cn.nicomoe.aimer.study.filter.autosignout.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignOutFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosignout.StudyRoomAutoSignOutFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomSignService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 在座签到记录查询过滤器
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
        context.setIndexClientRespDTO(indexClientRespDTO);
        CheckUtils.throwIfNull(indexClientRespDTO, "签到首页返回为空");

        if (!Objects.equals(indexClientRespDTO.getHasSeat(), 1)
                || Objects.isNull(indexClientRespDTO.getUserSign())) {
            return FilterResultDTO.fail("当前无在座签到记录");
        }
        if (Objects.isNull(indexClientRespDTO.getSignInUserPackage())) {
            return FilterResultDTO.fail("未找到可用结算套餐");
        }
        return FilterResultDTO.success(indexClientRespDTO);
    }
}
