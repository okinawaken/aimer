package cn.nicomoe.aimer.study.filter.autosignout.impl;

import cn.nicomoe.aimer.study.enums.StudyRoomAutoSignOutFilterOrderEnum;
import cn.nicomoe.aimer.study.filter.FilterResultDTO;
import cn.nicomoe.aimer.study.filter.autosignout.StudyRoomAutoSignOutFilter;
import cn.nicomoe.aimer.study.model.bo.StudyRoomAutoSignOutSessionBO;
import cn.nicomoe.aimer.study.model.context.StudyRoomAutoSignOutContext;
import cn.nicomoe.aimer.study.model.dto.StudyRoomIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlePackageClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomSettlePackageClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserPackageClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSignClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.service.client.StudyRoomSignService;
import cn.nicomoe.aimer.study.service.client.StudyRoomVenueService;
import cn.nicomoe.aimer.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 签退套餐解析过滤器
 * <p>
 * 为每条在座记录解析签退所需的扣费套餐与门店坐标，产出可直接执行的签退计划。
 * <ul>
 * <li>套餐取签到首页顶层的 {@code signInUserPackage}（上游真实返回），
 * 缺失时回退签退可选套餐中默认选中的一项。</li>
 * <li>场馆要求扫码签退（{@code qrCodeNeedSignOut=true}）的会话无法由定时任务完成，予以剔除并记录日志。</li>
 * </ul>
 *
 * @author bixiu
 * @since 4.2.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StudyRoomAutoSignOutSettlePackageFilter implements StudyRoomAutoSignOutFilter {

    private final StudyRoomSignService signService;
    private final StudyRoomVenueService venueService;

    @Override
    public int getOrder() {
        return StudyRoomAutoSignOutFilterOrderEnum.SETTLE_PACKAGE.getValue();
    }

    @Override
    public FilterResultDTO<?> doFilter(StudyRoomAutoSignOutContext context) {
        StudyRoomIndexClientRespDTO indexClientRespDTO = context.getIndexClientRespDTO();
        boolean needQrCode = Objects.equals(Boolean.TRUE.toString(), indexClientRespDTO.getQrCodeNeedSignOut());

        Map<Integer, StudyRoomVenueClientRespDTO> venueMap = new HashMap<>();
        List<StudyRoomAutoSignOutSessionBO> sessionBOList = new ArrayList<>();
        for (StudyRoomUserSignClientRespDTO userSignClientRespDTO : indexClientRespDTO.getUserSignInfoList()) {
            if (Objects.isNull(userSignClientRespDTO.getId())) {
                continue;
            }
            if (needQrCode) {
                log.info("自动签退跳过：场馆要求扫码签退，userSignId={}，venueId={}", userSignClientRespDTO.getId(), userSignClientRespDTO.getVenueId());
                continue;
            }
            // 在座记录未返回门店 ID 时，回退到触发本次执行的门店
            Integer venueId = Objects.nonNull(userSignClientRespDTO.getVenueId())
                    ? userSignClientRespDTO.getVenueId()
                    : context.getVenueClientRespDTO().getId();
            StudyRoomVenueClientRespDTO venueClientRespDTO = this.resolveVenue(venueMap, venueId, context.getUserId());
            if (Objects.isNull(venueClientRespDTO)) {
                log.warn("自动签退跳过：门店信息缺失，userSignId={}，venueId={}", userSignClientRespDTO.getId(), venueId);
                continue;
            }
            Long settleUserPackageId = this.resolveSettleUserPackageId(userSignClientRespDTO, indexClientRespDTO, context.getUserId(), venueClientRespDTO.getId());
            if (Objects.isNull(settleUserPackageId)) {
                log.warn("自动签退跳过：未找到可用结算套餐，userSignId={}", userSignClientRespDTO.getId());
                continue;
            }
            sessionBOList.add(this.buildSessionBO(userSignClientRespDTO, venueClientRespDTO, settleUserPackageId));
        }

        if (sessionBOList.isEmpty()) {
            return FilterResultDTO.fail("无可自动签退的在座记录");
        }
        context.setSessionBOList(sessionBOList);
        return FilterResultDTO.success(sessionBOList);
    }

    /**
     * 按门店 ID 解析门店坐标，带本地缓存。
     *
     * @param venueMap 门店缓存
     * @param venueId  门店 ID
     * @param userId   用户 ID
     * @return 门店信息，未找到时返回 {@code null}
     */
    private StudyRoomVenueClientRespDTO resolveVenue(Map<Integer, StudyRoomVenueClientRespDTO> venueMap, Integer venueId, Long userId) {
        if (Objects.isNull(venueId)) {
            return null;
        }
        // 不能用 computeIfAbsent：门店详情可能返回 null，会触发 NPE
        StudyRoomVenueClientRespDTO venueClientRespDTO = venueMap.get(venueId);
        if (Objects.nonNull(venueClientRespDTO)) {
            return venueClientRespDTO;
        }
        venueClientRespDTO = venueService.reserveVenueDetail(venueId, userId);
        if (Objects.nonNull(venueClientRespDTO)) {
            venueMap.put(venueId, venueClientRespDTO);
        }
        return venueClientRespDTO;
    }

    /**
     * 解析签退扣费套餐 ID
     * <p>
     * 上游的 {@code userSignInfoList} 元素不带套餐字段（小程序是在用户选完结算套餐后
     * 才自行往本地列表项上补 {@code signInUserPackageId}），故只能取签到首页顶层的
     * {@code signInUserPackage}；缺失时退回签退可选套餐的默认选中项。
     *
     * @param userSignClientRespDTO 在座签到记录
     * @param indexClientRespDTO    签到首页
     * @param userId                用户 ID
     * @param venueId               该记录所属门店 ID
     * @return 套餐 ID，无可用套餐时返回 {@code null}
     */
    private Long resolveSettleUserPackageId(StudyRoomUserSignClientRespDTO userSignClientRespDTO,
                                            StudyRoomIndexClientRespDTO indexClientRespDTO,
                                            Long userId,
                                            Integer venueId) {
        StudyRoomUserPackageClientRespDTO signInUserPackage = indexClientRespDTO.getSignInUserPackage();
        if (Objects.nonNull(signInUserPackage) && Objects.nonNull(signInUserPackage.getSignInUserPackageId())) {
            return signInUserPackage.getSignInUserPackageId();
        }
        return this.selectDefaultSettlePackage(userSignClientRespDTO.getId(), venueId, userId);
    }

    /**
     * 从签退可选套餐中取默认选中项（其次首个可扣费项）。
     *
     * @param userSignId 签到记录 ID
     * @param venueId    门店 ID
     * @param userId     用户 ID
     * @return 套餐 ID，无可用套餐时返回 {@code null}
     */
    private Long selectDefaultSettlePackage(Long userSignId, Integer venueId, Long userId) {
        if (Objects.isNull(venueId)) {
            return null;
        }
        StudyRoomSettlePackageClientReqDTO settlePackageReqDTO = new StudyRoomSettlePackageClientReqDTO();
        settlePackageReqDTO.setVenueId(venueId);
        settlePackageReqDTO.setUserSignId(userSignId);
        List<StudyRoomSettlePackageClientRespDTO> settlePackageList = signService.studySettlePackageSelect(settlePackageReqDTO, userId);
        if (Objects.isNull(settlePackageList) || settlePackageList.isEmpty()) {
            return null;
        }
        return settlePackageList.stream()
                .filter(pkg -> Objects.equals(pkg.getAutoSelected(), 1))
                .findFirst()
                .or(() -> settlePackageList.stream()
                        .filter(pkg -> Objects.equals(pkg.getCanUsePay(), 1))
                        .findFirst())
                .map(StudyRoomSettlePackageClientRespDTO::getId)
                .orElse(null);
    }

    /**
     * 组装单条签退执行计划。
     *
     * @param userSignClientRespDTO 在座签到记录
     * @param venueClientRespDTO    所属门店
     * @param settleUserPackageId   扣费套餐 ID
     * @return 签退执行计划
     */
    private StudyRoomAutoSignOutSessionBO buildSessionBO(StudyRoomUserSignClientRespDTO userSignClientRespDTO,
                                                         StudyRoomVenueClientRespDTO venueClientRespDTO,
                                                         Long settleUserPackageId) {
        CheckUtils.throwIfNull(venueClientRespDTO.getVenueLng(), "门店经度为空，无法自动签退");
        CheckUtils.throwIfNull(venueClientRespDTO.getVenueLat(), "门店纬度为空，无法自动签退");

        StudyRoomAutoSignOutSessionBO sessionBO = new StudyRoomAutoSignOutSessionBO();
        sessionBO.setUserSignId(userSignClientRespDTO.getId());
        sessionBO.setSettleUserPackageId(settleUserPackageId);
        sessionBO.setCurrentLng(venueClientRespDTO.getVenueLng());
        sessionBO.setCurrentLat(venueClientRespDTO.getVenueLat());
        sessionBO.setVenueName(venueClientRespDTO.getVenueName());
        if (Objects.nonNull(userSignClientRespDTO.getSeatInfo())) {
            sessionBO.setSeatNumber(userSignClientRespDTO.getSeatInfo().getSeatNumber());
        }
        return sessionBO;
    }
}
