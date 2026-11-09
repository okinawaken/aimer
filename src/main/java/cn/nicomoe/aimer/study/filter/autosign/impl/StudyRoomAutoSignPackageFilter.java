package cn.nicomoe.aimer.study.filter.autosign.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosign.StudyRoomAutoSignFilter;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserReserveClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomSignService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 签到扣费套餐查询过滤器
 * <p>
 * 按待签到预约的门店查询签到首页，取 {@code signInUserPackage.signInUserPackageId} 作为签到扣费套餐。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignPackageFilter implements StudyRoomAutoSignFilter {

    private final StudyRoomSignService signService;

    @Override
    public int getOrder() {
        return StudyRoomAutoSignFilterOrderEnum.SIGN_IN_PACKAGE.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoSignContext context) {
        StudyRoomUserReserveClientRespDTO reserveClientRespDTO = context.getReserveClientRespDTO();
        StudyRoomVenueClientRespDTO venueClientRespDTO = context.getVenueClientRespDTO();

        StudyRoomIndexClientReqDTO form = new StudyRoomIndexClientReqDTO();
        form.setCurrentLng(venueClientRespDTO.getVenueLng());
        form.setCurrentLat(venueClientRespDTO.getVenueLat());
        form.setVenueId(reserveClientRespDTO.getVenueId());
        StudyRoomIndexClientRespDTO indexClientRespDTO = signService.studyIndex(form, context.getUserId());
        CheckUtils.throwIfNull(indexClientRespDTO, "签到首页返回为空");

        StudyRoomUserPackageClientRespDTO userPackageClientRespDTO = indexClientRespDTO.getSignInUserPackage();
        context.setUserPackageClientRespDTO(userPackageClientRespDTO);
        return FilterResultDTO.success(userPackageClientRespDTO);
    }
}
