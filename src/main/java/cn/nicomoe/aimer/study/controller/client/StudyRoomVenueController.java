package cn.nicomoe.aimer.study.controller.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAreaSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomReserveIndexQuery;
import cn.nicomoe.aimer.study.model.vo.StudyRoomAreaSelectVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomDouYinVenueSelectVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomReserveIndexVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomVenueSelectVO;
import cn.nicomoe.aimer.study.model.vo.StudyRoomVenueVO;
import cn.nicomoe.aimer.study.service.client.StudyRoomVenueService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

/**
 * 自习室门店域接口
 *
 * @author bixiu
 * @since 4.2.0
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/study/venue")
public class StudyRoomVenueController {

    private final StudyRoomVenueService venueService;

    /**
     * 按定位查询附近门店（签到首页、报修页用）
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/select")
    public R<StudyRoomVenueSelectVO> venueSelect(@RequestParam(value = "currentLng") Double currentLng, @RequestParam(value = "currentLat") Double currentLat) {
        StudyRoomVenueSelectClientRespDTO venueSelectDTO = venueService.venueSelect(currentLng, currentLat);
        StudyRoomVenueSelectVO venueSelectVO = MapstructUtils.convert(venueSelectDTO, StudyRoomVenueSelectVO.class);
        return R.ok(venueSelectVO);
    }

    /**
     * 预约可选门店列表（无定位参数）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/reserve/select")
    public R<StudyRoomVenueSelectVO> reserveVenueSelect() {
        StudyRoomVenueSelectClientRespDTO venueSelectDTO = venueService.reserveVenueSelect();
        StudyRoomVenueSelectVO venueSelectVO = MapstructUtils.convert(venueSelectDTO, StudyRoomVenueSelectVO.class);
        return R.ok(venueSelectVO);
    }

    /**
     * 门店详情
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/reserve/detail")
    public R<StudyRoomVenueVO> reserveVenueDetail(@RequestParam(value = "venueId") Integer venueId) {
        StudyRoomVenueClientRespDTO venueDTO = venueService.reserveVenueDetail(venueId);
        StudyRoomVenueVO venueVO = MapstructUtils.convert(venueDTO, StudyRoomVenueVO.class);
        return R.ok(venueVO);
    }

    /**
     * 预约首页（banner + 门店列表 + 区域筛选）
     *
     * @param query 查询条件
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/reserve/index")
    public R<StudyRoomReserveIndexVO> reserveIndex(StudyRoomReserveIndexQuery query) {
        StudyRoomReserveIndexClientRespDTO reserveIndexDTO = venueService.reserveIndex(query);
        StudyRoomReserveIndexVO reserveIndexVO = MapstructUtils.convert(reserveIndexDTO, StudyRoomReserveIndexVO.class);
        return R.ok(reserveIndexVO);
    }

    /**
     * 区域列表（预约首页筛选）
     *
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/reserve/area/select")
    public R<StudyRoomAreaSelectVO> reserveAreaSelect() {
        StudyRoomAreaSelectClientRespDTO areaSelectDTO = venueService.reserveAreaSelect();
        StudyRoomAreaSelectVO areaSelectVO = MapstructUtils.convert(areaSelectDTO, StudyRoomAreaSelectVO.class);
        return R.ok(areaSelectVO);
    }

    /**
     * 收银台门店列表（美团团购核销）
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/receipt/select")
    public R<StudyRoomVenueSelectVO> receiptVenueSelect(@RequestParam(value = "currentLng") Double currentLng, @RequestParam(value = "currentLat") Double currentLat) {
        StudyRoomVenueSelectClientRespDTO venueSelectDTO = venueService.receiptVenueSelect(currentLng, currentLat);
        StudyRoomVenueSelectVO venueSelectVO = MapstructUtils.convert(venueSelectDTO, StudyRoomVenueSelectVO.class);
        return R.ok(venueSelectVO);
    }

    /**
     * 抖音门店列表（比普通门店多 poiId / poiName）
     *
     * @param currentLng 当前经度
     * @param currentLat 当前纬度
     * @return 响应数据
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/douyin/select")
    public R<StudyRoomDouYinVenueSelectVO> douYinVenueSelect(@RequestParam(value = "currentLng") Double currentLng, @RequestParam(value = "currentLat") Double currentLat) {
        StudyRoomDouYinVenueSelectClientRespDTO venueSelectDTO = venueService.douYinVenueSelect(currentLng, currentLat);
        StudyRoomDouYinVenueSelectVO venueSelectVO = MapstructUtils.convert(venueSelectDTO, StudyRoomDouYinVenueSelectVO.class);
        return R.ok(venueSelectVO);
    }
}
