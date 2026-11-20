package cn.nicomoe.aimer.aliyu.controller.client;

import cn.nicomoe.aimer.aliyu.model.dto.MemberCouponListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberCouponRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsExchangeDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsExchangeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallCurrentStoreReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallCurrentStoreRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallMemberCouponListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPointsExchangeDetailReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPointsExchangeListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPrizeExchangeReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPrizeStoreReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPrizeStoreRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallWallpaperExchangeReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallWallpaperListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PrizeCheckRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PrizeDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PrizeExchangeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PrizeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperCheckRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperDownloadRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperDressHomeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperExchangeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperSeriesRespDTO;
import cn.nicomoe.aimer.aliyu.model.vo.MemberCouponListVO;
import cn.nicomoe.aimer.aliyu.model.vo.MemberCouponVO;
import cn.nicomoe.aimer.aliyu.model.vo.PointsExchangeDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.PointsExchangeListVO;
import cn.nicomoe.aimer.aliyu.model.vo.PointsMallCurrentStoreVO;
import cn.nicomoe.aimer.aliyu.model.vo.PointsMallPrizeStoreVO;
import cn.nicomoe.aimer.aliyu.model.vo.PrizeCheckVO;
import cn.nicomoe.aimer.aliyu.model.vo.PrizeDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.PrizeExchangeVO;
import cn.nicomoe.aimer.aliyu.model.vo.PrizeListVO;
import cn.nicomoe.aimer.aliyu.model.vo.WallpaperCheckVO;
import cn.nicomoe.aimer.aliyu.model.vo.WallpaperDetailVO;
import cn.nicomoe.aimer.aliyu.model.vo.WallpaperDownloadVO;
import cn.nicomoe.aimer.aliyu.model.vo.WallpaperDressHomeVO;
import cn.nicomoe.aimer.aliyu.model.vo.WallpaperExchangeVO;
import cn.nicomoe.aimer.aliyu.model.vo.WallpaperListVO;
import cn.nicomoe.aimer.aliyu.model.vo.WallpaperSeriesVO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuPointsMallService;
import cn.nicomoe.aimer.util.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.erupt.core.annotation.EruptRouter;
import xyz.erupt.core.constant.EruptRestPath;
import xyz.erupt.core.view.R;

import java.util.List;

/**
 * 阿里鱼积分商城域接口：奖品、壁纸、会员券与积分兑换订单。
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = EruptRestPath.ERUPT_API + "/aliyu/points-mall")
public class AliyuPointsMallController {

    private final AliyuPointsMallService pointsMallService;

    /**
     * 当前门店。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/current-store")
    public R<List<PointsMallCurrentStoreVO>> currentStore(PointsMallCurrentStoreReqDTO query) {
        List<PointsMallCurrentStoreRespDTO> currentStoreListDTO = pointsMallService.currentStore(query);
        List<PointsMallCurrentStoreVO> currentStoreListVO = MapstructUtils.convert(currentStoreListDTO, PointsMallCurrentStoreVO.class);
        return R.ok(currentStoreListVO);
    }

    /**
     * 奖品列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/prize/list")
    public R<PrizeListVO> prizeList(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "cardId", required = false) String cardId,
            @RequestParam(value = "storeId", required = false) String storeId) {
        PrizeListRespDTO prizeListDTO = pointsMallService.prizeList(pageNo, pageSize, cardId, storeId);
        PrizeListVO prizeListVO = MapstructUtils.convert(prizeListDTO, PrizeListVO.class);
        return R.ok(prizeListVO);
    }

    /**
     * 奖品详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/prize/detail")
    public R<PrizeDetailVO> prizeDetail(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "cardId", required = false) String cardId,
            @RequestParam(value = "storeId", required = false) String storeId) {
        PrizeDetailRespDTO prizeDetailDTO = pointsMallService.prizeDetail(id, cardId, storeId);
        PrizeDetailVO prizeDetailVO = MapstructUtils.convert(prizeDetailDTO, PrizeDetailVO.class);
        return R.ok(prizeDetailVO);
    }

    /**
     * 奖品兑换校验。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/prize/check")
    public R<PrizeCheckVO> prizeCheck(
            @RequestParam(value = "pointsPrizeId") String pointsPrizeId,
            @RequestParam(value = "cardId", required = false) String cardId,
            @RequestParam(value = "amount", required = false) Integer amount,
            @RequestParam(value = "storeId", required = false) String storeId) {
        PrizeCheckRespDTO prizeCheckDTO = pointsMallService.prizeCheck(pointsPrizeId, cardId, amount, storeId);
        PrizeCheckVO prizeCheckVO = MapstructUtils.convert(prizeCheckDTO, PrizeCheckVO.class);
        return R.ok(prizeCheckVO);
    }

    /**
     * 奖品可用门店。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/prize/store")
    public R<List<PointsMallPrizeStoreVO>> prizeStore(PointsMallPrizeStoreReqDTO query) {
        List<PointsMallPrizeStoreRespDTO> prizeStoreListDTO = pointsMallService.prizeStore(query);
        List<PointsMallPrizeStoreVO> prizeStoreListVO = MapstructUtils.convert(prizeStoreListDTO, PointsMallPrizeStoreVO.class);
        return R.ok(prizeStoreListVO);
    }

    /**
     * 奖品兑换（消耗小鱼丸）。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/prize/exchange")
    public R<PrizeExchangeVO> prizeExchange(@RequestBody PointsMallPrizeExchangeReqDTO form) {
        PrizeExchangeRespDTO prizeExchangeDTO = pointsMallService.prizeExchange(form);
        PrizeExchangeVO prizeExchangeVO = MapstructUtils.convert(prizeExchangeDTO, PrizeExchangeVO.class);
        return R.ok(prizeExchangeVO);
    }

    /**
     * 壁纸列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/wallpaper/list")
    public R<WallpaperListVO> wallpaperList(PointsMallWallpaperListReqDTO query) {
        WallpaperListRespDTO wallpaperListDTO = pointsMallService.wallpaperList(query);
        WallpaperListVO wallpaperListVO = MapstructUtils.convert(wallpaperListDTO, WallpaperListVO.class);
        return R.ok(wallpaperListVO);
    }

    /**
     * 壁纸详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/wallpaper/detail")
    public R<WallpaperDetailVO> wallpaperDetail(
            @RequestParam(value = "pointsPrizeId") String pointsPrizeId) {
        WallpaperDetailRespDTO wallpaperDetailDTO = pointsMallService.wallpaperDetail(pointsPrizeId);
        WallpaperDetailVO wallpaperDetailVO = MapstructUtils.convert(wallpaperDetailDTO, WallpaperDetailVO.class);
        return R.ok(wallpaperDetailVO);
    }

    /**
     * 壁纸系列详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/wallpaper/series-detail")
    public R<WallpaperSeriesVO> wallpaperSeriesDetail(
            @RequestParam(value = "seriesId") String seriesId) {
        WallpaperSeriesRespDTO wallpaperSeriesDTO = pointsMallService.wallpaperSeriesDetail(seriesId);
        WallpaperSeriesVO wallpaperSeriesVO = MapstructUtils.convert(wallpaperSeriesDTO, WallpaperSeriesVO.class);
        return R.ok(wallpaperSeriesVO);
    }

    /**
     * 壁纸兑换校验。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/wallpaper/check")
    public R<WallpaperCheckVO> wallpaperCheck(
            @RequestParam(value = "pointsPrizeId") String pointsPrizeId,
            @RequestParam(value = "cardId", required = false) String cardId,
            @RequestParam(value = "amount", required = false) Integer amount,
            @RequestParam(value = "storeId", required = false) String storeId) {
        WallpaperCheckRespDTO wallpaperCheckDTO = pointsMallService.wallpaperCheck(pointsPrizeId, cardId, amount, storeId);
        WallpaperCheckVO wallpaperCheckVO = MapstructUtils.convert(wallpaperCheckDTO, WallpaperCheckVO.class);
        return R.ok(wallpaperCheckVO);
    }

    /**
     * 壁纸下载。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/wallpaper/download")
    public R<WallpaperDownloadVO> wallpaperDownload(
            @RequestParam(value = "materialId") String materialId) {
        WallpaperDownloadRespDTO wallpaperDownloadDTO = pointsMallService.wallpaperDownload(materialId);
        WallpaperDownloadVO wallpaperDownloadVO = MapstructUtils.convert(wallpaperDownloadDTO, WallpaperDownloadVO.class);
        return R.ok(wallpaperDownloadVO);
    }

    /**
     * 壁纸装扮首页。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/wallpaper/dress-home")
    public R<WallpaperDressHomeVO> wallpaperDressHome(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        WallpaperDressHomeRespDTO wallpaperDressHomeDTO = pointsMallService.wallpaperDressHome(pageNo, pageSize);
        WallpaperDressHomeVO wallpaperDressHomeVO = MapstructUtils.convert(wallpaperDressHomeDTO, WallpaperDressHomeVO.class);
        return R.ok(wallpaperDressHomeVO);
    }

    /**
     * 壁纸兑换。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/wallpaper/exchange")
    public R<WallpaperExchangeVO> wallpaperExchange(@RequestBody PointsMallWallpaperExchangeReqDTO form) {
        WallpaperExchangeRespDTO wallpaperExchangeDTO = pointsMallService.wallpaperExchange(form);
        WallpaperExchangeVO wallpaperExchangeVO = MapstructUtils.convert(wallpaperExchangeDTO, WallpaperExchangeVO.class);
        return R.ok(wallpaperExchangeVO);
    }

    /**
     * 会员券详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/member-coupon/detail")
    public R<MemberCouponVO> memberCouponDetail(
            @RequestParam(value = "memberCouponId") String memberCouponId) {
        MemberCouponRespDTO memberCouponDTO = pointsMallService.memberCouponDetail(memberCouponId);
        MemberCouponVO memberCouponVO = MapstructUtils.convert(memberCouponDTO, MemberCouponVO.class);
        return R.ok(memberCouponVO);
    }

    /**
     * 会员券列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/member-coupon/list")
    public R<MemberCouponListVO> memberCouponList(@RequestBody PointsMallMemberCouponListReqDTO form) {
        MemberCouponListRespDTO memberCouponListDTO = pointsMallService.memberCouponList(form);
        MemberCouponListVO memberCouponListVO = MapstructUtils.convert(memberCouponListDTO, MemberCouponListVO.class);
        return R.ok(memberCouponListVO);
    }

    /**
     * 积分兑换订单详情。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @GetMapping(value = "/points-exchange/detail")
    public R<PointsExchangeDetailVO> pointsExchangeDetail(PointsMallPointsExchangeDetailReqDTO query) {
        PointsExchangeDetailRespDTO pointsExchangeDetailDTO = pointsMallService.pointsExchangeDetail(query);
        PointsExchangeDetailVO pointsExchangeDetailVO = MapstructUtils.convert(pointsExchangeDetailDTO, PointsExchangeDetailVO.class);
        return R.ok(pointsExchangeDetailVO);
    }

    /**
     * 积分兑换订单列表。
     */
    @EruptRouter(verifyType = EruptRouter.VerifyType.LOGIN)
    @PostMapping(value = "/points-exchange/list")
    public R<PointsExchangeListVO> pointsExchangeList(@RequestBody PointsMallPointsExchangeListReqDTO form) {
        PointsExchangeListRespDTO pointsExchangeListDTO = pointsMallService.pointsExchangeList(form);
        PointsExchangeListVO pointsExchangeListVO = MapstructUtils.convert(pointsExchangeListDTO, PointsExchangeListVO.class);
        return R.ok(pointsExchangeListVO);
    }
}
